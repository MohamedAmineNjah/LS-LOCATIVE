package com.fininfo.java.web.rest;

import com.fininfo.java.service.GarantService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.GarantDTO;
import com.fininfo.java.service.dto.GarantCriteria;
import com.fininfo.java.service.GarantQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.Garant}.
 */
@RestController
@RequestMapping("/api")
public class GarantResource {

    private final Logger log = LoggerFactory.getLogger(GarantResource.class);

    private static final String ENTITY_NAME = "locativeGarant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GarantService garantService;

    private final GarantQueryService garantQueryService;

    public GarantResource(GarantService garantService, GarantQueryService garantQueryService) {
        this.garantService = garantService;
        this.garantQueryService = garantQueryService;
    }

    /**
     * {@code POST  /garants} : Create a new garant.
     *
     * @param garantDTO the garantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new garantDTO, or with status {@code 400 (Bad Request)} if the garant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/garants")
    public ResponseEntity<GarantDTO> createGarant(@RequestBody GarantDTO garantDTO) throws URISyntaxException {
        log.debug("REST request to save Garant : {}", garantDTO);
        if (garantDTO.getId() != null) {
            throw new BadRequestAlertException("A new garant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GarantDTO result = garantService.save(garantDTO);
        return ResponseEntity.created(new URI("/api/garants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /garants} : Updates an existing garant.
     *
     * @param garantDTO the garantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated garantDTO,
     * or with status {@code 400 (Bad Request)} if the garantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the garantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/garants")
    public ResponseEntity<GarantDTO> updateGarant(@RequestBody GarantDTO garantDTO) throws URISyntaxException {
        log.debug("REST request to update Garant : {}", garantDTO);
        if (garantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GarantDTO result = garantService.save(garantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, garantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /garants} : get all the garants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of garants in body.
     */
    @GetMapping("/garants")
    public ResponseEntity<List<GarantDTO>> getAllGarants(GarantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Garants by criteria: {}", criteria);
        Page<GarantDTO> page = garantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /garants/count} : count all the garants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/garants/count")
    public ResponseEntity<Long> countGarants(GarantCriteria criteria) {
        log.debug("REST request to count Garants by criteria: {}", criteria);
        return ResponseEntity.ok().body(garantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /garants/:id} : get the "id" garant.
     *
     * @param id the id of the garantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the garantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/garants/{id}")
    public ResponseEntity<GarantDTO> getGarant(@PathVariable Long id) {
        log.debug("REST request to get Garant : {}", id);
        Optional<GarantDTO> garantDTO = garantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(garantDTO);
    }

    /**
     * {@code DELETE  /garants/:id} : delete the "id" garant.
     *
     * @param id the id of the garantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/garants/{id}")
    public ResponseEntity<Void> deleteGarant(@PathVariable Long id) {
        log.debug("REST request to delete Garant : {}", id);
        garantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
