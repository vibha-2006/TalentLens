# TalentLens - AI-Powered Resume Screening Application

🎯 **Full-stack intelligent resume analysis platform** with multi-AI provider support for automated candidate screening and ranking.

## 🚀 Technology Stack

### Frontend
- **React 18** - Modern UI framework
- **JavaScript (ES6+)** - Core programming language
- **CSS3** - Responsive styling
- **Axios** - HTTP client for API communication

### Backend
- **Java 17** - Backend programming language
- **Spring Boot 3.x** - Enterprise application framework
- **Maven** - Dependency management and build tool
- **REST API** - RESTful web services

### AI Providers (Multi-Provider Support)
- **OpenAI** - GPT-4o, GPT-4-turbo, GPT-4, GPT-3.5-turbo
- **Google Gemini** - Gemini-1.5-pro, Gemini-1.5-flash, Gemini-pro
- **Groq** - Llama-3.3-70b, Llama-3.1-8b, Mixtral-8x7b, Gemma2-9b

## ✨ Key Features

- 📄 **Resume Upload** - Support for PDF and DOC formats
- 📦 **Multi-File Upload** - Upload multiple resumes individually or as a ZIP file
- 🔌 **Google Drive Integration** - Direct access to resumes from Google Drive
- 🤖 **Multi-AI Provider** - Choose between OpenAI, Gemini, or Groq for analysis
- 📊 **Skill Matching** - Rank resumes based on job requirement alignment
- ⚙️ **Admin Settings** - Configure API keys and models for all providers
- 🎯 **Job Requirements** - Define job criteria for targeted screening

## 📦 Project Structure

```
TalentLens/
├── frontend/                       # React frontend application
│   ├── public/
│   │   └── index.html             # HTML template
│   ├── src/
│   │   ├── components/            # React components
│   │   │   ├── AdminSettings.js   # AI provider configuration
│   │   │   ├── JobRequirementForm.js  # Job definition form
│   │   │   ├── ResumeUpload.js    # Resume upload component
│   │   │   └── ResumeList.js      # Resume ranking display
│   │   ├── services/
│   │   │   └── api.js             # API service layer
│   │   ├── styles/                # CSS stylesheets
│   │   ├── App.js                 # Main application component
│   │   └── index.js               # Application entry point
│   ├── package.json               # Node.js dependencies
│   └── package-lock.json
│
├── src/main/                      # Spring Boot backend
│   ├── java/org/example/
│   │   ├── controller/            # REST API controllers
│   │   ├── service/               # Business logic layer
│   │   │   ├── OpenAIService.java
│   │   │   ├── GeminiService.java
│   │   │   └── GroqService.java
│   │   ├── model/                 # Data models
│   │   └── config/                # Configuration classes
│   └── resources/
│       ├── application.properties # Backend configuration
│       └── application.properties.template
│
├── pom.xml                        # Maven configuration
├── README.md                      # This file
├── AdminSettings_Documentation.md # Detailed component guide
└── .gitignore                     # Git ignore rules
```

## 🛠️ Setup Instructions

### Prerequisites

- **Java 17+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **Node.js 16+** ([Download](https://nodejs.org/))
- **npm 8+** (comes with Node.js)

### Backend Setup

1. **Navigate to project root**:
   ```bash
   cd TalentLens
   ```

2. **Configure API keys**:
   - Copy `src/main/resources/application.properties.template` to `application.properties`
   - Add your API keys:
   ```properties
   # OpenAI Configuration
   openai.api.key=your_openai_api_key_here
   openai.model=gpt-3.5-turbo
   
   # Gemini Configuration
   gemini.api.key=your_gemini_api_key_here
   gemini.model=gemini-1.5-flash
   
   # Groq Configuration
   groq.api.key=your_groq_api_key_here
   groq.model=llama-3.3-70b-versatile
   ```

3. **Build the project**:
   ```bash
   mvn clean install
   ```

4. **Run the backend**:
   ```bash
   mvn spring-boot:run
   ```
   
   Backend will start on: **http://localhost:8080**

   Or use the batch file (Windows):
   ```bash
   start-backend.bat
   ```

### Frontend Setup

1. **Navigate to frontend directory**:
   ```bash
   cd frontend
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start development server**:
   ```bash
   npm start
   ```
   
   Frontend will start on: **http://localhost:3000**

   Or use the batch file (Windows):
   ```bash
   start-frontend.bat
   ```

### Quick Start (Both Services)

For Windows, use the combined startup script:
```bash
start-talentlens.bat
```

This will start both backend and frontend automatically.

## 🔑 API Keys

Get your API keys from these providers:

### OpenAI
1. Visit: [OpenAI Platform](https://platform.openai.com/api-keys)
2. Sign up or log in
3. Generate new API key
4. Copy and paste into `application.properties`

### Google Gemini
1. Visit: [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Sign in with Google account
3. Create API key
4. **Important**: Enable "Generative Language API" in [Google Cloud Console](https://console.cloud.google.com/apis/library/generativelanguage.googleapis.com)
5. Copy and paste into `application.properties`

### Groq
1. Visit: [Groq Console](https://console.groq.com/keys)
2. Sign up or log in
3. Generate API key
4. Copy and paste into `application.properties`

## 📖 Documentation

- **[AdminSettings Component Guide](./AdminSettings_Documentation.md)** - Comprehensive documentation for the admin configuration interface
- **[Quick Start Guide](./QUICKSTART.md)** - Fast setup instructions
- **[OpenAI Integration](./OPENAI_QUICK_START.md)** - OpenAI-specific setup
- **[Groq Integration](./GROQ_QUICK_START.md)** - Groq-specific setup

## 🎯 Usage

### 1. Configure AI Providers (Admin)
- Navigate to **Admin Settings** in the UI
- Enter API keys for desired providers
- Select AI models
- Test connections
- Save configuration

### 2. Define Job Requirements
- Go to **Job Requirements** tab
- Enter job title, required skills, and description
- Select AI provider for analysis
- Submit requirements

### 3. Upload Resumes
- Navigate to **Upload Resumes** tab
- Choose AI provider
- Upload individual PDF/DOC files OR ZIP file with multiple resumes
- Optionally: Connect Google Drive for direct access

### 4. View Results
- Check **Resume List** tab
- View ranked candidates by skill match percentage
- Review detailed analysis for each resume

## 🔒 Security Best Practices

- ✅ Never commit API keys to Git (use `.env` or `application.properties` locally)
- ✅ Use environment variables for production deployments
- ✅ Keep `application.properties` in `.gitignore` (template provided)
- ✅ Rotate API keys regularly
- ✅ Use separate keys for development and production
- ✅ Enable API key restrictions (IP whitelist, rate limits)

## 🚨 Troubleshooting

### Backend Issues

**Port Already in Use**:
```bash
# Change port in application.properties
server.port=8081
```

**Maven Build Fails**:
```bash
# Clear Maven cache
mvn clean
rm -rf ~/.m2/repository
mvn install
```

### Frontend Issues

**npm install fails**:
```bash
# Clear npm cache
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

**Port 3000 already in use**:
```bash
# Set different port (Windows)
set PORT=3001 && npm start

# Or (Linux/Mac)
PORT=3001 npm start
```

### API Issues

**OpenAI 401 Unauthorized**:
- Verify API key is correct
- Check if key has credits/billing enabled
- Ensure key hasn't expired

**Gemini 404 Not Found**:
- Verify "Generative Language API" is enabled in Google Cloud Console
- Check model name is correct (`gemini-1.5-flash` or `gemini-1.5-pro`)
- Ensure API version is `v1beta` in endpoint URL

**Groq Model Decommissioned**:
- Update to latest supported models (see Admin Settings for list)
- Refer to [Groq Deprecations](https://console.groq.com/docs/deprecations)

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository**
   ```bash
   git clone https://github.com/vibha-2006/TalentLens.git
   ```

2. **Create feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make changes and commit**:
   ```bash
   git add .
   git commit -m "feat: Add your feature description"
   ```

4. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

5. **Submit Pull Request** on GitHub

### Commit Message Convention
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `refactor:` Code refactoring
- `test:` Adding tests
- `chore:` Maintenance tasks

## 📊 Project Status

- ✅ Multi-AI provider support (OpenAI, Gemini, Groq)
- ✅ Resume upload and parsing
- ✅ Multi-file upload (individual + ZIP)
- ✅ Skill-based ranking
- ✅ Admin configuration panel
- ✅ Google Drive integration
- ✅ Job requirement management
- 🚧 Advanced analytics dashboard (planned)
- 🚧 Email notifications (planned)
- 🚧 Candidate interview scheduling (planned)

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author

**Vibha**
- GitHub: [@vibha-2006](https://github.com/vibha-2006)
- Project: [TalentLens](https://github.com/vibha-2006/TalentLens)

## 🙏 Acknowledgments

- OpenAI for GPT models
- Google for Gemini AI
- Groq for lightning-fast inference
- Spring Boot and React communities

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on [GitHub Issues](https://github.com/vibha-2006/TalentLens/issues)
- Refer to documentation files in the repository

---

**Built with ❤️ for smarter recruitment**

Last Updated: December 25, 2025

