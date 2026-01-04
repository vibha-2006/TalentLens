# 🚀 Deploy TalentLens to Render - Step by Step Guide

## ✅ Prerequisites Completed
- ✅ Docker configuration created
- ✅ Frontend and backend combined into single container
- ✅ Code pushed to GitHub: https://github.com/vibha-2006/TalentLens
- ✅ All files configured and tested

## 📋 What You Need
1. Render account (free): https://render.com
2. GitHub account connected to Render
3. API Keys for AI providers:
   - OpenAI API Key (from https://platform.openai.com/api-keys)
   - Gemini API Key (from https://makersuite.google.com/app/apikey)
   - Groq API Key (from https://console.groq.com/keys)

---

## 🎯 Step 1: Create Render Account

1. Go to https://render.com
2. Click **"Get Started"** or **"Sign Up"**
3. Choose **"Sign up with GitHub"**
4. Authorize Render to access your GitHub account
5. Complete the sign-up process

---

## 🎯 Step 2: Create New Web Service

1. Log in to Render Dashboard: https://dashboard.render.com
2. Click the **"New +"** button (top right)
3. Select **"Web Service"** from the dropdown
4. Click **"Connect account"** to connect GitHub (if not already connected)

---

## 🎯 Step 3: Select Repository

1. Find **"TalentLens"** in the repository list
2. Click **"Connect"**
3. Render will scan the repository and detect `render.yaml`

---

## 🎯 Step 4: Configure Service

### Option A: Use render.yaml (Recommended)
Render will detect the `render.yaml` file and show:
- **Name**: talentlens
- **Runtime**: Docker
- **Plan**: Free

Click **"Apply"** to use these settings.

### Option B: Manual Configuration
If render.yaml isn't detected, configure manually:

| Setting | Value |
|---------|-------|
| **Name** | talentlens |
| **Runtime** | Docker |
| **Region** | Oregon (US West) or closest to you |
| **Branch** | main |
| **Dockerfile Path** | ./Dockerfile |
| **Docker Build Context** | . |
| **Plan** | Free |

---

## 🎯 Step 5: Add Environment Variables

In the **Environment** section, add these variables:

### Required Variables:

| Key | Value | Secret? |
|-----|-------|---------|
| `SPRING_PROFILES_ACTIVE` | `prod` | No |
| `AI_PROVIDER` | `openai` | No |
| `OPENAI_API_KEY` | Your OpenAI key | ✅ Yes |
| `GEMINI_API_KEY` | Your Gemini key | ✅ Yes |
| `GROQ_API_KEY` | Your Groq key | ✅ Yes |

### Optional Variables (with defaults):

| Key | Default Value | Description |
|-----|---------------|-------------|
| `OPENAI_MODEL` | gpt-3.5-turbo | OpenAI model to use |
| `GEMINI_MODEL` | gemini-1.5-flash | Gemini model to use |
| `GROQ_MODEL` | llama-3.3-70b-versatile | Groq model to use |

### How to Add Variables:
1. Click **"Add Environment Variable"**
2. Enter the **Key** (e.g., `OPENAI_API_KEY`)
3. Enter the **Value** (your actual API key)
4. Toggle **"Secret"** for API keys
5. Repeat for each variable

---

## 🎯 Step 6: Configure Advanced Settings (Optional)

### Health Check
- **Path**: `/api/admin/settings`
- **Interval**: 30 seconds

### Auto-Deploy
- ✅ Enable **"Auto-Deploy"** for automatic deployments on git push

---

## 🎯 Step 7: Deploy

1. Review all settings
2. Click **"Create Web Service"**
3. Render will start building your Docker image

### Build Process (5-10 minutes):
```
[1/6] Building frontend...
[2/6] Installing Node dependencies...
[3/6] Building React app...
[4/6] Building backend...
[5/6] Packaging JAR...
[6/6] Creating runtime container...
```

You can watch the build logs in real-time.

---

## 🎯 Step 8: Wait for Deployment

### Build Status Indicators:
- 🟡 **Building**: Docker image is being created
- 🟢 **Live**: Service is running and accessible
- 🔴 **Failed**: Build or deployment error (check logs)

### Timeline:
- **First build**: 5-10 minutes
- **Subsequent builds**: 3-7 minutes (cached layers)

---

## 🎯 Step 9: Access Your Application

Once deployment shows **"Live"**:

1. Render provides a URL like: `https://talentlens.onrender.com`
2. Click the URL or copy it
3. Open in your browser

### Expected Landing Page:
You should see the TalentLens home page with navigation to:
- Upload Resumes
- Job Requirements
- Admin Settings

---

## 🧪 Step 10: Test the Application

### Test 1: Admin Settings
1. Navigate to **Admin Settings** (gear icon)
2. Verify all three AI providers are configured
3. Click **"Test Connection"** for each provider
4. Should see ✅ "Connection successful"

### Test 2: Job Requirement
1. Navigate to **Job Requirements**
2. Create a new job requirement
3. Add required skills (e.g., "Java, React, AWS")
4. Set minimum experience
5. Click **"Set as Active"**

### Test 3: Upload Resume
1. Navigate to **Upload Resumes**
2. Select AI Provider (OpenAI, Gemini, or Groq)
3. Upload a test resume (PDF or DOC)
4. Verify it appears in the ranked list
5. Check the skill match percentage

---

## 🔧 Troubleshooting

### Issue: Build Failed
**Symptoms**: Red error in build logs

**Solutions**:
1. Check build logs for specific error
2. Common issues:
   - Maven dependency download failure → Retry build
   - NPM dependency issue → Check package.json
   - Out of memory → Contact Render support

### Issue: Service Won't Start
**Symptoms**: Build succeeds but service shows "Failed"

**Solutions**:
1. Check runtime logs
2. Verify environment variables are set
3. Check health check endpoint: `/api/admin/settings`

### Issue: API Calls Failing
**Symptoms**: Frontend loads but uploads fail

**Solutions**:
1. Verify API keys are correct in Environment Variables
2. Check they are marked as "Secret"
3. Test connection in Admin Settings
4. Check runtime logs for API errors

### Issue: 404 on Page Refresh
**Symptoms**: Routes work initially but fail on browser refresh

**Solutions**:
1. Verify WebConfig.java is deployed correctly
2. Check that static resources are being served
3. Look for errors in logs about resource handling

### Issue: Slow First Request
**Symptoms**: First request takes 30+ seconds

**Explanation**: 
- Free tier services spin down after 15 minutes of inactivity
- First request wakes up the service
- This is normal behavior on free tier
- Subsequent requests are fast (<1 second)

---

## 📊 Monitoring Your Application

### View Logs
1. Go to Render Dashboard
2. Click on your **talentlens** service
3. Click **"Logs"** tab
4. View real-time logs

### Check Metrics
1. Click **"Metrics"** tab
2. View:
   - CPU usage
   - Memory usage
   - Network traffic
   - Response times

### View Events
1. Click **"Events"** tab
2. See deployment history
3. Track build and deploy times

---

## 🔄 Updating Your Application

### Automatic Deployment:
Every time you push to GitHub main branch:
```bash
git add .
git commit -m "Your changes"
git push origin main
```

Render automatically:
1. Detects the push
2. Builds new Docker image
3. Deploys with zero downtime
4. Sends notification email

### Manual Deployment:
1. Go to Render Dashboard
2. Click **"Manual Deploy"**
3. Select **"Deploy latest commit"**
4. Click **"Deploy"**

---

## 💰 Cost Information

### Free Tier Includes:
- ✅ 750 hours/month of runtime
- ✅ Automatic HTTPS/SSL
- ✅ Custom domain support
- ✅ Automatic deployments
- ✅ Build minutes included

### Limitations:
- ⚠️ Service spins down after 15 min inactivity
- ⚠️ Slower build times
- ⚠️ Shared resources
- ⚠️ Limited bandwidth

### Upgrading:
For production use, consider upgrading to:
- **Starter Plan**: $7/month (no spin-down)
- **Standard Plan**: $25/month (more resources)

---

## 🔐 Security Best Practices

1. ✅ **Never commit API keys** to GitHub
2. ✅ **Use Render's environment variables** for secrets
3. ✅ **Enable "Secret" toggle** for sensitive values
4. ✅ **Rotate API keys** periodically
5. ✅ **Monitor usage** in AI provider dashboards

---

## 📝 Environment Variable Reference

Copy this template to set up your environment:

```bash
# Required
SPRING_PROFILES_ACTIVE=prod
AI_PROVIDER=openai

# OpenAI (get from: https://platform.openai.com/api-keys)
OPENAI_API_KEY=sk-...

# Gemini (get from: https://makersuite.google.com/app/apikey)
GEMINI_API_KEY=AIza...

# Groq (get from: https://console.groq.com/keys)
GROQ_API_KEY=gsk_...

# Optional - Model Selection
OPENAI_MODEL=gpt-3.5-turbo
GEMINI_MODEL=gemini-1.5-flash
GROQ_MODEL=llama-3.3-70b-versatile
```

---

## 🎉 Success Checklist

Before considering deployment complete, verify:

- [ ] Service shows "Live" in Render dashboard
- [ ] URL is accessible in browser
- [ ] Frontend loads correctly
- [ ] All navigation links work
- [ ] Admin settings page accessible
- [ ] All AI providers show in dropdown
- [ ] Test connection works for at least one provider
- [ ] Can create job requirement
- [ ] Can upload resume
- [ ] Resume gets analyzed and ranked
- [ ] No errors in Render logs
- [ ] Custom domain configured (optional)

---

## 📞 Support Resources

### Render Support
- Documentation: https://render.com/docs
- Community Forum: https://community.render.com
- Status Page: https://status.render.com

### TalentLens Support
- GitHub Issues: https://github.com/vibha-2006/TalentLens/issues
- Documentation: See DOCKER_DEPLOYMENT_GUIDE.md

### AI Provider Support
- OpenAI: https://platform.openai.com/docs
- Gemini: https://ai.google.dev/docs
- Groq: https://console.groq.com/docs

---

## 🚀 Next Steps After Deployment

1. **Custom Domain**: Add your own domain in Render settings
2. **SSL Certificate**: Automatically provided by Render
3. **Monitoring**: Set up uptime monitoring (e.g., UptimeRobot)
4. **Backup**: Regularly backup your job requirements data
5. **Analytics**: Consider adding Google Analytics
6. **Feedback**: Share with users and collect feedback

---

## 🎊 Congratulations!

Your TalentLens application is now live and accessible worldwide! 🌍

Share your URL: `https://talentlens.onrender.com`

Happy hiring! 💼✨

