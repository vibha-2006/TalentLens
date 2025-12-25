package org.example.service;

import org.example.dto.JobRequirementDTO;
import org.example.model.JobRequirement;
import org.example.repository.JobRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobRequirementService {

    @Autowired
    private JobRequirementRepository repository;

    public JobRequirementDTO createJobRequirement(JobRequirementDTO dto) {
        // Deactivate all existing job requirements
        List<JobRequirement> existingReqs = repository.findAll();
        existingReqs.forEach(req -> req.setActive(false));
        repository.saveAll(existingReqs);

        // Create new job requirement
        JobRequirement jobReq = new JobRequirement();
        jobReq.setJobTitle(dto.getJobTitle());
        jobReq.setDescription(dto.getDescription());
        jobReq.setRequiredSkills(dto.getRequiredSkills());
        jobReq.setPreferredSkills(dto.getPreferredSkills());
        jobReq.setExperienceLevel(dto.getExperienceLevel());
        jobReq.setCreatedAt(LocalDateTime.now());
        jobReq.setActive(true);

        jobReq = repository.save(jobReq);
        return convertToDTO(jobReq);
    }

    public JobRequirementDTO getActiveJobRequirement() {
        return repository.findFirstByActiveTrue()
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<JobRequirementDTO> getAllJobRequirements() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public JobRequirementDTO getJobRequirementById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Job requirement not found"));
    }

    public JobRequirementDTO updateJobRequirement(Long id, JobRequirementDTO dto) {
        JobRequirement jobReq = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job requirement not found"));

        jobReq.setJobTitle(dto.getJobTitle());
        jobReq.setDescription(dto.getDescription());
        jobReq.setRequiredSkills(dto.getRequiredSkills());
        jobReq.setPreferredSkills(dto.getPreferredSkills());
        jobReq.setExperienceLevel(dto.getExperienceLevel());

        jobReq = repository.save(jobReq);
        return convertToDTO(jobReq);
    }

    public void setActiveJobRequirement(Long id) {
        // Deactivate all
        List<JobRequirement> allReqs = repository.findAll();
        allReqs.forEach(req -> req.setActive(false));
        repository.saveAll(allReqs);

        // Activate the specified one
        JobRequirement jobReq = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job requirement not found"));
        jobReq.setActive(true);
        repository.save(jobReq);
    }

    public void deleteJobRequirement(Long id) {
        repository.deleteById(id);
    }

    private JobRequirementDTO convertToDTO(JobRequirement jobReq) {
        JobRequirementDTO dto = new JobRequirementDTO();
        dto.setId(jobReq.getId());
        dto.setJobTitle(jobReq.getJobTitle());
        dto.setDescription(jobReq.getDescription());
        dto.setRequiredSkills(jobReq.getRequiredSkills());
        dto.setPreferredSkills(jobReq.getPreferredSkills());
        dto.setExperienceLevel(jobReq.getExperienceLevel());
        dto.setActive(jobReq.isActive());
        return dto;
    }
}

