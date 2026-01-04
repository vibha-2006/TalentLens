# TalentLens - Docker Deployment Quick Start

## What Was Changed

### 1. Created Dockerfile
A multi-stage Dockerfile that:
- Builds React frontend (Node.js 18)
- Builds Spring Boot backend (Maven + Java 17)
- Packages both into a single container
- Uses Alpine Linux for minimal image size
- Runs as non-root user for security

### 2. Updated Frontend API Configuration
**File**: `frontend/src/services/api.js`
- Uses relative URLs (`/api`) in production
- Uses `http://localhost:8080/api` in development
- Automatically detects environment

### 3. Updated Backend Configuration
**File**: `src/main/java/org/example/config/WebConfig.java`
- Added resource handler to serve React static files
- Routes all non-API requests to `index.html` for React routing
- Maintains API CORS configuration

### 4. Updated Render Configuration
**File**: `render.yaml`
- Changed from Java runtime to Docker runtime
- Simplified configuration
- Added all necessary environment variables

### 5. Created .dockerignore
Optimizes Docker build by excluding:
- node_modules
- target directory
- IDE files
- Documentation files

## How It Works

```
┌─────────────────────────────────────────┐
│  User Request (browser)                 │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│  Render.com (Port 443 HTTPS)           │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│  Docker Container (Port 8080)          │
│  ┌───────────────────────────────────┐ │
│  │  Spring Boot Application          │ │
│  │  ┌─────────────┬─────────────┐   │ │
│  │  │  /api/*     │  /*         │   │ │
│  │  │  Backend    │  Frontend   │   │ │
│  │  │  APIs       │  React SPA  │   │ │
│  │  └─────────────┴─────────────┘   │ │
│  └───────────────────────────────────┘ │
└─────────────────────────────────────────┘
```

## Deployment Steps

### Step 1: Commit Changes to GitHub
```bash
git add .
git commit -m "Add Docker configuration for Render deployment"
git push origin main
```

### Step 2: Deploy to Render
1. Go to https://dashboard.render.com/
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Render will detect `render.yaml` automatically
5. Click "Apply" to use the configuration

### Step 3: Set Environment Variables
In Render dashboard, add these secrets:
- `OPENAI_API_KEY`: Your OpenAI API key
- `GEMINI_API_KEY`: Your Gemini API key
- `GROQ_API_KEY`: Your Groq API key

### Step 4: Deploy
Click "Create Web Service" - Render will:
1. Clone your repository
2. Build the Docker image (5-10 minutes)
3. Deploy the container
4. Provide a public URL

## Testing Locally (Optional)

If you have Docker installed:

### Build:
```bash
docker build -t talentlens:latest .
```

### Run:
```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e OPENAI_API_KEY=sk-... \
  -e GEMINI_API_KEY=AIza... \
  -e GROQ_API_KEY=gsk_... \
  talentlens:latest
```

### Access:
Open http://localhost:8080

## What Changed in Application Behavior

### Development Mode (npm start)
- Frontend runs on port 3000
- Backend runs on port 8080
- Frontend proxies API calls to backend
- Hot reload enabled

### Production Mode (Docker)
- Single container on port 8080
- Frontend served from `/`
- Backend APIs on `/api/*`
- No hot reload, optimized for performance

## URLs After Deployment

Your Render URL will be something like: `https://talentlens-xxxx.onrender.com`

| Path | Description |
|------|-------------|
| `/` | React frontend home page |
| `/upload` | Upload resumes page |
| `/job-requirement` | Job requirements page |
| `/admin` | Admin settings page |
| `/api/resumes` | Resume API endpoints |
| `/api/job-requirements` | Job requirement API endpoints |
| `/api/admin/settings` | Admin settings API endpoints |

## Troubleshooting

### Issue: "API calls failing"
**Solution**: Check that environment variables are set in Render

### Issue: "Page not found on refresh"
**Solution**: WebConfig should handle this - check logs

### Issue: "Build failing"
**Solution**: Check Render logs for specific error

### Issue: "Service unavailable"
**Solution**: Free tier spins down after 15 min - first request takes 30s

## Next Steps

1. **Push to GitHub**: Commit all changes
2. **Create Render Account**: Sign up at render.com
3. **Deploy**: Follow Step 2 above
4. **Configure**: Add API keys
5. **Test**: Upload resumes and verify functionality

## Important Notes

- ✅ Frontend and backend in single container
- ✅ Environment-aware API URLs
- ✅ React routing works (SPA)
- ✅ Optimized for production
- ✅ Free tier compatible
- ✅ Auto-deploys on git push

## Files Modified

1. ✅ `Dockerfile` (new)
2. ✅ `.dockerignore` (new)
3. ✅ `render.yaml` (updated)
4. ✅ `frontend/src/services/api.js` (updated)
5. ✅ `src/main/java/org/example/config/WebConfig.java` (updated)

Ready to deploy! 🚀

