package com.fininfo.java.web.rest;

import com.fininfo.java.service.LocationEventService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.LocationEventDTO;
import com.fininfo.java.service.dto.LocationEventCriteria;
import com.fininfo.java.service.LocationEventQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.LocationEvent}.
 */
@RestController
@RequestMapping("/api")
public class LocationEventResource {

    private final Logger log = LoggerFactory.getLogger(LocationEventResource.class);

    private static final String ENTITY_NAME = "locativeLocationEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationEventService locationEventService;

    private final LocationEventQueryService locationEventQueryService;

    public LocationEventResource(LocationEventService locationEventService, LocationEventQueryService locationEventQueryService) {
        this.locationEventService = locationEventService;
        this.locationEventQueryService = locationEventQueryService;
    }

    /**
     * {@code POST  /location-events} : Create a new locationEvent.
     *
     * @param locationEventDTO the locationEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationEventDTO, or with status {@code 400 (Bad Request)} if the locationEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-events")
    public ResponseEntity<LocationEventDTO> createLocationEvent(@RequestBody LocationEventDTO locationEventDTO) throws URISyntaxException {
        log.debug("REST request to save LocationEvent : {}", locationEventDTO);
        if (locationEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationEventDTO result = locationEventService.save(locationEventDTO);
        return ResponseEntity.created(new URI("/api/location-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-events} : Updates an existing locationEvent.
     *
     * @param locationEventDTO the locationEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationEventDTO,
     * or with status {@code 400 (Bad Request)} if the locationEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-events")
    public ResponseEntity<LocationEventDTO> updateLocationEvent(@RequestBody LocationEventDTO locationEventDTO) throws URISyntaxException {
        log.debug("REST request to update LocationEvent : {}", locationEventDTO);
        if (locationEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationEventDTO result = locationEventService.save(locationEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /location-events} : get all the locationEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationEvents in body.
     */
    @GetMapping("/location-events")
    public ResponseEntity<List<LocationEventDTO>> getAllLocationEvents(LocationEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LocationEvents by criteria: {}", criteria);
        Page<LocationEventDTO> page = locationEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /location-events/count} : count all the locationEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/location-events/count")
    public ResponseEntity<Long> countLocationEvents(LocationEventCriteria criteria) {
        log.debug("REST request to count LocationEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-events/:id} : get the "id" locationEvent.
     *
     * @param id the id of the locationEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-events/{id}")
    public ResponseEntity<LocationEventDTO> getLocationEvent(@PathVariable Long id) {
        log.debug("REST request to get LocationEvent : {}", id);
        Optional<LocationEventDTO> locationEventDTO = locationEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationEventDTO);
    }

    /**
     * {@code DELETE  /location-events/:id} : delete the "id" locationEvent.
     *
     * @param id the id of the locationEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-events/{id}")
    public ResponseEntity<Void> deleteLocationEvent(@PathVariable Long id) {
        log.debug("REST request to delete LocationEvent : {}", id);
        locationEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
