import React, { useState } from 'react';
import { resumeService } from '../services/api';
import '../styles/ResumeUpload.css';

const ResumeUpload = ({ onUploadSuccess }) => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [selectedFiles, setSelectedFiles] = useState([]);
    const [uploadMode, setUploadMode] = useState('single'); // 'single', 'multiple', 'zip'
    const [uploading, setUploading] = useState(false);
    const [error, setError] = useState('');
    const [driveImporting, setDriveImporting] = useState(false);
    const [driveFolderId, setDriveFolderId] = useState('');
    const [aiProvider, setAiProvider] = useState('openai');
    const [uploadProgress, setUploadProgress] = useState('');

    const handleFileSelect = (e) => {
        const files = Array.from(e.target.files);

        if (uploadMode === 'zip') {
            const file = files[0];
            if (file) {
                if (file.type === 'application/zip' || file.type === 'application/x-zip-compressed' || file.name.toLowerCase().endsWith('.zip')) {
                    setSelectedFile(file);
                    setError('');
                } else {
                    setError('Please select a ZIP file');
                    setSelectedFile(null);
                }
            }
        } else if (uploadMode === 'multiple') {
            const validTypes = ['application/pdf', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'application/msword'];
            const validFiles = files.filter(file =>
                validTypes.includes(file.type) ||
                file.name.toLowerCase().endsWith('.pdf') ||
                file.name.toLowerCase().endsWith('.doc') ||
                file.name.toLowerCase().endsWith('.docx')
            );

            if (validFiles.length > 0) {
                setSelectedFiles(validFiles);
                setError('');
            } else {
                setError('Please select PDF or Word documents');
                setSelectedFiles([]);
            }
        } else {
            const file = files[0];
            if (file) {
                const validTypes = ['application/pdf', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'application/msword'];
                if (validTypes.includes(file.type) || file.name.toLowerCase().endsWith('.pdf') || file.name.toLowerCase().endsWith('.doc') || file.name.toLowerCase().endsWith('.docx')) {
                    setSelectedFile(file);
                    setError('');
                } else {
                    setError('Please select a PDF or Word document');
                    setSelectedFile(null);
                }
            }
        }
    };

    const handleUploadModeChange = (mode) => {
        setUploadMode(mode);
        setSelectedFile(null);
        setSelectedFiles([]);
        setError('');
        setUploadProgress('');
        // Reset file input
        const fileInput = document.getElementById('fileInput');
        if (fileInput) fileInput.value = '';
    };

    const handleUpload = async () => {
        if (uploadMode === 'single' && !selectedFile) {
            setError('Please select a file first');
            return;
        }

        if (uploadMode === 'multiple' && selectedFiles.length === 0) {
            setError('Please select files first');
            return;
        }

        if (uploadMode === 'zip' && !selectedFile) {
            setError('Please select a ZIP file first');
            return;
        }

        setUploading(true);
        setError('');
        setUploadProgress('');

        try {
            let result;

            if (uploadMode === 'zip') {
                setUploadProgress('Extracting and analyzing resumes from ZIP...');
                result = await resumeService.uploadZipFile(selectedFile, aiProvider);
                setUploadProgress(`Successfully processed ${result.length} resume(s) from ZIP file`);
            } else if (uploadMode === 'multiple') {
                setUploadProgress(`Analyzing ${selectedFiles.length} resume(s)...`);
                result = await resumeService.uploadMultipleResumes(selectedFiles, aiProvider);
                setUploadProgress(`Successfully processed ${result.length} resume(s)`);
            } else {
                result = await resumeService.uploadResume(selectedFile, aiProvider);
            }

            setSelectedFile(null);
            setSelectedFiles([]);
            // Reset file input
            document.getElementById('fileInput').value = '';

            if (onUploadSuccess) {
                onUploadSuccess(result);
            }
        } catch (err) {
            setError('Upload failed: ' + (err.response?.data || err.message));
            setUploadProgress('');
        } finally {
            setUploading(false);
        }
    };

    const handleDriveImport = async () => {
        setDriveImporting(true);
        setError('');

        try {
            const results = await resumeService.importFromGoogleDrive(driveFolderId, aiProvider);
            setDriveFolderId('');
            if (onUploadSuccess) {
                onUploadSuccess(results);
            }
        } catch (err) {
            setError('Google Drive import failed: ' + (err.response?.data || err.message));
        } finally {
            setDriveImporting(false);
        }
    };

    return (
        <div className="resume-upload">
            <h2>Upload Resumes</h2>

            <div className="ai-provider-selector">
                <label htmlFor="aiProvider">AI Provider:</label>
                <select
                    id="aiProvider"
                    value={aiProvider}
                    onChange={(e) => setAiProvider(e.target.value)}
                    disabled={uploading || driveImporting}
                    className="provider-select"
                >
                    <option value="openai">OpenAI (GPT-3.5)</option>
                    <option value="gemini">Google Gemini</option>
                    <option value="groq">Groq (Llama 3.1)</option>
                </select>
            </div>

            <div className="upload-section">
                <h3>Upload from Computer</h3>

                <div className="upload-mode-selector">
                    <button
                        className={`mode-button ${uploadMode === 'single' ? 'active' : ''}`}
                        onClick={() => handleUploadModeChange('single')}
                        disabled={uploading}
                    >
                        Single File
                    </button>
                    <button
                        className={`mode-button ${uploadMode === 'multiple' ? 'active' : ''}`}
                        onClick={() => handleUploadModeChange('multiple')}
                        disabled={uploading}
                    >
                        Multiple Files
                    </button>
                    <button
                        className={`mode-button ${uploadMode === 'zip' ? 'active' : ''}`}
                        onClick={() => handleUploadModeChange('zip')}
                        disabled={uploading}
                    >
                        ZIP File
                    </button>
                </div>

                <div className="file-input-container">
                    <input
                        type="file"
                        id="fileInput"
                        accept={uploadMode === 'zip' ? '.zip' : '.pdf,.doc,.docx'}
                        multiple={uploadMode === 'multiple'}
                        onChange={handleFileSelect}
                        disabled={uploading}
                    />
                    {uploadMode === 'single' && selectedFile && (
                        <div className="selected-file">
                            Selected: {selectedFile.name}
                        </div>
                    )}
                    {uploadMode === 'multiple' && selectedFiles.length > 0 && (
                        <div className="selected-files">
                            <strong>Selected {selectedFiles.length} file(s):</strong>
                            <ul>
                                {selectedFiles.map((file, index) => (
                                    <li key={index}>{file.name}</li>
                                ))}
                            </ul>
                        </div>
                    )}
                    {uploadMode === 'zip' && selectedFile && (
                        <div className="selected-file">
                            Selected ZIP: {selectedFile.name}
                        </div>
                    )}
                </div>

                {uploadProgress && (
                    <div className="upload-progress">
                        {uploadProgress}
                    </div>
                )}

                <button
                    onClick={handleUpload}
                    disabled={(uploadMode === 'single' && !selectedFile) ||
                             (uploadMode === 'multiple' && selectedFiles.length === 0) ||
                             (uploadMode === 'zip' && !selectedFile) ||
                             uploading}
                    className="upload-button"
                >
                    {uploading ? 'Analyzing...' : 'Upload & Analyze'}
                </button>
            </div>

            <div className="divider">OR</div>

            <div className="drive-section">
                <h3>Import from Google Drive</h3>
                <div className="drive-input-container">
                    <input
                        type="text"
                        placeholder="Folder ID (optional)"
                        value={driveFolderId}
                        onChange={(e) => setDriveFolderId(e.target.value)}
                        disabled={driveImporting}
                    />
                    <button
                        onClick={handleDriveImport}
                        disabled={driveImporting}
                        className="import-button"
                    >
                        {driveImporting ? 'Importing...' : 'Import from Drive'}
                    </button>
                </div>
                <p className="help-text">
                    Leave folder ID empty to import from root, or provide a specific folder ID
                </p>
            </div>

            {error && <div className="error-message">{error}</div>}
        </div>
    );
};

export default ResumeUpload;

