package com.fininfo.java.web.rest;

import com.fininfo.java.service.BaseService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.BaseDTO;
import com.fininfo.java.service.dto.BaseCriteria;
import com.fininfo.java.service.BaseQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.Base}.
 */
@RestController
@RequestMapping("/api")
public class BaseResource {

    private final Logger log = LoggerFactory.getLogger(BaseResource.class);

    private static final String ENTITY_NAME = "locativeBase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseService baseService;

    private final BaseQueryService baseQueryService;

    public BaseResource(BaseService baseService, BaseQueryService baseQueryService) {
        this.baseService = baseService;
        this.baseQueryService = baseQueryService;
    }

    /**
     * {@code POST  /bases} : Create a new base.
     *
     * @param baseDTO the baseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baseDTO, or with status {@code 400 (Bad Request)} if the base has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bases")
    public ResponseEntity<BaseDTO> createBase(@RequestBody BaseDTO baseDTO) throws URISyntaxException {
        log.debug("REST request to save Base : {}", baseDTO);
        if (baseDTO.getId() != null) {
            throw new BadRequestAlertException("A new base cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseDTO result = baseService.save(baseDTO);
        return ResponseEntity.created(new URI("/api/bases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bases} : Updates an existing base.
     *
     * @param baseDTO the baseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseDTO,
     * or with status {@code 400 (Bad Request)} if the baseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bases")
    public ResponseEntity<BaseDTO> updateBase(@RequestBody BaseDTO baseDTO) throws URISyntaxException {
        log.debug("REST request to update Base : {}", baseDTO);
        if (baseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaseDTO result = baseService.save(baseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, baseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bases} : get all the bases.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bases in body.
     */
    @GetMapping("/bases")
    public ResponseEntity<List<BaseDTO>> getAllBases(BaseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Bases by criteria: {}", criteria);
        Page<BaseDTO> page = baseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bases/count} : count all the bases.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bases/count")
    public ResponseEntity<Long> countBases(BaseCriteria criteria) {
        log.debug("REST request to count Bases by criteria: {}", criteria);
        return ResponseEntity.ok().body(baseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bases/:id} : get the "id" base.
     *
     * @param id the id of the baseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bases/{id}")
    public ResponseEntity<BaseDTO> getBase(@PathVariable Long id) {
        log.debug("REST request to get Base : {}", id);
        Optional<BaseDTO> baseDTO = baseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(baseDTO);
    }

    /**
     * {@code DELETE  /bases/:id} : delete the "id" base.
     *
     * @param id the id of the baseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bases/{id}")
    public ResponseEntity<Void> deleteBase(@PathVariable Long id) {
        log.debug("REST request to delete Base : {}", id);
        baseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
