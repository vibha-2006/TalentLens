@echo off
echo ============================================
echo  TalentLens - Automated GitHub Push
echo ============================================
echo.

REM Refresh PATH to include GitHub CLI
call refreshenv 2>nul
if errorlevel 1 (
    echo Refreshing environment variables...
    set "PATH=%PATH%;C:\Program Files\GitHub CLI"
)

echo Step 1: Authenticating with GitHub...
echo A browser window will open for authentication.
echo Please authorize GitHub CLI in your browser.
echo.
gh auth login --web --git-protocol https

if errorlevel 1 (
    echo.
    echo ERROR: Authentication failed!
    echo Please run: gh auth login --web
    pause
    exit /b 1
)

echo.
echo Step 2: Creating GitHub repository...
cd /d C:\Users\Vibha\TalentLens\TalentLens
gh repo create TalentLens --public --description "AI-Powered Resume Screening Application with OpenAI, Gemini, and Groq integration" --source=. --remote=origin

if errorlevel 1 (
    echo.
    echo Note: Repository might already exist or remote is already configured.
    echo Continuing with push...
)

echo.
echo Step 3: Pushing code to GitHub...
git push -u origin main

if errorlevel 1 (
    echo.
    echo ERROR: Push failed!
    echo.
    echo Possible solutions:
    echo 1. Repository might not be created yet - create it manually at: https://github.com/new
    echo 2. Try: git push -u origin main --force
    echo 3. Check authentication: gh auth status
    pause
    exit /b 1
)

echo.
echo ============================================
echo  SUCCESS! Code pushed to GitHub!
echo ============================================
echo.
echo Your repository is live at:
echo https://github.com/vibha-2006/TalentLens
echo.
echo Opening in browser...
start https://github.com/vibha-2006/TalentLens

echo.
pause

