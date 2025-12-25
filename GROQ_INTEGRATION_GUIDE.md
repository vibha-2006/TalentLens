# Groq API Integration Guide

## Overview
TalentLens now supports **Groq** as a third GenAI provider alongside OpenAI and Google Gemini. Groq offers ultra-fast inference speeds with open-source large language models.

## What is Groq?
Groq provides lightning-fast AI inference using their custom LPU (Language Processing Unit) hardware. They offer access to popular open-source models like:
- **Llama 3.1** (70B, 8B variants)
- **Mixtral** (8x7B)
- **Gemma** (7B)

## Setup Instructions

### 1. Get Your Groq API Key
1. Visit [https://console.groq.com/keys](https://console.groq.com/keys)
2. Sign up or log in to your Groq account
3. Click "Create API Key"
4. Copy your API key (it starts with `gsk_`)

### 2. Configure application.properties
Open `src/main/resources/application.properties` and update the Groq configuration:

```properties
# Groq API Configuration
groq.api.key=gsk_YOUR_ACTUAL_API_KEY_HERE
groq.model=llama-3.1-70b-versatile
groq.api.url=https://api.groq.com/openai/v1/chat/completions
```

### 3. Set Groq as Default Provider (Optional)
To use Groq as the default AI provider:

```properties
ai.provider=groq
```

## Available Groq Models

### Llama 3.1 (Recommended)
- `llama-3.1-70b-versatile` - Best balance of speed and quality (8K context)
- `llama-3.1-8b-instant` - Fastest response times (8K context)

### Mixtral
- `mixtral-8x7b-32768` - Excellent for complex tasks (32K context)

### Gemma
- `gemma-7b-it` - Google's lightweight model (8K context)

## How to Use Groq in TalentLens

### Via Frontend UI
1. Open the TalentLens application
2. Go to the **Upload Resumes** section
3. In the "AI Provider" dropdown, select **"Groq (Llama 3.1)"**
4. Upload your resume or import from Google Drive
5. The system will use Groq for analysis

### Via API
When calling the resume upload API endpoint, include the `aiProvider` parameter:

```bash
curl -X POST http://localhost:8080/api/resumes/upload \
  -F "file=@resume.pdf" \
  -F "aiProvider=groq"
```

## Benefits of Groq

### 🚀 Ultra-Fast Inference
- Up to 10x faster than traditional cloud AI providers
- Sub-second response times for most queries

### 💰 Cost-Effective
- Free tier available with generous limits
- Competitive pricing for paid tiers

### 🔓 Open-Source Models
- Access to state-of-the-art open-source LLMs
- No vendor lock-in

### 🎯 High Quality
- Llama 3.1 70B rivals proprietary models
- Excellent for resume analysis and matching

## Switching Between Providers

### Runtime Selection
Users can switch between OpenAI, Gemini, and Groq on a per-request basis using the UI dropdown.

### Default Provider
Set in `application.properties`:
```properties
ai.provider=groq  # Options: openai, gemini, groq
```

## Testing Groq Integration

### Run Unit Tests
```bash
mvn test -Dtest=GroqServiceTest
```

### Run All AI Provider Tests
```bash
mvn test -Dtest=AIProviderFactoryTest
```

## Troubleshooting

### Error: Authentication Failed (401/403)
- Verify your API key is correct and starts with `gsk_`
- Check if your API key is active in the Groq console
- Ensure no extra spaces in the configuration

### Error: Rate Limit Exceeded (429)
- Check your API usage quota at [https://console.groq.com](https://console.groq.com)
- Wait before retrying or upgrade your plan

### Error: Bad Request (400)
- Verify the model name is correct
- Ensure the prompt isn't too long for the model's context window

### Error: Model Not Available
Available models may change. Check the latest models at:
[https://console.groq.com/docs/models](https://console.groq.com/docs/models)

## API Compatibility

Groq uses an OpenAI-compatible API, making integration seamless. The endpoint format is:
```
https://api.groq.com/openai/v1/chat/completions
```

## Performance Comparison

| Provider | Average Response Time | Quality | Cost |
|----------|----------------------|---------|------|
| OpenAI   | 5-10 seconds        | ⭐⭐⭐⭐⭐ | $$$ |
| Gemini   | 3-6 seconds         | ⭐⭐⭐⭐  | $$ |
| Groq     | 1-2 seconds         | ⭐⭐⭐⭐⭐ | $ |

## Code Implementation

### GroqService.java
Located at: `src/main/java/org/example/service/GroqService.java`

The service implements the `AIService` interface and provides:
- Resume analysis using Groq's API
- JSON parsing and structured response generation
- Error handling with detailed messages

### AIProviderFactory.java
Updated to support three providers:
```java
return switch (normalizedProvider) {
    case "gemini" -> geminiService;
    case "openai" -> openAIService;
    case "groq" -> groqService;
    // ...
};
```

## Support and Resources

- **Groq Documentation**: [https://console.groq.com/docs](https://console.groq.com/docs)
- **API Reference**: [https://console.groq.com/docs/api-reference](https://console.groq.com/docs/api-reference)
- **Community Discord**: [https://groq.com/discord](https://groq.com/discord)

## Next Steps

1. Get your Groq API key
2. Update `application.properties`
3. Run the tests to verify integration
4. Start using Groq for ultra-fast resume analysis!

---

*For questions or issues, please refer to the main README.md or create an issue in the project repository.*

