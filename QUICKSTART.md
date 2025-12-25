# Quick Start Guide - TalentLens

## Step 1: Get Your Gemini API Key (5 minutes)

1. Visit https://makersuite.google.com/app/apikey
2. Sign in with your Google account
3. Click "Create API Key"
4. Copy the API key

## Step 2: Configure Backend

1. Open `src/main/resources/application.properties`
2. Replace `YOUR_GEMINI_API_KEY_HERE` with your actual API key:
   ```properties
   gemini.api.key=YOUR_ACTUAL_KEY_HERE
   ```
3. Save the file

## Step 3: Start Backend

Open a terminal/command prompt:

```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn spring-boot:run
```

Wait until you see: "Started TalentLensApplication"

## Step 4: Start Frontend

Open a NEW terminal/command prompt:

```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm start
```

Your browser should automatically open to http://localhost:3000

## Step 5: Use the Application

### First Time Setup:
1. Click "Job Requirements" tab
2. Fill in job details (example below)
3. Click "Create Job Requirement"

**Example Job Requirement:**
```
Job Title: Senior Java Developer
Description: Looking for an experienced Java developer with Spring Boot expertise
Required Skills: Java, Spring Boot, REST API, SQL, Git
Preferred Skills: React, AWS, Docker, Microservices
Experience Level: Senior (6-10 years)
```

### Upload Resumes:
1. Click "Upload Resumes" tab
2. Select a PDF or Word resume
3. Click "Upload & Analyze"
4. Wait 10-30 seconds for AI analysis

### View Results:
1. Click "View Rankings" tab
2. See candidates ranked by match score
3. Click "View Details" for full analysis

## Supported File Formats
- PDF (.pdf)
- Word Documents (.docx)

## Tips
- ✅ Ensure backend is running before starting frontend
- ✅ Create job requirement before uploading resumes
- ✅ Better formatted resumes = better AI analysis
- ✅ Each resume takes ~10-30 seconds to analyze

## Need Help?
- Check README.md for detailed documentation
- Ensure your Gemini API key is valid
- Verify both backend (8080) and frontend (3000) are running

## Common Issues

**"Failed to load resumes"**
- Backend might not be running
- Check http://localhost:8080/api/resumes in browser

**"Upload failed"**
- Make sure you created a job requirement first
- Check backend logs for errors

**"Gemini API call failed"**
- Verify your API key is correct
- Check you have API quota available
- Try again in a few seconds

Happy Hiring! 🎯

