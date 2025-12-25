@echo off
cd C:\Users\Vibha\TalentLens\TalentLens
echo Running OpenAI Service Tests...
mvn test -Dtest=OpenAIServiceTest
echo.
echo Tests completed!
pause

