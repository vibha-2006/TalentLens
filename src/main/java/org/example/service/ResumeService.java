package org.example.service;

import com.google.api.services.drive.model.File;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.example.dto.AIAnalysisResponse;
import org.example.dto.ResumeDTO;
import org.example.model.JobRequirement;
import org.example.model.Resume;
import org.example.repository.JobRequirementRepository;
import org.example.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobRequirementRepository jobRequirementRepository;

    @Autowired
    private ResumeParserService parserService;

    @Autowired
    private AIProviderFactory aiProviderFactory;

    @Autowired
    private GoogleDriveService driveService;

    public ResumeDTO uploadAndAnalyzeResume(MultipartFile file) throws IOException {
        return uploadAndAnalyzeResume(file, null);
    }

    public ResumeDTO uploadAndAnalyzeResume(MultipartFile file, String aiProvider) throws IOException {
        // Extract text from resume
        String extractedText = parserService.extractTextFromFile(file);

        // Get active job requirement
        JobRequirement jobReq = jobRequirementRepository.findFirstByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active job requirement found"));

        // Get AI service and analyze
        AIService aiService = aiProviderFactory.getAIService(aiProvider);
        String jobRequirements = buildJobRequirementText(jobReq);
        AIAnalysisResponse analysis = aiService.analyzeResume(extractedText, jobRequirements);

        System.out.println("DEBUG: Analyzed with " + aiService.getProviderName());

        // Create and save resume entity
        Resume resume = new Resume();
        resume.setExtractedText(extractedText);
        resume.setFileName(file.getOriginalFilename());
        resume.setFileType(file.getContentType());
        resume.setSource("UPLOAD");
        resume.setCandidateName(analysis.getCandidateName());
        resume.setEmail(analysis.getEmail());
        resume.setPhone(analysis.getPhone());
        resume.setSkills(analysis.getExtractedSkills());
        resume.setExperience(analysis.getExtractedExperience());
        resume.setMatchScore(analysis.getMatchScore());
        resume.setMatchAnalysis(analysis.getAnalysis());
        resume.setUploadedAt(LocalDateTime.now());
        resume.setAnalyzedAt(LocalDateTime.now());

        resume = resumeRepository.save(resume);
        return convertToDTO(resume);
    }

    public List<ResumeDTO> uploadAndAnalyzeMultipleResumes(MultipartFile[] files, String aiProvider) throws IOException {
        List<ResumeDTO> results = new ArrayList<>();

        // Get active job requirement once
        JobRequirement jobReq = jobRequirementRepository.findFirstByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active job requirement found"));
        String jobRequirements = buildJobRequirementText(jobReq);

        // Get AI service
        AIService aiService = aiProviderFactory.getAIService(aiProvider);

        for (MultipartFile file : files) {
            try {
                // Extract text from resume
                String extractedText = parserService.extractTextFromFile(file);

                // Analyze resume
                AIAnalysisResponse analysis = aiService.analyzeResume(extractedText, jobRequirements);

                System.out.println("DEBUG: Analyzed " + file.getOriginalFilename() + " with " + aiService.getProviderName());

                // Create and save resume entity
                Resume resume = new Resume();
                resume.setExtractedText(extractedText);
                resume.setFileName(file.getOriginalFilename());
                resume.setFileType(file.getContentType());
                resume.setSource("UPLOAD");
                resume.setCandidateName(analysis.getCandidateName());
                resume.setEmail(analysis.getEmail());
                resume.setPhone(analysis.getPhone());
                resume.setSkills(analysis.getExtractedSkills());
                resume.setExperience(analysis.getExtractedExperience());
                resume.setMatchScore(analysis.getMatchScore());
                resume.setMatchAnalysis(analysis.getAnalysis());
                resume.setUploadedAt(LocalDateTime.now());
                resume.setAnalyzedAt(LocalDateTime.now());

                resume = resumeRepository.save(resume);
                results.add(convertToDTO(resume));
            } catch (Exception e) {
                System.err.println("Error processing file " + file.getOriginalFilename() + ": " + e.getMessage());
                // Continue with next file
            }
        }

        return results;
    }

    public List<ResumeDTO> uploadAndAnalyzeZipFile(MultipartFile zipFile, String aiProvider) throws IOException {
        List<ResumeDTO> results = new ArrayList<>();

        // Get active job requirement once
        JobRequirement jobReq = jobRequirementRepository.findFirstByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active job requirement found"));
        String jobRequirements = buildJobRequirementText(jobReq);

        // Get AI service
        AIService aiService = aiProviderFactory.getAIService(aiProvider);

        try (ZipArchiveInputStream zipInput = new ZipArchiveInputStream(new ByteArrayInputStream(zipFile.getBytes()))) {
            ZipArchiveEntry entry;

            while ((entry = zipInput.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                String fileName = entry.getName();
                String lowerFileName = fileName.toLowerCase();

                // Process only PDF and Word documents
                if (lowerFileName.endsWith(".pdf") || lowerFileName.endsWith(".doc") || lowerFileName.endsWith(".docx")) {
                    try {
                        // Read file content from zip
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = zipInput.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        byte[] fileBytes = outputStream.toByteArray();

                        // Determine content type
                        String contentType;
                        if (lowerFileName.endsWith(".pdf")) {
                            contentType = "application/pdf";
                        } else if (lowerFileName.endsWith(".docx")) {
                            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                        } else {
                            contentType = "application/msword";
                        }

                        // Extract text from resume
                        String extractedText = parserService.extractTextFromBytes(fileBytes, contentType);

                        // Analyze resume
                        AIAnalysisResponse analysis = aiService.analyzeResume(extractedText, jobRequirements);

                        System.out.println("DEBUG: Analyzed " + fileName + " from ZIP with " + aiService.getProviderName());

                        // Create and save resume entity
                        Resume resume = new Resume();
                        resume.setExtractedText(extractedText);
                        resume.setFileName(fileName);
                        resume.setFileType(contentType);
                        resume.setSource("UPLOAD_ZIP");
                        resume.setCandidateName(analysis.getCandidateName());
                        resume.setEmail(analysis.getEmail());
                        resume.setPhone(analysis.getPhone());
                        resume.setSkills(analysis.getExtractedSkills());
                        resume.setExperience(analysis.getExtractedExperience());
                        resume.setMatchScore(analysis.getMatchScore());
                        resume.setMatchAnalysis(analysis.getAnalysis());
                        resume.setUploadedAt(LocalDateTime.now());
                        resume.setAnalyzedAt(LocalDateTime.now());

                        resume = resumeRepository.save(resume);
                        results.add(convertToDTO(resume));
                    } catch (Exception e) {
                        System.err.println("Error processing file " + fileName + " from ZIP: " + e.getMessage());
                        // Continue with next file
                    }
                }
            }
        }

        if (results.isEmpty()) {
            throw new RuntimeException("No valid resume files found in ZIP. Please ensure ZIP contains PDF or Word documents.");
        }

        return results;
    }

    public List<ResumeDTO> importFromGoogleDrive(String folderId) throws IOException, GeneralSecurityException {
        return importFromGoogleDrive(folderId, null);
    }

    public List<ResumeDTO> importFromGoogleDrive(String folderId, String aiProvider) throws IOException, GeneralSecurityException {
        List<File> driveFiles = driveService.listResumeFiles(folderId);
        List<ResumeDTO> results = new ArrayList<>();

        JobRequirement jobReq = jobRequirementRepository.findFirstByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active job requirement found"));
        String jobRequirements = buildJobRequirementText(jobReq);

        AIService aiService = aiProviderFactory.getAIService(aiProvider);

        for (File driveFile : driveFiles) {
            try {
                byte[] fileBytes = driveService.downloadFile(driveFile.getId());
                String extractedText = parserService.extractTextFromBytes(fileBytes, driveFile.getMimeType());

                AIAnalysisResponse analysis = aiService.analyzeResume(extractedText, jobRequirements);

                Resume resume = new Resume();
                resume.setExtractedText(extractedText);
                resume.setFileName(driveFile.getName());
                resume.setFileType(driveFile.getMimeType());
                resume.setSource("GOOGLE_DRIVE");
                resume.setDriveFileId(driveFile.getId());
                resume.setCandidateName(analysis.getCandidateName());
                resume.setEmail(analysis.getEmail());
                resume.setPhone(analysis.getPhone());
                resume.setSkills(analysis.getExtractedSkills());
                resume.setExperience(analysis.getExtractedExperience());
                resume.setMatchScore(analysis.getMatchScore());
                resume.setMatchAnalysis(analysis.getAnalysis());
                resume.setUploadedAt(LocalDateTime.now());
                resume.setAnalyzedAt(LocalDateTime.now());

                resume = resumeRepository.save(resume);
                results.add(convertToDTO(resume));
            } catch (Exception e) {
                System.err.println("Error processing file " + driveFile.getName() + ": " + e.getMessage());
            }
        }

        return results;
    }

    public List<ResumeDTO> getAllResumesRanked() {
        return resumeRepository.findByOrderByMatchScoreDesc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResumeDTO getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        return convertToDTO(resume);
    }

    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }

    private String buildJobRequirementText(JobRequirement jobReq) {
        return String.format("""
                Job Title: %s
                
                Description: %s
                
                Required Skills: %s
                
                Preferred Skills: %s
                
                Experience Level: %s
                """,
                jobReq.getJobTitle(),
                jobReq.getDescription(),
                jobReq.getRequiredSkills(),
                jobReq.getPreferredSkills(),
                jobReq.getExperienceLevel());
    }

    private ResumeDTO convertToDTO(Resume resume) {
        ResumeDTO dto = new ResumeDTO();
        dto.setId(resume.getId());
        dto.setCandidateName(resume.getCandidateName());
        dto.setEmail(resume.getEmail());
        dto.setPhone(resume.getPhone());
        dto.setSkills(resume.getSkills());
        dto.setExperience(resume.getExperience());
        dto.setFileName(resume.getFileName());
        dto.setFileType(resume.getFileType());
        dto.setSource(resume.getSource());
        dto.setMatchScore(resume.getMatchScore());
        dto.setMatchAnalysis(resume.getMatchAnalysis());
        dto.setUploadedAt(resume.getUploadedAt());
        dto.setAnalyzedAt(resume.getAnalyzedAt());
        return dto;
    }
}

