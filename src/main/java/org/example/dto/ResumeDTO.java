package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    private Long id;
    private String candidateName;
    private String email;
    private String phone;
    private String skills;
    private String experience;
    private String fileName;
    private String fileType;
    private String source;
    private Double matchScore;
    private String matchAnalysis;
    private LocalDateTime uploadedAt;
    private LocalDateTime analyzedAt;
}


