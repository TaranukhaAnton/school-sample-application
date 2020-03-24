package com.school.web.rest;

import com.school.SchoolApp;
import com.school.domain.SchollClass;
import com.school.repository.SchollClassRepository;
import com.school.service.SchollClassService;

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
 * Integration tests for the {@link SchollClassResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SchollClassResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SchollClassRepository schollClassRepository;

    @Mock
    private SchollClassRepository schollClassRepositoryMock;

    @Mock
    private SchollClassService schollClassServiceMock;

    @Autowired
    private SchollClassService schollClassService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchollClassMockMvc;

    private SchollClass schollClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchollClass createEntity(EntityManager em) {
        SchollClass schollClass = new SchollClass()
            .name(DEFAULT_NAME);
        return schollClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchollClass createUpdatedEntity(EntityManager em) {
        SchollClass schollClass = new SchollClass()
            .name(UPDATED_NAME);
        return schollClass;
    }

    @BeforeEach
    public void initTest() {
        schollClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchollClass() throws Exception {
        int databaseSizeBeforeCreate = schollClassRepository.findAll().size();

        // Create the SchollClass
        restSchollClassMockMvc.perform(post("/api/scholl-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schollClass)))
            .andExpect(status().isCreated());

        // Validate the SchollClass in the database
        List<SchollClass> schollClassList = schollClassRepository.findAll();
        assertThat(schollClassList).hasSize(databaseSizeBeforeCreate + 1);
        SchollClass testSchollClass = schollClassList.get(schollClassList.size() - 1);
        assertThat(testSchollClass.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSchollClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schollClassRepository.findAll().size();

        // Create the SchollClass with an existing ID
        schollClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchollClassMockMvc.perform(post("/api/scholl-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schollClass)))
            .andExpect(status().isBadRequest());

        // Validate the SchollClass in the database
        List<SchollClass> schollClassList = schollClassRepository.findAll();
        assertThat(schollClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSchollClasses() throws Exception {
        // Initialize the database
        schollClassRepository.saveAndFlush(schollClass);

        // Get all the schollClassList
        restSchollClassMockMvc.perform(get("/api/scholl-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schollClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSchollClassesWithEagerRelationshipsIsEnabled() throws Exception {
        when(schollClassServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchollClassMockMvc.perform(get("/api/scholl-classes?eagerload=true"))
            .andExpect(status().isOk());

        verify(schollClassServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSchollClassesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(schollClassServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchollClassMockMvc.perform(get("/api/scholl-classes?eagerload=true"))
            .andExpect(status().isOk());

        verify(schollClassServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSchollClass() throws Exception {
        // Initialize the database
        schollClassRepository.saveAndFlush(schollClass);

        // Get the schollClass
        restSchollClassMockMvc.perform(get("/api/scholl-classes/{id}", schollClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schollClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingSchollClass() throws Exception {
        // Get the schollClass
        restSchollClassMockMvc.perform(get("/api/scholl-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchollClass() throws Exception {
        // Initialize the database
        schollClassService.save(schollClass);

        int databaseSizeBeforeUpdate = schollClassRepository.findAll().size();

        // Update the schollClass
        SchollClass updatedSchollClass = schollClassRepository.findById(schollClass.getId()).get();
        // Disconnect from session so that the updates on updatedSchollClass are not directly saved in db
        em.detach(updatedSchollClass);
        updatedSchollClass
            .name(UPDATED_NAME);

        restSchollClassMockMvc.perform(put("/api/scholl-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchollClass)))
            .andExpect(status().isOk());

        // Validate the SchollClass in the database
        List<SchollClass> schollClassList = schollClassRepository.findAll();
        assertThat(schollClassList).hasSize(databaseSizeBeforeUpdate);
        SchollClass testSchollClass = schollClassList.get(schollClassList.size() - 1);
        assertThat(testSchollClass.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchollClass() throws Exception {
        int databaseSizeBeforeUpdate = schollClassRepository.findAll().size();

        // Create the SchollClass

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchollClassMockMvc.perform(put("/api/scholl-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schollClass)))
            .andExpect(status().isBadRequest());

        // Validate the SchollClass in the database
        List<SchollClass> schollClassList = schollClassRepository.findAll();
        assertThat(schollClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchollClass() throws Exception {
        // Initialize the database
        schollClassService.save(schollClass);

        int databaseSizeBeforeDelete = schollClassRepository.findAll().size();

        // Delete the schollClass
        restSchollClassMockMvc.perform(delete("/api/scholl-classes/{id}", schollClass.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchollClass> schollClassList = schollClassRepository.findAll();
        assertThat(schollClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
