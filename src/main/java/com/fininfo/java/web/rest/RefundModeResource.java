package com.fininfo.java.web.rest;

import com.fininfo.java.service.RefundModeService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.RefundModeDTO;
import com.fininfo.java.service.dto.RefundModeCriteria;
import com.fininfo.java.service.RefundModeQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.RefundMode}.
 */
@RestController
@RequestMapping("/api")
public class RefundModeResource {

    private final Logger log = LoggerFactory.getLogger(RefundModeResource.class);

    private static final String ENTITY_NAME = "locativeRefundMode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefundModeService refundModeService;

    private final RefundModeQueryService refundModeQueryService;

    public RefundModeResource(RefundModeService refundModeService, RefundModeQueryService refundModeQueryService) {
        this.refundModeService = refundModeService;
        this.refundModeQueryService = refundModeQueryService;
    }

    /**
     * {@code POST  /refund-modes} : Create a new refundMode.
     *
     * @param refundModeDTO the refundModeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refundModeDTO, or with status {@code 400 (Bad Request)} if the refundMode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refund-modes")
    public ResponseEntity<RefundModeDTO> createRefundMode(@RequestBody RefundModeDTO refundModeDTO) throws URISyntaxException {
        log.debug("REST request to save RefundMode : {}", refundModeDTO);
        if (refundModeDTO.getId() != null) {
            throw new BadRequestAlertException("A new refundMode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefundModeDTO result = refundModeService.save(refundModeDTO);
        return ResponseEntity.created(new URI("/api/refund-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /refund-modes} : Updates an existing refundMode.
     *
     * @param refundModeDTO the refundModeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refundModeDTO,
     * or with status {@code 400 (Bad Request)} if the refundModeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refundModeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refund-modes")
    public ResponseEntity<RefundModeDTO> updateRefundMode(@RequestBody RefundModeDTO refundModeDTO) throws URISyntaxException {
        log.debug("REST request to update RefundMode : {}", refundModeDTO);
        if (refundModeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefundModeDTO result = refundModeService.save(refundModeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refundModeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /refund-modes} : get all the refundModes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refundModes in body.
     */
    @GetMapping("/refund-modes")
    public ResponseEntity<List<RefundModeDTO>> getAllRefundModes(RefundModeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RefundModes by criteria: {}", criteria);
        Page<RefundModeDTO> page = refundModeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /refund-modes/count} : count all the refundModes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/refund-modes/count")
    public ResponseEntity<Long> countRefundModes(RefundModeCriteria criteria) {
        log.debug("REST request to count RefundModes by criteria: {}", criteria);
        return ResponseEntity.ok().body(refundModeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /refund-modes/:id} : get the "id" refundMode.
     *
     * @param id the id of the refundModeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refundModeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refund-modes/{id}")
    public ResponseEntity<RefundModeDTO> getRefundMode(@PathVariable Long id) {
        log.debug("REST request to get RefundMode : {}", id);
        Optional<RefundModeDTO> refundModeDTO = refundModeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refundModeDTO);
    }

    /**
     * {@code DELETE  /refund-modes/:id} : delete the "id" refundMode.
     *
     * @param id the id of the refundModeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refund-modes/{id}")
    public ResponseEntity<Void> deleteRefundMode(@PathVariable Long id) {
        log.debug("REST request to delete RefundMode : {}", id);
        refundModeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
