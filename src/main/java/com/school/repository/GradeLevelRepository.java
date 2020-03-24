package com.school.repository;

import com.school.domain.GradeLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GradeLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeLevelRepository extends JpaRepository<GradeLevel, Long> {
}
