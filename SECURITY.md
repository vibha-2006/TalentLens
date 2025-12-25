# Security Policy

## Supported Versions

We release patches for security vulnerabilities for the following versions:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

We take the security of TalentLens seriously. If you have discovered a security vulnerability, we appreciate your help in disclosing it to us responsibly.

### How to Report

**Please do NOT report security vulnerabilities through public GitHub issues.**

Instead, please report them via email to: **[your-security-email@example.com]**

Include the following information:
- Type of vulnerability
- Full paths of source file(s) related to the vulnerability
- Location of the affected source code (tag/branch/commit or direct URL)
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the issue, including how an attacker might exploit it

### What to Expect

1. **Acknowledgment**: We will acknowledge receipt of your vulnerability report within 48 hours.

2. **Initial Assessment**: We will provide an initial assessment of the vulnerability within 7 days, including:
   - Confirmation of the vulnerability
   - Severity assessment (Critical, High, Medium, Low)
   - Expected timeline for a fix

3. **Fix Development**: We will work on developing and testing a fix.

4. **Release**: Once a fix is ready:
   - We will release a security patch
   - We will publish a security advisory
   - We will credit you for the discovery (if you wish)

5. **Disclosure Timeline**: We aim to disclose vulnerabilities within 90 days of the initial report.

## Security Best Practices

### For Users

1. **API Keys**: Never commit API keys to the repository
   - Use environment variables
   - Use the admin settings UI to configure keys
   - Rotate keys regularly

2. **Updates**: Keep TalentLens and its dependencies up to date
   - Monitor security advisories
   - Apply patches promptly

3. **Access Control**: 
   - Implement authentication (not included in current version)
   - Restrict access to the application
   - Use HTTPS in production

4. **Data Protection**:
   - Be cautious with resume data containing PII
   - Implement appropriate data retention policies
   - Consider encryption for sensitive data

### For Developers

1. **Dependencies**: 
   - Regularly update dependencies
   - Use `mvn dependency:analyze` to check for vulnerabilities
   - Use `npm audit` for frontend dependencies

2. **Input Validation**:
   - Validate all user inputs
   - Sanitize file uploads
   - Use parameterized queries

3. **Error Handling**:
   - Don't expose sensitive information in error messages
   - Log errors securely
   - Implement proper exception handling

4. **Code Review**:
   - Review all code changes for security issues
   - Use automated security scanning tools
   - Follow secure coding practices

## Known Security Considerations

### Current Version Limitations

The current version (1.0) has the following security limitations:

1. **No Authentication**: The application does not include user authentication
   - Recommendation: Deploy behind a reverse proxy with authentication
   - Future: Authentication will be added in v2.0

2. **In-Memory Database**: Using H2 in-memory database
   - Data is not persisted
   - Recommendation: Use a persistent database in production

3. **HTTP Only**: No built-in HTTPS support
   - Recommendation: Use a reverse proxy (Nginx, Apache) with SSL/TLS

4. **API Key Storage**: API keys stored in application.properties
   - Recommendation: Use environment variables or secret management service

### Mitigations

Until these limitations are addressed:
- Deploy on a private network
- Use VPN for remote access
- Implement network-level security
- Use environment variables for sensitive configuration

## Security Updates

We will publish security advisories for all confirmed vulnerabilities on:
- GitHub Security Advisories
- Project README
- Release notes

Subscribe to repository notifications to stay informed about security updates.

## Compliance

TalentLens handles resume data which may contain personally identifiable information (PII). Users are responsible for:
- Compliance with GDPR, CCPA, and other data protection regulations
- Implementing appropriate data protection measures
- Obtaining necessary consents for data processing
- Establishing data retention and deletion policies

## Contact

For security-related questions or concerns, please contact:
- **Email**: [your-security-email@example.com]
- **GitHub Issues**: For non-sensitive security discussions

---

**Note**: This security policy may be updated from time to time. Please check back regularly for the latest information.

