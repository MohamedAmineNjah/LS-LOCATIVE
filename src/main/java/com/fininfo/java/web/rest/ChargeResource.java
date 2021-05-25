package com.fininfo.java.web.rest;

import com.fininfo.java.service.ChargeService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.ChargeDTO;
import com.fininfo.java.service.dto.ChargeCriteria;
import com.fininfo.java.service.ChargeQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.Charge}.
 */
@RestController
@RequestMapping("/api")
public class ChargeResource {

    private final Logger log = LoggerFactory.getLogger(ChargeResource.class);

    private static final String ENTITY_NAME = "locativeCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChargeService chargeService;

    private final ChargeQueryService chargeQueryService;

    public ChargeResource(ChargeService chargeService, ChargeQueryService chargeQueryService) {
        this.chargeService = chargeService;
        this.chargeQueryService = chargeQueryService;
    }

    /**
     * {@code POST  /charges} : Create a new charge.
     *
     * @param chargeDTO the chargeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chargeDTO, or with status {@code 400 (Bad Request)} if the charge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/charges")
    public ResponseEntity<ChargeDTO> createCharge(@RequestBody ChargeDTO chargeDTO) throws URISyntaxException {
        log.debug("REST request to save Charge : {}", chargeDTO);
        if (chargeDTO.getId() != null) {
            throw new BadRequestAlertException("A new charge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargeDTO result = chargeService.save(chargeDTO);
        return ResponseEntity.created(new URI("/api/charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /charges} : Updates an existing charge.
     *
     * @param chargeDTO the chargeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chargeDTO,
     * or with status {@code 400 (Bad Request)} if the chargeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chargeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/charges")
    public ResponseEntity<ChargeDTO> updateCharge(@RequestBody ChargeDTO chargeDTO) throws URISyntaxException {
        log.debug("REST request to update Charge : {}", chargeDTO);
        if (chargeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChargeDTO result = chargeService.save(chargeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chargeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /charges} : get all the charges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of charges in body.
     */
    @GetMapping("/charges")
    public ResponseEntity<List<ChargeDTO>> getAllCharges(ChargeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Charges by criteria: {}", criteria);
        Page<ChargeDTO> page = chargeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /charges/count} : count all the charges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/charges/count")
    public ResponseEntity<Long> countCharges(ChargeCriteria criteria) {
        log.debug("REST request to count Charges by criteria: {}", criteria);
        return ResponseEntity.ok().body(chargeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /charges/:id} : get the "id" charge.
     *
     * @param id the id of the chargeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chargeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/charges/{id}")
    public ResponseEntity<ChargeDTO> getCharge(@PathVariable Long id) {
        log.debug("REST request to get Charge : {}", id);
        Optional<ChargeDTO> chargeDTO = chargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chargeDTO);
    }

    /**
     * {@code DELETE  /charges/:id} : delete the "id" charge.
     *
     * @param id the id of the chargeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/charges/{id}")
    public ResponseEntity<Void> deleteCharge(@PathVariable Long id) {
        log.debug("REST request to delete Charge : {}", id);
        chargeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
