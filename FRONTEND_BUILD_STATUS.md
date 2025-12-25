node# ⚠️ IMPORTANT: Frontend Build Requirements

## Status Update

I attempted to build the frontend project but discovered that **Node.js is not installed** on your system.

## What Happened

When I tried to run:
```bash
npm install
```

The system responded:
```
npm : The term 'npm' is not recognized...
```

This means Node.js (which includes npm) is not installed.

## What You Need To Do Now

### 🎯 Quick Action Plan (10 Minutes Total)

#### 1️⃣ Install Node.js (5 minutes)

**Visit:** https://nodejs.org/

- Click the **LTS** button (Long Term Support - recommended)
- Download and run the installer
- Keep all default settings
- ✅ Make sure "Add to PATH" is checked
- Complete the installation

#### 2️⃣ Verify Installation (1 minute)

Open a **NEW** terminal and run:
```bash
node --version
npm --version
```

Should show something like:
```
v20.10.0
10.2.3
```

#### 3️⃣ Build the Frontend (4 minutes)

**Option A - Easy (Recommended):**
- Double-click: `build-frontend.bat`
- Wait for it to complete

**Option B - Manual:**
```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm run build
```

## Files I Created For You

To help you with this process, I've created:

1. **`FRONTEND_BUILD_GUIDE.md`** ⭐ 
   - Complete step-by-step guide
   - Screenshots and explanations
   - Troubleshooting tips

2. **`build-frontend.bat`**
   - Automated build script
   - Checks for Node.js automatically
   - Guides you if anything is missing

3. **`INSTALL_NODEJS.md`**
   - Detailed Node.js installation instructions
   - Alternative installation methods
   - PATH configuration help

## Why Node.js is Required

- React is a JavaScript framework that runs on Node.js
- npm (Node Package Manager) manages JavaScript dependencies
- Build tools compile and optimize the React code
- Without Node.js, the frontend cannot be built

## What About the Backend?

The **backend can be built independently** using Maven (which is already available):

```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn clean install -DskipTests
```

This will create: `target/TalentLens-1.0-SNAPSHOT.jar`

## After Installing Node.js

Once Node.js is installed, you can:

### Build for Production
```bash
cd frontend
npm install
npm run build
```
Output: `frontend/build/` directory

### Run Development Server
```bash
cd frontend
npm install
npm start
```
Opens: http://localhost:3000

### Run Full Application
Terminal 1 (Backend):
```bash
mvn spring-boot:run
```

Terminal 2 (Frontend):
```bash
cd frontend
npm start
```

Then visit: http://localhost:3000

## Download Links

- **Node.js Official:** https://nodejs.org/
- **Direct Download (Windows 64-bit):** https://nodejs.org/dist/v20.10.0/node-v20.10.0-x64.msi

## Troubleshooting

### After installing, "npm" still not recognized

**Solution:**
1. Close ALL terminal windows
2. Open a NEW terminal
3. Try again

If still not working:
- Restart your computer
- Check if Node.js was added to PATH

### Build errors after npm install

**Solution:**
```bash
# Delete node_modules and try again
cd frontend
rm -rf node_modules
rm package-lock.json
npm install
```

## Alternative: Use Pre-built Frontend

If you prefer not to install Node.js right now:

1. You can still run the backend:
   ```bash
   mvn spring-boot:run
   ```

2. Access the API directly:
   - http://localhost:8080/api/resumes
   - http://localhost:8080/api/job-requirements

3. Use a REST client (Postman, Insomnia, or curl) to test the APIs

## Summary

✅ **Backend:** Ready to build (Maven available)
❌ **Frontend:** Needs Node.js installation first

**Next Step:** Install Node.js from https://nodejs.org/, then run `build-frontend.bat`

---

**Need Help?** Check:
- `FRONTEND_BUILD_GUIDE.md` - Complete guide
- `INSTALL_NODEJS.md` - Installation details
- `README.md` - Full project documentation

**Once Node.js is installed, run:**
```bash
build-frontend.bat
```

or manually:
```bash
cd frontend
npm install && npm run build
```

🎉 You'll then have a fully built frontend ready to deploy!

