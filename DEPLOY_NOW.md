# 🎯 DEPLOY NOW - Exact Steps to Follow

## Your Backend is Ready! Follow These Exact Steps:

---

## ⚡ STEP 1: Open Render

1. Open your browser
2. Go to: **https://render.com**
3. Click **"Get Started for Free"**
4. Click **"Sign up with GitHub"**
5. Authorize Render when prompted
6. Verify email if asked

✅ You should now see the Render Dashboard

---

## ⚡ STEP 2: Create Service

1. Click the blue **"New +"** button (top right)
2. Click **"Blueprint"** from the dropdown
3. If first time: Click **"Connect account"** and authorize GitHub
4. You'll see a list of repositories
5. Find and click: **`vibha-2006/TalentLens`**
6. Render will show: "Blueprint detected in render.yaml"
7. Review the settings (they're pre-configured)
8. Click **"Apply"** button at the bottom

✅ You should see environment variables screen

---

## ⚡ STEP 3: Add Your API Keys

Click **"Add Environment Variable"** for each:

### Add Variable 1:
```
Key:   OPENAI_API_KEY
Value: [PASTE YOUR OPENAI KEY HERE]
```
> Get from: https://platform.openai.com/api-keys

Click **"Add"**

### Add Variable 2:
```
Key:   GEMINI_API_KEY
Value: [PASTE YOUR GEMINI KEY HERE]
```
> Get from: https://makersuite.google.com/app/apikey

Click **"Add"**

### Add Variable 3:
```
Key:   GROQ_API_KEY
Value: [PASTE YOUR GROQ KEY HERE]
```
> Get from: https://console.groq.com/keys

Click **"Add"**

### Add Variable 4:
```
Key:   FRONTEND_URL
Value: http://localhost:3000
```
> For now, use localhost. Update later with Vercel URL

Click **"Add"**

**⚠️ IMPORTANT:**
- Don't put quotes around the values
- Don't add spaces before/after
- Just paste the key directly

✅ All 4 variables should be listed

---

## ⚡ STEP 4: Deploy!

1. Click the big **"Create Web Service"** button at the bottom
2. You'll be taken to the deployment logs page
3. Watch the magic happen! ✨

### What You'll See:

**Minute 1-3:** Cloning and Building
```
==> Cloning from GitHub...
==> Running 'mvn clean install -DskipTests'
==> Downloading dependencies...
```

**Minute 3-5:** Compiling
```
==> Compiling 25 source files...
==> Building jar...
==> BUILD SUCCESS
```

**Minute 5-7:** Deploying
```
==> Starting service...
==> Tomcat started on port
==> Your service is live!
```

**Total Time:** 5-10 minutes (first deployment)

✅ Look for green **"Live"** status at the top

---

## ⚡ STEP 5: Get Your URL

1. At the top of the page, you'll see:
   ```
   https://talentlens-backend-XXXX.onrender.com
   ```
2. Copy this URL
3. Save it somewhere - you'll need it for the frontend!

✅ Your backend URL is ready

---

## ⚡ STEP 6: Test It!

### Option A: Using Browser

Open a new tab and paste:
```
https://your-backend-url.onrender.com/api/admin/settings
```

Replace `your-backend-url` with your actual URL.

**You should see:**
```json
{
  "openai": {
    "configured": true,
    "apiKey": "sk-****",
    "model": "gpt-3.5-turbo"
  },
  "gemini": {...},
  "groq": {...}
}
```

### Option B: Using PowerShell

```powershell
curl https://your-backend-url.onrender.com/api/admin/settings
```

✅ If you see JSON with your API keys (masked), it works!

---

## 🎉 SUCCESS!

Your backend is now live at:
```
https://your-backend-url.onrender.com
```

---

## 📝 What to Do Next

1. **Save your backend URL** - Write it down!

2. **Update Frontend**:
   - When you deploy frontend to Vercel, you'll use this URL
   - In your frontend code: `REACT_APP_API_URL=https://your-backend-url.onrender.com`

3. **Update FRONTEND_URL** (after deploying frontend):
   - Go to Render Dashboard
   - Click "talentlens-backend"
   - Click "Environment" tab
   - Find `FRONTEND_URL`
   - Change from `http://localhost:3000` to your Vercel URL
   - Click "Save Changes"
   - Service will restart automatically

4. **Test Full Application**:
   - Create a job requirement
   - Upload a resume
   - See AI-powered ranking!

---

## ⚠️ Important Notes

### Free Tier Behavior:
- Your backend **sleeps after 15 minutes** of no activity
- **First request** after sleep takes 30-60 seconds
- **Subsequent requests** are instant
- This is normal for free tier!

### Database:
- Uses H2 **in-memory** database
- Data **resets when service restarts**
- Good for demo/testing
- Can upgrade to PostgreSQL later

### Costs:
- **Render hosting**: FREE ✅
- **AI API calls**: You pay based on usage
  - OpenAI: ~$0.002 per analysis
  - Gemini: Free tier available
  - Groq: Free tier available

---

## 🐛 Troubleshooting

### ❌ Build Failed

**Check:**
1. Render logs for error message
2. Verify Java version is 17
3. Try building locally: `mvn clean install`

### ❌ Service Won't Start

**Check:**
1. All 4 environment variables are set
2. No extra spaces in API keys
3. API keys are valid (test on provider websites)

### ❌ CORS Error

**Check:**
1. `FRONTEND_URL` is set correctly
2. Includes `https://` protocol
3. No trailing slash
4. Matches your frontend URL exactly

### ❌ 404 Errors

**Check:**
1. URL includes `/api/` prefix
2. Correct: `/api/resumes`
3. Wrong: `/resumes`

---

## 📞 Need Help?

1. **Check logs**: Render Dashboard → Logs tab
2. **Read docs**: `RENDER_DEPLOYMENT_GUIDE.md`
3. **Render Community**: https://community.render.com
4. **Render Support**: support@render.com

---

## ✅ Deployment Checklist

Before you start, make sure you have:

- [ ] Render account created
- [ ] GitHub connected to Render
- [ ] OpenAI API key ready
- [ ] Gemini API key ready
- [ ] Groq API key ready
- [ ] 10 minutes of time

After deployment, verify:

- [ ] Service shows "Live" status
- [ ] `/api/admin/settings` returns JSON
- [ ] Backend URL is saved
- [ ] No errors in logs

---

## 🚀 Ready? Let's Go!

1. Open **https://render.com** now
2. Follow the steps above
3. Come back when you see "Live" status

**Good luck! You've got this! 🎉**

---

**Quick Reference:**

- **Render**: https://render.com
- **GitHub Repo**: https://github.com/vibha-2006/TalentLens
- **OpenAI Keys**: https://platform.openai.com/api-keys
- **Gemini Keys**: https://makersuite.google.com/app/apikey
- **Groq Keys**: https://console.groq.com/keys

---

**Created**: January 3, 2026  
**Status**: ✅ READY TO DEPLOY  
**Build**: ✅ VERIFIED SUCCESS

🎯 **START DEPLOYING NOW!**

