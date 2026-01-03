# 🚀 Deploy TalentLens Backend to Render - Quick Start

## ⚡ 5-Minute Deployment Guide

Follow these steps to deploy your backend to Render:

---

## Step 1: Sign Up for Render ✍️

1. Open your browser and go to: **https://render.com**
2. Click **"Get Started for Free"**
3. Choose **"Sign up with GitHub"** (Recommended)
4. Authorize Render to access your GitHub repositories
5. Verify your email if prompted

✅ **Checkpoint**: You should now be on the Render Dashboard

---

## Step 2: Create New Web Service 🌐

### Option A: Using Blueprint (Easiest)

1. From the Render Dashboard, click **"New +"** in the top right
2. Select **"Blueprint"**
3. Click **"Connect account"** if this is your first time
4. You'll see a list of your GitHub repositories
5. Find and select: **`vibha-2006/TalentLens`**
6. Render will automatically detect `render.yaml`
7. Review the configuration and click **"Apply"**

### Option B: Manual Setup (Alternative)

1. From the Render Dashboard, click **"New +"**
2. Select **"Web Service"**
3. Connect your GitHub account
4. Select repository: **`vibha-2006/TalentLens`**
5. Fill in the form:
   ```
   Name: talentlens-backend
   Region: Choose closest to you (e.g., Oregon, Frankfurt, Singapore)
   Branch: main
   Root Directory: (leave empty)
   Runtime: Java
   Build Command: mvn clean install -DskipTests
   Start Command: java -Dserver.port=$PORT -jar target/TalentLens-1.0-SNAPSHOT.jar
   ```
6. Click **"Advanced"** to add environment variables (see Step 3)

✅ **Checkpoint**: You should see a service configuration screen

---

## Step 3: Add Environment Variables 🔑

This is the most important step! Click **"Environment"** tab or **"Add Environment Variable"** button.

Add these four variables:

### Variable 1: OpenAI API Key
```
Key:   OPENAI_API_KEY
Value: sk-proj-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```
> Get your key from: https://platform.openai.com/api-keys

### Variable 2: Gemini API Key
```
Key:   GEMINI_API_KEY
Value: AIzaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```
> Get your key from: https://makersuite.google.com/app/apikey

### Variable 3: Groq API Key
```
Key:   GROQ_API_KEY
Value: gsk_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```
> Get your key from: https://console.groq.com/keys

### Variable 4: Frontend URL
```
Key:   FRONTEND_URL
Value: https://your-frontend-app.vercel.app
```
> ⚠️ Replace with your actual frontend URL (or use * for testing: `http://localhost:3000`)
> ⚠️ Don't add quotes around the values!

**Important Notes:**
- Values should NOT have quotes
- No spaces before or after the values
- For testing, you can temporarily use `*` for FRONTEND_URL
- You can add/update these later from the Environment tab

✅ **Checkpoint**: All 4 environment variables should be listed

---

## Step 4: Deploy! 🎉

1. Click **"Create Web Service"** (or "Deploy" if using Blueprint)
2. You'll be taken to the deployment logs page
3. Watch the build process in real-time

### What You'll See:

**Phase 1: Building (3-5 minutes)**
```
==> Cloning from GitHub...
==> Building with Maven...
==> Downloading dependencies...
==> Compiling Java files...
==> BUILD SUCCESS
```

**Phase 2: Deploying (1-2 minutes)**
```
==> Starting service...
==> Health check passed
==> Your service is live!
```

### Common Log Messages:

✅ **Success Signs:**
```
Started Main in X.XXX seconds
Tomcat started on port(s): XXXXX (http)
Live
```

❌ **Error Signs:**
```
Application failed to start
BUILD FAILURE
Error creating bean
```

✅ **Checkpoint**: You should see **"Live"** status in green

---

## Step 5: Get Your Backend URL 🌍

Once deployment is complete:

1. You'll see your service URL at the top of the page
2. It will look like: **`https://talentlens-backend.onrender.com`**
3. Click the URL to open it (you'll get a 404 - that's normal, the API endpoints need `/api/`)

✅ **Checkpoint**: Note down your backend URL

---

## Step 6: Test Your Backend 🧪

### Test 1: Health Check

Open a new browser tab or use PowerShell:

```powershell
curl https://talentlens-backend.onrender.com/api/admin/settings
```

**Expected Response:**
```json
{
  "openai": {
    "configured": true,
    "apiKey": "sk-proj-****",
    "model": "gpt-3.5-turbo"
  },
  "gemini": {
    "configured": true,
    "apiKey": "AIza****",
    "model": "gemini-1.5-flash"
  },
  "groq": {
    "configured": true,
    "apiKey": "gsk_****",
    "model": "llama-3.3-70b-versatile"
  }
}
```

### Test 2: Job Requirements Endpoint

```powershell
curl https://talentlens-backend.onrender.com/api/job-requirements
```

**Expected Response:**
```json
[]
```
(Empty array is fine - no jobs created yet)

### Test 3: Resumes Endpoint

```powershell
curl https://talentlens-backend.onrender.com/api/resumes
```

**Expected Response:**
```json
[]
```

✅ **Checkpoint**: All three endpoints should respond successfully

---

## Step 7: Update Frontend Configuration 🔄

Now that your backend is live, update your frontend:

### Method 1: Using Environment Variable (Recommended)

1. Create/update `frontend/.env.production`:
   ```env
   REACT_APP_API_URL=https://talentlens-backend.onrender.com
   ```

2. Redeploy your frontend (if already deployed)

### Method 2: Direct Code Update

1. Open `frontend/src/services/api.js`
2. Find the line:
   ```javascript
   const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
   ```
3. Temporarily change to:
   ```javascript
   const API_URL = 'https://talentlens-backend.onrender.com';
   ```
4. Rebuild: `npm run build`

✅ **Checkpoint**: Frontend should now communicate with deployed backend

---

## 🎊 Success! Your Backend is Live!

Your TalentLens backend is now deployed and accessible at:
```
https://talentlens-backend.onrender.com
```

---

## 📊 Monitor Your Application

### View Logs
1. Go to Render Dashboard
2. Click on **"talentlens-backend"**
3. Click **"Logs"** tab
4. See real-time application logs

### Check Metrics
1. Click **"Metrics"** tab
2. View request count, response times, memory usage

### Update Environment Variables
1. Click **"Environment"** tab
2. Edit any variable
3. Service will automatically restart

---

## 🔄 Auto-Deployment is Enabled

From now on, whenever you push to GitHub:
1. Render detects the changes
2. Automatically rebuilds your application
3. Deploys the new version
4. Zero-downtime deployment

To disable: Go to **Settings** → **Auto-Deploy** → OFF

---

## ⚠️ Important Notes

### Free Tier Behavior
- ⏰ **Service sleeps after 15 minutes** of inactivity
- ⏳ **First request after sleep**: 30-60 seconds to wake up
- ⚡ **Subsequent requests**: Normal speed
- 🔄 **Database resets** on restart (H2 in-memory)

### Cost
- ✅ **Completely FREE** for backend hosting
- 💰 **You pay only for AI API usage** (OpenAI, Gemini, Groq)

### Security
- 🔒 Never commit API keys to GitHub
- 🔑 Rotate keys every 90 days
- 👁️ Monitor usage on provider dashboards

---

## 🐛 Troubleshooting

### Problem: Build Failed

**Error Message:**
```
BUILD FAILURE
[ERROR] Failed to execute goal
```

**Solution:**
1. Check Render logs for specific error
2. Verify Java version (should be 17)
3. Try building locally: `mvn clean install`
4. Check `pom.xml` for syntax errors

---

### Problem: Application Won't Start

**Error Message:**
```
Application failed to start
Error creating bean
```

**Solution:**
1. Check environment variables are set correctly
2. No extra spaces in API keys
3. Review application logs for stack trace
4. Verify `application-prod.properties` exists

---

### Problem: CORS Errors

**Error Message:**
```
Access to XMLHttpRequest blocked by CORS policy
```

**Solution:**
1. Verify `FRONTEND_URL` environment variable
2. Must include `https://` protocol
3. No trailing slash
4. Example: `https://talentlens.vercel.app`
5. For testing, temporarily set to `*`

---

### Problem: 404 Not Found

**Error Message:**
```
404 - Page not found
```

**Solution:**
1. Verify URL includes `/api/` prefix
2. Correct: `https://backend.onrender.com/api/resumes`
3. Wrong: `https://backend.onrender.com/resumes`
4. Check controller mappings in code

---

### Problem: Service is Slow

**Possible Reasons:**
1. Service was sleeping (first request after 15 min)
2. Free tier has limited resources (512MB RAM)
3. Large AI API responses

**Solution:**
- Wait for service to warm up
- Optimize queries
- Consider upgrading to paid plan ($7/month)

---

## 📞 Need Help?

- **Render Documentation**: https://render.com/docs
- **Render Community**: https://community.render.com
- **Render Status**: https://status.render.com
- **GitHub Issues**: https://github.com/vibha-2006/TalentLens/issues

---

## ✅ Deployment Checklist

Print this and check off as you go:

- [ ] Created Render account
- [ ] Connected GitHub repository
- [ ] Selected TalentLens repository
- [ ] Added OPENAI_API_KEY environment variable
- [ ] Added GEMINI_API_KEY environment variable
- [ ] Added GROQ_API_KEY environment variable
- [ ] Added FRONTEND_URL environment variable
- [ ] Clicked "Create Web Service"
- [ ] Waited for build to complete (5-10 min)
- [ ] Service shows "Live" status
- [ ] Tested `/api/admin/settings` endpoint
- [ ] Tested `/api/job-requirements` endpoint
- [ ] Tested `/api/resumes` endpoint
- [ ] Updated frontend with backend URL
- [ ] Verified frontend can communicate with backend
- [ ] Checked application logs
- [ ] Noted down backend URL

---

## 🎯 What's Next?

1. **Deploy Frontend**: Follow similar steps for Vercel
2. **Test Full Application**: Upload a resume end-to-end
3. **Monitor Usage**: Keep an eye on AI API costs
4. **Share Your App**: Demo to friends/colleagues!

---

**🎉 Congratulations on deploying your backend!**

Your TalentLens backend is now live and ready to shortlist resumes using AI! 🚀

---

**Created**: January 3, 2026  
**Build Status**: ✅ Successful  
**GitHub Repo**: https://github.com/vibha-2006/TalentLens  
**Render Blueprint**: Ready

