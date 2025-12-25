# 🚀 START HERE - TalentLens Quick Launch

## What You Have
A complete AI-powered resume shortlisting application ready to run!

## ⚡ Super Quick Start (5 Minutes)

### Step 1: Get Your API Key (2 minutes)
1. Open: https://makersuite.google.com/app/apikey
2. Sign in with Google
3. Click "Create API Key"
4. Copy the key

### Step 2: Configure (1 minute)
1. Open: `src/main/resources/application.properties`
2. Find line: `gemini.api.key=YOUR_GEMINI_API_KEY_HERE`
3. Replace with your key: `gemini.api.key=AIzaSy...your-key-here`
4. Save file

### Step 3: Run (2 minutes)

**Option A: Easy Way (Windows)**
- Double-click: `start-talentlens.bat`
- Wait for browser to open
- Done! ✅

**Option B: Manual Way**

Terminal 1 (Backend):
```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn spring-boot:run
```

Terminal 2 (Frontend):
```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm start
```

Browser opens at: http://localhost:3000

## 📝 First Use

### Create Job (1 minute)
1. Click "Job Requirements"
2. Fill form:
   - Title: "Software Developer"
   - Description: "Looking for developer"
   - Required Skills: "Java, Spring Boot, React"
   - Experience: "Mid Level"
3. Click "Create"

### Upload Resume (1 minute)
1. Click "Upload Resumes"
2. Choose a PDF/Word resume
3. Click "Upload & Analyze"
4. Wait ~15 seconds

### View Results
1. Click "View Rankings"
2. See candidate with match score!
3. Click "View Details" for analysis

## 🎯 You're Done!

The app is now running and analyzing resumes with AI!

## 📚 Need Help?

- **Quick Setup**: Read `QUICKSTART.md`
- **Detailed Guide**: Read `README.md`
- **Checklist**: Follow `SETUP_CHECKLIST.md`
- **Issues**: See troubleshooting in `README.md`

## ❓ Common Issues

**"Port already in use"**
- Another app is using port 8080 or 3000
- Close other applications or change ports

**"API key invalid"**
- Check you copied the entire key
- No spaces before/after
- No quotes around the key

**"npm not found"**
- Install Node.js from https://nodejs.org/

**"mvn not found"**
- Maven should come with Java
- Or download from https://maven.apache.org/

## 🌟 Features

✅ Upload PDF/Word resumes
✅ AI analyzes and scores each resume
✅ Automatic ranking (best to worst)
✅ Extract skills and experience
✅ Detailed match analysis
✅ Import from Google Drive (optional)

## 🎊 Enjoy!

You now have a powerful AI recruitment tool!

**Next**: Try uploading multiple resumes to see rankings!

---

*Questions? Check the documentation files or review the code!*

