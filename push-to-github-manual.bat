@echo off
echo ============================================
echo  TalentLens - Manual GitHub Push
echo ============================================
echo.
echo IMPORTANT: Before running this script:
echo 1. Create repository at: https://github.com/new
echo    - Name: TalentLens
echo    - Public repository
echo    - DO NOT initialize with README
echo.
echo 2. Press any key to continue after creating repository...
pause

echo.
echo Pushing code to GitHub...
cd /d C:\Users\Vibha\TalentLens\TalentLens

echo.
echo Current remote configuration:
git remote -v

echo.
echo Pushing to main branch...
git push -u origin main

if errorlevel 1 (
    echo.
    echo ERROR: Push failed!
    echo.
    echo Troubleshooting:
    echo - If authentication fails, generate Personal Access Token:
    echo   https://github.com/settings/tokens
    echo - When prompted for password, paste the token
    echo.
    echo - If repository not found:
    echo   1. Make sure you created the repository at https://github.com/new
    echo   2. Repository name must be: TalentLens
    echo   3. Repository owner must be: vibha-2006
    echo.
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

