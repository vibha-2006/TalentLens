package org.example.controller;

import org.example.dto.ResumeDTO;
import org.example.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "*")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "aiProvider", required = false) String aiProvider) {
        try {
            ResumeDTO result = resumeService.uploadAndAnalyzeResume(file, aiProvider);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing resume: " + e.getMessage());
        }
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<?> uploadMultipleResumes(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "aiProvider", required = false) String aiProvider) {
        try {
            List<ResumeDTO> results = resumeService.uploadAndAnalyzeMultipleResumes(files, aiProvider);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing resumes: " + e.getMessage());
        }
    }

    @PostMapping("/upload-zip")
    public ResponseEntity<?> uploadZipFile(
            @RequestParam("file") MultipartFile zipFile,
            @RequestParam(value = "aiProvider", required = false) String aiProvider) {
        try {
            List<ResumeDTO> results = resumeService.uploadAndAnalyzeZipFile(zipFile, aiProvider);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing ZIP file: " + e.getMessage());
        }
    }

    @PostMapping("/import-from-drive")
    public ResponseEntity<?> importFromGoogleDrive(
            @RequestParam(required = false) String folderId,
            @RequestParam(value = "aiProvider", required = false) String aiProvider) {
        try {
            List<ResumeDTO> results = resumeService.importFromGoogleDrive(folderId, aiProvider);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error importing from Google Drive: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ResumeDTO>> getAllResumes() {
        List<ResumeDTO> resumes = resumeService.getAllResumesRanked();
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResumeById(@PathVariable Long id) {
        try {
            ResumeDTO resume = resumeService.getResumeById(id);
            return ResponseEntity.ok(resume);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Resume not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResume(@PathVariable Long id) {
        try {
            resumeService.deleteResume(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting resume: " + e.getMessage());
        }
    }
}


