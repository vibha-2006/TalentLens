package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.dto.AIAnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiService implements AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model:gemini-1.5-flash}")
    private String model;

    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models}")
    private String apiUrl;

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public GeminiService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public AIAnalysisResponse analyzeResume(String resumeText, String jobRequirements) {
        String prompt = buildAnalysisPrompt(resumeText, jobRequirements);
        String response = callGeminiAPI(prompt);
        return parseGeminiResponse(response);
    }

    @Override
    public String getProviderName() {
        return "Gemini";
    }

    private String buildAnalysisPrompt(String resumeText, String jobRequirements) {
        return String.format("""
                Analyze the following resume against the job requirements and provide a detailed assessment.
                
                JOB REQUIREMENTS:
                %s
                
                RESUME:
                %s
                
                Please provide your analysis in the following JSON format:
                {
                  "candidateName": "extracted candidate name",
                  "email": "extracted email address",
                  "phone": "extracted phone number",
                  "matchScore": numerical score from 0 to 100,
                  "extractedSkills": "comma-separated list of skills found in resume",
                  "extractedExperience": "brief summary of experience",
                  "analysis": "detailed analysis of strengths, weaknesses, and overall fit"
                }
                
                Consider the following in your analysis:
                1. Skills match (technical and soft skills)
                2. Experience level and relevance
                3. Education background
                4. Projects and achievements
                5. Overall cultural and role fit
                
                Provide a match score from 0-100 where:
                - 90-100: Excellent match
                - 75-89: Good match
                - 60-74: Fair match
                - Below 60: Poor match
                
                Return ONLY the JSON object, no additional text.
                """, jobRequirements, resumeText);
    }

    private String callGeminiAPI(String prompt) {
        try {
            // Build the Gemini API URL with the model and API key
            String fullUrl = String.format("%s/%s:generateContent?key=%s",
                    apiUrl, model, apiKey);

            // Build the JSON request body for Gemini API
            String requestBody = String.format("""
                {
                  "contents": [{
                    "parts": [{
                      "text": %s
                    }]
                  }],
                  "generationConfig": {
                    "temperature": 0.7,
                    "maxOutputTokens": 2000
                  }
                }
                """, objectMapper.writeValueAsString(prompt));

            RequestBody body = RequestBody.create(
                    requestBody,
                    MediaType.parse("application/json")
            );

            System.out.println("DEBUG: Calling Gemini API at: " + fullUrl.replace(apiKey, "***"));
            System.out.println("DEBUG: Using model: " + model);

            Request request = new Request.Builder()
                    .url(fullUrl)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "No error details";
                    System.err.println("ERROR: Gemini API failed with status " + response.code());
                    System.err.println("ERROR: Response message: " + response.message());
                    System.err.println("ERROR: Response body: " + errorBody);

                    if (response.code() == 401 || response.code() == 403) {
                        throw new RuntimeException("Gemini API authentication failed (" + response.code() + "). Please verify:\n" +
                                "1. API key is valid and active\n" +
                                "2. API key is properly formatted\n" +
                                "3. Generative Language API is enabled in Google Cloud Console\n" +
                                "Error details: " + errorBody);
                    }

                    if (response.code() == 404) {
                        throw new RuntimeException("Gemini API endpoint not found (404). Please verify:\n" +
                                "1. API key is valid and active\n" +
                                "2. Generative Language API is enabled in Google Cloud Console\n" +
                                "3. Model name is correct (try gemini-1.5-flash or gemini-pro)\n" +
                                "Error details: " + errorBody);
                    }

                    if (response.code() == 429) {
                        throw new RuntimeException("Gemini API rate limit exceeded (429). Please:\n" +
                                "1. Check your API usage quota\n" +
                                "2. Wait before retrying\n" +
                                "Error details: " + errorBody);
                    }

                    throw new RuntimeException("Gemini API call failed: " + response.code() + " - " +
                            response.message() + "\nDetails: " + errorBody);
                }

                String responseBody = response.body().string();
                System.out.println("DEBUG: Gemini Response received");

                // Parse the Gemini response to extract the content
                JsonNode jsonResponse = objectMapper.readTree(responseBody);
                JsonNode candidates = jsonResponse.get("candidates");

                if (candidates != null && candidates.size() > 0) {
                    JsonNode content = candidates.get(0).get("content");
                    if (content != null && content.has("parts")) {
                        JsonNode parts = content.get("parts");
                        if (parts.size() > 0 && parts.get(0).has("text")) {
                            return parts.get(0).get("text").asText();
                        }
                    }
                }

                throw new RuntimeException("Invalid response structure from Gemini API");
            }
        } catch (IOException e) {
            System.err.println("ERROR: Gemini API call failed: " + e.getMessage());
            throw new RuntimeException("Gemini API call failed: " + e.getMessage(), e);
        }
    }

    private AIAnalysisResponse parseGeminiResponse(String apiResponse) {
        try {
            // Try to extract JSON from the response
            String jsonContent = extractJsonFromResponse(apiResponse);

            if (jsonContent != null) {
                JsonNode jsonNode = objectMapper.readTree(jsonContent);

                return new AIAnalysisResponse(
                    jsonNode.has("matchScore") ? jsonNode.get("matchScore").asDouble() : 50.0,
                    jsonNode.has("analysis") ? jsonNode.get("analysis").asText() : "No analysis available",
                    jsonNode.has("extractedSkills") ? jsonNode.get("extractedSkills").asText() : "",
                    jsonNode.has("extractedExperience") ? jsonNode.get("extractedExperience").asText() : "",
                    jsonNode.has("candidateName") ? jsonNode.get("candidateName").asText() : "",
                    jsonNode.has("email") ? jsonNode.get("email").asText() : "",
                    jsonNode.has("phone") ? jsonNode.get("phone").asText() : ""
                );
            }
        } catch (Exception e) {
            System.err.println("Error parsing Gemini response: " + e.getMessage());
        }

        // Return default response if parsing fails
        return new AIAnalysisResponse(50.0, apiResponse, "", "", "", "", "");
    }

    private String extractJsonFromResponse(String text) {
        // Try to find JSON in the response
        Pattern jsonPattern = Pattern.compile("\\{[^{}]*(?:\\{[^{}]*}[^{}]*)*}", Pattern.DOTALL);
        Matcher matcher = jsonPattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }
}

