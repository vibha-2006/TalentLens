package org.example.service;

import org.example.dto.AIAnalysisResponse;

/**
 * Interface for AI service providers (OpenAI, Gemini, etc.)
 */
public interface AIService {

    /**
     * Analyze a resume against job requirements
     * @param resumeText The extracted resume text
     * @param jobRequirements The job requirements
     * @return Analysis response with match score and details
     */
    AIAnalysisResponse analyzeResume(String resumeText, String jobRequirements);

    /**
     * Get the name of the AI provider
     * @return Provider name (e.g., "OpenAI", "Gemini")
     */
    String getProviderName();
}

