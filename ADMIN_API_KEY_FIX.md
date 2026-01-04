# Admin Settings API Key Fix

## Problem
The application was unable to read API keys in Render deployment because:
1. The application.properties file inside a JAR is read-only
2. API keys updated via Admin Settings UI were stored in memory only
3. Services used `@Value` annotations which only read properties at startup
4. On container restart, all settings were lost

## Solution
We've implemented a dynamic API key management system that works in both local and production (Render) environments.

### Changes Made

#### 1. AISettingsService.java
- **Removed**: `@Value` annotations for API keys
- **Added**: `runtimeSettings` HashMap to store API keys in memory
- **Added**: Public getter methods (`getOpenAiApiKey()`, `getGeminiApiKey()`, `getGroqApiKey()`, etc.)
- **Modified**: `updateSettings()` to store keys in the runtime map
- **Modified**: `getAllSettings()` to read from runtime storage

Key features:
- Reads from environment variables first (for Render)
- Falls back to runtime settings (for Admin UI updates)
- Settings persist as long as the application is running
- Environment variables take precedence

#### 2. GroqService.java
- **Removed**: `@Value` annotations
- **Added**: `@Autowired AISettingsService`
- **Modified**: `callGroqAPI()` to get API key dynamically at runtime
- Now reads fresh API keys on every API call

#### 3. OpenAIService.java
- **Removed**: `@Value` annotations
- **Added**: `@Autowired AISettingsService`
- **Modified**: `callOpenAIAPI()` to get API key dynamically at runtime
- Now reads fresh API keys on every API call

#### 4. GeminiService.java
- **Removed**: `@Value` annotations
- **Added**: `@Autowired AISettingsService`
- **Modified**: `callGeminiAPI()` to get API key dynamically at runtime
- Now reads fresh API keys on every API call

#### 5. application.properties
- **Updated**: All hardcoded API keys removed
- **Updated**: Using environment variable syntax: `${GROQ_API_KEY:}`
- **Added**: Comprehensive documentation

### How It Works

#### Local Development
1. Set environment variables in your system or IDE
2. OR use Admin Settings UI to configure API keys at runtime
3. Keys are stored in memory and work until application restart

#### Production (Render)
1. Set environment variables in Render Dashboard:
   - `GROQ_API_KEY=gsk_your_actual_key`
   - `OPENAI_API_KEY=sk-your_actual_key`
   - `GEMINI_API_KEY=your_actual_key`
2. Application reads from environment variables automatically
3. Admin UI can temporarily override these (until restart)
4. On restart, environment variables are loaded again

### Setting Environment Variables in Render

1. Go to your Render service dashboard
2. Click on "Environment" in the left sidebar
3. Add the following environment variables:
   ```
   GROQ_API_KEY=gsk_your_actual_groq_key_here
   OPENAI_API_KEY=sk_your_actual_openai_key_here
   GEMINI_API_KEY=your_actual_gemini_key_here
   ```
4. Save changes
5. Render will automatically redeploy with the new environment variables

### Benefits
1. ✅ API keys work in both local and production
2. ✅ Admin UI can update keys dynamically
3. ✅ Environment variables ensure keys survive restarts
4. ✅ No hardcoded secrets in application.properties
5. ✅ Services always get the latest API key values
6. ✅ Compatible with Docker and cloud platforms

### Testing
1. Start the backend: `mvn spring-boot:run`
2. Go to Admin Settings in the UI
3. Update API keys
4. Upload a resume - should work with updated keys
5. Restart backend
6. Upload again - keys from environment variables should work

### Important Notes
- Admin UI updates are temporary (lost on restart)
- For production, ALWAYS set environment variables
- Environment variables take precedence over Admin UI
- API keys are masked in the Admin UI for security
- Services fetch keys dynamically on every API call

