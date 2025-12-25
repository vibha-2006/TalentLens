package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequirementDTO {
    private Long id;
    private String jobTitle;
    private String description;
    private String requiredSkills;
    private String preferredSkills;
    private String experienceLevel;
    private boolean active;
}

