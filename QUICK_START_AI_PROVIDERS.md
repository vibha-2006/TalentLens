# Quick Start: Using Multiple AI Providers

## What's New? 🎉

TalentLens now supports **BOTH OpenAI and Google Gemini AI** for resume analysis!

## How to Use

### Step 1: Start the Application

Run the start script:
```bash
start-talentlens.bat
```

Or manually:
```bash
# Terminal 1 - Backend
mvn spring-boot:run

# Terminal 2 - Frontend
cd frontend
npm start
```

### Step 2: Access the Application

Open browser: **http://localhost:3000**

### Step 3: Select AI Provider

1. Click on **"Upload Resumes"** tab
2. You'll see a new **"AI Provider"** dropdown at the top
3. Choose your preferred AI:
   - **OpenAI (GPT-3.5)** - Fast, reliable, excellent quality
   - **Google Gemini** - Very fast, cost-effective

### Step 4: Upload Resume

1. Click "Choose File" and select a PDF or Word resume
2. Click **"Upload & Analyze"**
3. The selected AI provider will analyze the resume

### Step 5: View Results

- Go to **"View Rankings"** tab
- See analyzed resumes ranked by match score
- View detailed analysis including:
  - Candidate name, email, phone
  - Skills extracted
  - Experience summary
  - Match score (0-100)
  - Detailed analysis

## Switching Between Providers

You can switch AI providers **anytime**:
- Each resume can be analyzed with a different provider
- No need to restart the application
- Simply select from the dropdown before uploading

## Visual Guide

```
┌─────────────────────────────────────────┐
│  🎯 TalentLens                          │
│  AI-Powered Resume Shortlisting         │
│  with OpenAI & Gemini                   │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│  Upload Resumes                         │
├─────────────────────────────────────────┤
│                                          │
│  AI Provider: [OpenAI (GPT-3.5) ▼]     │
│               [Google Gemini     ]      │
│                                          │
│  ┌────────────────────────────────┐    │
│  │  Choose File  [resume.pdf]      │    │
│  └────────────────────────────────┘    │
│                                          │
│  [Upload & Analyze]                     │
│                                          │
└─────────────────────────────────────────┘
```

## Configuration (Optional)

### Set Default Provider

Edit `application.properties`:
```properties
# Options: openai or gemini
ai.provider=openai
```

### Configure API Keys

```properties
# OpenAI
openai.api.key=your-openai-key
openai.model=gpt-3.5-turbo

# Gemini
gemini.api.key=your-gemini-key
gemini.model=gemini-1.5-flash-latest
```

### Get API Keys

**OpenAI:**
- Visit: https://platform.openai.com/api-keys
- Create new key
- Copy (starts with `sk-`)

**Gemini:**
- Visit: https://makersuite.google.com/app/apikey
- Create API key
- Enable "Generative Language API" in Google Cloud Console
- Copy (starts with `AIza`)

## Features Comparison

| Feature | OpenAI GPT-3.5 | Google Gemini |
|---------|----------------|---------------|
| Speed | Fast ⚡ | Very Fast ⚡⚡ |
| Cost | Medium 💰💰 | Low 💰 |
| Quality | Excellent ⭐⭐⭐⭐⭐ | Very Good ⭐⭐⭐⭐ |
| Best For | Detailed analysis | High volume |

## When to Use Which?

### Use OpenAI when:
- ✅ You need detailed, comprehensive analysis
- ✅ Working with complex job requirements
- ✅ Quality is more important than cost
- ✅ Processing English language resumes

### Use Gemini when:
- ✅ Processing high volume of resumes
- ✅ Need faster turnaround time
- ✅ Cost optimization is important
- ✅ Working with multi-language resumes

## Tips & Tricks

### 1. Batch Processing
- Use Gemini for initial screening (fast & cheap)
- Use OpenAI for final candidates (detailed analysis)

### 2. Create Job Requirement First
- Always create a job requirement before uploading resumes
- Go to "Job Requirements" tab
- Fill in job details, required skills
- This is used by both AI providers for matching

### 3. Compare Providers
- Upload same resume with both providers
- Compare analysis quality
- Choose the one that works best for your needs

### 4. Check Match Scores
- 90-100: Excellent match 🟢
- 75-89: Good match 🔵
- 60-74: Fair match 🟡
- Below 60: Poor match 🔴

## Troubleshooting

### "No active job requirement found"
**Solution**: Create a job requirement first
1. Go to "Job Requirements" tab
2. Fill in job details
3. Submit
4. Then upload resumes

### "API authentication failed"
**Solution**: Check API key
1. Verify key is correct in `application.properties`
2. Ensure key is active and has credits
3. For Gemini: Enable "Generative Language API"

### Provider selector not visible
**Solution**: Clear browser cache and refresh
```bash
Ctrl + Shift + R  (Windows/Linux)
Cmd + Shift + R   (Mac)
```

## Example Workflow

```
1. Create Job Requirement
   └─> Job Title: "Senior Java Developer"
   └─> Skills: "Java, Spring Boot, React"

2. Select AI Provider
   └─> Choose: "OpenAI (GPT-3.5)"

3. Upload Resume
   └─> File: "john_doe_resume.pdf"

4. View Analysis
   └─> Match Score: 85/100 ✅
   └─> Analysis: "Strong Java skills, 5+ years..."
   └─> Skills: "Java, Spring Boot, React, Docker"

5. Try Another Provider
   └─> Switch to: "Google Gemini"
   └─> Upload same or different resume
   └─> Compare results
```

## Advanced Usage

### API Integration

Upload with specific provider via API:
```bash
curl -X POST "http://localhost:8080/api/resumes/upload?aiProvider=gemini" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@resume.pdf"
```

### Programmatic Access

```java
// In your Java code
AIService service = aiProviderFactory.getAIService("gemini");
AIAnalysisResponse response = service.analyzeResume(resumeText, jobReqs);
System.out.println("Match Score: " + response.getMatchScore());
```

## Support & Documentation

- **Full Guide**: See `AI_PROVIDER_GUIDE.md`
- **Technical Details**: See `AI_PROVIDER_SUMMARY.md`
- **General Help**: See `README.md`

## Summary

You now have the power to:
- ✅ Choose between OpenAI and Gemini
- ✅ Switch providers on-the-fly
- ✅ Optimize for speed, cost, or quality
- ✅ Process resumes efficiently

**Enjoy using TalentLens with multiple AI providers! 🚀**

---

**Quick Commands:**
```bash
# Start application
.\start-talentlens.bat

# Access frontend
http://localhost:3000

# Access backend
http://localhost:8080

# View H2 database
http://localhost:8080/h2-console
```

**Need Help?**
Check the logs in the terminal windows for any errors or issues.

