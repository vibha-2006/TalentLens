# Setting Environment Variables in Render - Quick Guide

## Problem You're Facing
When you upload a resume in your deployed application, you get:
```
Upload failed: Error processing resume: Groq API authentication failed (401)
```

This happens because the backend cannot read the API key.

## Solution: Set Environment Variables in Render

### Step-by-Step Instructions

1. **Go to Render Dashboard**
   - Open https://dashboard.render.com/
   - Login with your account
   - Find your TalentLens service

2. **Navigate to Environment Variables**
   - Click on your service name
   - In the left sidebar, click **"Environment"**
   - You'll see a section called "Environment Variables"

3. **Add Your API Keys**
   Click "Add Environment Variable" and add the following:

   **For Groq:**
   - **Key**: `GROQ_API_KEY`
   - **Value**: `gsk_YOUR_ACTUAL_API_KEY_HERE`
   
   **For OpenAI (if using):**
   - **Key**: `OPENAI_API_KEY`
   - **Value**: `sk_YOUR_ACTUAL_API_KEY_HERE`
   
   **For Gemini (if using):**
   - **Key**: `GEMINI_API_KEY`
   - **Value**: `YOUR_ACTUAL_GEMINI_KEY_HERE`

4. **Optional: Set Default Provider**
   - **Key**: `AI_PROVIDER`
   - **Value**: `groq` (or `openai` or `gemini`)

5. **Save Changes**
   - Click **"Save Changes"** button
   - Render will automatically redeploy your application
   - Wait 2-3 minutes for the deployment to complete

6. **Verify It Works**
   - Go to your deployed application URL
   - Navigate to Admin Settings
   - You should see the API keys (masked with ***)
   - Try uploading a resume
   - It should now work!

## Where to Get API Keys

### Groq API Key
1. Go to https://console.groq.com/
2. Sign in or create an account
3. Click on "API Keys" in the left menu
4. Click "Create API Key"
5. Copy the key (starts with `gsk_`)

### OpenAI API Key
1. Go to https://platform.openai.com/api-keys
2. Sign in or create an account
3. Click "Create new secret key"
4. Copy the key (starts with `sk-`)

### Gemini API Key
1. Go to https://makersuite.google.com/app/apikey
2. Sign in with Google account
3. Click "Create API Key"
4. Enable "Generative Language API" in Google Cloud Console
5. Copy the key

## Troubleshooting

### If You Still Get 401 Error
1. Double-check the environment variable name is exactly: `GROQ_API_KEY` (case-sensitive)
2. Make sure there are no extra spaces in the API key
3. Verify the API key starts with `gsk_`
4. Check the Render deployment logs for any errors

### If Upload Works But Shows Wrong Results
- The API key is valid but you may need to update the model name
- In Render Environment Variables, add:
  - **Key**: `GROQ_MODEL`
  - **Value**: `llama-3.3-70b-versatile`

### Check Deployment Logs
1. In Render dashboard, click on your service
2. Click "Logs" in the left sidebar
3. Look for lines like:
   ```
   DEBUG: API key loaded: Yes (starts with gsk_)
   ```

## Important Notes

- ✅ Environment variables persist across restarts
- ✅ Environment variables take precedence over Admin UI settings
- ✅ API keys set in Render are secure and not visible in logs
- ⚠️ Admin UI updates are temporary (lost on restart)
- ⚠️ For production, ALWAYS use Render environment variables

## Alternative: Use Admin Settings UI (Temporary)

If you don't want to set environment variables in Render:
1. Go to your application's Admin Settings page
2. Enter your API keys
3. Click Save
4. This will work until the next deployment/restart

**Recommendation**: Use Render environment variables for production deployment!

