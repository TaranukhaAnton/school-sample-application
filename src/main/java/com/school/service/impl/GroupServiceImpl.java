package com.school.service.impl;

import com.school.service.GroupService;
import com.school.domain.Group;
import com.school.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Group}.
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Save a group.
     *
     * @param group the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Group save(Group group) {
        log.debug("Request to save Group : {}", group);
        return groupRepository.save(group);
    }

    /**
     * Get all the groups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Group> findAll(Pageable pageable) {
        log.debug("Request to get all Groups");
        return groupRepository.findAll(pageable);
    }

    /**
     * Get all the groups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Group> findAllWithEagerRelationships(Pageable pageable) {
        return groupRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one group by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Group> findOne(Long id) {
        log.debug("Request to get Group : {}", id);
        return groupRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the group by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Group : {}", id);
        groupRepository.deleteById(id);
    }
}
