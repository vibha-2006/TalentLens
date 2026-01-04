# Render Environment Variables Setup Guide

## Quick Setup Steps

### 1. Access Render Dashboard
1. Go to https://dashboard.render.com/
2. Select your **talentlens** service
3. Click on **Environment** tab in the left sidebar

### 2. Add Required Environment Variables

Click **Add Environment Variable** button and add each of these:

#### OpenAI Configuration (Required if using OpenAI)
```
Key: OPENAI_API_KEY
Value: [Your OpenAI API Key from https://platform.openai.com/api-keys]
```

#### Gemini Configuration (Required if using Gemini)
```
Key: GEMINI_API_KEY
Value: [Your Gemini API Key from https://makersuite.google.com/app/apikey]
```

#### Groq Configuration (Required if using Groq)
```
Key: GROQ_API_KEY
Value: [Your Groq API Key from https://console.groq.com/keys]
```

### 3. Optional Environment Variables

These are already set with default values in render.yaml, but you can override them:

```
Key: AI_PROVIDER
Value: openai (or gemini or groq)

Key: OPENAI_MODEL
Value: gpt-3.5-turbo (or gpt-4)

Key: GEMINI_MODEL
Value: gemini-1.5-flash (or gemini-pro)

Key: GROQ_MODEL
Value: llama-3.3-70b-versatile
```

### 4. Save and Deploy

1. After adding all environment variables, click **Save Changes**
2. Render will automatically trigger a new deployment
3. Wait for the deployment to complete (check Logs tab)

## How to Get API Keys

### OpenAI API Key
1. Go to https://platform.openai.com/
2. Sign up or log in
3. Navigate to https://platform.openai.com/api-keys
4. Click **Create new secret key**
5. Copy the key (you won't be able to see it again!)
6. Add billing information if you haven't already

### Gemini API Key
1. Go to https://makersuite.google.com/app/apikey
2. Sign in with your Google account
3. Click **Create API key**
4. Select or create a Google Cloud project
5. Enable **Generative Language API** in Google Cloud Console
6. Copy the API key

### Groq API Key
1. Go to https://console.groq.com/
2. Sign up or log in
3. Navigate to https://console.groq.com/keys
4. Click **Create API Key**
5. Give it a name and copy the key

## Verifying Configuration

### After Deployment:

1. **Check Logs**
   - Go to Render Dashboard → Logs tab
   - Look for successful startup messages
   - Verify no errors about missing API keys

2. **Test Admin Settings Page**
   - Open your deployed URL: `https://talentlens.onrender.com/admin-settings`
   - You should see the settings loaded
   - API keys will be masked (showing only first and last 4 characters)

3. **Test Resume Upload**
   - Create a job requirement
   - Upload a resume
   - Check if AI processing works

## Troubleshooting

### If you see "API key not configured" errors:

1. Verify environment variables are set in Render dashboard
2. Check spelling of variable names (case-sensitive)
3. Ensure no extra spaces in values
4. Try redeploying manually: Dashboard → Manual Deploy → Deploy latest commit

### If deployment fails:

1. Check Render logs for specific errors
2. Verify Docker build completes successfully
3. Check that all services start properly
4. Look for port binding issues (should use PORT environment variable)

### If CORS errors persist:

1. Check the deployed URL matches the pattern `https://*.onrender.com`
2. If using custom domain, add it to CORS configuration in WebConfig.java
3. Redeploy after code changes

## Important Notes

⚠️ **Security Best Practices:**
- Never commit API keys to Git repositories
- Use Render's environment variables for sensitive data
- Rotate API keys periodically
- Monitor API usage to detect unauthorized access

💡 **Cost Management:**
- Free tier has limitations (check each provider)
- Monitor API usage in respective dashboards
- Set up billing alerts if using paid tiers

🔄 **Updates:**
- Environment variables can be updated anytime in Render
- Changes to environment variables trigger automatic redeployment
- No code changes needed to update API keys

## Testing with Different Providers

You can switch between AI providers by:

1. **Method 1: Update Environment Variable**
   - Go to Render Dashboard → Environment
   - Change `AI_PROVIDER` value to: `openai`, `gemini`, or `groq`
   - Save (triggers redeploy)

2. **Method 2: Use Admin Settings Page** (Runtime only)
   - Go to `/admin-settings`
   - Configure API keys for all providers
   - Use dropdown in resume upload page to select provider
   - Note: Settings persist only until restart

## Health Check

Render uses `/api/admin/settings` as health check endpoint.

**Healthy Response:**
```json
{
  "openai": {...},
  "gemini": {...},
  "groq": {...}
}
```

If health check fails, Render will restart the service automatically.

## Next Steps After Setup

1. ✅ Add environment variables in Render
2. ✅ Wait for deployment to complete
3. ✅ Test admin settings page
4. ✅ Test resume upload with each AI provider
5. ✅ Monitor logs for any errors
6. ✅ Share deployed URL for demo

## Support

If you encounter issues:
- Check PRODUCTION_FIX.md for recent fixes
- Review Render logs for error messages
- Verify API keys are valid and active
- Check API provider status pages

