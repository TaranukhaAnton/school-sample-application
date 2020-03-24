package com.school.service;

import com.school.domain.GradeLevel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GradeLevel}.
 */
public interface GradeLevelService {

    /**
     * Save a gradeLevel.
     *
     * @param gradeLevel the entity to save.
     * @return the persisted entity.
     */
    GradeLevel save(GradeLevel gradeLevel);

    /**
     * Get all the gradeLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GradeLevel> findAll(Pageable pageable);

    /**
     * Get the "id" gradeLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GradeLevel> findOne(Long id);

    /**
     * Delete the "id" gradeLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
