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

    @Value("${openai.api.key:}")
    private String openaiApiKey;

    @Value("${openai.model:gpt-3.5-turbo}")
    private String openaiModel;

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    @Value("${gemini.model:gemini-1.5-flash}")
    private String geminiModel;

    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models}")
    private String geminiApiUrl;

    @Value("${groq.api.key:}")
    private String groqApiKey;

    @Value("${groq.model:llama-3.3-70b-versatile}")
    private String groqModel;

    @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String groqApiUrl;

    public AISettingsService(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    public AllAISettingsDTO getAllSettings() {
        AllAISettingsDTO allSettings = new AllAISettingsDTO();

        // OpenAI settings
        AISettingsDTO openaiSettings = new AISettingsDTO();
        openaiSettings.setProvider("openai");
        openaiSettings.setApiKey(maskApiKey(openaiApiKey));
        openaiSettings.setModel(openaiModel);
        openaiSettings.setApiUrl("https://api.openai.com/v1/chat/completions");
        openaiSettings.setEnabled(!openaiApiKey.isEmpty());

        // Gemini settings
        AISettingsDTO geminiSettings = new AISettingsDTO();
        geminiSettings.setProvider("gemini");
        geminiSettings.setApiKey(maskApiKey(geminiApiKey));
        geminiSettings.setModel(geminiModel);
        geminiSettings.setApiUrl(geminiApiUrl);
        geminiSettings.setEnabled(!geminiApiKey.isEmpty());

        // Groq settings
        AISettingsDTO groqSettings = new AISettingsDTO();
        groqSettings.setProvider("groq");
        groqSettings.setApiKey(maskApiKey(groqApiKey));
        groqSettings.setModel(groqModel);
        groqSettings.setApiUrl(groqApiUrl);
        groqSettings.setEnabled(!groqApiKey.isEmpty());

        allSettings.setOpenai(openaiSettings);
        allSettings.setGemini(geminiSettings);
        allSettings.setGroq(groqSettings);

        return allSettings;
    }

    public void updateSettings(AllAISettingsDTO settings) throws IOException {
        Map<String, Object> propertyMap = new HashMap<>();

        // Update OpenAI settings
        if (settings.getOpenai() != null) {
            AISettingsDTO openai = settings.getOpenai();
            if (openai.getApiKey() != null && !openai.getApiKey().contains("***")) {
                propertyMap.put("openai.api.key", openai.getApiKey());
                this.openaiApiKey = openai.getApiKey();
            }
            if (openai.getModel() != null && !openai.getModel().isEmpty()) {
                propertyMap.put("openai.model", openai.getModel());
                this.openaiModel = openai.getModel();
            }
        }

        // Update Gemini settings
        if (settings.getGemini() != null) {
            AISettingsDTO gemini = settings.getGemini();
            if (gemini.getApiKey() != null && !gemini.getApiKey().contains("***")) {
                propertyMap.put("gemini.api.key", gemini.getApiKey());
                this.geminiApiKey = gemini.getApiKey();
            }
            if (gemini.getModel() != null && !gemini.getModel().isEmpty()) {
                propertyMap.put("gemini.model", gemini.getModel());
                this.geminiModel = gemini.getModel();
            }
            if (gemini.getApiUrl() != null && !gemini.getApiUrl().isEmpty()) {
                propertyMap.put("gemini.api.url", gemini.getApiUrl());
                this.geminiApiUrl = gemini.getApiUrl();
            }
        }

        // Update Groq settings
        if (settings.getGroq() != null) {
            AISettingsDTO groq = settings.getGroq();
            if (groq.getApiKey() != null && !groq.getApiKey().contains("***")) {
                propertyMap.put("groq.api.key", groq.getApiKey());
                this.groqApiKey = groq.getApiKey();
            }
            if (groq.getModel() != null && !groq.getModel().isEmpty()) {
                propertyMap.put("groq.model", groq.getModel());
                this.groqModel = groq.getModel();
            }
            if (groq.getApiUrl() != null && !groq.getApiUrl().isEmpty()) {
                propertyMap.put("groq.api.url", groq.getApiUrl());
                this.groqApiUrl = groq.getApiUrl();
            }
        }

        // Update runtime properties only (no file persistence in production)
        MapPropertySource mapPropertySource = new MapPropertySource("dynamicProperties", propertyMap);
        environment.getPropertySources().addFirst(mapPropertySource);

        // Note: In production (when running from JAR), settings are stored in memory only
        // To persist settings across restarts, use environment variables in your deployment platform
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
                return !openaiApiKey.isEmpty();
            case "gemini":
                return !geminiApiKey.isEmpty();
            case "groq":
                return !groqApiKey.isEmpty();
            default:
                return false;
        }
    }
}

