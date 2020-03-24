package com.school.service;

import com.school.domain.Subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Subject}.
 */
public interface SubjectService {

    /**
     * Save a subject.
     *
     * @param subject the entity to save.
     * @return the persisted entity.
     */
    Subject save(Subject subject);

    /**
     * Get all the subjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Subject> findAll(Pageable pageable);

    /**
     * Get all the subjects with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Subject> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" subject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Subject> findOne(Long id);

    /**
     * Delete the "id" subject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
