package com.school.service.impl;

import com.school.service.GradeLevelService;
import com.school.domain.GradeLevel;
import com.school.repository.GradeLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GradeLevel}.
 */
@Service
@Transactional
public class GradeLevelServiceImpl implements GradeLevelService {

    private final Logger log = LoggerFactory.getLogger(GradeLevelServiceImpl.class);

    private final GradeLevelRepository gradeLevelRepository;

    public GradeLevelServiceImpl(GradeLevelRepository gradeLevelRepository) {
        this.gradeLevelRepository = gradeLevelRepository;
    }

    /**
     * Save a gradeLevel.
     *
     * @param gradeLevel the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GradeLevel save(GradeLevel gradeLevel) {
        log.debug("Request to save GradeLevel : {}", gradeLevel);
        return gradeLevelRepository.save(gradeLevel);
    }

    /**
     * Get all the gradeLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GradeLevel> findAll(Pageable pageable) {
        log.debug("Request to get all GradeLevels");
        return gradeLevelRepository.findAll(pageable);
    }

    /**
     * Get one gradeLevel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GradeLevel> findOne(Long id) {
        log.debug("Request to get GradeLevel : {}", id);
        return gradeLevelRepository.findById(id);
    }

    /**
     * Delete the gradeLevel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GradeLevel : {}", id);
        gradeLevelRepository.deleteById(id);
    }
}
