# Groq API Key Configuration Guide

## Error Details
**Error Message:** "Upload failed: Error processing resume: Groq API authentication failed (401). Invalid API Key"

## Root Cause
The Groq API key is not properly configured. This happens when:
1. The API key is still set to the placeholder value `your_groq_api_key_here`
2. The API key is invalid or expired
3. The API key is not properly formatted (should start with `gsk_`)

## Solution Steps

### Option 1: Update via Admin Settings UI (Recommended for Runtime)

1. **Start the Backend:**
   ```bash
   cd C:\Users\Vibha\TalentLens\TalentLens
   mvn spring-boot:run
   ```

2. **Open Admin Settings:**
   - Navigate to: http://localhost:8080/admin-settings
   
3. **Configure Groq Settings:**
   - Find the "Groq" section
   - Enter your valid Groq API key (starts with `gsk_`)
   - Model is already set to: `llama-3.3-70b-versatile`
   - Click "Save All Settings"

4. **Get a Valid Groq API Key:**
   - Go to: https://console.groq.com/keys
   - Sign in or create an account
   - Click "Create API Key"
   - Copy the key (it starts with `gsk_`)
   - Paste it in the Admin Settings

### Option 2: Update application.properties (For Development)

1. **Open the properties file:**
   ```
   C:\Users\Vibha\TalentLens\TalentLens\src\main\resources\application.properties
   ```

2. **Update the Groq API key:**
   ```properties
   # Replace this line:
   groq.api.key=your_groq_api_key_here
   
   # With your actual key:
   groq.api.key=gsk_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   ```

3. **Save and restart the application**

### Option 3: Environment Variables (For Production/Render)

If deploying to Render:

1. **Go to Render Dashboard:**
   - https://dashboard.render.com/

2. **Select your TalentLens service**

3. **Go to Environment tab**

4. **Add/Update environment variable:**
   ```
   Key: GROQ_API_KEY
   Value: gsk_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   ```

5. **Save** (triggers auto-deployment)

## How to Get a Valid Groq API Key

### Step-by-Step:

1. **Visit Groq Console:**
   - Go to: https://console.groq.com/

2. **Sign Up/Login:**
   - Create an account if you don't have one
   - Or login with existing credentials

3. **Navigate to API Keys:**
   - Click on your profile/settings
   - Go to: https://console.groq.com/keys

4. **Create New API Key:**
   - Click "Create API Key" button
   - Give it a descriptive name (e.g., "TalentLens")
   - Copy the key immediately (you won't see it again!)

5. **Verify Key Format:**
   - Valid Groq keys start with: `gsk_`
   - Example format: `gsk_abc123xyz456...`
   - Length: typically 50+ characters

## Testing Your Configuration

### Test via Admin Settings:

1. Go to: http://localhost:8080/admin-settings
2. Click "Test Connection" button next to Groq settings
3. Should show: ✅ "Connected" if key is valid

### Test via Resume Upload:

1. Create or select a Job Requirement
2. Select "Groq" as AI Provider
3. Upload a resume
4. Should process successfully

## Troubleshooting

### Still Getting 401 Error?

**Check 1: API Key Format**
```bash
# Valid format:
gsk_abc123xyz456def789...

# Invalid formats:
- Empty string
- "your_groq_api_key_here" (placeholder)
- Keys not starting with "gsk_"
- Keys with extra spaces or quotes
```

**Check 2: API Key Status**
- Login to https://console.groq.com/keys
- Verify the key is "Active"
- Check if key has been revoked
- Try creating a new key

**Check 3: Application Logs**
Look for debug messages in console:
```
DEBUG: API key loaded: Yes (starts with gsk_)
DEBUG: API key length: 50+
```

If you see:
```
DEBUG: API key loaded: No (empty or null)
```
Then the key is not being loaded properly.

### Common Mistakes:

❌ **Leaving placeholder value:**
```properties
groq.api.key=your_groq_api_key_here
```

❌ **Adding extra quotes:**
```properties
groq.api.key="gsk_abc123..."
```

❌ **Spaces or line breaks:**
```properties
groq.api.key= gsk_abc123...
```

✅ **Correct format:**
```properties
groq.api.key=gsk_abc123xyz456def789...
```

## Model Configuration

The default model is: `llama-3.3-70b-versatile`

### Available Groq Models:

- `llama-3.3-70b-versatile` (Default - Latest, most capable)
- `llama-3.1-70b-versatile` (Fast and capable)
- `llama-3.1-8b-instant` (Fastest, lower cost)
- `mixtral-8x7b-32768` (Good for long contexts)
- `gemma2-9b-it` (Alternative option)

**Note:** Some models may be deprecated. Check https://console.groq.com/docs/models for current list.

## Security Best Practices

⚠️ **Important:**

1. **Never commit API keys to Git:**
   - Use `.gitignore` for `application.properties`
   - Or use environment variables

2. **Rotate keys regularly:**
   - Create new keys periodically
   - Delete old/unused keys

3. **Use Admin Settings for testing:**
   - Keys stored in memory only
   - Don't persist in files

4. **Monitor usage:**
   - Check Groq dashboard for unusual activity
   - Set up usage alerts if available

## Quick Fix Summary

### For Local Development:
```bash
# 1. Get API key from https://console.groq.com/keys
# 2. Update application.properties
# 3. Restart application
mvn spring-boot:run
```

### For Production (Render):
```bash
# 1. Get API key from https://console.groq.com/keys
# 2. Add to Render environment variables
# 3. Redeploy
```

### For Runtime Testing:
```bash
# 1. Start application
# 2. Go to http://localhost:8080/admin-settings
# 3. Enter API key in Groq section
# 4. Save and test
```

## Support

If you continue to have issues:

1. Check Groq service status: https://status.groq.com/
2. Verify your account is active
3. Check Groq API documentation: https://console.groq.com/docs
4. Review application logs for detailed error messages

## Additional Resources

- Groq Console: https://console.groq.com/
- API Keys: https://console.groq.com/keys
- Documentation: https://console.groq.com/docs
- Models List: https://console.groq.com/docs/models
- Status Page: https://status.groq.com/

