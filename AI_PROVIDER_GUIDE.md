# AI Provider Configuration Guide

## Overview

TalentLens now supports **multiple AI providers** for resume analysis:
- **OpenAI** (GPT-3.5-turbo, GPT-4)
- **Google Gemini** (gemini-1.5-flash-latest, gemini-pro)

You can switch between AI providers seamlessly through the UI or by configuring the default provider in the backend.

## Features

### 1. Dynamic AI Provider Selection
- Select AI provider from the UI dropdown before uploading resumes
- Switch between OpenAI and Gemini without restarting the application
- Each resume can be analyzed with a different AI provider

### 2. Backend Architecture
```
AIService (Interface)
    ├── OpenAIService (Implementation)
    └── GeminiService (Implementation)

AIProviderFactory
    └── Manages provider selection and instantiation
```

### 3. Configuration

#### Application Properties
Located in: `src/main/resources/application.properties`

```properties
# Default AI Provider (openai or gemini)
ai.provider=openai

# OpenAI Configuration
openai.api.key=your-openai-api-key
openai.model=gpt-3.5-turbo

# Gemini Configuration
gemini.api.key=your-gemini-api-key
gemini.model=gemini-1.5-flash-latest
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models
```

## Setup Instructions

### OpenAI Setup

1. **Get API Key**
   - Visit: https://platform.openai.com/api-keys
   - Create a new API key
   - Copy the key (starts with `sk-...`)

2. **Configure Application**
   ```properties
   openai.api.key=sk-your-api-key-here
   openai.model=gpt-3.5-turbo
   ```

3. **Verify**
   - Run: `mvn test -Dtest=OpenAIServiceTest`
   - Should see ✓ API Call Successful!

### Gemini Setup

1. **Get API Key**
   - Visit: https://makersuite.google.com/app/apikey
   - Click "Create API Key"
   - Select or create a Google Cloud project
   - Copy the API key (starts with `AIza...`)

2. **Enable API**
   - Go to: https://console.cloud.google.com/
   - Navigate to "APIs & Services" > "Library"
   - Search for "Generative Language API"
   - Click "Enable"

3. **Configure Application**
   ```properties
   gemini.api.key=AIza-your-api-key-here
   gemini.model=gemini-1.5-flash-latest
   ```

4. **Verify**
   - Run: `mvn test -Dtest=GeminiServiceTest`
   - Should see ✓ API Call Successful!

## Using the Application

### Frontend - Upload Resume with AI Provider Selection

1. **Navigate to Upload Tab**
   - Click "Upload Resumes" in the navigation

2. **Select AI Provider**
   - Choose from dropdown:
     - "OpenAI (GPT-3.5)" 
     - "Google Gemini"

3. **Upload Resume**
   - Select a PDF or Word document
   - Click "Upload & Analyze"
   - The selected AI provider will analyze the resume

### Default Provider

The application uses the provider specified in `application.properties`:
```properties
ai.provider=openai  # or gemini
```

This is used when:
- No provider is specified in the API request
- Batch imports from Google Drive
- Background processing

## API Endpoints

### Upload Resume with Specific Provider
```http
POST /api/resumes/upload?aiProvider=gemini
Content-Type: multipart/form-data

file: [resume.pdf]
```

### Import from Google Drive with Specific Provider
```http
POST /api/resumes/import-from-drive?folderId=xyz&aiProvider=openai
```

### Parameters
- `aiProvider` (optional): `openai` or `gemini`
- If not specified, uses default from configuration

## Code Structure

### New Files Created

1. **AIService.java** - Interface defining AI service contract
   ```java
   public interface AIService {
       AIAnalysisResponse analyzeResume(String resumeText, String jobRequirements);
       String getProviderName();
   }
   ```

2. **GeminiService.java** - Gemini API implementation
   - Implements AIService interface
   - Handles Gemini-specific API calls
   - Parses Gemini response format

3. **AIProviderFactory.java** - Provider management
   - Returns appropriate AI service based on configuration
   - Supports case-insensitive provider names
   - Falls back to default provider for unknown values

4. **GeminiServiceTest.java** - Unit tests for Gemini
5. **AIProviderFactoryTest.java** - Factory tests

### Modified Files

1. **OpenAIService.java**
   - Now implements AIService interface
   - Added `getProviderName()` method

2. **ResumeService.java**
   - Uses AIProviderFactory instead of direct OpenAIService
   - Accepts `aiProvider` parameter in methods
   - Logs which provider is used

3. **ResumeController.java**
   - Accepts `aiProvider` query parameter
   - Passes provider to service layer

4. **Frontend Components**
   - **ResumeUpload.js**: Added AI provider selector dropdown
   - **App.js**: Updated branding to reflect both providers
   - **api.js**: Passes provider parameter to backend
   - **ResumeUpload.css**: Styled provider selector

## Testing

### Run All Tests
```bash
mvn test
```

### Test Specific Provider
```bash
# OpenAI
mvn test -Dtest=OpenAIServiceTest

# Gemini  
mvn test -Dtest=GeminiServiceTest

# Factory
mvn test -Dtest=AIProviderFactoryTest
```

### Expected Test Output
```
✓ API Call Successful!
=================================
Candidate Name: John Doe
Email: john.doe@example.com
Phone: +1-555-0123
Match Score: 85.0
Extracted Skills: Java, Spring Boot, React.js...
...
=================================
```

## Troubleshooting

### Gemini API 404 Error
**Error**: `models/gemini-pro is not found for API version v1beta`

**Solution**:
1. Check if Generative Language API is enabled in Google Cloud Console
2. Try different model names:
   - `gemini-1.5-flash-latest`
   - `gemini-1.5-pro-latest`
   - `gemini-pro`
3. Verify API key is active and has correct permissions

### OpenAI API 401 Error
**Error**: `OpenAI API authentication failed (401)`

**Solution**:
1. Verify API key is correct and active
2. Check if you have sufficient credits
3. Ensure API key hasn't been revoked

### Provider Not Switching
**Issue**: Always uses same provider regardless of selection

**Solution**:
1. Clear browser cache
2. Restart backend application
3. Check browser console for errors
4. Verify `aiProvider` parameter is being sent in API request

## Performance Comparison

| Provider | Speed | Cost | Quality | Best For |
|----------|-------|------|---------|----------|
| **OpenAI GPT-3.5** | Fast | Low | Good | High volume, quick analysis |
| **OpenAI GPT-4** | Slow | High | Excellent | Detailed analysis, critical roles |
| **Gemini 1.5 Flash** | Very Fast | Low | Good | Real-time processing |
| **Gemini Pro** | Medium | Medium | Very Good | Balanced performance |

## Best Practices

1. **Use OpenAI for**:
   - Established reliability
   - Consistent formatting
   - English language resumes

2. **Use Gemini for**:
   - Cost-effective processing
   - High-volume scenarios
   - Multi-language support
   - Faster response times

3. **Configuration**:
   - Set default provider based on your primary use case
   - Keep both API keys configured for fallback options
   - Monitor API usage and costs

4. **Error Handling**:
   - Application gracefully handles API failures
   - Error messages guide users to solutions
   - Logs provide debugging information

## Future Enhancements

Potential improvements:
- Add more AI providers (Anthropic Claude, Cohere, etc.)
- Provider failover (try Gemini if OpenAI fails)
- Cost tracking per provider
- Performance metrics and comparison
- A/B testing between providers
- Custom model selection per provider

## Support

For issues or questions:
1. Check application logs in console
2. Review error messages in UI
3. Verify API keys and quotas
4. Check provider status pages:
   - OpenAI: https://status.openai.com/
   - Google: https://status.cloud.google.com/

## Summary

TalentLens now provides flexible AI provider support, allowing you to:
- ✅ Choose between OpenAI and Gemini
- ✅ Switch providers dynamically via UI
- ✅ Configure default provider
- ✅ Use different providers for different resumes
- ✅ Optimize for cost, speed, or quality

The implementation is extensible and ready for additional AI providers in the future!

