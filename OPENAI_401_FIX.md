# Fix OpenAI 401 Authentication Error

## ❌ Error You're Seeing
```
Upload failed: Error processing resume: OpenAI API authentication failed (401).
Please verify: 1. API key is valid and active 2. API key is properly formatted
Error details: { "error": { "message": "You didn't provide an API key..."
```

## ✅ Root Cause
The OpenAI API key is not configured. The application is trying to call OpenAI API without a valid API key.

## 🚀 Solution (Choose One)

### Option 1: Use Groq Instead (Recommended - Free!)
Groq offers free API access and is already configured in your app.

1. **Get Groq API Key** (2 minutes):
   - Go to: https://console.groq.com/
   - Sign in (or create free account)
   - Click "API Keys" → "Create API Key"
   - Copy the key (starts with `gsk_`)

2. **Set Environment Variable in Render**:
   - Go to: https://dashboard.render.com/
   - Click your TalentLens service
   - Click "Environment" in left sidebar
   - Add: 
     - **Key**: `GROQ_API_KEY`
     - **Value**: Your Groq API key
   - Click "Save Changes"
   - Wait 2-3 minutes for deployment

3. **Use Groq in the App**:
   - When uploading resume, select **AI Provider: Groq**
   - Upload and analyze - it will work!

### Option 2: Set Up OpenAI API Key

If you specifically want to use OpenAI:

1. **Get OpenAI API Key**:
   - Go to: https://platform.openai.com/api-keys
   - Sign in (requires account with payment method)
   - Click "Create new secret key"
   - Copy the key (starts with `sk-`)
   - ⚠️ **Note**: OpenAI is a paid service

2. **Set Environment Variable in Render**:
   - Go to: https://dashboard.render.com/
   - Click your TalentLens service
   - Click "Environment" in left sidebar
   - Add:
     - **Key**: `OPENAI_API_KEY`
     - **Value**: Your OpenAI API key
   - Click "Save Changes"
   - Wait 2-3 minutes for deployment

3. **Use OpenAI in the App**:
   - When uploading resume, select **AI Provider: OpenAI**
   - Upload and analyze

### Option 3: Use Gemini (Free with Limits)

Google's Gemini offers free tier:

1. **Get Gemini API Key**:
   - Go to: https://makersuite.google.com/app/apikey
   - Sign in with Google account
   - Click "Create API Key"
   - Enable "Generative Language API" in Google Cloud Console
   - Copy the key

2. **Set Environment Variable in Render**:
   - Go to: https://dashboard.render.com/
   - Click your TalentLens service
   - Click "Environment" in left sidebar
   - Add:
     - **Key**: `GEMINI_API_KEY`
     - **Value**: Your Gemini API key
   - Click "Save Changes"
   - Wait 2-3 minutes for deployment

3. **Use Gemini in the App**:
   - When uploading resume, select **AI Provider: Gemini**
   - Upload and analyze

## 📋 Quick Comparison

| Provider | Cost | Speed | Setup |
|----------|------|-------|-------|
| **Groq** | ✅ Free | ⚡ Fastest | Easy |
| **OpenAI** | 💰 Paid | 🚀 Fast | Easy |
| **Gemini** | ✅ Free (limits) | 🚀 Fast | Medium |

**Recommendation**: Use **Groq** - it's free, fast, and easy to set up!

## 🔍 How to Verify It's Working

After setting the environment variable in Render:

1. **Wait for deployment** (2-3 minutes)
2. **Check status** shows "Live" in Render
3. **Open your app**
4. **Go to Admin Settings** - you should see the API key (masked)
5. **Create a Job Requirement**
6. **Upload a resume** with the provider you configured
7. **Success!** ✅

## 🆘 Troubleshooting

### Still getting 401 error?

**Check Environment Variable**:
- Variable name must be exact (case-sensitive):
  - For Groq: `GROQ_API_KEY`
  - For OpenAI: `OPENAI_API_KEY`
  - For Gemini: `GEMINI_API_KEY`
- No spaces before/after the key value
- Key format:
  - Groq: starts with `gsk_`
  - OpenAI: starts with `sk-`

**Check Render Logs**:
1. Go to Render dashboard
2. Click on your service
3. Click "Logs" tab
4. Look for:
   ```
   DEBUG: API key loaded: Yes (starts with gsk_)
   ```
5. If you see "No (empty or null)", the variable isn't set correctly

### API key not working?

**Verify the key is active**:
- Go back to the provider's dashboard
- Check if the key is active/enabled
- If needed, create a new key
- Update the environment variable in Render

## 💡 Pro Tips

1. **Use Groq for Development**: It's free and fast
2. **Use OpenAI for Production** (if you have budget): Best quality
3. **Set All Three Keys**: Let users choose their preferred provider
4. **Admin Settings**: You can also update keys via Admin UI (temporary)

## 📚 Full Documentation

For complete details, see:
- `QUICK_FIX_GUIDE.md` - 5-minute quick fix
- `RENDER_ENV_VARIABLES_GUIDE.md` - Detailed Render setup
- `SOLUTION_COMPLETE.md` - Complete overview

---

## ✅ Quick Action Plan

**Right Now** (5 minutes):
1. Choose your provider (Groq recommended)
2. Get API key from provider's website
3. Add environment variable in Render
4. Wait for deployment
5. Test resume upload
6. ✅ Done!

**Your application will work perfectly after setting up one API key!** 🎉

