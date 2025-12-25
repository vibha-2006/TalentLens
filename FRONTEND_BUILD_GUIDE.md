# 🚀 Frontend Build Status

## Current Situation

❌ **Node.js is not installed on your system**

The frontend cannot be built because Node.js and npm (Node Package Manager) are required for React applications.

## What You Need To Do

### Step 1: Install Node.js (5 minutes)

**Quick Install:**
1. 🌐 Visit: **https://nodejs.org/**
2. 📥 Click the **LTS** (Long Term Support) button - it's the green one!
3. 💾 Download will start automatically (about 30-50 MB)
4. 🖱️ Run the downloaded file
5. ✅ Click through the installer (keep all defaults, especially "Add to PATH")
6. ⏳ Wait for installation to complete
7. ✨ Installation done!

**Direct Download Link:**
- Windows 64-bit: https://nodejs.org/dist/v20.10.0/node-v20.10.0-x64.msi

### Step 2: Verify Installation

After installation, **close this terminal** and open a **NEW** PowerShell/Command Prompt, then run:

```bash
node --version
npm --version
```

You should see version numbers like:
```
v20.10.0
10.2.3
```

### Step 3: Build the Frontend

Once Node.js is installed, you have **two options**:

#### Option A: Use the Automated Script (Easy!)

Double-click: **`build-frontend.bat`** in the project root directory.

This script will:
- ✅ Check if Node.js is installed
- ✅ Install all dependencies automatically
- ✅ Build the production-ready frontend
- ✅ Show you where the output is

#### Option B: Manual Commands

Open a terminal and run:

```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm run build
```

The first time will take 2-5 minutes to download dependencies.

## After Building

Once the build completes successfully, you'll have:

📁 **`frontend/build/`** directory containing:
- `index.html` - Main HTML file
- `static/` - CSS, JavaScript, and assets
- All optimized for production

## Running Development Mode (Alternative)

Instead of building, you can run the frontend in development mode:

```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm start
```

This will:
- ✅ Start a development server at http://localhost:3000
- ✅ Auto-reload when you make changes
- ✅ Show helpful error messages

## Complete Application Startup

To run the **full application** (backend + frontend):

**Terminal 1 (Backend):**
```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn spring-boot:run
```

**Terminal 2 (Frontend):**
```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm start
```

Then open: **http://localhost:3000** in your browser

## Troubleshooting

### "npm is not recognized" after installing Node.js

**Solution:**
1. Close **ALL** terminal windows
2. Open a **NEW** terminal
3. Try again

If still not working:
- Restart your computer
- Node.js should now be in your PATH

### Build fails with errors

**Solution:**
1. Delete `frontend/node_modules` folder
2. Delete `frontend/package-lock.json` file
3. Run `npm install` again

### Internet connection issues

**Solution:**
- Ensure you have a stable internet connection
- npm needs to download dependencies from the internet
- First time will download ~200-300 MB

## Why Node.js is Needed

- React is a JavaScript framework
- npm manages JavaScript dependencies
- Build tools (Webpack, Babel) need Node.js
- Creates optimized production bundles

## Files Created

✅ `INSTALL_NODEJS.md` - Detailed installation guide
✅ `build-frontend.bat` - Automated build script
✅ This file - Quick reference

## Next Steps Checklist

- [ ] 1. Install Node.js from https://nodejs.org/
- [ ] 2. Verify installation: `node --version`
- [ ] 3. Close and reopen terminal
- [ ] 4. Run `build-frontend.bat` OR `npm install && npm run build`
- [ ] 5. Check `frontend/build/` folder exists
- [ ] 6. Start backend: `mvn spring-boot:run`
- [ ] 7. Start frontend: `npm start`
- [ ] 8. Open http://localhost:3000
- [ ] 9. Enjoy TalentLens! 🎉

---

## Quick Links

- 📦 Node.js Download: https://nodejs.org/
- 📚 Node.js Docs: https://nodejs.org/docs/
- 📖 npm Docs: https://docs.npmjs.com/
- ⚛️ React Docs: https://react.dev/

---

**Ready?** Install Node.js, then run `build-frontend.bat`! 🚀

