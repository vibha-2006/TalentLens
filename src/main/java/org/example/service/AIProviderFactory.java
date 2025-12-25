package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Factory class to get the appropriate AI service based on configuration
 */
@Component
public class AIProviderFactory {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private GroqService groqService;

    @Value("${ai.provider:openai}")
    private String defaultProvider;

    /**
     * Get AI service based on default configuration
     */
    public AIService getAIService() {
        return getAIService(defaultProvider);
    }

    /**
     * Get AI service based on provider name
     * @param provider "openai", "gemini", or "groq"
     */
    public AIService getAIService(String provider) {
        if (provider == null || provider.trim().isEmpty()) {
            provider = defaultProvider;
        }

        String normalizedProvider = provider.toLowerCase().trim();

        return switch (normalizedProvider) {
            case "gemini" -> geminiService;
            case "openai" -> openAIService;
            case "groq" -> groqService;
            default -> {
                System.out.println("WARNING: Unknown AI provider '" + provider +
                        "', defaulting to: " + defaultProvider);
                yield defaultProvider.equalsIgnoreCase("gemini") ? geminiService :
                      defaultProvider.equalsIgnoreCase("groq") ? groqService : openAIService;
            }
        };
    }

    /**
     * Check if a provider is available
     */
    public boolean isProviderAvailable(String provider) {
        try {
            AIService service = getAIService(provider);
            return service != null;
        } catch (Exception e) {
            return false;
        }
    }
}

