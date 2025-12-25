package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIAnalysisResponse {
    private Double matchScore;
    private String analysis;
    private String extractedSkills;
    private String extractedExperience;
    private String candidateName;
    private String email;
    private String phone;
}

