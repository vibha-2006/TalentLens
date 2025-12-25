import React, { useState, useEffect } from 'react';
import { jobRequirementService } from '../services/api';
import '../styles/JobRequirementForm.css';

const JobRequirementForm = ({ onJobRequirementCreated }) => {
    const [formData, setFormData] = useState({
        jobTitle: '',
        description: '',
        requiredSkills: '',
        preferredSkills: '',
        experienceLevel: '',
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [activeJobReq, setActiveJobReq] = useState(null);

    useEffect(() => {
        loadActiveJobRequirement();
    }, []);

    const loadActiveJobRequirement = async () => {
        try {
            const jobReq = await jobRequirementService.getActiveJobRequirement();
            setActiveJobReq(jobReq);
        } catch (err) {
            console.log('No active job requirement');
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const created = await jobRequirementService.createJobRequirement(formData);
            setActiveJobReq(created);
            setFormData({
                jobTitle: '',
                description: '',
                requiredSkills: '',
                preferredSkills: '',
                experienceLevel: '',
            });
            if (onJobRequirementCreated) {
                onJobRequirementCreated(created);
            }
        } catch (err) {
            setError('Failed to create job requirement: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="job-requirement-form">
            <h2>Job Requirement</h2>

            {activeJobReq && (
                <div className="active-job-requirement">
                    <h3>Active Job: {activeJobReq.jobTitle}</h3>
                    <p><strong>Required Skills:</strong> {activeJobReq.requiredSkills}</p>
                    <p><strong>Experience Level:</strong> {activeJobReq.experienceLevel}</p>
                </div>
            )}

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="jobTitle">Job Title *</label>
                    <input
                        type="text"
                        id="jobTitle"
                        name="jobTitle"
                        value={formData.jobTitle}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="description">Job Description *</label>
                    <textarea
                        id="description"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        rows="4"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="requiredSkills">Required Skills (comma-separated) *</label>
                    <input
                        type="text"
                        id="requiredSkills"
                        name="requiredSkills"
                        value={formData.requiredSkills}
                        onChange={handleChange}
                        placeholder="e.g., Java, Spring Boot, React, SQL"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="preferredSkills">Preferred Skills (comma-separated)</label>
                    <input
                        type="text"
                        id="preferredSkills"
                        name="preferredSkills"
                        value={formData.preferredSkills}
                        onChange={handleChange}
                        placeholder="e.g., AWS, Docker, Kubernetes"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="experienceLevel">Experience Level *</label>
                    <select
                        id="experienceLevel"
                        name="experienceLevel"
                        value={formData.experienceLevel}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select experience level</option>
                        <option value="Entry Level">Entry Level (0-2 years)</option>
                        <option value="Mid Level">Mid Level (3-5 years)</option>
                        <option value="Senior">Senior (6-10 years)</option>
                        <option value="Lead">Lead (10+ years)</option>
                    </select>
                </div>

                {error && <div className="error-message">{error}</div>}

                <button type="submit" disabled={loading}>
                    {loading ? 'Creating...' : 'Create Job Requirement'}
                </button>
            </form>
        </div>
    );
};

export default JobRequirementForm;

