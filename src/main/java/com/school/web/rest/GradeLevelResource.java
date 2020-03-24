package com.school.web.rest;

import com.school.domain.GradeLevel;
import com.school.service.GradeLevelService;
import com.school.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.school.domain.GradeLevel}.
 */
@RestController
@RequestMapping("/api")
public class GradeLevelResource {

    private final Logger log = LoggerFactory.getLogger(GradeLevelResource.class);

    private static final String ENTITY_NAME = "gradeLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradeLevelService gradeLevelService;

    public GradeLevelResource(GradeLevelService gradeLevelService) {
        this.gradeLevelService = gradeLevelService;
    }

    /**
     * {@code POST  /grade-levels} : Create a new gradeLevel.
     *
     * @param gradeLevel the gradeLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gradeLevel, or with status {@code 400 (Bad Request)} if the gradeLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grade-levels")
    public ResponseEntity<GradeLevel> createGradeLevel(@RequestBody GradeLevel gradeLevel) throws URISyntaxException {
        log.debug("REST request to save GradeLevel : {}", gradeLevel);
        if (gradeLevel.getId() != null) {
            throw new BadRequestAlertException("A new gradeLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GradeLevel result = gradeLevelService.save(gradeLevel);
        return ResponseEntity.created(new URI("/api/grade-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grade-levels} : Updates an existing gradeLevel.
     *
     * @param gradeLevel the gradeLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gradeLevel,
     * or with status {@code 400 (Bad Request)} if the gradeLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gradeLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grade-levels")
    public ResponseEntity<GradeLevel> updateGradeLevel(@RequestBody GradeLevel gradeLevel) throws URISyntaxException {
        log.debug("REST request to update GradeLevel : {}", gradeLevel);
        if (gradeLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GradeLevel result = gradeLevelService.save(gradeLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gradeLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grade-levels} : get all the gradeLevels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gradeLevels in body.
     */
    @GetMapping("/grade-levels")
    public ResponseEntity<List<GradeLevel>> getAllGradeLevels(Pageable pageable) {
        log.debug("REST request to get a page of GradeLevels");
        Page<GradeLevel> page = gradeLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grade-levels/:id} : get the "id" gradeLevel.
     *
     * @param id the id of the gradeLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gradeLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grade-levels/{id}")
    public ResponseEntity<GradeLevel> getGradeLevel(@PathVariable Long id) {
        log.debug("REST request to get GradeLevel : {}", id);
        Optional<GradeLevel> gradeLevel = gradeLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gradeLevel);
    }

    /**
     * {@code DELETE  /grade-levels/:id} : delete the "id" gradeLevel.
     *
     * @param id the id of the gradeLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grade-levels/{id}")
    public ResponseEntity<Void> deleteGradeLevel(@PathVariable Long id) {
        log.debug("REST request to delete GradeLevel : {}", id);
        gradeLevelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
