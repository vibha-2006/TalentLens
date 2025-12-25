# Gemini Service Fix - Instructions

## Changes Made

### 1. GeminiService.java Updates
- ✅ Updated default Gemini model from `gemini-pro` to `gemini-1.5-flash`
- ✅ Added comprehensive error handling for 404 errors
- ✅ Added debug logging to help diagnose API issues
- ✅ Improved error messages with actionable troubleshooting steps

### 2. application.properties Updates
- ✅ Changed Gemini API URL to use `gemini-1.5-flash` model
- ✅ API Key configured: `AIzaSyDzxa7mD3v2m0poYKT01HhVtP9w0tNjZRE`

## What Was Fixed

### Original Problem
- Error: "Upload failed: Error processing resume: Gemini API call failed: 404"

### Root Causes
1. **Incorrect Model Name**: The older `gemini-pro` model endpoint may not be available
2. **API Endpoint**: Need to use the latest Gemini 1.5 Flash model

### The Fix
- Updated to use `gemini-1.5-flash` which is Google's latest fast model
- Added better error handling to provide clear troubleshooting steps
- Added debug logging to see exact API calls being made

## How to Test

### Step 1: Rebuild Backend
```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn clean install -DskipTests
```

### Step 2: Start Backend
```bash
java -jar target\TalentLens-1.0-SNAPSHOT.jar
```

Or use the provided script:
```bash
.\start-talentlens.bat
```

### Step 3: Verify Backend is Running
- Check console output for: "Started Main in X seconds"
- Verify port 8080 is listening: `netstat -ano | findstr :8080`
- Test health: Open http://localhost:8080 in browser

### Step 4: Test the Gemini Integration

1. **Create a Job Requirement First**
   - Open the frontend: http://localhost:3000
   - Navigate to Job Requirements section
   - Fill in:
     - Title: "Software Engineer"
     - Description: "Looking for experienced developers"
     - Required Skills: "Java, Spring Boot, React, JavaScript"
     - Experience Level: "3-5 years"
   - Click "Save Job Requirement"

2. **Upload a Resume**
   - Navigate to Resume Upload section
   - Select a PDF or DOCX resume file
   - Click "Upload Resume"
   - Wait for analysis

3. **Check Console Output**
   - Look for debug messages like:
     ```
     DEBUG: Calling Gemini API at: https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=<REDACTED>
     ```
   - If successful, you'll see the resume analysis results

## Troubleshooting

### If You Still Get 404 Error:

1. **Verify API Key is Active**
   - Go to: https://makersuite.google.com/app/apikey
   - Check if your API key is valid
   - Create a new key if needed
   - Update `src/main/resources/application.properties` with the new key

2. **Enable Generative Language API**
   - Go to: https://console.cloud.google.com/apis/library/generativelanguage.googleapis.com
   - Click "Enable" if not already enabled
   - Wait a few minutes for activation

3. **Try Different Model**
   - Change `gemini-1.5-flash` to `gemini-pro` in application.properties
   - Rebuild and restart

4. **Check API Quotas**
   - Go to: https://console.cloud.google.com/apis/api/generativelanguage.googleapis.com/quotas
   - Verify you haven't exceeded quota limits

### If Backend Won't Start:

1. **Port 8080 Already in Use**
   ```powershell
   # Find process on port 8080
   netstat -ano | findstr :8080
   
   # Kill the process (replace PID with actual process ID)
   Stop-Process -Id <PID> -Force
   ```

2. **Check Java Version**
   ```bash
   java -version
   # Should be Java 17 or higher
   ```

3. **Check Logs**
   - Look at console output for error messages
   - Check for "APPLICATION FAILED TO START" messages
   - Look for specific error details

## Expected Behavior After Fix

### Success Indicators:
1. ✅ Backend starts without errors
2. ✅ Console shows: "DEBUG: Calling Gemini API..."
3. ✅ Resume upload completes successfully
4. ✅ Match score and analysis appear in UI
5. ✅ Resume is saved to database with analysis

### Error Messages to Watch For:
- ❌ "No active job requirement found" → Create job requirement first
- ❌ "Gemini API call failed: 404" → Check API key and model name
- ❌ "Gemini API call failed: 429" → Rate limit exceeded, wait and retry
- ❌ "Gemini API call failed: 403" → API not enabled or invalid key

## Files Modified

1. `src/main/java/org/example/service/GeminiService.java`
   - Line 22: Updated default API URL
   - Lines 95-122: Enhanced error handling

2. `src/main/resources/application.properties`
   - Line 25: Updated Gemini API URL to gemini-1.5-flash

3. `pom.xml`
   - Updated Lombok version to edge-SNAPSHOT for Java 25 compatibility
   - Updated Maven compiler plugin to 3.13.0

## Additional Notes

- The Gemini API key in the config is exposed in this file for reference only
- In production, use environment variables or secure vaults for API keys
- The frontend is running on port 3000 (already started)
- The backend should run on port 8080
- Frontend proxies API calls to backend via proxy configuration

## Support

If you continue to face issues:
1. Check the console logs for detailed error messages
2. Verify all configuration settings in application.properties
3. Test the API key directly using curl:
   ```bash
   curl -X POST "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=YOUR_KEY" -H "Content-Type: application/json" -d "{\"contents\":[{\"parts\":[{\"text\":\"Hello\"}]}]}"
   ```

