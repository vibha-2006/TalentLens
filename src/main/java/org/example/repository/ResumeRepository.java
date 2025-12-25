package org.example.repository;

import org.example.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByOrderByMatchScoreDesc();
    List<Resume> findBySourceOrderByMatchScoreDesc(String source);
}


