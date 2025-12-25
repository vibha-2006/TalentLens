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
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateName;
    private String email;
    private String phone;

    @Column(length = 10000)
    private String extractedText;

    @Column(length = 5000)
    private String skills;

    @Column(length = 5000)
    private String experience;

    private String fileName;
    private String fileType;
    private String source; // "UPLOAD" or "GOOGLE_DRIVE"
    private String driveFileId;

    private Double matchScore;

    @Column(length = 5000)
    private String matchAnalysis;

    private LocalDateTime uploadedAt;
    private LocalDateTime analyzedAt;
}


