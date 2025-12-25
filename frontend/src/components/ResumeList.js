import React, { useState, useEffect } from 'react';
import { resumeService } from '../services/api';
import '../styles/ResumeList.css';

const ResumeList = ({ refreshTrigger }) => {
    const [resumes, setResumes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [selectedResume, setSelectedResume] = useState(null);

    useEffect(() => {
        loadResumes();
    }, [refreshTrigger]);

    const loadResumes = async () => {
        setLoading(true);
        setError('');
        try {
            const data = await resumeService.getAllResumes();
            setResumes(data);
        } catch (err) {
            setError('Failed to load resumes: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this resume?')) {
            try {
                await resumeService.deleteResume(id);
                loadResumes();
            } catch (err) {
                alert('Failed to delete resume: ' + err.message);
            }
        }
    };

    const handleViewDetails = (resume) => {
        setSelectedResume(selectedResume?.id === resume.id ? null : resume);
    };

    const getScoreColor = (score) => {
        if (score >= 90) return '#4CAF50';
        if (score >= 75) return '#8BC34A';
        if (score >= 60) return '#FFC107';
        return '#F44336';
    };

    const getScoreLabel = (score) => {
        if (score >= 90) return 'Excellent Match';
        if (score >= 75) return 'Good Match';
        if (score >= 60) return 'Fair Match';
        return 'Poor Match';
    };

    if (loading) {
        return <div className="loading">Loading resumes...</div>;
    }

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    return (
        <div className="resume-list">
            <h2>Ranked Resumes ({resumes.length})</h2>

            {resumes.length === 0 ? (
                <div className="no-resumes">
                    <p>No resumes uploaded yet. Upload resumes to get started.</p>
                </div>
            ) : (
                <div className="resumes-container">
                    {resumes.map((resume) => (
                        <div key={resume.id} className="resume-card">
                            <div className="resume-header">
                                <div className="resume-info">
                                    <h3>{resume.candidateName || 'Unknown Candidate'}</h3>
                                    <p className="filename">{resume.fileName}</p>
                                    {resume.email && <p className="contact">📧 {resume.email}</p>}
                                    {resume.phone && <p className="contact">📱 {resume.phone}</p>}
                                </div>
                                <div
                                    className="score-badge"
                                    style={{ backgroundColor: getScoreColor(resume.matchScore) }}
                                >
                                    <div className="score-value">{resume.matchScore?.toFixed(1)}%</div>
                                    <div className="score-label">{getScoreLabel(resume.matchScore)}</div>
                                </div>
                            </div>

                            <div className="resume-meta">
                                <span className="source-badge">{resume.source}</span>
                                <span className="date">
                                    Analyzed: {new Date(resume.analyzedAt).toLocaleDateString()}
                                </span>
                            </div>

                            {resume.skills && (
                                <div className="skills-preview">
                                    <strong>Skills:</strong> {resume.skills.substring(0, 100)}
                                    {resume.skills.length > 100 && '...'}
                                </div>
                            )}

                            <div className="resume-actions">
                                <button
                                    onClick={() => handleViewDetails(resume)}
                                    className="btn-details"
                                >
                                    {selectedResume?.id === resume.id ? 'Hide Details' : 'View Details'}
                                </button>
                                <button
                                    onClick={() => handleDelete(resume.id)}
                                    className="btn-delete"
                                >
                                    Delete
                                </button>
                            </div>

                            {selectedResume?.id === resume.id && (
                                <div className="resume-details">
                                    <div className="detail-section">
                                        <h4>Skills</h4>
                                        <p>{resume.skills || 'Not extracted'}</p>
                                    </div>
                                    <div className="detail-section">
                                        <h4>Experience</h4>
                                        <p>{resume.experience || 'Not extracted'}</p>
                                    </div>
                                    <div className="detail-section">
                                        <h4>AI Analysis</h4>
                                        <p className="analysis-text">{resume.matchAnalysis || 'No analysis available'}</p>
                                    </div>
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ResumeList;

