# TalentLens - Project Summary

## ✅ What Has Been Created

### Backend (Java Spring Boot)
- **Main Application**: `TalentLensApplication.java` - Spring Boot entry point
- **Controllers**: REST API endpoints for resumes and job requirements
- **Services**: 
  - `GeminiService.java` - Integrates with Google Gemini AI for resume analysis
  - `ResumeService.java` - Core resume processing logic
  - `ResumeParserService.java` - Extracts text from PDF/Word documents
  - `GoogleDriveService.java` - Imports resumes from Google Drive
  - `JobRequirementService.java` - Manages job requirements
- **Models**: JPA entities for Resume and JobRequirement
- **Repositories**: Data access layer with Spring Data JPA
- **Database**: H2 in-memory database (auto-configured)

### Frontend (React)
- **Components**:
  - `JobRequirementForm.js` - Create and manage job requirements
  - `ResumeUpload.js` - Upload resumes and import from Google Drive
  - `ResumeList.js` - Display ranked candidates with AI analysis
- **Services**: `api.js` - Axios-based API client
- **Styling**: Modern, responsive CSS with gradient themes
- **Routing**: Single-page app with tab navigation

### Configuration
- `application.properties` - Backend configuration (needs API key)
- `package.json` - Frontend dependencies and scripts
- `.gitignore` - Prevents committing sensitive files

### Documentation
- `README.md` - Comprehensive project documentation
- `QUICKSTART.md` - Fast setup guide for immediate use
- `GOOGLE_DRIVE_SETUP.md` - Optional Google Drive integration guide

## 🚀 Next Steps to Run the Application

### 1. Get Gemini API Key (Required)
Visit: https://makersuite.google.com/app/apikey
- Sign in with Google account
- Create API key
- Copy the key

### 2. Configure API Key
Edit: `src/main/resources/application.properties`
```properties
gemini.api.key=YOUR_ACTUAL_API_KEY_HERE
```

### 3. Start Backend
```bash
cd C:\Users\Vibha\TalentLens\TalentLens
mvn spring-boot:run
```
Wait for: "Started TalentLensApplication in X seconds"

### 4. Start Frontend (in new terminal)
```bash
cd C:\Users\Vibha\TalentLens\TalentLens\frontend
npm install
npm start
```
Browser opens automatically to http://localhost:3000

## 🎯 How It Works

1. **Define Job**: Create a job requirement with skills and experience level
2. **Upload Resumes**: Upload PDF/Word resumes (or import from Google Drive)
3. **AI Analysis**: Gemini AI analyzes each resume:
   - Extracts candidate information (name, email, phone)
   - Identifies skills and experience
   - Matches against job requirements
   - Generates match score (0-100%)
   - Provides detailed analysis
4. **View Rankings**: See all candidates ranked by match score
5. **Make Decisions**: Review detailed analysis for top candidates

## 📊 Key Features

✅ **Automatic Resume Parsing** - Extracts text from PDF and Word documents
✅ **AI-Powered Analysis** - Uses Gemini to understand resume content
✅ **Smart Matching** - Compares resumes against job requirements
✅ **Skill Extraction** - Automatically identifies technical and soft skills
✅ **Experience Analysis** - Evaluates candidate experience level
✅ **Score-Based Ranking** - Ranks candidates from best to worst match
✅ **Detailed Insights** - AI provides reasoning for each score
✅ **Multiple Upload Options** - Direct upload or Google Drive import
✅ **Responsive UI** - Works on desktop and mobile devices

## 📋 API Endpoints Created

### Job Requirements
- `POST /api/job-requirements` - Create job requirement
- `GET /api/job-requirements/active` - Get active job
- `GET /api/job-requirements` - List all jobs
- `PUT /api/job-requirements/{id}` - Update job
- `DELETE /api/job-requirements/{id}` - Delete job

### Resumes
- `POST /api/resumes/upload` - Upload single resume
- `POST /api/resumes/import-from-drive` - Import from Google Drive
- `GET /api/resumes` - Get all resumes (ranked)
- `GET /api/resumes/{id}` - Get specific resume
- `DELETE /api/resumes/{id}` - Delete resume

## 🔧 Technology Stack

**Backend:**
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Google Gemini AI API
- Apache PDFBox (PDF parsing)
- Apache POI (Word parsing)
- OkHttp (HTTP client)
- Gson (JSON parsing)

**Frontend:**
- React 18
- Axios
- Modern CSS3

## 📁 Project Structure

```
TalentLens/
├── src/main/java/org/example/
│   ├── TalentLensApplication.java
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── repository/
│   └── dto/
├── src/main/resources/
│   └── application.properties
├── frontend/
│   ├── public/
│   └── src/
│       ├── components/
│       ├── services/
│       └── styles/
├── pom.xml
├── README.md
├── QUICKSTART.md
└── GOOGLE_DRIVE_SETUP.md
```

## ⚙️ Configuration Options

### Application Properties
```properties
# Server
server.port=8080

# Database (H2 in-memory)
spring.datasource.url=jdbc:h2:mem:talentlens

# File Upload
spring.servlet.multipart.max-file-size=10MB

# Gemini AI (REQUIRED)
gemini.api.key=YOUR_KEY_HERE

# Google Drive (Optional)
google.drive.enabled=false
```

## 🎨 Match Score Interpretation

- **90-100%** 🟢 Excellent Match - Highly recommended
- **75-89%** 🟡 Good Match - Strong candidate
- **60-74%** 🟠 Fair Match - Consider with training
- **Below 60%** 🔴 Poor Match - Significant gaps

## 🔒 Security Considerations

⚠️ **Never commit these files:**
- `application.properties` with real API keys
- `credentials.json` (Google Drive)
- `tokens/` directory

✅ Already in .gitignore

## 🐛 Common Issues & Solutions

**Backend won't start:**
- Check Java 17 is installed: `java -version`
- Verify API key is set in application.properties
- Check port 8080 is not in use

**Frontend errors:**
- Run `npm install` in frontend directory
- Ensure backend is running first
- Check proxy setting in package.json

**"No active job requirement found":**
- Create a job requirement before uploading resumes

**Gemini API errors:**
- Verify API key is correct
- Check API quota at Google AI Studio
- Ensure internet connection is stable

## 📈 Future Enhancements (Not Implemented Yet)

- User authentication
- Persistent database (PostgreSQL/MySQL)
- Export to Excel/PDF
- Email notifications
- Bulk upload support
- Interview scheduling
- Candidate communication portal
- Advanced filtering/search
- Custom scoring weights
- Resume storage and retrieval

## 📞 Support

For detailed instructions, see:
- `README.md` - Full documentation
- `QUICKSTART.md` - Fast setup guide
- `GOOGLE_DRIVE_SETUP.md` - Drive integration

## 🎉 Ready to Go!

Your TalentLens application is fully set up and ready to use. Just:
1. Add your Gemini API key
2. Start backend and frontend
3. Create a job requirement
4. Upload resumes
5. Watch AI rank your candidates!

Happy hiring! 🚀

