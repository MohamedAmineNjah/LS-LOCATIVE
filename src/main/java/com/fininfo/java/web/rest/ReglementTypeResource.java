package com.fininfo.java.web.rest;

import com.fininfo.java.service.ReglementTypeService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.ReglementTypeDTO;
import com.fininfo.java.service.dto.ReglementTypeCriteria;
import com.fininfo.java.service.ReglementTypeQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.ReglementType}.
 */
@RestController
@RequestMapping("/api")
public class ReglementTypeResource {

    private final Logger log = LoggerFactory.getLogger(ReglementTypeResource.class);

    private static final String ENTITY_NAME = "locativeReglementType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReglementTypeService reglementTypeService;

    private final ReglementTypeQueryService reglementTypeQueryService;

    public ReglementTypeResource(ReglementTypeService reglementTypeService, ReglementTypeQueryService reglementTypeQueryService) {
        this.reglementTypeService = reglementTypeService;
        this.reglementTypeQueryService = reglementTypeQueryService;
    }

    /**
     * {@code POST  /reglement-types} : Create a new reglementType.
     *
     * @param reglementTypeDTO the reglementTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reglementTypeDTO, or with status {@code 400 (Bad Request)} if the reglementType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reglement-types")
    public ResponseEntity<ReglementTypeDTO> createReglementType(@RequestBody ReglementTypeDTO reglementTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ReglementType : {}", reglementTypeDTO);
        if (reglementTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new reglementType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReglementTypeDTO result = reglementTypeService.save(reglementTypeDTO);
        return ResponseEntity.created(new URI("/api/reglement-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reglement-types} : Updates an existing reglementType.
     *
     * @param reglementTypeDTO the reglementTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reglementTypeDTO,
     * or with status {@code 400 (Bad Request)} if the reglementTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reglementTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reglement-types")
    public ResponseEntity<ReglementTypeDTO> updateReglementType(@RequestBody ReglementTypeDTO reglementTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ReglementType : {}", reglementTypeDTO);
        if (reglementTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReglementTypeDTO result = reglementTypeService.save(reglementTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reglementTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reglement-types} : get all the reglementTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reglementTypes in body.
     */
    @GetMapping("/reglement-types")
    public ResponseEntity<List<ReglementTypeDTO>> getAllReglementTypes(ReglementTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReglementTypes by criteria: {}", criteria);
        Page<ReglementTypeDTO> page = reglementTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reglement-types/count} : count all the reglementTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reglement-types/count")
    public ResponseEntity<Long> countReglementTypes(ReglementTypeCriteria criteria) {
        log.debug("REST request to count ReglementTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(reglementTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reglement-types/:id} : get the "id" reglementType.
     *
     * @param id the id of the reglementTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reglementTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reglement-types/{id}")
    public ResponseEntity<ReglementTypeDTO> getReglementType(@PathVariable Long id) {
        log.debug("REST request to get ReglementType : {}", id);
        Optional<ReglementTypeDTO> reglementTypeDTO = reglementTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reglementTypeDTO);
    }

    /**
     * {@code DELETE  /reglement-types/:id} : delete the "id" reglementType.
     *
     * @param id the id of the reglementTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reglement-types/{id}")
    public ResponseEntity<Void> deleteReglementType(@PathVariable Long id) {
        log.debug("REST request to delete ReglementType : {}", id);
        reglementTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
