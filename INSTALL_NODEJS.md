# Node.js Installation Required

## Issue
Node.js and npm are not installed on your system. You need these to build the React frontend.

## Quick Installation Steps

### 1. Download Node.js (Recommended: LTS Version)

Visit: https://nodejs.org/

- Click the **LTS (Long Term Support)** button (recommended)
- Or use this direct link: https://nodejs.org/dist/v20.10.0/node-v20.10.0-x64.msi
- Download will start automatically

### 2. Install Node.js

1. Run the downloaded `.msi` installer
2. Click "Next" through the installation wizard
3. Accept the license agreement
4. Keep default installation location (C:\Program Files\nodejs\)
5. **Important**: Make sure "Add to PATH" is checked
6. Click "Install"
7. Wait for installation to complete
8. Click "Finish"

### 3. Verify Installation

Open a **NEW** PowerShell or Command Prompt window and run:

```bash
node --version
npm --version
```

You should see version numbers like:
```
v20.10.0
10.2.3
```

### 4. Build the Frontend

Once Node.js is installed, return to your project and run:

```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm run build
```

## Alternative: Using Chocolatey (Windows Package Manager)

If you have Chocolatey installed:

```bash
choco install nodejs-lts -y
```

Then restart your terminal.

## Alternative: Using Winget (Windows 11)

If you have Windows 11 with winget:

```bash
winget install OpenJS.NodeJS.LTS
```

Then restart your terminal.

## After Installation

Once Node.js is installed, you can build the frontend by:

1. **Open a NEW terminal** (important - to refresh PATH)
2. Navigate to frontend directory:
   ```bash
   cd C:\Users\Vibha\TalentLens\TalentLens\frontend
   ```
3. Install dependencies:
   ```bash
   npm install
   ```
4. Build the project:
   ```bash
   npm run build
   ```

The built files will be in `frontend/build/` directory.

## Running the Frontend (Development Mode)

After installation, to run the development server:

```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm start
```

This will start the development server on http://localhost:3000

## Troubleshooting

**If npm is still not recognized after installation:**
1. Close ALL terminal windows
2. Open a brand new PowerShell or Command Prompt
3. Try again

**If that doesn't work:**
1. Add Node.js to PATH manually:
   - Open System Properties → Environment Variables
   - Under System Variables, find "Path"
   - Add: `C:\Program Files\nodejs\`
   - Restart terminal

**Check PATH:**
```bash
$env:Path -split ';' | Select-String nodejs
```

## Next Steps

1. ✅ Install Node.js from https://nodejs.org/
2. ✅ Verify with `node --version`
3. ✅ Run `npm install` in frontend directory
4. ✅ Run `npm run build` to create production build
5. ✅ Or run `npm start` for development server

---

**Note**: The backend can be built independently with Maven, which appears to be already available on your system.

