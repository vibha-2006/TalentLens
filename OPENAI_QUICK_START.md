# Quick Start Guide - OpenAI Integration

## Prerequisites

1. Java 17 or higher
2. Maven 3.6+
3. Node.js 14+ (for frontend)
4. OpenAI API Key

## Step 1: Get OpenAI API Key

1. Go to https://platform.openai.com/
2. Sign up or log in
3. Navigate to **API Keys** in your account settings
4. Click **"Create new secret key"**
5. Copy the key (it starts with `sk-`)

⚠️ **Important**: Store this key securely. You won't be able to see it again!

## Step 2: Configure the Application

Edit `src/main/resources/application.properties`:

```properties
# OpenAI API Configuration
openai.api.key=sk-your-actual-api-key-here
openai.model=gpt-3.5-turbo
```

## Step 3: Build the Backend

```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn clean package
```

## Step 4: Run the Tests

```bash
# Test OpenAI integration
mvn test -Dtest=OpenAIServiceTest

# If tests pass, you're ready!
```

Expected output:
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.example.service.OpenAIServiceTest
✓ API Call Successful!
✓ All response fields are properly initialized
✓ Service is properly configured
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Step 5: Start the Backend

```bash
mvn spring-boot:run
```

Or use the JAR:
```bash
java -jar target/TalentLens-1.0-SNAPSHOT.jar
```

The backend will start on http://localhost:8080

## Step 6: Build and Start the Frontend

```bash
cd frontend
npm install
npm start
```

The frontend will start on http://localhost:3000

## Step 7: Create a Job Requirement

1. Open http://localhost:3000
2. Fill in the job requirement form:
   - Job Title: "Senior Java Developer"
   - Required Skills: "Java, Spring Boot, React.js"
   - Preferred Skills: "Docker, Kubernetes"
   - Experience Level: "Senior"
   - Description: "Looking for experienced Java developer"
3. Click **"Create Job Requirement"**

## Step 8: Upload a Resume

1. Click on **"Upload Resume"** tab
2. Select a resume file (PDF, DOC, DOCX, or TXT)
3. Click **"Upload"**
4. Wait for OpenAI to analyze the resume (5-10 seconds)
5. View the results with match score and detailed analysis

## Verification Checklist

- ✅ Backend starts without errors
- ✅ Frontend connects to backend
- ✅ Can create job requirements
- ✅ Can upload resumes
- ✅ OpenAI analyzes resumes successfully
- ✅ Match scores are displayed (0-100)
- ✅ Detailed analysis is shown

## Common Issues

### Issue: "OpenAI API authentication failed (401)"

**Solution:**
```properties
# Make sure your API key starts with 'sk-'
openai.api.key=sk-your-key-here

# Not this:
openai.api.key=your-key-here  ❌
```

### Issue: "No active job requirement found"

**Solution:**
- Create a job requirement first before uploading resumes
- Only one job requirement can be active at a time

### Issue: "Rate limit exceeded (429)"

**Solution:**
- Wait 60 seconds before trying again
- Upgrade to paid tier on OpenAI platform
- Free tier has lower limits (3 requests per minute)

### Issue: Frontend can't connect to backend

**Solution:**
```bash
# Check if backend is running
curl http://localhost:8080/api/resumes

# Should return: []
```

## Testing with Sample Resume

Create a file `sample-resume.txt`:

```
John Doe
Email: john.doe@example.com
Phone: (555) 123-4567

PROFESSIONAL SUMMARY
Experienced Software Engineer with 5+ years in Java and Spring Boot development.

SKILLS
- Java, Spring Boot, React.js
- Docker, Kubernetes
- RESTful APIs, Microservices
- AWS, MySQL

EXPERIENCE
Senior Software Engineer | Tech Corp | 2020-2023
- Developed microservices using Spring Boot
- Built React.js front-end applications
- Implemented CI/CD pipelines

EDUCATION
B.S. Computer Science | University | 2015-2019
```

Upload this file and you should get:
- Match Score: 85-95 (excellent match for Senior Java Developer role)
- Extracted candidate name, email, phone
- List of skills
- Detailed analysis

## Next Steps

1. ✅ System is working!
2. 📊 Try uploading different resumes
3. 🎯 Experiment with different job requirements
4. 📈 Monitor OpenAI API usage at https://platform.openai.com/usage
5. 💰 Check costs (should be ~$0.002-0.003 per resume with GPT-3.5-turbo)

## API Usage Monitoring

Check your API usage:
1. Go to https://platform.openai.com/usage
2. View requests and token usage
3. Monitor costs

For 100 resumes analyzed:
- **Cost**: ~$0.25-0.30 with GPT-3.5-turbo
- **Time**: ~500-1000 seconds (5-10 seconds per resume)

## Support

If you encounter issues:
1. Check the migration guide: `OPENAI_MIGRATION_GUIDE.md`
2. Review logs in the terminal
3. Verify API key is active on OpenAI platform
4. Check network connectivity

---

## Success! 🎉

Your TalentLens application is now powered by OpenAI for intelligent resume analysis!

