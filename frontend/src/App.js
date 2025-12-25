import React, { useState } from 'react';
import JobRequirementForm from './components/JobRequirementForm';
import ResumeUpload from './components/ResumeUpload';
import ResumeList from './components/ResumeList';
import AdminSettings from './components/AdminSettings';
import './styles/App.css';

function App() {
    const [refreshTrigger, setRefreshTrigger] = useState(0);
    const [activeTab, setActiveTab] = useState('upload');

    const handleUploadSuccess = () => {
        setRefreshTrigger(prev => prev + 1);
        setActiveTab('list');
    };

    const handleJobRequirementCreated = () => {
        // You could show a success message here
        console.log('Job requirement created successfully');
    };

    return (
        <div className="App">
            <header className="app-header">
                <h1>🎯 TalentLens</h1>
                <p className="tagline">AI-Powered Resume Shortlisting with OpenAI & Gemini</p>
            </header>

            <div className="app-container">
                <div className="sidebar">
                    <nav className="nav-tabs">
                        <button
                            className={activeTab === 'job' ? 'active' : ''}
                            onClick={() => setActiveTab('job')}
                        >
                            Job Requirements
                        </button>
                        <button
                            className={activeTab === 'upload' ? 'active' : ''}
                            onClick={() => setActiveTab('upload')}
                        >
                            Upload Resumes
                        </button>
                        <button
                            className={activeTab === 'list' ? 'active' : ''}
                            onClick={() => setActiveTab('list')}
                        >
                            View Rankings
                        </button>
                        <button
                            className={activeTab === 'admin' ? 'active' : ''}
                            onClick={() => setActiveTab('admin')}
                        >
                            Admin Settings
                        </button>
                    </nav>
                </div>

                <main className="main-content">
                    {activeTab === 'job' && (
                        <JobRequirementForm onJobRequirementCreated={handleJobRequirementCreated} />
                    )}

                    {activeTab === 'upload' && (
                        <ResumeUpload onUploadSuccess={handleUploadSuccess} />
                    )}

                    {activeTab === 'list' && (
                        <ResumeList refreshTrigger={refreshTrigger} />
                    )}

                    {activeTab === 'admin' && (
                        <AdminSettings />
                    )}
                </main>
            </div>

            <footer className="app-footer">
                <p>Powered by OpenAI & Google Gemini AI | TalentLens © 2024</p>
            </footer>
        </div>
    );
}

export default App;

