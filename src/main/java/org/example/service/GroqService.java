package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.dto.AIAnalysisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class GroqService implements AIService {

    @Autowired
    private AISettingsService aiSettingsService;

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public GroqService() {
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
        String response = callGroqAPI(prompt);
        return parseGroqResponse(response);
    }

    @Override
    public String getProviderName() {
        return "Groq";
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

    private String callGroqAPI(String prompt) {
        try {
            // Get current settings dynamically
            String apiKey = aiSettingsService.getGroqApiKey();
            String model = aiSettingsService.getGroqModel();
            String apiUrl = aiSettingsService.getGroqApiUrl();

            // Build the JSON request body (Groq uses OpenAI-compatible API)
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

            System.out.println("DEBUG: Calling Groq API at: " + apiUrl);
            System.out.println("DEBUG: Using model: " + model);
            System.out.println("DEBUG: API key loaded: " + (apiKey != null && !apiKey.isEmpty() ?
                (apiKey.startsWith("gsk_") ? "Yes (starts with gsk_)" : "Yes (but format may be incorrect)") : "No (empty or null)"));
            System.out.println("DEBUG: API key length: " + (apiKey != null ? apiKey.length() : 0));

            // Validate API key
            if (apiKey == null || apiKey.isEmpty() || apiKey.equals("your_groq_api_key_here")) {
                throw new RuntimeException("Groq API key is not configured. Please:\n" +
                        "1. Get your API key from https://console.groq.com/keys\n" +
                        "2. Update it in Admin Settings or set GROQ_API_KEY environment variable\n" +
                        "3. Ensure the key starts with 'gsk_'");
            }

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "No error details";
                    System.err.println("ERROR: Groq API failed with status " + response.code());
                    System.err.println("ERROR: Response message: " + response.message());
                    System.err.println("ERROR: Response body: " + errorBody);

                    if (response.code() == 401 || response.code() == 403) {
                        throw new RuntimeException("Groq API authentication failed (" + response.code() + "). Please verify:\n" +
                                "1. API key is valid and active\n" +
                                "2. API key is properly formatted (starts with 'gsk_')\n" +
                                "3. Get your API key from https://console.groq.com/keys\n" +
                                "Error details: " + errorBody);
                    }

                    if (response.code() == 429) {
                        throw new RuntimeException("Groq API rate limit exceeded (429). Please:\n" +
                                "1. Check your API usage quota\n" +
                                "2. Wait before retrying\n" +
                                "Error details: " + errorBody);
                    }

                    if (response.code() == 400) {
                        throw new RuntimeException("Groq API bad request (400). Please verify:\n" +
                                "1. Request format is correct\n" +
                                "2. Model name is valid (e.g., llama-3.1-70b-versatile, mixtral-8x7b-32768)\n" +
                                "Error details: " + errorBody);
                    }

                    throw new RuntimeException("Groq API call failed: " + response.code() + " - " + errorBody);
                }

                String responseBody = response.body().string();
                System.out.println("DEBUG: Groq API response received successfully");
                return responseBody;
            }

        } catch (IOException e) {
            System.err.println("ERROR: IOException while calling Groq API: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to call Groq API: " + e.getMessage(), e);
        }
    }

    private AIAnalysisResponse parseGroqResponse(String responseJson) {
        try {
            System.out.println("DEBUG: Parsing Groq response");

            JsonNode root = objectMapper.readTree(responseJson);
            JsonNode choices = root.get("choices");

            if (choices == null || choices.isEmpty()) {
                throw new RuntimeException("No choices in Groq response");
            }

            String content = choices.get(0).get("message").get("content").asText();
            System.out.println("DEBUG: Extracted content from Groq response");

            // Clean up the content - remove markdown code blocks if present
            content = content.replaceAll("```json\\s*", "").replaceAll("```\\s*", "").trim();

            // Parse the JSON content
            JsonNode analysisJson = objectMapper.readTree(content);

            AIAnalysisResponse response = new AIAnalysisResponse();
            response.setCandidateName(getStringValue(analysisJson, "candidateName", "Unknown"));
            response.setEmail(getStringValue(analysisJson, "email", "Not provided"));
            response.setPhone(getStringValue(analysisJson, "phone", "Not provided"));
            response.setMatchScore(getDoubleValue(analysisJson, "matchScore", 0.0));
            response.setExtractedSkills(getStringValue(analysisJson, "extractedSkills", ""));
            response.setExtractedExperience(getStringValue(analysisJson, "extractedExperience", ""));
            response.setAnalysis(getStringValue(analysisJson, "analysis", ""));

            System.out.println("DEBUG: Successfully parsed analysis response");
            return response;

        } catch (Exception e) {
            System.err.println("ERROR: Failed to parse Groq response: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to parse Groq response: " + e.getMessage(), e);
        }
    }

    private String getStringValue(JsonNode node, String fieldName, String defaultValue) {
        JsonNode fieldNode = node.get(fieldName);
        if (fieldNode != null && !fieldNode.isNull()) {
            return fieldNode.asText(defaultValue);
        }
        return defaultValue;
    }

    private Double getDoubleValue(JsonNode node, String fieldName, double defaultValue) {
        JsonNode fieldNode = node.get(fieldName);
        if (fieldNode != null && !fieldNode.isNull()) {
            if (fieldNode.isNumber()) {
                return fieldNode.asDouble(defaultValue);
            } else if (fieldNode.isTextual()) {
                try {
                    return Double.parseDouble(fieldNode.asText());
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }
}

