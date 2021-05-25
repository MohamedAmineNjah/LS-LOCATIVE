package com.fininfo.java.web.rest;

import com.fininfo.java.service.BailService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.BailDTO;
import com.fininfo.java.service.dto.BailCriteria;
import com.fininfo.java.service.BailQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.Bail}.
 */
@RestController
@RequestMapping("/api")
public class BailResource {

    private final Logger log = LoggerFactory.getLogger(BailResource.class);

    private static final String ENTITY_NAME = "locativeBail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BailService bailService;

    private final BailQueryService bailQueryService;

    public BailResource(BailService bailService, BailQueryService bailQueryService) {
        this.bailService = bailService;
        this.bailQueryService = bailQueryService;
    }

    /**
     * {@code POST  /bails} : Create a new bail.
     *
     * @param bailDTO the bailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bailDTO, or with status {@code 400 (Bad Request)} if the bail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bails")
    public ResponseEntity<BailDTO> createBail(@RequestBody BailDTO bailDTO) throws URISyntaxException {
        log.debug("REST request to save Bail : {}", bailDTO);
        if (bailDTO.getId() != null) {
            throw new BadRequestAlertException("A new bail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BailDTO result = bailService.save(bailDTO);
        return ResponseEntity.created(new URI("/api/bails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bails} : Updates an existing bail.
     *
     * @param bailDTO the bailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bailDTO,
     * or with status {@code 400 (Bad Request)} if the bailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bails")
    public ResponseEntity<BailDTO> updateBail(@RequestBody BailDTO bailDTO) throws URISyntaxException {
        log.debug("REST request to update Bail : {}", bailDTO);
        if (bailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BailDTO result = bailService.save(bailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bails} : get all the bails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bails in body.
     */
    @GetMapping("/bails")
    public ResponseEntity<List<BailDTO>> getAllBails(BailCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Bails by criteria: {}", criteria);
        Page<BailDTO> page = bailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bails/count} : count all the bails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bails/count")
    public ResponseEntity<Long> countBails(BailCriteria criteria) {
        log.debug("REST request to count Bails by criteria: {}", criteria);
        return ResponseEntity.ok().body(bailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bails/:id} : get the "id" bail.
     *
     * @param id the id of the bailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bails/{id}")
    public ResponseEntity<BailDTO> getBail(@PathVariable Long id) {
        log.debug("REST request to get Bail : {}", id);
        Optional<BailDTO> bailDTO = bailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bailDTO);
    }

    /**
     * {@code DELETE  /bails/:id} : delete the "id" bail.
     *
     * @param id the id of the bailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bails/{id}")
    public ResponseEntity<Void> deleteBail(@PathVariable Long id) {
        log.debug("REST request to delete Bail : {}", id);
        bailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
