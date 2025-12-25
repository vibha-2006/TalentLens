# Google Drive Integration Setup

This file explains how to set up Google Drive integration for TalentLens.

## Prerequisites
- A Google Cloud Platform account
- Access to Google Cloud Console

## Steps to Enable Google Drive Integration

### 1. Create a Google Cloud Project

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Click "Select a Project" → "New Project"
3. Enter project name: "TalentLens"
4. Click "Create"

### 2. Enable Google Drive API

1. In your project, go to "APIs & Services" → "Library"
2. Search for "Google Drive API"
3. Click on it and press "Enable"

### 3. Create OAuth 2.0 Credentials

1. Go to "APIs & Services" → "Credentials"
2. Click "Create Credentials" → "OAuth client ID"
3. If prompted, configure OAuth consent screen:
   - User Type: External
   - App name: TalentLens
   - User support email: Your email
   - Developer contact: Your email
   - Click "Save and Continue"
   - Scopes: Skip for now
   - Test users: Add your email
   - Click "Save and Continue"
4. Back to "Create OAuth client ID":
   - Application type: Desktop app
   - Name: TalentLens Desktop Client
   - Click "Create"
5. Download the JSON file

### 4. Install Credentials

1. Rename the downloaded file to `credentials.json`
2. Place it in: `src/main/resources/credentials.json`
3. **IMPORTANT**: This file is git-ignored for security

### 5. Enable in Application

Edit `src/main/resources/application.properties`:

```properties
google.drive.enabled=true
```

### 6. First Run Authorization

1. Start the backend application
2. Try to import from Google Drive
3. A browser window will open
4. Sign in to your Google account
5. Grant permissions to access Google Drive
6. You'll see "Received verification code"
7. Return to the application

The authorization token will be saved in the `tokens/` directory (also git-ignored).

## Testing

To test if Google Drive integration is working:

1. Upload some resume PDFs/Word docs to your Google Drive
2. In TalentLens, click "Upload Resumes" → "Import from Drive"
3. Leave folder ID empty (or provide specific folder ID)
4. Click "Import from Drive"

## Getting a Folder ID

To import from a specific folder:

1. Open Google Drive in browser
2. Navigate to the folder
3. Look at the URL: `https://drive.google.com/drive/folders/FOLDER_ID_HERE`
4. Copy the folder ID
5. Paste it in TalentLens

## Troubleshooting

**"Resource not found: credentials.json"**
- Make sure credentials.json is in src/main/resources/
- File name must be exactly "credentials.json"

**"Google Drive integration is not enabled"**
- Set `google.drive.enabled=true` in application.properties

**"Access denied"**
- Make sure you granted all required permissions
- Delete the `tokens/` folder and re-authorize

**"403 Forbidden"**
- Your API quota might be exceeded
- Wait a few minutes and try again
- Check Google Cloud Console for quota limits

## Security Notes

⚠️ **NEVER commit these files to git:**
- `src/main/resources/credentials.json`
- `tokens/` directory

These are already in .gitignore, but be careful not to share them publicly.

## Optional: Service Account (Production)

For production deployments, consider using a Service Account instead of OAuth:

1. In Google Cloud Console → "IAM & Admin" → "Service Accounts"
2. Create a service account
3. Grant it Google Drive access
4. Download the JSON key
5. Update the code to use service account authentication

This avoids the browser-based OAuth flow.

## Supported File Types

The Drive integration will import:
- PDF files (`.pdf`)
- Word documents (`.docx`)

Other file types will be skipped.

## Rate Limits

Google Drive API has rate limits:
- 1,000 requests per 100 seconds per user
- 10,000 requests per 100 seconds per project

If importing many resumes, consider adding delays between requests.

