@echo off
pause
:END

echo.
echo    - Create PR from feature/frontend-ui-improvements to main
echo    - Create PR from feature/backend-api-enhancements to main
echo    - Click "New pull request"
echo    - Go to: https://github.com/YOUR_USERNAME/TalentLens/pulls
echo 3. Create PRs on GitHub:
echo.
echo    git push origin feature/frontend-ui-improvements
echo    git push origin feature/backend-api-enhancements
echo    git push -u origin main
echo    git remote add origin https://github.com/YOUR_USERNAME/TalentLens.git
echo.
echo 2. After creating, run these commands:
echo.
echo    - Click "Create repository"
echo    - Do NOT initialize with README
echo    - Public repository
echo    - Name: TalentLens
echo    - Go to: https://github.com/new
echo 1. Create GitHub Repository:
echo.
echo Please follow these steps:
echo.
echo ============================================
echo Manual Setup Instructions
echo ============================================
echo.
:MANUAL_PROCESS

goto END
echo.
echo View in browser: gh repo view --web
echo View your PRs: gh pr list
echo.
echo ============================================
echo Success! All PRs Created
echo ============================================
echo.

gh pr create --base main --head feature/frontend-ui-improvements --title "feat(frontend): add dark mode support and UI enhancements" --body-file PR_FRONTEND.md --label enhancement --label frontend
echo Creating Frontend PR...
echo.

gh pr create --base main --head feature/backend-api-enhancements --title "feat(backend): add batch upload response DTO and enhance error handling" --body-file PR_BACKEND.md --label enhancement --label backend
echo Creating Backend PR...

echo ============================================
echo Step 4: Creating Pull Requests
echo ============================================
echo.

git push origin feature/frontend-ui-improvements
git push origin feature/backend-api-enhancements
echo ============================================
echo Step 3: Pushing Feature Branches
echo ============================================
echo.

git push -u origin main
echo ============================================
echo Step 2: Pushing Main Branch
echo ============================================
echo.

)
    )
        git remote add origin https://github.com/%USERNAME%/TalentLens.git 2>nul
        echo Repository exists. Adding remote...
    if %ERRORLEVEL% EQU 0 (
    gh repo view TalentLens >nul 2>nul
    echo Repository might already exist. Checking...
if %ERRORLEVEL% NEQ 0 (

gh repo create TalentLens --public --description "AI-powered resume screening and candidate ranking system with multi-AI provider support (OpenAI, Gemini, Groq)" --source=. --remote=origin --push=false
echo ============================================
echo Step 1: Creating GitHub Repository
echo ============================================
echo.

)
    gh auth login
    echo Please authenticate with GitHub:
if %ERRORLEVEL% NEQ 0 (
gh auth status >nul 2>nul
echo Checking GitHub authentication...
REM Authenticate with GitHub (if not already)

echo.
echo Using GitHub CLI to automate setup...

)
    goto MANUAL_PROCESS
    echo GitHub CLI not found. Using manual process...
if %ERRORLEVEL% NEQ 0 (
where gh >nul 2>nul
REM Check if GitHub CLI is installed

echo.
echo ============================================
echo   TalentLens GitHub Push Script
echo ============================================

