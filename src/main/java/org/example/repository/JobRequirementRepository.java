package org.example.repository;

import org.example.model.JobRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRequirementRepository extends JpaRepository<JobRequirement, Long> {
    Optional<JobRequirement> findFirstByActiveTrue();
}

