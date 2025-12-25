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
public class GeminiServiceTest {

    @Autowired
    private GeminiService geminiService;

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
    public void testGeminiServiceNotNull() {
        assertNotNull(geminiService, "GeminiService should be autowired and not null");
    }

    @Test
    public void testGetProviderName() {
        assertEquals("Gemini", geminiService.getProviderName(), "Provider name should be 'Gemini'");
    }

    @Test
    public void testAnalyzeResumeWithValidApiKey() {
        System.out.println("\n=== Testing Gemini API Call with Real API Key ===");

        try {
            AIAnalysisResponse response = geminiService.analyzeResume(sampleResumeText, sampleJobRequirements);

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
            System.err.println("1. Gemini API key is valid and active");
            System.err.println("2. Generative Language API is enabled in Google Cloud Console");
            System.err.println("3. You have sufficient API quota");
            System.err.println("4. Network connection is available");
            System.err.println("5. Model name is correct (gemini-pro or gemini-1.5-flash)");
            System.err.println("=================================\n");
            fail("Gemini API call failed: " + e.getMessage());
        }
    }

    @Test
    public void testAnalyzeResumeWithEmptyResume() {
        System.out.println("\n=== Testing with Empty Resume ===");

        try {
            AIAnalysisResponse response = geminiService.analyzeResume("", sampleJobRequirements);

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
            AIAnalysisResponse response = geminiService.analyzeResume(sampleResumeText, "");

            assertNotNull(response, "Response should not be null even with empty job requirements");
            System.out.println("✓ Handled empty job requirements gracefully");
            System.out.println("Match Score: " + response.getMatchScore());

        } catch (Exception e) {
            System.out.println("✓ Expected behavior: " + e.getMessage());
        }
    }
}

