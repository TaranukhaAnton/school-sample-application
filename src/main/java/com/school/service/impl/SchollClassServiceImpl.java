package com.school.service.impl;

import com.school.service.SchollClassService;
import com.school.domain.SchollClass;
import com.school.repository.SchollClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SchollClass}.
 */
@Service
@Transactional
public class SchollClassServiceImpl implements SchollClassService {

    private final Logger log = LoggerFactory.getLogger(SchollClassServiceImpl.class);

    private final SchollClassRepository schollClassRepository;

    public SchollClassServiceImpl(SchollClassRepository schollClassRepository) {
        this.schollClassRepository = schollClassRepository;
    }

    /**
     * Save a schollClass.
     *
     * @param schollClass the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SchollClass save(SchollClass schollClass) {
        log.debug("Request to save SchollClass : {}", schollClass);
        return schollClassRepository.save(schollClass);
    }

    /**
     * Get all the schollClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchollClass> findAll(Pageable pageable) {
        log.debug("Request to get all SchollClasses");
        return schollClassRepository.findAll(pageable);
    }

    /**
     * Get all the schollClasses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SchollClass> findAllWithEagerRelationships(Pageable pageable) {
        return schollClassRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one schollClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SchollClass> findOne(Long id) {
        log.debug("Request to get SchollClass : {}", id);
        return schollClassRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the schollClass by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchollClass : {}", id);
        schollClassRepository.deleteById(id);
    }
}
