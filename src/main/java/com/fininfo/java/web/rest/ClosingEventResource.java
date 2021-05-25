package com.fininfo.java.web.rest;

import com.fininfo.java.service.ClosingEventService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.ClosingEventDTO;
import com.fininfo.java.service.dto.ClosingEventCriteria;
import com.fininfo.java.service.ClosingEventQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.ClosingEvent}.
 */
@RestController
@RequestMapping("/api")
public class ClosingEventResource {

    private final Logger log = LoggerFactory.getLogger(ClosingEventResource.class);

    private static final String ENTITY_NAME = "locativeClosingEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClosingEventService closingEventService;

    private final ClosingEventQueryService closingEventQueryService;

    public ClosingEventResource(ClosingEventService closingEventService, ClosingEventQueryService closingEventQueryService) {
        this.closingEventService = closingEventService;
        this.closingEventQueryService = closingEventQueryService;
    }

    /**
     * {@code POST  /closing-events} : Create a new closingEvent.
     *
     * @param closingEventDTO the closingEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new closingEventDTO, or with status {@code 400 (Bad Request)} if the closingEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/closing-events")
    public ResponseEntity<ClosingEventDTO> createClosingEvent(@RequestBody ClosingEventDTO closingEventDTO) throws URISyntaxException {
        log.debug("REST request to save ClosingEvent : {}", closingEventDTO);
        if (closingEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new closingEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClosingEventDTO result = closingEventService.save(closingEventDTO);
        return ResponseEntity.created(new URI("/api/closing-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /closing-events} : Updates an existing closingEvent.
     *
     * @param closingEventDTO the closingEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated closingEventDTO,
     * or with status {@code 400 (Bad Request)} if the closingEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the closingEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/closing-events")
    public ResponseEntity<ClosingEventDTO> updateClosingEvent(@RequestBody ClosingEventDTO closingEventDTO) throws URISyntaxException {
        log.debug("REST request to update ClosingEvent : {}", closingEventDTO);
        if (closingEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClosingEventDTO result = closingEventService.save(closingEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, closingEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /closing-events} : get all the closingEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of closingEvents in body.
     */
    @GetMapping("/closing-events")
    public ResponseEntity<List<ClosingEventDTO>> getAllClosingEvents(ClosingEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClosingEvents by criteria: {}", criteria);
        Page<ClosingEventDTO> page = closingEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /closing-events/count} : count all the closingEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/closing-events/count")
    public ResponseEntity<Long> countClosingEvents(ClosingEventCriteria criteria) {
        log.debug("REST request to count ClosingEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(closingEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /closing-events/:id} : get the "id" closingEvent.
     *
     * @param id the id of the closingEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the closingEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/closing-events/{id}")
    public ResponseEntity<ClosingEventDTO> getClosingEvent(@PathVariable Long id) {
        log.debug("REST request to get ClosingEvent : {}", id);
        Optional<ClosingEventDTO> closingEventDTO = closingEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(closingEventDTO);
    }

    /**
     * {@code DELETE  /closing-events/:id} : delete the "id" closingEvent.
     *
     * @param id the id of the closingEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/closing-events/{id}")
    public ResponseEntity<Void> deleteClosingEvent(@PathVariable Long id) {
        log.debug("REST request to delete ClosingEvent : {}", id);
        closingEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
