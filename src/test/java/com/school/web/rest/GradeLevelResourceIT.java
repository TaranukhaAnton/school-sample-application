package com.school.web.rest;

import com.school.SchoolApp;
import com.school.domain.GradeLevel;
import com.school.repository.GradeLevelRepository;
import com.school.service.GradeLevelService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GradeLevelResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GradeLevelResourceIT {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private GradeLevelRepository gradeLevelRepository;

    @Autowired
    private GradeLevelService gradeLevelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGradeLevelMockMvc;

    private GradeLevel gradeLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeLevel createEntity(EntityManager em) {
        GradeLevel gradeLevel = new GradeLevel()
            .value(DEFAULT_VALUE);
        return gradeLevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeLevel createUpdatedEntity(EntityManager em) {
        GradeLevel gradeLevel = new GradeLevel()
            .value(UPDATED_VALUE);
        return gradeLevel;
    }

    @BeforeEach
    public void initTest() {
        gradeLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createGradeLevel() throws Exception {
        int databaseSizeBeforeCreate = gradeLevelRepository.findAll().size();

        // Create the GradeLevel
        restGradeLevelMockMvc.perform(post("/api/grade-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeLevel)))
            .andExpect(status().isCreated());

        // Validate the GradeLevel in the database
        List<GradeLevel> gradeLevelList = gradeLevelRepository.findAll();
        assertThat(gradeLevelList).hasSize(databaseSizeBeforeCreate + 1);
        GradeLevel testGradeLevel = gradeLevelList.get(gradeLevelList.size() - 1);
        assertThat(testGradeLevel.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createGradeLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradeLevelRepository.findAll().size();

        // Create the GradeLevel with an existing ID
        gradeLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeLevelMockMvc.perform(post("/api/grade-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeLevel)))
            .andExpect(status().isBadRequest());

        // Validate the GradeLevel in the database
        List<GradeLevel> gradeLevelList = gradeLevelRepository.findAll();
        assertThat(gradeLevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGradeLevels() throws Exception {
        // Initialize the database
        gradeLevelRepository.saveAndFlush(gradeLevel);

        // Get all the gradeLevelList
        restGradeLevelMockMvc.perform(get("/api/grade-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gradeLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getGradeLevel() throws Exception {
        // Initialize the database
        gradeLevelRepository.saveAndFlush(gradeLevel);

        // Get the gradeLevel
        restGradeLevelMockMvc.perform(get("/api/grade-levels/{id}", gradeLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gradeLevel.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingGradeLevel() throws Exception {
        // Get the gradeLevel
        restGradeLevelMockMvc.perform(get("/api/grade-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGradeLevel() throws Exception {
        // Initialize the database
        gradeLevelService.save(gradeLevel);

        int databaseSizeBeforeUpdate = gradeLevelRepository.findAll().size();

        // Update the gradeLevel
        GradeLevel updatedGradeLevel = gradeLevelRepository.findById(gradeLevel.getId()).get();
        // Disconnect from session so that the updates on updatedGradeLevel are not directly saved in db
        em.detach(updatedGradeLevel);
        updatedGradeLevel
            .value(UPDATED_VALUE);

        restGradeLevelMockMvc.perform(put("/api/grade-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGradeLevel)))
            .andExpect(status().isOk());

        // Validate the GradeLevel in the database
        List<GradeLevel> gradeLevelList = gradeLevelRepository.findAll();
        assertThat(gradeLevelList).hasSize(databaseSizeBeforeUpdate);
        GradeLevel testGradeLevel = gradeLevelList.get(gradeLevelList.size() - 1);
        assertThat(testGradeLevel.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingGradeLevel() throws Exception {
        int databaseSizeBeforeUpdate = gradeLevelRepository.findAll().size();

        // Create the GradeLevel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeLevelMockMvc.perform(put("/api/grade-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeLevel)))
            .andExpect(status().isBadRequest());

        // Validate the GradeLevel in the database
        List<GradeLevel> gradeLevelList = gradeLevelRepository.findAll();
        assertThat(gradeLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGradeLevel() throws Exception {
        // Initialize the database
        gradeLevelService.save(gradeLevel);

        int databaseSizeBeforeDelete = gradeLevelRepository.findAll().size();

        // Delete the gradeLevel
        restGradeLevelMockMvc.perform(delete("/api/grade-levels/{id}", gradeLevel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GradeLevel> gradeLevelList = gradeLevelRepository.findAll();
        assertThat(gradeLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
