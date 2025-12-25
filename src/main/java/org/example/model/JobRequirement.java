package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;

    @Column(length = 10000)
    private String description;

    @Column(length = 5000)
    private String requiredSkills;

    @Column(length = 2000)
    private String preferredSkills;

    private String experienceLevel;
    private LocalDateTime createdAt;
    private boolean active;
}

