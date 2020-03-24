package com.school.service;

import com.school.domain.SchollClass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SchollClass}.
 */
public interface SchollClassService {

    /**
     * Save a schollClass.
     *
     * @param schollClass the entity to save.
     * @return the persisted entity.
     */
    SchollClass save(SchollClass schollClass);

    /**
     * Get all the schollClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchollClass> findAll(Pageable pageable);

    /**
     * Get all the schollClasses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SchollClass> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" schollClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchollClass> findOne(Long id);

    /**
     * Delete the "id" schollClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
