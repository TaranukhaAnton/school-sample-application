package com.school.web.rest;

import com.school.domain.SchollClass;
import com.school.service.SchollClassService;
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
 * REST controller for managing {@link com.school.domain.SchollClass}.
 */
@RestController
@RequestMapping("/api")
public class SchollClassResource {

    private final Logger log = LoggerFactory.getLogger(SchollClassResource.class);

    private static final String ENTITY_NAME = "schollClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchollClassService schollClassService;

    public SchollClassResource(SchollClassService schollClassService) {
        this.schollClassService = schollClassService;
    }

    /**
     * {@code POST  /scholl-classes} : Create a new schollClass.
     *
     * @param schollClass the schollClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schollClass, or with status {@code 400 (Bad Request)} if the schollClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scholl-classes")
    public ResponseEntity<SchollClass> createSchollClass(@RequestBody SchollClass schollClass) throws URISyntaxException {
        log.debug("REST request to save SchollClass : {}", schollClass);
        if (schollClass.getId() != null) {
            throw new BadRequestAlertException("A new schollClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchollClass result = schollClassService.save(schollClass);
        return ResponseEntity.created(new URI("/api/scholl-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scholl-classes} : Updates an existing schollClass.
     *
     * @param schollClass the schollClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schollClass,
     * or with status {@code 400 (Bad Request)} if the schollClass is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schollClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scholl-classes")
    public ResponseEntity<SchollClass> updateSchollClass(@RequestBody SchollClass schollClass) throws URISyntaxException {
        log.debug("REST request to update SchollClass : {}", schollClass);
        if (schollClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchollClass result = schollClassService.save(schollClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schollClass.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scholl-classes} : get all the schollClasses.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schollClasses in body.
     */
    @GetMapping("/scholl-classes")
    public ResponseEntity<List<SchollClass>> getAllSchollClasses(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of SchollClasses");
        Page<SchollClass> page;
        if (eagerload) {
            page = schollClassService.findAllWithEagerRelationships(pageable);
        } else {
            page = schollClassService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scholl-classes/:id} : get the "id" schollClass.
     *
     * @param id the id of the schollClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schollClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scholl-classes/{id}")
    public ResponseEntity<SchollClass> getSchollClass(@PathVariable Long id) {
        log.debug("REST request to get SchollClass : {}", id);
        Optional<SchollClass> schollClass = schollClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schollClass);
    }

    /**
     * {@code DELETE  /scholl-classes/:id} : delete the "id" schollClass.
     *
     * @param id the id of the schollClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scholl-classes/{id}")
    public ResponseEntity<Void> deleteSchollClass(@PathVariable Long id) {
        log.debug("REST request to delete SchollClass : {}", id);
        schollClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
