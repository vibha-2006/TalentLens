# Migration from Gemini AI to OpenAI

## Summary

The TalentLens application has been successfully migrated from Google Gemini AI to OpenAI API. This document outlines the changes made and provides instructions for configuration and testing.

---

## Changes Made

### 1. Service Layer Changes

#### Files Created:
- **`OpenAIService.java`** - New service replacing `GeminiService.java`
  - Uses OkHttp to make direct API calls to OpenAI
  - Implements chat completion endpoint
  - Supports GPT-3.5-turbo and GPT-4 models
  - Includes comprehensive error handling

#### Files Updated:
- **`ResumeService.java`**
  - Replaced `GeminiService` dependency with `OpenAIService`
  - Updated all method calls from `geminiService` to `openAIService`
  - Updated imports from `GeminiAnalysisResponse` to `AIAnalysisResponse`

### 2. DTO Changes

#### Files Created:
- **`AIAnalysisResponse.java`** - Replaces `GeminiAnalysisResponse.java`
- **`AIAnalysisRequest.java`** - Replaces `GeminiAnalysisRequest.java`

Both DTOs maintain the same structure to ensure backward compatibility:
```java
public class AIAnalysisResponse {
    private Double matchScore;
    private String analysis;
    private String extractedSkills;
    private String extractedExperience;
    private String candidateName;
    private String email;
    private String phone;
}
```

### 3. Test Files

#### Files Created:
- **`OpenAIServiceTest.java`** - Complete test suite for OpenAI integration
  - 7 comprehensive unit tests
  - Tests API key validation
  - Tests response structure
  - Tests multiple consecutive calls
  - Tests edge cases (empty inputs)

### 4. Configuration Changes

#### `pom.xml` Updates:
**Removed:**
```xml
<!-- Google Generative AI (Gemini) -->
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>google-cloud-aiplatform</artifactId>
    <version>3.34.0</version>
</dependency>
```

**Kept (for OpenAI HTTP calls):**
```xml
<!-- OkHttp for HTTP calls to OpenAI API -->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.12.0</version>
</dependency>
```

#### `application.properties` Updates:
**Before:**
```properties
# Gemini API Configuration
gemini.api.key=AIzaSyD0x32Td48OmutIEbmw0N5S9lsbgs3wBmk
gemini.api.url=https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent
```

**After:**
```properties
# OpenAI API Configuration
openai.api.key=your-openai-api-key-here
openai.model=gpt-3.5-turbo
```

---

## Configuration Instructions

### Step 1: Get an OpenAI API Key

1. Visit [OpenAI Platform](https://platform.openai.com/)
2. Sign up or log in to your account
3. Navigate to **API Keys** section
4. Click **"Create new secret key"**
5. Copy the generated API key (you won't be able to see it again!)

### Step 2: Update Configuration

Edit `src/main/resources/application.properties`:

```properties
# OpenAI API Configuration
openai.api.key=sk-your-actual-api-key-here
openai.model=gpt-3.5-turbo
```

**Available Models:**
- `gpt-3.5-turbo` - Fast and cost-effective (recommended for development)
- `gpt-4` - More powerful but more expensive
- `gpt-4-turbo` - Latest GPT-4 variant with improved performance
- `gpt-3.5-turbo-16k` - Extended context window

### Step 3: Verify API Key

You can test your API key using curl:

```bash
curl https://api.openai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_API_KEY" \
  -d '{
    "model": "gpt-3.5-turbo",
    "messages": [{"role": "user", "content": "Hello!"}]
  }'
```

Or using PowerShell:

```powershell
$headers = @{
    "Content-Type" = "application/json"
    "Authorization" = "Bearer YOUR_API_KEY"
}

$body = @{
    model = "gpt-3.5-turbo"
    messages = @(
        @{
            role = "user"
            content = "Hello!"
        }
    )
} | ConvertTo-Json

Invoke-RestMethod -Uri "https://api.openai.com/v1/chat/completions" -Method Post -Headers $headers -Body $body
```

---

## Building and Testing

### Build the Project

```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn clean package
```

### Run Unit Tests

```bash
# Run all tests
mvn test

# Run only OpenAI service tests
mvn test -Dtest=OpenAIServiceTest

# Run specific test
mvn test -Dtest=OpenAIServiceTest#testAnalyzeResumeWithValidApiKey
```

### Run the Application

```bash
# Start the backend
mvn spring-boot:run

# Or use the JAR file
java -jar target/TalentLens-1.0-SNAPSHOT.jar
```

---

## API Comparison

### Request Structure

**Gemini API:**
```json
{
  "contents": [{
    "parts": [{
      "text": "prompt here"
    }]
  }]
}
```

**OpenAI API:**
```json
{
  "model": "gpt-3.5-turbo",
  "messages": [
    {
      "role": "system",
      "content": "You are an expert HR analyst..."
    },
    {
      "role": "user",
      "content": "Analyze this resume..."
    }
  ],
  "temperature": 0.7,
  "max_tokens": 2000
}
```

### Response Structure

**Gemini API:**
```json
{
  "candidates": [{
    "content": {
      "parts": [{
        "text": "response here"
      }]
    }
  }]
}
```

**OpenAI API:**
```json
{
  "choices": [{
    "message": {
      "role": "assistant",
      "content": "response here"
    }
  }]
}
```

---

## Error Handling

The OpenAIService includes comprehensive error handling for:

### 401 Unauthorized
```
OpenAI API authentication failed (401). Please verify:
1. API key is valid and active
2. API key is properly formatted
```

### 429 Rate Limit
```
OpenAI API rate limit exceeded (429). Please:
1. Check your API usage quota
2. Wait before retrying
```

### Other Errors
All API errors include detailed error messages from OpenAI to help with debugging.

---

## Cost Considerations

### Pricing (as of December 2024)

**GPT-3.5-turbo:**
- Input: $0.0015 per 1K tokens
- Output: $0.002 per 1K tokens

**GPT-4:**
- Input: $0.03 per 1K tokens
- Output: $0.06 per 1K tokens

### Estimated Cost per Resume Analysis

Assuming average resume analysis uses ~1000 input tokens and ~500 output tokens:

- **GPT-3.5-turbo**: ~$0.0025 per resume
- **GPT-4**: ~$0.06 per resume

**For 1000 resumes:**
- **GPT-3.5-turbo**: ~$2.50
- **GPT-4**: ~$60

💡 **Recommendation**: Use GPT-3.5-turbo for development and testing, consider GPT-4 for production if higher quality is needed.

---

## Testing the Integration

### Test 1: Basic API Call
```java
@Test
public void testAnalyzeResumeWithValidApiKey() {
    AIAnalysisResponse response = openAIService.analyzeResume(sampleResumeText, sampleJobRequirements);
    assertNotNull(response);
    assertTrue(response.getMatchScore() >= 0 && response.getMatchScore() <= 100);
}
```

### Test 2: Response Structure
```java
@Test
public void testAnalyzeResumeResponseStructure() {
    AIAnalysisResponse response = openAIService.analyzeResume(sampleResumeText, sampleJobRequirements);
    assertNotNull(response.getCandidateName());
    assertNotNull(response.getEmail());
    assertNotNull(response.getPhone());
    assertNotNull(response.getExtractedSkills());
    assertNotNull(response.getExtractedExperience());
    assertNotNull(response.getAnalysis());
}
```

### Test 3: Multiple Calls
```java
@Test
public void testMultipleConsecutiveCalls() {
    AIAnalysisResponse response1 = openAIService.analyzeResume(resume1, jobReq);
    AIAnalysisResponse response2 = openAIService.analyzeResume(resume2, jobReq);
    assertNotNull(response1);
    assertNotNull(response2);
}
```

---

## Advantages of OpenAI vs Gemini

### ✅ Advantages

1. **Better API Documentation** - OpenAI has extensive, well-maintained documentation
2. **More Stable** - Fewer breaking changes and better versioning
3. **Wider Adoption** - Larger community and more examples available
4. **Better Error Messages** - More detailed error responses
5. **Flexible Models** - Easy to switch between GPT-3.5 and GPT-4
6. **Consistent Performance** - More predictable response times
7. **Better Token Management** - Clear token usage in responses

### ⚠️ Considerations

1. **Cost** - OpenAI charges per token (but very affordable for GPT-3.5-turbo)
2. **Rate Limits** - Free tier has lower limits (upgrade to paid for production)
3. **API Key Management** - Need to keep API keys secure

---

## Troubleshooting

### Issue: "Authentication failed (401)"
**Solution:** 
- Verify API key is correct in `application.properties`
- Ensure API key starts with `sk-`
- Check if API key is still active on OpenAI platform

### Issue: "Rate limit exceeded (429)"
**Solution:**
- Wait 60 seconds before retrying
- Upgrade to paid tier for higher limits
- Implement request throttling in production

### Issue: "Model not found"
**Solution:**
- Verify model name in configuration
- Check available models: `gpt-3.5-turbo`, `gpt-4`, `gpt-4-turbo`
- Ensure your account has access to the model

### Issue: "Insufficient credits"
**Solution:**
- Add payment method to OpenAI account
- Check billing settings on OpenAI platform
- Monitor usage at https://platform.openai.com/usage

---

## Production Deployment Checklist

- [ ] Replace placeholder API key with production key
- [ ] Set up API key as environment variable (not in properties file)
- [ ] Implement rate limiting
- [ ] Add request caching where appropriate
- [ ] Set up monitoring for API costs
- [ ] Implement fallback error handling
- [ ] Configure appropriate timeout values
- [ ] Set up logging for API calls
- [ ] Test with production-like data volumes
- [ ] Review and optimize prompt for token efficiency

---

## Environment Variable Configuration (Recommended for Production)

Instead of storing the API key in `application.properties`, use environment variables:

```properties
# application.properties
openai.api.key=${OPENAI_API_KEY}
openai.model=${OPENAI_MODEL:gpt-3.5-turbo}
```

Set environment variables:

**Windows:**
```cmd
set OPENAI_API_KEY=sk-your-key-here
set OPENAI_MODEL=gpt-3.5-turbo
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY=sk-your-key-here
export OPENAI_MODEL=gpt-3.5-turbo
```

**Docker:**
```yaml
environment:
  - OPENAI_API_KEY=sk-your-key-here
  - OPENAI_MODEL=gpt-3.5-turbo
```

---

## Files to Remove (Optional Cleanup)

Once migration is verified working, you can optionally remove old Gemini files:

- `src/main/java/org/example/service/GeminiService.java`
- `src/main/java/org/example/dto/GeminiAnalysisRequest.java`
- `src/main/java/org/example/dto/GeminiAnalysisResponse.java`
- `src/test/java/org/example/service/GeminiServiceTest.java`

---

## Support and Resources

- **OpenAI Documentation**: https://platform.openai.com/docs
- **OpenAI API Reference**: https://platform.openai.com/docs/api-reference
- **OpenAI Pricing**: https://openai.com/pricing
- **OpenAI Community**: https://community.openai.com/
- **Rate Limits**: https://platform.openai.com/docs/guides/rate-limits

---

## Next Steps

1. ✅ Migration completed
2. 🔧 Update `application.properties` with your OpenAI API key
3. 🧪 Run tests: `mvn test -Dtest=OpenAIServiceTest`
4. 🚀 Start application: `mvn spring-boot:run`
5. 📊 Test with real resume uploads
6. 🎯 Monitor API usage and costs
7. 🔒 Set up proper API key security for production

---

## Migration Complete! 🎉

The application is now using OpenAI instead of Gemini AI. All services, tests, and configurations have been updated accordingly.

