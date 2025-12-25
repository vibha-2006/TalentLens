@echo off
echo ================================================
echo       Starting TalentLens Application
echo ================================================
echo.
echo This will start both the backend and frontend...
echo.
echo STEP 1: Starting Backend (Spring Boot on port 8080)
start "TalentLens Backend" cmd /k "cd /d C:\Users\Vibha\TalentLens\TalentLens && mvn spring-boot:run"
echo Backend starting in new window...
echo.
echo Waiting 15 seconds for backend to initialize...
timeout /t 15 /nobreak
echo.
echo STEP 2: Starting Frontend (React on port 3000)
start "TalentLens Frontend" cmd /k "cd /d C:\Users\Vibha\TalentLens\TalentLens\frontend && npm start"
echo Frontend starting in new window...
echo.
echo ================================================
echo Both applications are starting!
echo ================================================
echo.
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo.
echo Press any key to close this window...
pause > nul

