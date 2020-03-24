package com.school.web.rest;

import com.school.SchoolApp;
import com.school.domain.Group;
import com.school.repository.GroupRepository;
import com.school.service.GroupService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GroupResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GroupRepository groupRepository;

    @Mock
    private GroupRepository groupRepositoryMock;

    @Mock
    private GroupService groupServiceMock;

    @Autowired
    private GroupService groupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupMockMvc;

    private Group group;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Group createEntity(EntityManager em) {
        Group group = new Group()
            .name(DEFAULT_NAME);
        return group;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Group createUpdatedEntity(EntityManager em) {
        Group group = new Group()
            .name(UPDATED_NAME);
        return group;
    }

    @BeforeEach
    public void initTest() {
        group = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroup() throws Exception {
        int databaseSizeBeforeCreate = groupRepository.findAll().size();

        // Create the Group
        restGroupMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(group)))
            .andExpect(status().isCreated());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeCreate + 1);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupRepository.findAll().size();

        // Create the Group with an existing ID
        group.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMockMvc.perform(post("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(group)))
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroups() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList
        restGroupMockMvc.perform(get("/api/groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(group.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        when(groupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupMockMvc.perform(get("/api/groups?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(groupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupMockMvc.perform(get("/api/groups?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGroup() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get the group
        restGroupMockMvc.perform(get("/api/groups/{id}", group.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(group.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingGroup() throws Exception {
        // Get the group
        restGroupMockMvc.perform(get("/api/groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroup() throws Exception {
        // Initialize the database
        groupService.save(group);

        int databaseSizeBeforeUpdate = groupRepository.findAll().size();

        // Update the group
        Group updatedGroup = groupRepository.findById(group.getId()).get();
        // Disconnect from session so that the updates on updatedGroup are not directly saved in db
        em.detach(updatedGroup);
        updatedGroup
            .name(UPDATED_NAME);

        restGroupMockMvc.perform(put("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroup)))
            .andExpect(status().isOk());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();

        // Create the Group

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMockMvc.perform(put("/api/groups").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(group)))
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroup() throws Exception {
        // Initialize the database
        groupService.save(group);

        int databaseSizeBeforeDelete = groupRepository.findAll().size();

        // Delete the group
        restGroupMockMvc.perform(delete("/api/groups/{id}", group.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
