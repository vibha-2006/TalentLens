package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.dto.AIAnalysisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OpenAIService implements AIService {

    @Autowired
    private AISettingsService aiSettingsService;

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public OpenAIService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public AIAnalysisResponse analyzeResume(String resumeText, String jobRequirements) {
        String prompt = buildAnalysisPrompt(resumeText, jobRequirements);
        String response = callOpenAIAPI(prompt);
        return parseOpenAIResponse(response);
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

    private String callOpenAIAPI(String prompt) {
        try {
            // Get current settings dynamically
            String apiKey = aiSettingsService.getOpenAiApiKey();
            String model = aiSettingsService.getOpenAiModel();
            String apiUrl = "https://api.openai.com/v1/chat/completions";

            // Build the JSON request body
            String requestBody = String.format("""
                {
                  "model": "%s",
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are an expert HR analyst specializing in resume evaluation and candidate matching."
                    },
                    {
                      "role": "user",
                      "content": %s
                    }
                  ],
                  "temperature": 0.7,
                  "max_tokens": 2000
                }
                """, model, objectMapper.writeValueAsString(prompt));

            RequestBody body = RequestBody.create(
                    requestBody,
                    MediaType.parse("application/json")
            );

            System.out.println("DEBUG: Calling OpenAI API at: " + apiUrl);
            System.out.println("DEBUG: Using model: " + model);

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "No error details";
                    System.err.println("ERROR: OpenAI API failed with status " + response.code());
                    System.err.println("ERROR: Response message: " + response.message());
                    System.err.println("ERROR: Response body: " + errorBody);

                    if (response.code() == 401) {
                        throw new RuntimeException("OpenAI API authentication failed (401). Please verify:\n" +
                                "1. API key is valid and active\n" +
                                "2. API key is properly formatted\n" +
                                "Error details: " + errorBody);
                    }

                    if (response.code() == 429) {
                        throw new RuntimeException("OpenAI API rate limit exceeded (429). Please:\n" +
                                "1. Check your API usage quota\n" +
                                "2. Wait before retrying\n" +
                                "Error details: " + errorBody);
                    }

                    throw new RuntimeException("OpenAI API call failed: " + response.code() + " - " +
                            response.message() + "\nDetails: " + errorBody);
                }

                String responseBody = response.body().string();
                System.out.println("DEBUG: OpenAI Response received");

                // Parse the response to extract the content
                JsonNode jsonResponse = objectMapper.readTree(responseBody);
                JsonNode choices = jsonResponse.get("choices");

                if (choices != null && choices.size() > 0) {
                    JsonNode message = choices.get(0).get("message");
                    if (message != null && message.has("content")) {
                        return message.get("content").asText();
                    }
                }

                throw new RuntimeException("Invalid response structure from OpenAI API");
            }
        } catch (IOException e) {
            System.err.println("ERROR: OpenAI API call failed: " + e.getMessage());
            throw new RuntimeException("OpenAI API call failed: " + e.getMessage(), e);
        }
    }

    private AIAnalysisResponse parseOpenAIResponse(String apiResponse) {
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
            System.err.println("Error parsing OpenAI response: " + e.getMessage());
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

        // If no JSON found, try to parse the entire text as JSON
        if (text.trim().startsWith("{") && text.trim().endsWith("}")) {
            return text.trim();
        }

        return null;
    }

    @Override
    public String getProviderName() {
        return "OpenAI";
    }
}

