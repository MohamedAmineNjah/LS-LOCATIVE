package com.fininfo.java.web.rest;

import com.fininfo.java.service.RefundFrequencyService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.RefundFrequencyDTO;
import com.fininfo.java.service.dto.RefundFrequencyCriteria;
import com.fininfo.java.service.RefundFrequencyQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.RefundFrequency}.
 */
@RestController
@RequestMapping("/api")
public class RefundFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(RefundFrequencyResource.class);

    private static final String ENTITY_NAME = "locativeRefundFrequency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefundFrequencyService refundFrequencyService;

    private final RefundFrequencyQueryService refundFrequencyQueryService;

    public RefundFrequencyResource(RefundFrequencyService refundFrequencyService, RefundFrequencyQueryService refundFrequencyQueryService) {
        this.refundFrequencyService = refundFrequencyService;
        this.refundFrequencyQueryService = refundFrequencyQueryService;
    }

    /**
     * {@code POST  /refund-frequencies} : Create a new refundFrequency.
     *
     * @param refundFrequencyDTO the refundFrequencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refundFrequencyDTO, or with status {@code 400 (Bad Request)} if the refundFrequency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refund-frequencies")
    public ResponseEntity<RefundFrequencyDTO> createRefundFrequency(@RequestBody RefundFrequencyDTO refundFrequencyDTO) throws URISyntaxException {
        log.debug("REST request to save RefundFrequency : {}", refundFrequencyDTO);
        if (refundFrequencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new refundFrequency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefundFrequencyDTO result = refundFrequencyService.save(refundFrequencyDTO);
        return ResponseEntity.created(new URI("/api/refund-frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /refund-frequencies} : Updates an existing refundFrequency.
     *
     * @param refundFrequencyDTO the refundFrequencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refundFrequencyDTO,
     * or with status {@code 400 (Bad Request)} if the refundFrequencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refundFrequencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refund-frequencies")
    public ResponseEntity<RefundFrequencyDTO> updateRefundFrequency(@RequestBody RefundFrequencyDTO refundFrequencyDTO) throws URISyntaxException {
        log.debug("REST request to update RefundFrequency : {}", refundFrequencyDTO);
        if (refundFrequencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefundFrequencyDTO result = refundFrequencyService.save(refundFrequencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refundFrequencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /refund-frequencies} : get all the refundFrequencies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refundFrequencies in body.
     */
    @GetMapping("/refund-frequencies")
    public ResponseEntity<List<RefundFrequencyDTO>> getAllRefundFrequencies(RefundFrequencyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RefundFrequencies by criteria: {}", criteria);
        Page<RefundFrequencyDTO> page = refundFrequencyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /refund-frequencies/count} : count all the refundFrequencies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/refund-frequencies/count")
    public ResponseEntity<Long> countRefundFrequencies(RefundFrequencyCriteria criteria) {
        log.debug("REST request to count RefundFrequencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(refundFrequencyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /refund-frequencies/:id} : get the "id" refundFrequency.
     *
     * @param id the id of the refundFrequencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refundFrequencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refund-frequencies/{id}")
    public ResponseEntity<RefundFrequencyDTO> getRefundFrequency(@PathVariable Long id) {
        log.debug("REST request to get RefundFrequency : {}", id);
        Optional<RefundFrequencyDTO> refundFrequencyDTO = refundFrequencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refundFrequencyDTO);
    }

    /**
     * {@code DELETE  /refund-frequencies/:id} : delete the "id" refundFrequency.
     *
     * @param id the id of the refundFrequencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refund-frequencies/{id}")
    public ResponseEntity<Void> deleteRefundFrequency(@PathVariable Long id) {
        log.debug("REST request to delete RefundFrequency : {}", id);
        refundFrequencyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
