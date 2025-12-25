# TalentLens Architecture

## System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER INTERFACE                          │
│                     (React Frontend - Port 3000)                │
├─────────────────────────────────────────────────────────────────┤
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │     Job      │  │    Resume    │  │    Resume    │         │
│  │ Requirements │  │    Upload    │  │     List     │         │
│  │     Form     │  │              │  │  (Rankings)  │         │
│  └──────────────┘  └──────────────┘  └──────────────┘         │
└────────────────────────┬────────────────────────────────────────┘
                         │ HTTP/REST API
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND API LAYER                            │
│                (Spring Boot - Port 8080)                        │
├─────────────────────────────────────────────────────────────────┤
│  ┌──────────────────────┐    ┌──────────────────────┐         │
│  │  ResumeController    │    │ JobRequirement       │         │
│  │                      │    │ Controller           │         │
│  │ POST /upload         │    │ POST /create         │         │
│  │ POST /import-drive   │    │ GET  /active         │         │
│  │ GET  /resumes        │    │ PUT  /update         │         │
│  │ DELETE /{id}         │    │ DELETE /{id}         │         │
│  └──────────┬───────────┘    └──────────┬───────────┘         │
│             │                            │                      │
│             ▼                            ▼                      │
│  ┌──────────────────────┐    ┌──────────────────────┐         │
│  │   ResumeService      │    │ JobRequirement       │         │
│  │                      │    │ Service              │         │
│  └──────────┬───────────┘    └──────────┬───────────┘         │
│             │                            │                      │
│             ├────────────────────────────┤                      │
│             │                            │                      │
│             ▼                            ▼                      │
│  ┌──────────────────────────────────────────────────┐         │
│  │            Business Logic Layer                  │         │
│  ├──────────────────────────────────────────────────┤         │
│  │ ┌─────────────────┐  ┌─────────────────┐       │         │
│  │ │  ResumeParser   │  │   GeminiService  │       │         │
│  │ │   Service       │  │   (AI Analysis)  │       │         │
│  │ │ - PDF parsing   │  │ - Score resumes  │       │         │
│  │ │ - Word parsing  │  │ - Extract info   │       │         │
│  │ └─────────────────┘  │ - Match skills   │       │         │
│  │                      └─────────────────┘       │         │
│  │ ┌─────────────────┐                            │         │
│  │ │ GoogleDrive     │                            │         │
│  │ │   Service       │                            │         │
│  │ │ - List files    │                            │         │
│  │ │ - Download      │                            │         │
│  │ └─────────────────┘                            │         │
│  └──────────────────────────────────────────────────┘         │
│             │                            │                      │
│             ▼                            ▼                      │
│  ┌──────────────────────┐    ┌──────────────────────┐         │
│  │ ResumeRepository     │    │ JobRequirement       │         │
│  │ (Spring Data JPA)    │    │ Repository           │         │
│  └──────────┬───────────┘    └──────────┬───────────┘         │
└─────────────┼────────────────────────────┼─────────────────────┘
              │                            │
              ▼                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                    DATABASE LAYER                               │
│                   (H2 In-Memory Database)                       │
├─────────────────────────────────────────────────────────────────┤
│  ┌──────────────────┐         ┌──────────────────┐            │
│  │  Resume Table    │         │ JobRequirement   │            │
│  │                  │         │     Table        │            │
│  │ - id             │         │ - id             │            │
│  │ - candidateName  │         │ - jobTitle       │            │
│  │ - email          │         │ - description    │            │
│  │ - phone          │         │ - requiredSkills │            │
│  │ - skills         │         │ - preferredSkills│            │
│  │ - experience     │         │ - experienceLevel│            │
│  │ - matchScore     │         │ - active         │            │
│  │ - matchAnalysis  │         │ - createdAt      │            │
│  │ - fileName       │         └──────────────────┘            │
│  │ - source         │                                          │
│  │ - uploadedAt     │                                          │
│  └──────────────────┘                                          │
└─────────────────────────────────────────────────────────────────┘

              │                            │
              ▼                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                    EXTERNAL SERVICES                            │
├─────────────────────────────────────────────────────────────────┤
│  ┌──────────────────────┐    ┌──────────────────────┐         │
│  │   Google Gemini AI   │    │   Google Drive API   │         │
│  │   (Resume Analysis)  │    │  (File Import)       │         │
│  └──────────────────────┘    └──────────────────────┘         │
└─────────────────────────────────────────────────────────────────┘
```

## Data Flow: Resume Upload & Analysis

```
1. User selects resume file
   │
   ▼
2. Frontend sends POST /api/resumes/upload (multipart/form-data)
   │
   ▼
3. ResumeController receives file
   │
   ▼
4. ResumeService.uploadAndAnalyzeResume()
   │
   ├─▶ ResumeParserService.extractTextFromFile()
   │   └─▶ Extracts text from PDF/Word
   │
   ├─▶ JobRequirementService.getActiveJobRequirement()
   │   └─▶ Retrieves current job criteria
   │
   ├─▶ GeminiService.analyzeResume(text, requirements)
   │   └─▶ Sends to Gemini AI API
   │       └─▶ Gemini analyzes and returns:
   │           - Candidate name, email, phone
   │           - Extracted skills
   │           - Experience summary
   │           - Match score (0-100)
   │           - Detailed analysis
   │
   └─▶ ResumeRepository.save(resume)
       └─▶ Saves to database with all analyzed data
       │
       ▼
5. Returns ResumeDTO to frontend
   │
   ▼
6. Frontend displays success and ranking
```

## Data Flow: Google Drive Import

```
1. User clicks "Import from Drive"
   │
   ▼
2. Frontend sends POST /api/resumes/import-from-drive?folderId=XXX
   │
   ▼
3. ResumeController.importFromGoogleDrive()
   │
   ▼
4. ResumeService.importFromGoogleDrive(folderId)
   │
   ├─▶ GoogleDriveService.listResumeFiles(folderId)
   │   └─▶ Lists all PDF/Word files in folder
   │
   └─▶ For each file:
       │
       ├─▶ GoogleDriveService.downloadFile(fileId)
       │   └─▶ Downloads file content
       │
       ├─▶ ResumeParserService.extractTextFromBytes()
       │   └─▶ Extracts text
       │
       ├─▶ GeminiService.analyzeResume()
       │   └─▶ AI analysis
       │
       └─▶ ResumeRepository.save()
           └─▶ Save to database
   │
   ▼
5. Returns list of ResumeDTO objects
   │
   ▼
6. Frontend displays all imported resumes
```

## Component Responsibilities

### Frontend Components

**JobRequirementForm**
- Create/edit job requirements
- Display active job
- Form validation

**ResumeUpload**
- File upload interface
- Google Drive import
- Upload progress feedback

**ResumeList**
- Display ranked candidates
- Show match scores
- Expandable details
- Delete functionality

### Backend Services

**ResumeService**
- Orchestrates resume processing
- Manages upload and import flows
- Coordinates between services

**ResumeParserService**
- Extracts text from PDF files
- Extracts text from Word documents
- Handles different file formats

**GeminiService**
- Integrates with Gemini AI API
- Constructs analysis prompts
- Parses AI responses
- Extracts structured data

**GoogleDriveService**
- Authenticates with Google
- Lists files from Drive
- Downloads file content
- Handles OAuth flow

**JobRequirementService**
- CRUD operations for jobs
- Manages active job
- Provides job context

## Security Layers

```
┌─────────────────────────────────────┐
│  Input Validation                   │
│  - File type checking               │
│  - File size limits (10MB)          │
│  - Input sanitization               │
└─────────────────────────────────────┘
           ▼
┌─────────────────────────────────────┐
│  API Key Security                   │
│  - Keys in properties file          │
│  - Not committed to git             │
│  - Environment variable support     │
└─────────────────────────────────────┘
           ▼
┌─────────────────────────────────────┐
│  CORS Configuration                 │
│  - Restrict origins                 │
│  - Configured in Spring Boot        │
└─────────────────────────────────────┘
           ▼
┌─────────────────────────────────────┐
│  OAuth 2.0 (Google Drive)           │
│  - User consent required            │
│  - Token-based authentication       │
│  - Refresh token handling           │
└─────────────────────────────────────┘
```

## Technology Choices

| Component | Technology | Reason |
|-----------|-----------|--------|
| Backend Framework | Spring Boot | Rapid development, robust ecosystem |
| Database | H2 (in-memory) | Quick setup, development friendly |
| PDF Parsing | Apache PDFBox | Reliable, well-maintained |
| Word Parsing | Apache POI | Industry standard |
| AI Analysis | Google Gemini | State-of-the-art language model |
| HTTP Client | OkHttp | Modern, efficient |
| JSON Processing | Gson | Simple, reliable |
| Frontend | React | Component-based, modern |
| API Client | Axios | Promise-based, easy to use |

## Deployment Options

### Development (Current)
- H2 in-memory database
- Embedded Tomcat server
- npm development server

### Production (Recommended)
- PostgreSQL/MySQL database
- Containerized (Docker)
- Nginx reverse proxy
- HTTPS/SSL certificates
- Environment-based configuration
- Logging and monitoring

---

This architecture provides a solid foundation for AI-powered resume analysis with clean separation of concerns and extensibility for future enhancements.

