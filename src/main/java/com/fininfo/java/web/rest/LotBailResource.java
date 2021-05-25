package com.fininfo.java.web.rest;

import com.fininfo.java.service.LotBailService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.LotBailDTO;
import com.fininfo.java.service.dto.LotBailCriteria;
import com.fininfo.java.service.LotBailQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.LotBail}.
 */
@RestController
@RequestMapping("/api")
public class LotBailResource {

    private final Logger log = LoggerFactory.getLogger(LotBailResource.class);

    private static final String ENTITY_NAME = "locativeLotBail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LotBailService lotBailService;

    private final LotBailQueryService lotBailQueryService;

    public LotBailResource(LotBailService lotBailService, LotBailQueryService lotBailQueryService) {
        this.lotBailService = lotBailService;
        this.lotBailQueryService = lotBailQueryService;
    }

    /**
     * {@code POST  /lot-bails} : Create a new lotBail.
     *
     * @param lotBailDTO the lotBailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lotBailDTO, or with status {@code 400 (Bad Request)} if the lotBail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lot-bails")
    public ResponseEntity<LotBailDTO> createLotBail(@RequestBody LotBailDTO lotBailDTO) throws URISyntaxException {
        log.debug("REST request to save LotBail : {}", lotBailDTO);
        if (lotBailDTO.getId() != null) {
            throw new BadRequestAlertException("A new lotBail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LotBailDTO result = lotBailService.save(lotBailDTO);
        return ResponseEntity.created(new URI("/api/lot-bails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lot-bails} : Updates an existing lotBail.
     *
     * @param lotBailDTO the lotBailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotBailDTO,
     * or with status {@code 400 (Bad Request)} if the lotBailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lotBailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lot-bails")
    public ResponseEntity<LotBailDTO> updateLotBail(@RequestBody LotBailDTO lotBailDTO) throws URISyntaxException {
        log.debug("REST request to update LotBail : {}", lotBailDTO);
        if (lotBailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LotBailDTO result = lotBailService.save(lotBailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lotBailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lot-bails} : get all the lotBails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotBails in body.
     */
    @GetMapping("/lot-bails")
    public ResponseEntity<List<LotBailDTO>> getAllLotBails(LotBailCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LotBails by criteria: {}", criteria);
        Page<LotBailDTO> page = lotBailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lot-bails/count} : count all the lotBails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lot-bails/count")
    public ResponseEntity<Long> countLotBails(LotBailCriteria criteria) {
        log.debug("REST request to count LotBails by criteria: {}", criteria);
        return ResponseEntity.ok().body(lotBailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lot-bails/:id} : get the "id" lotBail.
     *
     * @param id the id of the lotBailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lotBailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lot-bails/{id}")
    public ResponseEntity<LotBailDTO> getLotBail(@PathVariable Long id) {
        log.debug("REST request to get LotBail : {}", id);
        Optional<LotBailDTO> lotBailDTO = lotBailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lotBailDTO);
    }

    /**
     * {@code DELETE  /lot-bails/:id} : delete the "id" lotBail.
     *
     * @param id the id of the lotBailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lot-bails/{id}")
    public ResponseEntity<Void> deleteLotBail(@PathVariable Long id) {
        log.debug("REST request to delete LotBail : {}", id);
        lotBailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
