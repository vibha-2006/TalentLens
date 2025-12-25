# Multi-File Upload Feature Guide

## Overview
TalentLens now supports uploading multiple resumes in three different ways:
1. **Single File Upload** - Upload one resume at a time
2. **Multiple Files Upload** - Select and upload multiple resume files at once
3. **ZIP File Upload** - Upload a ZIP file containing multiple resumes

## Features

### 1. Single File Upload
- Upload one PDF or Word document (.pdf, .doc, .docx)
- Instant AI analysis using your selected provider (OpenAI, Gemini, or Groq)
- Real-time feedback

### 2. Multiple Files Upload
- Select multiple resume files at once from your computer
- Supports PDF and Word documents
- All files are analyzed sequentially using the same AI provider
- Progress indication showing number of files being processed
- Continues processing even if one file fails

### 3. ZIP File Upload
- Upload a ZIP archive containing multiple resumes
- Automatically extracts and processes all PDF and Word documents
- Ignores non-resume files (images, folders, etc.)
- Perfect for batch processing large numbers of resumes
- Shows how many resumes were successfully processed

## How to Use

### Frontend (React)

1. **Navigate to Upload Resumes tab**
2. **Select AI Provider** - Choose between OpenAI, Gemini, or Groq
3. **Choose Upload Mode** - Click one of three buttons:
   - "Single File" - For individual resume upload
   - "Multiple Files" - For selecting multiple files
   - "ZIP File" - For uploading a ZIP archive

4. **Select Files**:
   - **Single Mode**: Click "Choose File" and select one PDF/Word document
   - **Multiple Mode**: Click "Choose File", hold Ctrl/Cmd and select multiple files
   - **ZIP Mode**: Click "Choose File" and select a ZIP file

5. **Upload & Analyze** - Click the button to start processing
6. **View Results** - Switch to "View Rankings" tab to see all analyzed resumes

### Backend API Endpoints

#### Upload Single Resume
```
POST /api/resumes/upload
Parameters:
  - file: MultipartFile (PDF or Word document)
  - aiProvider: String (optional, default: "openai")
Response: ResumeDTO
```

#### Upload Multiple Resumes
```
POST /api/resumes/upload-multiple
Parameters:
  - files: MultipartFile[] (array of PDF or Word documents)
  - aiProvider: String (optional, default: "openai")
Response: List<ResumeDTO>
```

#### Upload ZIP File
```
POST /api/resumes/upload-zip
Parameters:
  - file: MultipartFile (ZIP file containing resumes)
  - aiProvider: String (optional, default: "openai")
Response: List<ResumeDTO>
```

## Technical Implementation

### Backend Changes

1. **ResumeController.java**
   - Added `uploadMultipleResumes()` endpoint
   - Added `uploadZipFile()` endpoint

2. **ResumeService.java**
   - Added `uploadAndAnalyzeMultipleResumes()` method
   - Added `uploadAndAnalyzeZipFile()` method
   - Uses Apache Commons Compress for ZIP file handling

3. **pom.xml**
   - Added Apache Commons Compress dependency (version 1.25.0)

### Frontend Changes

1. **ResumeUpload.js**
   - Added upload mode selector (single/multiple/zip)
   - Added state management for multiple files
   - Updated file selection handler for different modes
   - Added progress indicator
   - Enhanced UI to show selected files list

2. **api.js**
   - Added `uploadMultipleResumes()` function
   - Added `uploadZipFile()` function

3. **ResumeUpload.css**
   - Added styles for upload mode buttons
   - Added styles for selected files list
   - Added styles for progress indicator

## File Support

### Supported Formats
- **PDF** - `.pdf` (application/pdf)
- **Word 2007+** - `.docx` (application/vnd.openxmlformats-officedocument.wordprocessingml.document)
- **Word 97-2003** - `.doc` (application/msword)
- **ZIP Archives** - `.zip` (application/zip)

### ZIP File Requirements
- Must be a valid ZIP archive
- Can contain PDF and Word documents
- Subdirectories are supported (files at any depth will be processed)
- Non-resume files are automatically ignored
- At least one valid resume file must be present

## Error Handling

### Frontend
- Validates file types before upload
- Shows clear error messages for invalid files
- Displays progress for multi-file operations
- Continues processing if individual files fail

### Backend
- Validates file formats
- Handles corrupted or unreadable files gracefully
- Continues processing remaining files if one fails
- Returns detailed error messages
- Ensures at least one file is processed successfully from ZIP

## Performance Considerations

### Multiple Files Upload
- Files are processed sequentially (one at a time)
- Uses the same job requirement and AI provider for all files
- Efficient database operations with batch processing

### ZIP File Upload
- Extracts files in-memory (no temporary disk storage)
- Processes only supported file types
- 8KB buffer size for optimal extraction speed
- Memory-efficient streaming approach

## Best Practices

1. **Batch Processing**
   - Use ZIP upload for large batches (10+ resumes)
   - Use multiple files for small batches (2-10 resumes)
   - Use single file for immediate analysis

2. **File Preparation**
   - Ensure resumes are in PDF or Word format
   - Remove non-resume files from ZIP archives
   - Use clear, descriptive filenames

3. **AI Provider Selection**
   - Choose AI provider before uploading
   - All files in a batch use the same provider
   - OpenAI is default and most reliable

4. **Job Requirements**
   - Create job requirement before uploading resumes
   - Only one active job requirement at a time
   - All uploaded resumes are ranked against active job

## Troubleshooting

### "No active job requirement found"
- Create a job requirement first in the "Job Requirements" tab
- Ensure at least one job requirement is active

### "Please select files first"
- In Multiple Files mode, ensure files are selected
- Check that files are PDF or Word documents

### "No valid resume files found in ZIP"
- Ensure ZIP contains PDF or Word documents
- Check that files are not corrupted
- Verify file extensions are correct

### Upload fails for some files
- Check individual file formats
- Ensure files are not password-protected
- Verify files are not corrupted
- Check backend logs for specific errors

## Example Usage Scenarios

### Scenario 1: Recruiting Event
You collected 50 resumes at a job fair in a folder.
1. Create a ZIP file of all resumes
2. Select "ZIP File" mode
3. Upload the ZIP file
4. All 50 resumes are analyzed and ranked automatically

### Scenario 2: Email Applications
You received 5 resume emails today.
1. Download all 5 resume attachments
2. Select "Multiple Files" mode
3. Choose all 5 files at once
4. Upload and analyze together

### Scenario 3: Quick Review
You need to quickly check one candidate.
1. Select "Single File" mode
2. Choose the resume PDF
3. Get instant analysis and ranking

## Future Enhancements

Potential improvements for future versions:
- Parallel processing for multiple files
- Progress bar showing percentage complete
- Drag-and-drop file upload
- Ability to preview files before upload
- Support for additional file formats (TXT, RTF)
- Cloud storage integration (Dropbox, OneDrive)
- Resume deduplication
- Bulk delete/export functionality

## Related Documentation

- [QUICK_START_AI_PROVIDERS.md](QUICK_START_AI_PROVIDERS.md) - AI Provider setup guide
- [FRONTEND_BUILD_GUIDE.md](FRONTEND_BUILD_GUIDE.md) - Frontend build instructions
- [ARCHITECTURE.md](ARCHITECTURE.md) - System architecture overview
- [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) - Complete project documentation

## Support

For issues or questions:
1. Check the troubleshooting section above
2. Review backend console logs for errors
3. Check browser console for frontend errors
4. Verify all dependencies are installed correctly
5. Ensure backend and frontend servers are running

---

**Note**: This feature is production-ready and has been tested with various file types and sizes. The application handles errors gracefully and provides clear feedback to users.

