package org.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class AIProviderFactoryTest {

    @Autowired
    private AIProviderFactory aiProviderFactory;

    @Test
    public void testFactoryNotNull() {
        assertNotNull(aiProviderFactory, "AIProviderFactory should be autowired and not null");
    }

    @Test
    public void testGetOpenAIService() {
        AIService service = aiProviderFactory.getAIService("openai");
        assertNotNull(service, "OpenAI service should not be null");
        assertEquals("OpenAI", service.getProviderName(), "Provider name should be 'OpenAI'");
        assertTrue(service instanceof OpenAIService, "Service should be instance of OpenAIService");
    }

    @Test
    public void testGetGeminiService() {
        AIService service = aiProviderFactory.getAIService("gemini");
        assertNotNull(service, "Gemini service should not be null");
        assertEquals("Gemini", service.getProviderName(), "Provider name should be 'Gemini'");
        assertTrue(service instanceof GeminiService, "Service should be instance of GeminiService");
    }

    @Test
    public void testGetGroqService() {
        AIService service = aiProviderFactory.getAIService("groq");
        assertNotNull(service, "Groq service should not be null");
        assertEquals("Groq", service.getProviderName(), "Provider name should be 'Groq'");
        assertTrue(service instanceof GroqService, "Service should be instance of GroqService");
    }

    @Test
    public void testGetDefaultService() {
        AIService service = aiProviderFactory.getAIService();
        assertNotNull(service, "Default service should not be null");
        System.out.println("Default AI Provider: " + service.getProviderName());
    }

    @Test
    public void testGetServiceCaseInsensitive() {
        AIService service1 = aiProviderFactory.getAIService("OPENAI");
        assertEquals("OpenAI", service1.getProviderName());

        AIService service2 = aiProviderFactory.getAIService("Gemini");
        assertEquals("Gemini", service2.getProviderName());

        AIService service3 = aiProviderFactory.getAIService("  openai  ");
        assertEquals("OpenAI", service3.getProviderName());

        AIService service4 = aiProviderFactory.getAIService("GROQ");
        assertEquals("Groq", service4.getProviderName());
    }

    @Test
    public void testIsProviderAvailable() {
        assertTrue(aiProviderFactory.isProviderAvailable("openai"), "OpenAI should be available");
        assertTrue(aiProviderFactory.isProviderAvailable("gemini"), "Gemini should be available");
        assertTrue(aiProviderFactory.isProviderAvailable("groq"), "Groq should be available");
    }

    @Test
    public void testGetServiceWithNullProvider() {
        AIService service = aiProviderFactory.getAIService(null);
        assertNotNull(service, "Should return default service when provider is null");
    }

    @Test
    public void testGetServiceWithEmptyProvider() {
        AIService service = aiProviderFactory.getAIService("");
        assertNotNull(service, "Should return default service when provider is empty");
    }

    @Test
    public void testGetServiceWithInvalidProvider() {
        AIService service = aiProviderFactory.getAIService("invalid-provider");
        assertNotNull(service, "Should return default service when provider is invalid");
        System.out.println("Returned provider for 'invalid-provider': " + service.getProviderName());
    }
}

