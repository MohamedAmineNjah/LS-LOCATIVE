package com.fininfo.java.web.rest;

import com.fininfo.java.service.RateTypeService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.RateTypeDTO;
import com.fininfo.java.service.dto.RateTypeCriteria;
import com.fininfo.java.service.RateTypeQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.RateType}.
 */
@RestController
@RequestMapping("/api")
public class RateTypeResource {

    private final Logger log = LoggerFactory.getLogger(RateTypeResource.class);

    private static final String ENTITY_NAME = "locativeRateType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RateTypeService rateTypeService;

    private final RateTypeQueryService rateTypeQueryService;

    public RateTypeResource(RateTypeService rateTypeService, RateTypeQueryService rateTypeQueryService) {
        this.rateTypeService = rateTypeService;
        this.rateTypeQueryService = rateTypeQueryService;
    }

    /**
     * {@code POST  /rate-types} : Create a new rateType.
     *
     * @param rateTypeDTO the rateTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rateTypeDTO, or with status {@code 400 (Bad Request)} if the rateType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rate-types")
    public ResponseEntity<RateTypeDTO> createRateType(@RequestBody RateTypeDTO rateTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RateType : {}", rateTypeDTO);
        if (rateTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new rateType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RateTypeDTO result = rateTypeService.save(rateTypeDTO);
        return ResponseEntity.created(new URI("/api/rate-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rate-types} : Updates an existing rateType.
     *
     * @param rateTypeDTO the rateTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rateTypeDTO,
     * or with status {@code 400 (Bad Request)} if the rateTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rateTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rate-types")
    public ResponseEntity<RateTypeDTO> updateRateType(@RequestBody RateTypeDTO rateTypeDTO) throws URISyntaxException {
        log.debug("REST request to update RateType : {}", rateTypeDTO);
        if (rateTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RateTypeDTO result = rateTypeService.save(rateTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rateTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rate-types} : get all the rateTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rateTypes in body.
     */
    @GetMapping("/rate-types")
    public ResponseEntity<List<RateTypeDTO>> getAllRateTypes(RateTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RateTypes by criteria: {}", criteria);
        Page<RateTypeDTO> page = rateTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rate-types/count} : count all the rateTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rate-types/count")
    public ResponseEntity<Long> countRateTypes(RateTypeCriteria criteria) {
        log.debug("REST request to count RateTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(rateTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rate-types/:id} : get the "id" rateType.
     *
     * @param id the id of the rateTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rateTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rate-types/{id}")
    public ResponseEntity<RateTypeDTO> getRateType(@PathVariable Long id) {
        log.debug("REST request to get RateType : {}", id);
        Optional<RateTypeDTO> rateTypeDTO = rateTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rateTypeDTO);
    }

    /**
     * {@code DELETE  /rate-types/:id} : delete the "id" rateType.
     *
     * @param id the id of the rateTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rate-types/{id}")
    public ResponseEntity<Void> deleteRateType(@PathVariable Long id) {
        log.debug("REST request to delete RateType : {}", id);
        rateTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
