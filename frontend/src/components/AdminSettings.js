import React, { useState, useEffect } from 'react';
import { adminSettingsService } from '../services/api';
import '../styles/AdminSettings.css';

function AdminSettings() {
    const [settings, setSettings] = useState({
        openai: {
            provider: 'openai',
            apiKey: '',
            model: 'gpt-3.5-turbo',
            apiUrl: 'https://api.openai.com/v1/chat/completions',
            enabled: false
        },
        gemini: {
            provider: 'gemini',
            apiKey: '',
            model: 'gemini-1.5-flash',
            apiUrl: 'https://generativelanguage.googleapis.com/v1beta/models',
            enabled: false
        },
        groq: {
            provider: 'groq',
            apiKey: '',
            model: 'llama-3.3-70b-versatile',
            apiUrl: 'https://api.groq.com/openai/v1/chat/completions',
            enabled: false
        }
    });

    const [editMode, setEditMode] = useState({
        openai: false,
        gemini: false,
        groq: false
    });

    const [tempKeys, setTempKeys] = useState({
        openai: '',
        gemini: '',
        groq: ''
    });

    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState({ type: '', text: '' });
    const [testResults, setTestResults] = useState({});

    useEffect(() => {
        loadSettings();
    }, []);

    const loadSettings = async () => {
        try {
            setLoading(true);
            const data = await adminSettingsService.getAllSettings();
            setSettings(data);
            setMessage({ type: '', text: '' });
        } catch (error) {
            setMessage({
                type: 'error',
                text: 'Failed to load settings: ' + error.message
            });
        } finally {
            setLoading(false);
        }
    };

    const handleEdit = (provider) => {
        setEditMode({ ...editMode, [provider]: true });
        setTempKeys({ ...tempKeys, [provider]: '' });
    };

    const handleCancel = (provider) => {
        setEditMode({ ...editMode, [provider]: false });
        setTempKeys({ ...tempKeys, [provider]: '' });
    };

    const handleInputChange = (provider, field, value) => {
        setSettings({
            ...settings,
            [provider]: {
                ...settings[provider],
                [field]: value
            }
        });
    };

    const handleSave = async (provider) => {
        try {
            setLoading(true);
            setMessage({ type: '', text: '' });

            const updatedSettings = { ...settings };

            // If a new API key was entered, use it
            if (tempKeys[provider] && tempKeys[provider].trim() !== '') {
                updatedSettings[provider].apiKey = tempKeys[provider];
            }

            await adminSettingsService.updateSettings(updatedSettings);

            setMessage({
                type: 'success',
                text: `${provider.toUpperCase()} settings saved successfully! Please restart the backend for changes to take full effect.`
            });

            setEditMode({ ...editMode, [provider]: false });
            setTempKeys({ ...tempKeys, [provider]: '' });

            // Reload settings to get masked key
            await loadSettings();
        } catch (error) {
            setMessage({
                type: 'error',
                text: `Failed to save ${provider} settings: ` + error.message
            });
        } finally {
            setLoading(false);
        }
    };

    const handleTestConnection = async (provider) => {
        try {
            setLoading(true);
            const result = await adminSettingsService.testConnection(provider);
            setTestResults({ ...testResults, [provider]: result });
            setMessage({
                type: result.connected ? 'success' : 'warning',
                text: `${provider.toUpperCase()}: ${result.message}`
            });
        } catch (error) {
            setMessage({
                type: 'error',
                text: `Failed to test ${provider} connection: ` + error.message
            });
        } finally {
            setLoading(false);
        }
    };

    const renderProviderCard = (provider, providerName, availableModels) => {
        const config = settings[provider];
        const isEditing = editMode[provider];

        return (
            <div className="provider-card" key={provider}>
                <div className="provider-header">
                    <h3>
                        {providerName}
                        {config.enabled && <span className="status-badge enabled">Configured</span>}
                        {!config.enabled && <span className="status-badge disabled">Not Configured</span>}
                    </h3>
                </div>

                <div className="provider-body">
                    <div className="form-group">
                        <label>API Key:</label>
                        {!isEditing ? (
                            <div className="api-key-display">
                                <input
                                    type="text"
                                    value={config.apiKey || 'Not configured'}
                                    disabled
                                    className="masked-input"
                                />
                                <button
                                    onClick={() => handleEdit(provider)}
                                    className="btn-edit"
                                >
                                    Edit
                                </button>
                            </div>
                        ) : (
                            <input
                                type="password"
                                value={tempKeys[provider]}
                                onChange={(e) => setTempKeys({ ...tempKeys, [provider]: e.target.value })}
                                placeholder="Enter new API key"
                                className="input-field"
                            />
                        )}
                    </div>

                    <div className="form-group">
                        <label>Model:</label>
                        {!isEditing ? (
                            <input
                                type="text"
                                value={config.model}
                                disabled
                                className="input-field"
                            />
                        ) : (
                            <select
                                value={config.model}
                                onChange={(e) => handleInputChange(provider, 'model', e.target.value)}
                                className="input-field"
                            >
                                {availableModels.map(model => (
                                    <option key={model} value={model}>{model}</option>
                                ))}
                            </select>
                        )}
                    </div>

                    <div className="form-group">
                        <label>API URL:</label>
                        <input
                            type="text"
                            value={config.apiUrl}
                            onChange={(e) => handleInputChange(provider, 'apiUrl', e.target.value)}
                            disabled={!isEditing}
                            className="input-field"
                        />
                    </div>

                    <div className="button-group">
                        {!isEditing ? (
                            <>
                                <button
                                    onClick={() => handleTestConnection(provider)}
                                    disabled={loading || !config.enabled}
                                    className="btn btn-test"
                                >
                                    Test Connection
                                </button>
                            </>
                        ) : (
                            <>
                                <button
                                    onClick={() => handleSave(provider)}
                                    disabled={loading}
                                    className="btn btn-primary"
                                >
                                    Save
                                </button>
                                <button
                                    onClick={() => handleCancel(provider)}
                                    disabled={loading}
                                    className="btn btn-secondary"
                                >
                                    Cancel
                                </button>
                            </>
                        )}
                    </div>
                </div>
            </div>
        );
    };

    return (
        <div className="admin-settings">
            <div className="settings-header">
                <h2>🔧 Admin Settings - AI Provider Configuration</h2>
                <p className="subtitle">Configure API keys and settings for AI service providers</p>
            </div>

            {message.text && (
                <div className={`message message-${message.type}`}>
                    {message.text}
                </div>
            )}

            <div className="providers-grid">
                {renderProviderCard('openai', 'OpenAI', [
                    'gpt-4o',
                    'gpt-4o-mini',
                    'gpt-4-turbo',
                    'gpt-4',
                    'gpt-3.5-turbo'
                ])}

                {renderProviderCard('gemini', 'Google Gemini', [
                    'gemini-1.5-pro',
                    'gemini-1.5-flash',
                    'gemini-pro'
                ])}

                {renderProviderCard('groq', 'Groq', [
                    'llama-3.3-70b-versatile',
                    'llama-3.1-8b-instant',
                    'mixtral-8x7b-32768',
                    'gemma2-9b-it'
                ])}
            </div>

            <div className="help-section">
                <h3>📚 Help & Documentation</h3>
                <div className="help-content">
                    <div className="help-item">
                        <h4>OpenAI</h4>
                        <p>Get your API key from: <a href="https://platform.openai.com/api-keys" target="_blank" rel="noopener noreferrer">OpenAI Platform</a></p>
                    </div>
                    <div className="help-item">
                        <h4>Google Gemini</h4>
                        <p>Get your API key from: <a href="https://makersuite.google.com/app/apikey" target="_blank" rel="noopener noreferrer">Google AI Studio</a></p>
                        <p>Make sure to enable "Generative Language API" in Google Cloud Console</p>
                    </div>
                    <div className="help-item">
                        <h4>Groq</h4>
                        <p>Get your API key from: <a href="https://console.groq.com/keys" target="_blank" rel="noopener noreferrer">Groq Console</a></p>
                    </div>
                </div>
            </div>

            <div className="warning-section">
                <p>⚠️ <strong>Important:</strong> After saving settings, please restart the backend server for changes to take full effect.</p>
            </div>
        </div>
    );
}

export default AdminSettings;

