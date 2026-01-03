# TalentLens Backend - Render Deployment Guide

## 📋 Prerequisites

Before deploying, ensure you have:
- ✅ GitHub account
- ✅ Code pushed to GitHub repository
- ✅ OpenAI, Gemini, and/or Groq API keys
- ✅ Render.com account (sign up at https://render.com)

---

## 🚀 Step-by-Step Deployment

### Step 1: Push Code to GitHub

If you haven't already pushed your code:

```powershell
# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit changes
git commit -m "Prepare for Render deployment"

# Add remote repository (replace with your GitHub repo URL)
git remote add origin https://github.com/YOUR_USERNAME/TalentLens.git

# Push to GitHub
git push -u origin main
```

---

### Step 2: Create Render Account

1. Go to https://render.com
2. Click **"Get Started for Free"**
3. Sign up using GitHub (recommended) or email
4. Verify your email if required

---

### Step 3: Deploy Backend on Render

#### Option A: Using render.yaml (Recommended)

1. **Connect Repository**:
   - Go to Render Dashboard: https://dashboard.render.com
   - Click **"New +"** → **"Blueprint"**
   - Click **"Connect account"** to link GitHub
   - Select your TalentLens repository
   - Render will automatically detect `render.yaml`

2. **Configure Service**:
   - Service Name: `talentlens-backend` (auto-filled)
   - Branch: `main` (or your default branch)
   - Click **"Apply"**

3. **Set Environment Variables**:
   - You'll see a list of required environment variables
   - Click **"Add Environment Variable"** for each:
   
   ```
   OPENAI_API_KEY = your_actual_openai_api_key
   GEMINI_API_KEY = your_actual_gemini_api_key
   GROQ_API_KEY = your_actual_groq_api_key
   FRONTEND_URL = https://your-frontend-app.vercel.app
   ```
   
   ⚠️ **Important**: Don't include quotes around values

4. **Deploy**:
   - Click **"Create Web Service"**
   - Wait for deployment (5-10 minutes for first deploy)
   - Monitor build logs in real-time

#### Option B: Manual Setup

If render.yaml doesn't work:

1. **Create Web Service**:
   - Click **"New +"** → **"Web Service"**
   - Connect your GitHub repository
   - Select TalentLens repository

2. **Configure Build Settings**:
   ```
   Name: talentlens-backend
   Branch: main
   Root Directory: (leave empty if project is at root)
   Runtime: Java
   Build Command: mvn clean install -DskipTests
   Start Command: java -Dserver.port=$PORT -jar target/TalentLens-1.0-SNAPSHOT.jar
   ```

3. **Select Plan**:
   - Choose **"Free"** plan
   - Note: App will sleep after 15 minutes of inactivity

4. **Advanced Settings**:
   - Click **"Advanced"**
   - Add environment variables (same as Option A)
   - Auto-Deploy: **Yes** (recommended)

5. **Create Web Service**:
   - Click **"Create Web Service"**
   - Wait for deployment

---

### Step 4: Verify Deployment

Once deployed, you'll receive a URL like:
```
https://talentlens-backend.onrender.com
```

**Test the API**:

1. **Check Health Endpoint**:
   ```powershell
   curl https://talentlens-backend.onrender.com/api/admin/settings
   ```

2. **Expected Response**:
   ```json
   {
     "openai": {...},
     "gemini": {...},
     "groq": {...}
   }
   ```

3. **Test Other Endpoints**:
   - GET /api/job-requirements
   - GET /api/resumes
   - POST /api/admin/settings

---

## 🔧 Post-Deployment Configuration

### Update Frontend to Use Render Backend

In your frontend project:

1. **Create/Update `.env.production`**:
   ```env
   REACT_APP_API_URL=https://talentlens-backend.onrender.com
   ```

2. **Update API Service** (if not using env variable):
   
   In `frontend/src/services/api.js`:
   ```javascript
   const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
   ```

3. **Redeploy Frontend** with updated environment variable

---

## 📊 Monitoring & Logs

### View Logs

1. Go to Render Dashboard
2. Click on your **talentlens-backend** service
3. Click **"Logs"** tab
4. View real-time application logs

### Check Service Status

- **Dashboard**: Shows service status (Live/Suspended)
- **Metrics**: View request count, response times
- **Events**: Deployment history and status

---

## 🔄 Updating the Application

### Automatic Deployments (Recommended)

When Auto-Deploy is enabled:
1. Push changes to GitHub
2. Render automatically detects changes
3. Rebuilds and redeploys service
4. Zero downtime (health checks ensure smooth transition)

### Manual Deployments

From Render Dashboard:
1. Go to your service
2. Click **"Manual Deploy"** → **"Deploy latest commit"**
3. Or select specific commit to deploy

---

## ⚙️ Environment Variables Management

### Update API Keys

1. Go to **Environment** tab in service
2. Find the variable to update
3. Click edit icon
4. Enter new value
5. Click **"Save Changes"**
6. Service will automatically restart

### Add New Variables

1. Click **"Add Environment Variable"**
2. Enter Key and Value
3. Click **"Save Changes"**

---

## 🐛 Troubleshooting

### Build Failures

**Issue**: Maven build fails
- **Solution**: Check logs for missing dependencies
- Run locally: `mvn clean install -DskipTests`
- Ensure `pom.xml` is valid

**Issue**: Java version mismatch
- **Solution**: Verify `system.properties` has `java.runtime.version=17`

### Runtime Errors

**Issue**: Service starts but crashes immediately
- **Solution**: Check application logs
- Verify environment variables are set
- Test locally with production profile: `mvn spring-boot:run -Dspring-boot.run.profiles=prod`

**Issue**: CORS errors from frontend
- **Solution**: 
  - Verify `FRONTEND_URL` environment variable is set correctly
  - Must include protocol: `https://your-app.vercel.app` (no trailing slash)
  - Check `WebConfig.java` is present and correct

**Issue**: AI API calls failing
- **Solution**:
  - Verify API keys are correct (no extra spaces)
  - Check API key validity on provider dashboards
  - Review logs for specific error messages

### Performance Issues

**Issue**: Service is slow or unresponsive
- **Solution**:
  - Free tier has limited resources (512MB RAM)
  - Optimize database queries
  - Consider upgrading to paid plan for better performance

**Issue**: Service sleeps after inactivity
- **Solution**:
  - Free tier sleeps after 15 minutes
  - First request after sleep takes 30-60 seconds
  - Upgrade to paid plan for 24/7 availability
  - Or use a cron job to ping every 14 minutes (not recommended for free tier ethics)

---

## 💰 Cost Considerations

### Free Tier Limits

- **750 hours/month** of runtime
- **512MB RAM**, **0.5 CPU**
- **100GB bandwidth/month**
- **Automatic sleep** after 15 minutes of inactivity
- **Multiple services** can share the 750 hours

### Paid Plans

Starting at **$7/month**:
- No sleep
- More resources
- Better performance
- Custom domains
- Priority support

---

## 🔐 Security Best Practices

1. **Never commit API keys** to GitHub
2. **Use environment variables** for all secrets
3. **Review .gitignore** before pushing
4. **Rotate API keys** regularly
5. **Enable 2FA** on Render account
6. **Monitor usage** to detect unauthorized access

---

## 📱 Render Dashboard URLs

- **Main Dashboard**: https://dashboard.render.com
- **Service Logs**: https://dashboard.render.com/web/YOUR_SERVICE_ID/logs
- **Environment**: https://dashboard.render.com/web/YOUR_SERVICE_ID/env
- **Settings**: https://dashboard.render.com/web/YOUR_SERVICE_ID/settings

---

## 🎯 Quick Commands Reference

### Local Testing with Production Profile
```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Build JAR Locally
```powershell
mvn clean install -DskipTests
```

### Test JAR Locally
```powershell
java -Dserver.port=8080 -Dspring.profiles.active=prod -jar target/TalentLens-1.0-SNAPSHOT.jar
```

### Test API Endpoints
```powershell
# Health check
curl https://talentlens-backend.onrender.com/api/admin/settings

# Get job requirements
curl https://talentlens-backend.onrender.com/api/job-requirements

# Get resumes
curl https://talentlens-backend.onrender.com/api/resumes
```

---

## 📞 Support & Resources

- **Render Documentation**: https://render.com/docs
- **Render Community**: https://community.render.com
- **Support**: support@render.com (response within 24-48 hours)
- **Status Page**: https://status.render.com

---

## ✅ Deployment Checklist

Before deploying, verify:

- [ ] Code is pushed to GitHub
- [ ] `render.yaml` is in root directory
- [ ] `system.properties` specifies Java 17
- [ ] `application-prod.properties` exists
- [ ] `.gitignore` excludes sensitive files
- [ ] All controllers have proper CORS configuration
- [ ] API keys are ready (OpenAI, Gemini, Groq)
- [ ] Build succeeds locally: `mvn clean install`
- [ ] Application runs locally with prod profile

After deployment, verify:

- [ ] Build completes successfully
- [ ] Application starts without errors
- [ ] Health endpoint responds: `/api/admin/settings`
- [ ] All API endpoints are accessible
- [ ] CORS allows frontend requests
- [ ] AI provider integrations work
- [ ] File uploads work correctly
- [ ] Frontend can communicate with backend

---

## 🎉 Success!

Once deployed, share your backend URL:
```
https://talentlens-backend.onrender.com
```

Update your frontend to use this URL and you're ready to demo! 🚀

---

**Last Updated**: January 3, 2026
**Version**: 1.0
**Author**: TalentLens Development Team

