@echo off
echo ========================================
echo   TalentLens Frontend Build Script
echo ========================================
echo.

REM Check if Node.js is installed
echo Checking for Node.js installation...
node --version >nul 2>&1
if errorlevel 1 (
    echo.
    echo ========================================
    echo   ERROR: Node.js is not installed!
    echo ========================================
    echo.
    echo Node.js is required to build the React frontend.
    echo.
    echo Please follow these steps:
    echo.
    echo 1. Visit: https://nodejs.org/
    echo 2. Download the LTS ^(Long Term Support^) version
    echo 3. Run the installer
    echo 4. Make sure "Add to PATH" is checked during installation
    echo 5. After installation, CLOSE this window and open a NEW terminal
    echo 6. Run this script again
    echo.
    echo For detailed instructions, see: INSTALL_NODEJS.md
    echo.
    echo Opening Node.js download page...
    timeout /t 3 /nobreak >nul
    start https://nodejs.org/
    echo.
    pause
    exit /b 1
)

REM Check if npm is available
echo Checking for npm...
npm --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: npm is not available!
    echo This is unusual - npm should come with Node.js
    echo Please reinstall Node.js from https://nodejs.org/
    pause
    exit /b 1
)

echo ✅ Node.js found:
node --version
echo ✅ npm found:
npm --version
echo.

REM Navigate to frontend directory
if not exist "frontend" (
    echo ERROR: frontend directory not found!
    echo Please run this script from the TalentLens project root directory.
    pause
    exit /b 1
)

cd frontend

echo ========================================
echo   Installing Dependencies...
echo ========================================
echo.
echo This may take a few minutes on first run...
echo.

call npm install
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ========================================
    echo   ERROR: npm install failed!
    echo ========================================
    echo.
    echo Common fixes:
    echo 1. Check your internet connection
    echo 2. Try running as Administrator
    echo 3. Delete node_modules folder and try again
    echo 4. Clear npm cache: npm cache clean --force
    echo.
    cd ..
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Building Production Bundle...
echo ========================================
echo.

call npm run build
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ========================================
    echo   ERROR: Build failed!
    echo ========================================
    echo.
    echo Please check the error messages above.
    echo.
    cd ..
    pause
    exit /b 1
)

cd ..

echo.
echo ========================================
echo   ✅ Frontend Build Complete!
echo ========================================
echo.
echo Build output location: frontend\build\
echo.
echo You can now:
echo 1. Deploy the build folder to a web server
echo 2. Or run development mode: npm start
echo.
echo To run the development server:
echo   cd frontend
echo   npm start
echo.
pause

