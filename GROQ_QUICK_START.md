# TalentLens - Quick Start with All AI Providers

## 🚀 Quick Start

TalentLens now supports **three AI providers**: OpenAI, Google Gemini, and Groq. Choose the one that best fits your needs!

## 📋 Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- Maven 3.6+
- API key from at least one provider (OpenAI, Gemini, or Groq)

## 🔑 Step 1: Get Your API Keys

### Option A: OpenAI (GPT-3.5/4)
1. Visit [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys)
2. Sign up or log in
3. Create a new API key
4. Copy the key (starts with `sk-proj-` or `sk-`)

**Pros**: High quality, well-established, good documentation
**Cons**: More expensive, requires payment after free trial

### Option B: Google Gemini
1. Visit [https://makersuite.google.com/app/apikey](https://makersuite.google.com/app/apikey)
2. Sign in with Google account
3. Create API key
4. Enable "Generative Language API" in Google Cloud Console

**Pros**: Free tier, good quality, large context window
**Cons**: Regional availability may vary

### Option C: Groq (Recommended for Speed)
1. Visit [https://console.groq.com/keys](https://console.groq.com/keys)
2. Sign up or log in
3. Create API key
4. Copy the key (starts with `gsk_`)

**Pros**: ⚡ Ultra-fast (10x faster), free tier, high quality (Llama 3.1)
**Cons**: Newer platform, fewer model options

## ⚙️ Step 2: Configure Application

Edit `src/main/resources/application.properties`:

```properties
# Choose your default provider (openai, gemini, or groq)
ai.provider=groq

# OpenAI Configuration
openai.api.key=YOUR_OPENAI_KEY_HERE
openai.model=gpt-3.5-turbo

# Gemini Configuration
gemini.api.key=YOUR_GEMINI_KEY_HERE
gemini.model=gemini-1.5-flash

# Groq Configuration (Fastest!)
groq.api.key=YOUR_GROQ_KEY_HERE
groq.model=llama-3.1-70b-versatile
```

> **Note**: You only need to configure the provider(s) you want to use.

## 🏗️ Step 3: Build the Application

### Build Backend
```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn clean package -DskipTests
```

### Build Frontend
```bash
cd frontend
npm install
npm run build
```

## ▶️ Step 4: Start the Application

### Start Backend
```bash
java -jar target/TalentLens-1.0-SNAPSHOT.jar
```
Backend will start on: `http://localhost:8080`

### Start Frontend (Development Mode)
```bash
cd frontend
npm start
```
Frontend will open at: `http://localhost:3000`

### OR Use Production Build
```bash
# Serve the built frontend
cd frontend
npx serve -s build -p 3000
```

## 🎯 Step 5: Use TalentLens

### 1. Create a Job Requirement
1. Open `http://localhost:3000`
2. Fill in job details:
   - Job Title (e.g., "Senior Java Developer")
   - Description
   - Required Skills (e.g., "Java, Spring Boot, React")
   - Experience Level
3. Click "Create Job Requirement"

### 2. Upload Resumes
1. **Select AI Provider** from dropdown:
   - OpenAI (GPT-3.5)
   - Google Gemini
   - Groq (Llama 3.1) ⚡ Fastest!

2. **Choose upload method**:
   - **Upload from Computer**: Select PDF/DOCX files
   - **Import from Google Drive**: Enter folder ID (optional)

3. Click "Upload & Analyze" or "Import from Drive"

### 3. View Results
- Resumes are analyzed in real-time
- See match scores (0-100)
- View extracted skills and experience
- Read detailed AI analysis
- Resumes are ranked by match score

## 🎨 Switching Between Providers

### In the UI
Simply select a different provider from the dropdown before uploading. Each resume can use a different provider!

### Set Default Provider
Edit `application.properties`:
```properties
ai.provider=groq  # or openai, or gemini
```

### Via API
```bash
curl -X POST http://localhost:8080/api/resumes/upload \
  -F "file=@resume.pdf" \
  -F "aiProvider=groq"
```

## ⚡ Provider Comparison

| Provider | Speed | Quality | Cost | Free Tier | Best For |
|----------|-------|---------|------|-----------|----------|
| **Groq** | ⭐⭐⭐⭐⭐ (1-2s) | ⭐⭐⭐⭐⭐ | 💰 | ✅ Generous | **Speed & Value** |
| **OpenAI** | ⭐⭐⭐ (5-10s) | ⭐⭐⭐⭐⭐ | 💰💰💰 | ⚠️ Limited | Quality |
| **Gemini** | ⭐⭐⭐⭐ (3-6s) | ⭐⭐⭐⭐ | 💰💰 | ✅ Yes | Balance |

## 🔍 Testing Each Provider

### Test OpenAI
```bash
mvn test -Dtest=OpenAIServiceTest
```

### Test Gemini
```bash
mvn test -Dtest=GeminiServiceTest
```

### Test Groq
```bash
mvn test -Dtest=GroqServiceTest
```

### Test All Providers
```bash
mvn test -Dtest=AIProviderFactoryTest
```

## 🐛 Troubleshooting

### OpenAI Errors
- **401 Unauthorized**: Invalid API key
- **429 Rate Limit**: Quota exceeded, upgrade plan or wait
- **Insufficient Credits**: Add payment method

### Gemini Errors
- **404 Not Found**: Enable "Generative Language API" in Google Cloud
- **403 Forbidden**: Check API key permissions
- **Model Not Available**: Try `gemini-1.5-flash` or `gemini-pro`

### Groq Errors
- **401 Unauthorized**: Check API key starts with `gsk_`
- **429 Rate Limit**: Wait or upgrade plan
- **Slow Response**: Normal for first request (cold start)

### General Issues
- **Backend not starting**: Check if port 8080 is free
- **Frontend not loading**: Clear browser cache
- **No job requirement**: Create a job before uploading resumes

## 📱 Using the UI

### AI Provider Selector
Located at the top of the "Upload Resumes" section:
```
AI Provider: [OpenAI (GPT-3.5) ▼]
```
Click dropdown to switch between:
- OpenAI (GPT-3.5)
- Google Gemini
- Groq (Llama 3.1)

### Resume Upload
- Supports: PDF, DOC, DOCX
- Max size: 10MB per file
- Analysis time: 1-10 seconds (depending on provider)

### Google Drive Integration
- Enter folder ID or leave blank for root
- Imports all supported files from folder
- Batch processing with selected AI provider

## 💡 Best Practices

### For Testing/Development
**Use Groq**:
- Free tier is generous
- Ultra-fast responses
- High quality results

### For Production (High Volume)
**Use Groq**:
- Best speed-to-cost ratio
- Scales well
- Reliable performance

### For Maximum Quality
**Use OpenAI GPT-4**:
- Change model to `gpt-4`
- Best understanding of nuanced requirements
- More expensive

### For Free Tier
**Use Gemini or Groq**:
- Both have generous free tiers
- Gemini: Larger context window
- Groq: Faster responses

## 🚀 Performance Tips

1. **Use Groq for best speed**: Typically 1-2 seconds per resume
2. **Batch uploads**: Upload multiple resumes at once
3. **Clear job descriptions**: Better AI analysis results
4. **Specific skills**: List clear, specific required skills
5. **Test providers**: Try all three to see what works best

## 📊 Sample Results

### Example Analysis
```
Candidate: John Doe
Match Score: 92/100
Skills: Java, Spring Boot, React, AWS, Docker
Experience: 5+ years in full-stack development

Analysis:
"Excellent match for the Senior Java Developer role.
Strong expertise in required technologies. Extensive
experience with microservices and cloud platforms.
Recommended for interview."
```

## 🎓 Next Steps

1. ✅ Set up at least one AI provider
2. ✅ Create your first job requirement
3. ✅ Upload test resumes
4. ✅ Compare results across providers
5. ✅ Choose your preferred provider
6. 🚀 Start hiring talent!

## 📚 Additional Resources

- **Groq Integration**: See `GROQ_INTEGRATION_GUIDE.md`
- **Architecture**: See `ARCHITECTURE.md`
- **Multi-Provider Details**: See `MULTI_AI_PROVIDER_FEATURE.md`
- **OpenAI Setup**: See `OPENAI_QUICK_START.md`

## ☎️ Support

For issues or questions:
1. Check the relevant provider documentation
2. Review error messages in console
3. Check application.properties configuration
4. Verify API keys are active

## 🎉 You're Ready!

Start uploading resumes and let AI help you find the best candidates!

**Recommended**: Start with Groq for the best experience - it's fast, free, and high-quality!

---

*Last Updated: December 4, 2025*
*Version: 1.0*

