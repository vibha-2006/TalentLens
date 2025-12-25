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
public class GroqServiceTest {

    @Autowired
    private GroqService groqService;

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
    public void testGroqServiceNotNull() {
        assertNotNull(groqService, "GroqService should be autowired and not null");
    }

    @Test
    public void testGetProviderName() {
        assertEquals("Groq", groqService.getProviderName(), "Provider name should be 'Groq'");
    }

    @Test
    public void testAnalyzeResumeWithValidApiKey() {
        System.out.println("\n=== Testing Groq API Call with Real API Key ===");

        try {
            AIAnalysisResponse response = groqService.analyzeResume(sampleResumeText, sampleJobRequirements);

            assertNotNull(response, "Response should not be null");

            System.out.println("\n✓ API Call Successful!");
            System.out.println("=================================");
            System.out.println("AI Provider: Groq");
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
            System.err.println("1. Groq API key is valid and active (get it from https://console.groq.com/keys)");
            System.err.println("2. API key starts with 'gsk_'");
            System.err.println("3. You have sufficient API quota");
            System.err.println("4. Network connection is available");
            System.err.println("5. Model name is correct (e.g., llama-3.1-70b-versatile, mixtral-8x7b-32768)");
            System.err.println("=================================\n");

            // If the API key is placeholder, skip the test
            if (e.getMessage().contains("YOUR_GROQ_API_KEY_HERE")) {
                System.out.println("⚠ SKIPPING TEST: Please configure a valid Groq API key in application.properties");
                return;
            }

            fail("Groq API call failed: " + e.getMessage());
        }
    }

    @Test
    public void testAnalyzeResumeResponseStructure() {
        System.out.println("\n=== Testing Groq API Response Structure ===");

        // Skip this test if API key is not configured
        try {
            AIAnalysisResponse response = groqService.analyzeResume(sampleResumeText, sampleJobRequirements);

            // Validate response structure
            assertNotNull(response.getCandidateName(), "Candidate name should not be null");
            assertNotNull(response.getEmail(), "Email should not be null");
            assertNotNull(response.getPhone(), "Phone should not be null");
            assertNotNull(response.getExtractedSkills(), "Extracted skills should not be null");
            assertNotNull(response.getExtractedExperience(), "Extracted experience should not be null");
            assertNotNull(response.getAnalysis(), "Analysis should not be null");

            System.out.println("✓ Response structure is valid");
            System.out.println("=================================\n");

        } catch (Exception e) {
            if (e.getMessage().contains("YOUR_GROQ_API_KEY_HERE")) {
                System.out.println("⚠ SKIPPING TEST: Please configure a valid Groq API key in application.properties");
                return;
            }
            fail("Test failed: " + e.getMessage());
        }
    }
}

