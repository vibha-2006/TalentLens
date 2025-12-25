# TalentLens - Setup Checklist

Use this checklist to ensure everything is properly configured before running the application.

## ✅ Pre-Setup Requirements

- [ ] Java 17 or higher installed
  - Test: Open terminal and run `java -version`
  - Should show version 17 or higher
  
- [ ] Maven installed
  - Test: Run `mvn -version`
  - Usually comes with Java installation
  
- [ ] Node.js and npm installed
  - Test: Run `node -version` and `npm -version`
  - Download from: https://nodejs.org/

## ✅ Get Gemini API Key (Required)

- [ ] Visit https://makersuite.google.com/app/apikey
- [ ] Sign in with Google account
- [ ] Click "Create API Key"
- [ ] Copy the API key (keep it safe!)

## ✅ Backend Configuration

- [ ] Open `src/main/resources/application.properties`
- [ ] Replace `YOUR_GEMINI_API_KEY_HERE` with your actual API key
- [ ] Save the file
- [ ] Verify the key has no extra spaces or quotes

## ✅ Optional: Google Drive Setup

**Only if you want to import resumes from Google Drive:**

- [ ] Follow instructions in `GOOGLE_DRIVE_SETUP.md`
- [ ] Get OAuth credentials from Google Cloud Console
- [ ] Place `credentials.json` in `src/main/resources/`
- [ ] Set `google.drive.enabled=true` in application.properties

**OR skip this step** - You can still upload files directly!

## ✅ Backend Startup

- [ ] Open terminal/command prompt
- [ ] Navigate to project: `cd C:\Users\Vibha\TalentLens\TalentLens`
- [ ] Run: `mvn clean install` (first time only)
- [ ] Run: `mvn spring-boot:run`
- [ ] Wait for message: "Started TalentLensApplication"
- [ ] Verify: Open browser to http://localhost:8080/api/resumes
  - Should see: `[]` (empty array)

## ✅ Frontend Startup

- [ ] Open NEW terminal/command prompt (keep backend running)
- [ ] Navigate to frontend: `cd C:\Users\Vibha\TalentLens\TalentLens\frontend`
- [ ] Run: `npm install` (wait for completion)
- [ ] Run: `npm start`
- [ ] Browser should open automatically to http://localhost:3000
- [ ] You should see the TalentLens interface

## ✅ First Time Usage

- [ ] Click "Job Requirements" tab
- [ ] Fill in sample job requirement:
  ```
  Job Title: Software Developer
  Description: Looking for experienced developer
  Required Skills: Java, Spring Boot, React
  Preferred Skills: AWS, Docker
  Experience Level: Mid Level
  ```
- [ ] Click "Create Job Requirement"
- [ ] Should see success and job details displayed

## ✅ Test Resume Upload

- [ ] Click "Upload Resumes" tab
- [ ] Prepare a test PDF or Word resume
- [ ] Click "Choose File" and select the resume
- [ ] Click "Upload & Analyze"
- [ ] Wait 10-30 seconds (AI is analyzing)
- [ ] Should see success message

## ✅ View Results

- [ ] Click "View Rankings" tab
- [ ] Should see the uploaded resume with a match score
- [ ] Click "View Details" to see full analysis
- [ ] Verify all information is displayed correctly

## 🎯 If Everything Works

Congratulations! Your TalentLens application is fully functional!

You can now:
- Create different job requirements
- Upload multiple resumes
- Compare candidates
- View detailed AI analysis
- Delete resumes you don't need

## ❌ Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```properties
# Change in application.properties
server.port=8081
# Also update frontend/package.json proxy
```

**Gemini API errors:**
- Double-check API key is correct
- No quotes around the key
- Check Google AI Studio for quota limits
- Try: https://makersuite.google.com/app/apikey

**Maven build fails:**
```bash
mvn clean
mvn dependency:resolve
mvn install
```

### Frontend Issues

**npm install fails:**
```bash
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

**Cannot connect to backend:**
- Ensure backend is running (check terminal)
- Verify http://localhost:8080/api/resumes works in browser
- Check proxy in package.json is correct

**Page is blank:**
- Check browser console for errors (F12)
- Clear browser cache
- Try different browser

### Application Issues

**"No active job requirement found":**
- You MUST create a job requirement first
- Go to "Job Requirements" tab and create one

**Upload fails:**
- Ensure file is PDF or Word document (.pdf, .docx)
- File size must be under 10MB
- Check backend logs for detailed error

**Resume analysis takes too long:**
- Normal: 10-30 seconds per resume
- If longer: Check internet connection
- Gemini API might be slow - be patient

## 📞 Need More Help?

Refer to these documents:
- `README.md` - Complete documentation
- `QUICKSTART.md` - Quick setup guide  
- `GOOGLE_DRIVE_SETUP.md` - Drive integration
- `PROJECT_SUMMARY.md` - Technical overview

## 🎉 Success Indicators

✅ Backend terminal shows: "Started TalentLensApplication"
✅ Frontend opens in browser automatically
✅ Can create job requirements
✅ Can upload resumes
✅ Resumes are analyzed and ranked
✅ Can view detailed AI analysis

## 🚀 Ready to Use!

Once all checkboxes are complete, your TalentLens application is ready for production use!

Start shortlisting candidates with AI! 🎯

