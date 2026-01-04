package org.example.controller;

import org.example.dto.JobRequirementDTO;
import org.example.service.JobRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-requirements")
public class JobRequirementController {

    @Autowired
    private JobRequirementService jobRequirementService;

    @PostMapping
    public ResponseEntity<JobRequirementDTO> createJobRequirement(@RequestBody JobRequirementDTO dto) {
        JobRequirementDTO created = jobRequirementService.createJobRequirement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/active")
    public ResponseEntity<JobRequirementDTO> getActiveJobRequirement() {
        JobRequirementDTO jobReq = jobRequirementService.getActiveJobRequirement();
        if (jobReq == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobReq);
    }

    @GetMapping
    public ResponseEntity<List<JobRequirementDTO>> getAllJobRequirements() {
        List<JobRequirementDTO> jobReqs = jobRequirementService.getAllJobRequirements();
        return ResponseEntity.ok(jobReqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobRequirementById(@PathVariable Long id) {
        try {
            JobRequirementDTO jobReq = jobRequirementService.getJobRequirementById(id);
            return ResponseEntity.ok(jobReq);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Job requirement not found: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobRequirement(@PathVariable Long id, @RequestBody JobRequirementDTO dto) {
        try {
            JobRequirementDTO updated = jobRequirementService.updateJobRequirement(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Job requirement not found: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> setActiveJobRequirement(@PathVariable Long id) {
        try {
            jobRequirementService.setActiveJobRequirement(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Job requirement not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobRequirement(@PathVariable Long id) {
        try {
            jobRequirementService.deleteJobRequirement(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting job requirement: " + e.getMessage());
        }
    }
}

