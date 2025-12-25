import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const resumeService = {
    uploadResume: async (file, aiProvider = 'openai') => {
        const formData = new FormData();
        formData.append('file', file);
        const response = await axios.post(`${API_BASE_URL}/resumes/upload`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            params: { aiProvider }
        });
        return response.data;
    },

    uploadMultipleResumes: async (files, aiProvider = 'openai') => {
        const formData = new FormData();
        files.forEach(file => {
            formData.append('files', file);
        });
        const response = await axios.post(`${API_BASE_URL}/resumes/upload-multiple`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            params: { aiProvider }
        });
        return response.data;
    },

    uploadZipFile: async (zipFile, aiProvider = 'openai') => {
        const formData = new FormData();
        formData.append('file', zipFile);
        const response = await axios.post(`${API_BASE_URL}/resumes/upload-zip`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            params: { aiProvider }
        });
        return response.data;
    },

    importFromGoogleDrive: async (folderId = '', aiProvider = 'openai') => {
        const response = await axios.post(
            `${API_BASE_URL}/resumes/import-from-drive`,
            null,
            { params: { folderId, aiProvider } }
        );
        return response.data;
    },

    getAllResumes: async () => {
        const response = await axios.get(`${API_BASE_URL}/resumes`);
        return response.data;
    },

    getResumeById: async (id) => {
        const response = await axios.get(`${API_BASE_URL}/resumes/${id}`);
        return response.data;
    },

    deleteResume: async (id) => {
        await axios.delete(`${API_BASE_URL}/resumes/${id}`);
    },
};

export const jobRequirementService = {
    createJobRequirement: async (jobReq) => {
        const response = await axios.post(`${API_BASE_URL}/job-requirements`, jobReq);
        return response.data;
    },

    getActiveJobRequirement: async () => {
        const response = await axios.get(`${API_BASE_URL}/job-requirements/active`);
        return response.data;
    },

    getAllJobRequirements: async () => {
        const response = await axios.get(`${API_BASE_URL}/job-requirements`);
        return response.data;
    },

    getJobRequirementById: async (id) => {
        const response = await axios.get(`${API_BASE_URL}/job-requirements/${id}`);
        return response.data;
    },

    updateJobRequirement: async (id, jobReq) => {
        const response = await axios.put(`${API_BASE_URL}/job-requirements/${id}`, jobReq);
        return response.data;
    },

    setActiveJobRequirement: async (id) => {
        await axios.put(`${API_BASE_URL}/job-requirements/${id}/activate`);
    },

    deleteJobRequirement: async (id) => {
        await axios.delete(`${API_BASE_URL}/job-requirements/${id}`);
    },
};

export const adminSettingsService = {
    getAllSettings: async () => {
        const response = await axios.get(`${API_BASE_URL}/admin/settings`);
        return response.data;
    },

    updateSettings: async (settings) => {
        const response = await axios.put(`${API_BASE_URL}/admin/settings`, settings);
        return response.data;
    },

    testConnection: async (provider) => {
        const response = await axios.get(`${API_BASE_URL}/admin/settings/test/${provider}`);
        return response.data;
    },
};

