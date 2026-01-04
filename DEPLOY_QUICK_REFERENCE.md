# 🚀 TalentLens Render Deployment - Quick Reference Card

## 📋 Pre-Deployment Checklist
- ✅ Code pushed to GitHub: https://github.com/vibha-2006/TalentLens
- ✅ Dockerfile created and tested
- ✅ render.yaml configured
- ✅ Frontend API paths updated
- ✅ Backend resource handler configured
- ✅ Documentation complete

## 🔑 Required API Keys

Get your API keys before deploying:

| Provider | Get Key From | Format |
|----------|--------------|--------|
| **OpenAI** | https://platform.openai.com/api-keys | `sk-proj-...` |
| **Gemini** | https://makersuite.google.com/app/apikey | `AIza...` |
| **Groq** | https://console.groq.com/keys | `gsk_...` |

## 🎯 Deployment Steps (5 Minutes)

### Step 1: Sign Up
🔗 https://render.com → "Sign up with GitHub"

### Step 2: Create Service
Dashboard → "New +" → "Web Service" → Select "TalentLens"

### Step 3: Auto-Configure
Render detects `render.yaml` → Click "Apply"

### Step 4: Add Secrets
Add these environment variables:
```
SPRING_PROFILES_ACTIVE=prod
OPENAI_API_KEY=<your-openai-key>
GEMINI_API_KEY=<your-gemini-key>
GROQ_API_KEY=<your-groq-key>
AI_PROVIDER=openai
```

### Step 5: Deploy
Click "Create Web Service" → Wait 5-10 minutes → Done! 🎉

## 📊 What Gets Deployed

```
Your Render URL: https://talentlens-xxxx.onrender.com
│
├── / ...................... Homepage
├── /upload ................ Upload Resumes
├── /job-requirement ....... Job Requirements
├── /admin ................. Admin Settings
│
└── /api/
    ├── resumes ............ Resume APIs
    ├── job-requirements ... Job Requirement APIs
    └── admin/settings ..... Admin APIs
```

## 🧪 Post-Deployment Testing

Quick test sequence:
1. ✅ Open your Render URL
2. ✅ Go to Admin Settings
3. ✅ Test AI provider connections
4. ✅ Create a job requirement
5. ✅ Upload a test resume
6. ✅ Verify ranking appears

## ⚡ Quick Commands

### View Logs
Render Dashboard → Your Service → "Logs" tab

### Redeploy
Render Dashboard → "Manual Deploy" → "Deploy latest commit"

### Update Code
```bash
git add .
git commit -m "Your changes"
git push origin main
# Render auto-deploys!
```

## 🐛 Common Issues & Fixes

### Issue: "Service won't start"
✅ **Fix**: Check environment variables are set correctly

### Issue: "API calls failing"
✅ **Fix**: Verify API keys in Render environment variables

### Issue: "404 on page refresh"
✅ **Fix**: Ensure WebConfig.java is deployed (check logs)

### Issue: "First request slow (30s)"
✅ **Expected**: Free tier spins down after 15 min inactivity

## 📱 Mobile Testing

Test on mobile devices:
- Chrome (Android)
- Safari (iOS)
- Responsive layout should adapt

## 🔒 Security Checklist

- ✅ API keys stored as environment variables
- ✅ Secrets marked as "secret" in Render
- ✅ HTTPS enforced automatically
- ✅ CORS configured properly
- ✅ Non-root container user

## 📈 Performance Expectations

| Metric | Value |
|--------|-------|
| Cold start | 20-30 seconds |
| Warm response | < 500ms |
| Build time | 5-10 minutes |
| Memory usage | 300-500 MB |

## 💰 Free Tier Limits

- ✅ 750 hours/month (enough for demo)
- ✅ Unlimited requests
- ⚠️ Spins down after 15 min idle
- ⚠️ Slower build times

## 📚 Documentation

| File | Purpose |
|------|---------|
| `RENDER_DEPLOYMENT_STEPS.md` | Full step-by-step guide |
| `DOCKER_QUICK_START.md` | Developer reference |
| `DOCKER_DEPLOYMENT_GUIDE.md` | Technical details |
| `DOCKER_DEPLOYMENT_SUMMARY.md` | Architecture overview |

## 🎯 Success Criteria

Your deployment is successful when:
- ✅ URL loads without errors
- ✅ All pages accessible
- ✅ Can upload resumes
- ✅ AI analysis works
- ✅ Admin settings functional
- ✅ No console errors

## 📞 Support Links

- **Render Docs**: https://render.com/docs
- **Render Status**: https://status.render.com
- **GitHub Repo**: https://github.com/vibha-2006/TalentLens
- **OpenAI Docs**: https://platform.openai.com/docs
- **Gemini Docs**: https://ai.google.dev/docs
- **Groq Docs**: https://console.groq.com/docs

## 🎊 Ready to Deploy?

**All prerequisites are complete!**

👉 Go to: https://render.com
👉 Follow: RENDER_DEPLOYMENT_STEPS.md
👉 Time needed: ~30 minutes
👉 Cost: $0 (free tier)

---

**Status**: ✅ READY
**Repository**: https://github.com/vibha-2006/TalentLens
**Branch**: main
**Last Updated**: January 4, 2026

## 🚀 Let's Go Live!

