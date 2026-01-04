package org.example.service;

import org.example.dto.AISettingsDTO;
import org.example.dto.AllAISettingsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AISettingsService {

    private final ConfigurableEnvironment environment;

    // Runtime storage for settings (survives in memory)
    private final Map<String, String> runtimeSettings = new HashMap<>();

    public AISettingsService(ConfigurableEnvironment environment) {
        this.environment = environment;
        initializeRuntimeSettings();
    }

    private void initializeRuntimeSettings() {
        // Initialize from environment or properties
        runtimeSettings.put("openai.api.key", getProperty("openai.api.key", ""));
        runtimeSettings.put("openai.model", getProperty("openai.model", "gpt-3.5-turbo"));
        runtimeSettings.put("gemini.api.key", getProperty("gemini.api.key", ""));
        runtimeSettings.put("gemini.model", getProperty("gemini.model", "gemini-1.5-flash"));
        runtimeSettings.put("gemini.api.url", getProperty("gemini.api.url", "https://generativelanguage.googleapis.com/v1beta/models"));
        runtimeSettings.put("groq.api.key", getProperty("groq.api.key", ""));
        runtimeSettings.put("groq.model", getProperty("groq.model", "llama-3.3-70b-versatile"));
        runtimeSettings.put("groq.api.url", getProperty("groq.api.url", "https://api.groq.com/openai/v1/chat/completions"));
    }

    private String getProperty(String key, String defaultValue) {
        // First check runtime settings, then environment, then default
        String value = runtimeSettings.get(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        return environment.getProperty(key, defaultValue);
    }

    // Public getters for API keys and settings
    public String getOpenAiApiKey() {
        return getProperty("openai.api.key", "");
    }

    public String getOpenAiModel() {
        return getProperty("openai.model", "gpt-3.5-turbo");
    }

    public String getGeminiApiKey() {
        return getProperty("gemini.api.key", "");
    }

    public String getGeminiModel() {
        return getProperty("gemini.model", "gemini-1.5-flash");
    }

    public String getGeminiApiUrl() {
        return getProperty("gemini.api.url", "https://generativelanguage.googleapis.com/v1beta/models");
    }

    public String getGroqApiKey() {
        return getProperty("groq.api.key", "");
    }

    public String getGroqModel() {
        return getProperty("groq.model", "llama-3.3-70b-versatile");
    }

    public String getGroqApiUrl() {
        return getProperty("groq.api.url", "https://api.groq.com/openai/v1/chat/completions");
    }

    public AllAISettingsDTO getAllSettings() {
        AllAISettingsDTO allSettings = new AllAISettingsDTO();

        // OpenAI settings
        AISettingsDTO openaiSettings = new AISettingsDTO();
        openaiSettings.setProvider("openai");
        openaiSettings.setApiKey(maskApiKey(getOpenAiApiKey()));
        openaiSettings.setModel(getOpenAiModel());
        openaiSettings.setApiUrl("https://api.openai.com/v1/chat/completions");
        openaiSettings.setEnabled(!getOpenAiApiKey().isEmpty());

        // Gemini settings
        AISettingsDTO geminiSettings = new AISettingsDTO();
        geminiSettings.setProvider("gemini");
        geminiSettings.setApiKey(maskApiKey(getGeminiApiKey()));
        geminiSettings.setModel(getGeminiModel());
        geminiSettings.setApiUrl(getGeminiApiUrl());
        geminiSettings.setEnabled(!getGeminiApiKey().isEmpty());

        // Groq settings
        AISettingsDTO groqSettings = new AISettingsDTO();
        groqSettings.setProvider("groq");
        groqSettings.setApiKey(maskApiKey(getGroqApiKey()));
        groqSettings.setModel(getGroqModel());
        groqSettings.setApiUrl(getGroqApiUrl());
        groqSettings.setEnabled(!getGroqApiKey().isEmpty());

        allSettings.setOpenai(openaiSettings);
        allSettings.setGemini(geminiSettings);
        allSettings.setGroq(groqSettings);

        return allSettings;
    }

    public void updateSettings(AllAISettingsDTO settings) throws IOException {
        // Update OpenAI settings
        if (settings.getOpenai() != null) {
            AISettingsDTO openai = settings.getOpenai();
            if (openai.getApiKey() != null && !openai.getApiKey().contains("***")) {
                runtimeSettings.put("openai.api.key", openai.getApiKey());
            }
            if (openai.getModel() != null && !openai.getModel().isEmpty()) {
                runtimeSettings.put("openai.model", openai.getModel());
            }
        }

        // Update Gemini settings
        if (settings.getGemini() != null) {
            AISettingsDTO gemini = settings.getGemini();
            if (gemini.getApiKey() != null && !gemini.getApiKey().contains("***")) {
                runtimeSettings.put("gemini.api.key", gemini.getApiKey());
            }
            if (gemini.getModel() != null && !gemini.getModel().isEmpty()) {
                runtimeSettings.put("gemini.model", gemini.getModel());
            }
            if (gemini.getApiUrl() != null && !gemini.getApiUrl().isEmpty()) {
                runtimeSettings.put("gemini.api.url", gemini.getApiUrl());
            }
        }

        // Update Groq settings
        if (settings.getGroq() != null) {
            AISettingsDTO groq = settings.getGroq();
            if (groq.getApiKey() != null && !groq.getApiKey().contains("***")) {
                runtimeSettings.put("groq.api.key", groq.getApiKey());
            }
            if (groq.getModel() != null && !groq.getModel().isEmpty()) {
                runtimeSettings.put("groq.model", groq.getModel());
            }
            if (groq.getApiUrl() != null && !groq.getApiUrl().isEmpty()) {
                runtimeSettings.put("groq.api.url", groq.getApiUrl());
            }
        }

        // Note: Settings are stored in memory only
        // For production, set environment variables in your deployment platform (Render)
        // The runtime settings will persist as long as the application is running
    }

    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "";
        }
        if (apiKey.length() <= 8) {
            return "***";
        }
        return apiKey.substring(0, 4) + "***" + apiKey.substring(apiKey.length() - 4);
    }

    public boolean testConnection(String provider) {
        // This method can be enhanced to actually test the API connection
        // For now, it just checks if the API key is configured
        switch (provider.toLowerCase()) {
            case "openai":
                return !getOpenAiApiKey().isEmpty();
            case "gemini":
                return !getGeminiApiKey().isEmpty();
            case "groq":
                return !getGroqApiKey().isEmpty();
            default:
                return false;
        }
    }
}

