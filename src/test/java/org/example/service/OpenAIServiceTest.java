package org.example.service;

import org.example.dto.AIAnalysisResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class OpenAIServiceTest {

    @Autowired
    private OpenAIService openAIService;

    private String sampleResumeText;
    private String sampleJobRequirements;

    @BeforeEach
    public void setUp() {
        sampleResumeText = """
                John Doe
                Email: john.doe@example.com
                Phone: +1-555-0123
                
                PROFESSIONAL SUMMARY
                Experienced Software Engineer with 5+ years of experience in Java, Spring Boot, 
                and React.js development. Strong background in building scalable web applications 
                and RESTful APIs.
                
                SKILLS
                - Programming Languages: Java, JavaScript, Python
                - Frameworks: Spring Boot, React.js, Node.js
                - Databases: MySQL, PostgreSQL, MongoDB
                - Tools: Git, Docker, Kubernetes, Jenkins
                - Cloud: AWS, Azure
                
                EXPERIENCE
                Senior Software Engineer | Tech Corp | 2020-2023
                - Developed microservices architecture using Spring Boot
                - Built responsive front-end applications using React.js
                - Implemented CI/CD pipelines with Jenkins and Docker
                - Collaborated with cross-functional teams using Agile methodologies
                
                Software Engineer | StartupXYZ | 2018-2020
                - Created RESTful APIs for mobile and web applications
                - Optimized database queries improving performance by 40%
                - Mentored junior developers on best coding practices
                
                EDUCATION
                Bachelor of Science in Computer Science
                State University | 2014-2018
                """;

        sampleJobRequirements = """
                Position: Senior Java Developer
                
                Required Skills:
                - 5+ years of experience in Java development
                - Strong expertise in Spring Boot framework
                - Experience with React.js or similar front-end frameworks
                - Proficiency in RESTful API design
                - Experience with microservices architecture
                - Knowledge of cloud platforms (AWS/Azure)
                - Strong problem-solving and communication skills
                
                Preferred Skills:
                - Experience with Docker and Kubernetes
                - CI/CD pipeline implementation
                - Database optimization experience
                - Agile/Scrum methodology experience
                """;
    }

    @Test
    public void testOpenAIServiceNotNull() {
        assertNotNull(openAIService, "OpenAIService should be autowired and not null");
    }

    @Test
    public void testAnalyzeResumeWithValidApiKey() {
        System.out.println("\n=== Testing OpenAI API Call with Real API Key ===");

        try {
            AIAnalysisResponse response = openAIService.analyzeResume(sampleResumeText, sampleJobRequirements);

            assertNotNull(response, "Response should not be null");

            System.out.println("\n✓ API Call Successful!");
            System.out.println("=================================");
            System.out.println("Candidate Name: " + response.getCandidateName());
            System.out.println("Email: " + response.getEmail());
            System.out.println("Phone: " + response.getPhone());
            System.out.println("Match Score: " + response.getMatchScore());
            System.out.println("Extracted Skills: " + response.getExtractedSkills());
            System.out.println("Extracted Experience: " + response.getExtractedExperience());
            System.out.println("\nAnalysis Summary:");
            System.out.println(response.getAnalysis());
            System.out.println("=================================\n");

            // Verify response has meaningful data
            assertTrue(response.getMatchScore() >= 0 && response.getMatchScore() <= 100,
                    "Match score should be between 0 and 100");
            assertNotNull(response.getAnalysis(), "Analysis should not be null");
            assertFalse(response.getAnalysis().isEmpty(), "Analysis should not be empty");

        } catch (Exception e) {
            System.err.println("\n✗ API Call Failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nPlease verify:");
            System.err.println("1. OpenAI API key is valid and active");
            System.err.println("2. You have sufficient API credits");
            System.err.println("3. Network connection is available");
            System.err.println("4. Model name is correct (gpt-3.5-turbo or gpt-4)");
            System.err.println("=================================\n");
            fail("OpenAI API call failed: " + e.getMessage());
        }
    }

    @Test
    public void testAnalyzeResumeWithEmptyResume() {
        System.out.println("\n=== Testing with Empty Resume ===");

        try {
            AIAnalysisResponse response = openAIService.analyzeResume("", sampleJobRequirements);

            assertNotNull(response, "Response should not be null even with empty resume");
            System.out.println("✓ Handled empty resume gracefully");
            System.out.println("Match Score: " + response.getMatchScore());

        } catch (Exception e) {
            System.out.println("✓ Expected behavior: " + e.getMessage());
        }
    }

    @Test
    public void testAnalyzeResumeWithEmptyJobRequirements() {
        System.out.println("\n=== Testing with Empty Job Requirements ===");

        try {
            AIAnalysisResponse response = openAIService.analyzeResume(sampleResumeText, "");

            assertNotNull(response, "Response should not be null even with empty job requirements");
            System.out.println("✓ Handled empty job requirements gracefully");
            System.out.println("Match Score: " + response.getMatchScore());

        } catch (Exception e) {
            System.out.println("✓ Expected behavior: " + e.getMessage());
        }
    }

    @Test
    public void testAnalyzeResumeResponseStructure() {
        System.out.println("\n=== Testing Response Structure ===");

        try {
            AIAnalysisResponse response = openAIService.analyzeResume(sampleResumeText, sampleJobRequirements);

            assertNotNull(response, "Response should not be null");
            assertNotNull(response.getAnalysis(), "Analysis field should not be null");

            // Check if basic fields are present (even if empty)
            assertNotNull(response.getCandidateName(), "Candidate name field should not be null");
            assertNotNull(response.getEmail(), "Email field should not be null");
            assertNotNull(response.getPhone(), "Phone field should not be null");
            assertNotNull(response.getExtractedSkills(), "Extracted skills field should not be null");
            assertNotNull(response.getExtractedExperience(), "Extracted experience field should not be null");

            System.out.println("✓ All response fields are properly initialized");

        } catch (Exception e) {
            fail("API call should succeed with valid inputs: " + e.getMessage());
        }
    }

    @Test
    public void testMultipleConsecutiveCalls() {
        System.out.println("\n=== Testing Multiple Consecutive API Calls ===");

        try {
            // First call
            AIAnalysisResponse response1 = openAIService.analyzeResume(sampleResumeText, sampleJobRequirements);
            assertNotNull(response1, "First response should not be null");
            System.out.println("✓ First API call successful - Score: " + response1.getMatchScore());

            // Second call with different resume
            String differentResume = """
                    Jane Smith
                    Email: jane.smith@example.com
                    
                    SKILLS
                    - Python, Django, Flask
                    - Machine Learning, TensorFlow
                    - Data Analysis
                    
                    EXPERIENCE
                    Data Scientist | AI Corp | 2021-2023
                    - Developed ML models
                    - Analyzed large datasets
                    """;

            AIAnalysisResponse response2 = openAIService.analyzeResume(differentResume, sampleJobRequirements);
            assertNotNull(response2, "Second response should not be null");
            System.out.println("✓ Second API call successful - Score: " + response2.getMatchScore());

            // Verify responses are different (different candidates)
            assertNotEquals(response1.getCandidateName(), response2.getCandidateName(),
                    "Different resumes should produce different candidate names");

            System.out.println("✓ Multiple consecutive calls handled successfully");

        } catch (Exception e) {
            fail("Multiple API calls should succeed: " + e.getMessage());
        }
    }

    @Test
    public void testApiKeyConfiguration() {
        System.out.println("\n=== Verifying API Key Configuration ===");

        // This test verifies that the service is properly configured
        assertNotNull(openAIService, "OpenAIService should be initialized");

        try {
            // Try a simple call to verify the API key works
            AIAnalysisResponse response = openAIService.analyzeResume(
                    "Test Resume\nJohn Doe\nJava Developer with 3 years experience",
                    "Looking for Java Developer"
            );

            assertNotNull(response, "Response should be received with configured API key");
            System.out.println("✓ API Key is valid and working");
            System.out.println("✓ OpenAI API endpoint is accessible");
            System.out.println("✓ Service is properly configured");

        } catch (Exception e) {
            System.err.println("✗ API Key configuration issue: " + e.getMessage());
            fail("API key validation failed: " + e.getMessage());
        }
    }

    @Test
    public void testServiceConfiguration() {
        System.out.println("\n=== Testing Service Configuration ===");

        // Verify service is properly injected
        assertNotNull(openAIService, "OpenAIService should not be null");

        System.out.println("✓ Service is properly configured and injected");
    }
}

