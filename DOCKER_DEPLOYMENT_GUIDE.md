# Docker Deployment Guide for TalentLens

## Overview
This guide explains how to deploy TalentLens using Docker. The application uses a multi-stage Dockerfile that builds both the React frontend and Spring Boot backend into a single container.

## Architecture
- **Stage 1**: Builds React frontend using Node.js
- **Stage 2**: Builds Spring Boot backend with Maven and includes the frontend build
- **Stage 3**: Runtime container with Java 17 JRE

## Prerequisites
- Docker installed on your system
- API keys for AI providers (OpenAI, Gemini, or Groq)

## Local Docker Build and Run

### 1. Build the Docker Image
```bash
docker build -t talentlens:latest .
```

### 2. Run the Container
```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e OPENAI_API_KEY=your_openai_key \
  -e GEMINI_API_KEY=your_gemini_key \
  -e GROQ_API_KEY=your_groq_key \
  -e AI_PROVIDER=openai \
  talentlens:latest
```

### 3. Access the Application
Open your browser and navigate to: `http://localhost:8080`

## Render Deployment

### Prerequisites
1. Create a Render account at https://render.com
2. Connect your GitHub repository to Render

### Deployment Steps

#### 1. Push Code to GitHub
```bash
git add Dockerfile .dockerignore render.yaml
git commit -m "Add Docker configuration for Render deployment"
git push origin main
```

#### 2. Create Web Service in Render
1. Log in to [Render Dashboard](https://dashboard.render.com/)
2. Click **"New +"** → **"Web Service"**
3. Connect your GitHub repository
4. Render will auto-detect the `render.yaml` file

#### 3. Configure Environment Variables
In the Render dashboard, add the following environment variables:
- `OPENAI_API_KEY`: Your OpenAI API key
- `GEMINI_API_KEY`: Your Gemini API key
- `GROQ_API_KEY`: Your Groq API key
- `AI_PROVIDER`: Default provider (openai/gemini/groq)

#### 4. Deploy
- Click **"Create Web Service"**
- Render will automatically build and deploy your Docker container
- The build process takes 5-10 minutes

#### 5. Access Your Application
Once deployed, Render provides a URL like: `https://talentlens.onrender.com`

## Configuration Details

### Environment Variables
| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Spring profile to use | prod |
| `OPENAI_API_KEY` | OpenAI API key | - |
| `GEMINI_API_KEY` | Google Gemini API key | - |
| `GROQ_API_KEY` | Groq API key | - |
| `AI_PROVIDER` | Default AI provider | openai |
| `OPENAI_MODEL` | OpenAI model name | gpt-3.5-turbo |
| `GEMINI_MODEL` | Gemini model name | gemini-1.5-flash |
| `GROQ_MODEL` | Groq model name | llama-3.3-70b-versatile |

### Health Check
The application exposes a health check endpoint:
- **URL**: `/api/admin/settings`
- **Method**: GET
- **Response**: 200 OK if healthy

### Port Configuration
- **Default Port**: 8080
- **Render Port**: Automatically set via `$PORT` environment variable

## File Structure
```
TalentLens/
├── Dockerfile              # Multi-stage Docker build configuration
├── .dockerignore          # Files to exclude from Docker build
├── render.yaml            # Render deployment configuration
├── frontend/              # React frontend source
│   ├── src/
│   ├── public/
│   └── package.json
└── src/                   # Spring Boot backend source
    └── main/
        ├── java/
        └── resources/
            ├── application.properties
            └── application-prod.properties
```

## API Endpoints
Once deployed, the following endpoints are available:

### Frontend
- `https://your-app.onrender.com/` - React application

### Backend APIs
- `https://your-app.onrender.com/api/resumes` - Resume management
- `https://your-app.onrender.com/api/job-requirements` - Job requirements
- `https://your-app.onrender.com/api/admin/settings` - Admin settings

## Troubleshooting

### Build Failures
1. **Maven dependency issues**: Check `pom.xml` for correct dependencies
2. **Node.js build errors**: Verify `package.json` and frontend code
3. **Out of memory**: Docker may need more memory allocation

### Runtime Issues
1. **404 errors**: Ensure WebConfig is properly routing React routes
2. **API connection errors**: Check environment variables are set correctly
3. **AI provider errors**: Verify API keys and model names

### Render-Specific Issues
1. **Free tier limitations**: 
   - Service spins down after 15 minutes of inactivity
   - First request after spin-down takes ~30 seconds
2. **Build timeout**: Increase build timeout in Render settings
3. **Health check failures**: Ensure `/api/admin/settings` endpoint is accessible

## Performance Optimization

### Docker Image Size
Current image size: ~250-300 MB
- Frontend build: ~2 MB
- Backend JAR: ~50 MB
- JRE: ~200 MB

### Build Time
- Local build: 3-5 minutes
- Render build: 5-10 minutes

### Runtime Performance
- Memory usage: ~300-500 MB
- Startup time: 20-30 seconds
- Response time: <500ms for most endpoints

## Security Considerations
1. API keys are stored as environment variables (not in code)
2. Docker runs as non-root user
3. CORS is configured for API security
4. H2 console is disabled in production

## Updating the Application
1. Make code changes
2. Commit and push to GitHub
3. Render automatically rebuilds and redeploys

## Monitoring
Monitor your application in Render:
- **Logs**: View real-time logs in Render dashboard
- **Metrics**: CPU, memory, and bandwidth usage
- **Events**: Deployment history and status

## Support
For issues or questions:
1. Check Render logs for errors
2. Review application logs
3. Verify environment variables
4. Test AI provider connections in Admin Settings

## License
[Your License Here]

