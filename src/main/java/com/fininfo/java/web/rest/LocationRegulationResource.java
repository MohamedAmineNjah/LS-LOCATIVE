package com.fininfo.java.web.rest;

import com.fininfo.java.service.LocationRegulationService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.LocationRegulationDTO;
import com.fininfo.java.service.dto.LocationRegulationCriteria;
import com.fininfo.java.service.LocationRegulationQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.LocationRegulation}.
 */
@RestController
@RequestMapping("/api")
public class LocationRegulationResource {

    private final Logger log = LoggerFactory.getLogger(LocationRegulationResource.class);

    private static final String ENTITY_NAME = "locativeLocationRegulation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationRegulationService locationRegulationService;

    private final LocationRegulationQueryService locationRegulationQueryService;

    public LocationRegulationResource(LocationRegulationService locationRegulationService, LocationRegulationQueryService locationRegulationQueryService) {
        this.locationRegulationService = locationRegulationService;
        this.locationRegulationQueryService = locationRegulationQueryService;
    }

    /**
     * {@code POST  /location-regulations} : Create a new locationRegulation.
     *
     * @param locationRegulationDTO the locationRegulationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationRegulationDTO, or with status {@code 400 (Bad Request)} if the locationRegulation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-regulations")
    public ResponseEntity<LocationRegulationDTO> createLocationRegulation(@RequestBody LocationRegulationDTO locationRegulationDTO) throws URISyntaxException {
        log.debug("REST request to save LocationRegulation : {}", locationRegulationDTO);
        if (locationRegulationDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationRegulation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationRegulationDTO result = locationRegulationService.save(locationRegulationDTO);
        return ResponseEntity.created(new URI("/api/location-regulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-regulations} : Updates an existing locationRegulation.
     *
     * @param locationRegulationDTO the locationRegulationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationRegulationDTO,
     * or with status {@code 400 (Bad Request)} if the locationRegulationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationRegulationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-regulations")
    public ResponseEntity<LocationRegulationDTO> updateLocationRegulation(@RequestBody LocationRegulationDTO locationRegulationDTO) throws URISyntaxException {
        log.debug("REST request to update LocationRegulation : {}", locationRegulationDTO);
        if (locationRegulationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationRegulationDTO result = locationRegulationService.save(locationRegulationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationRegulationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /location-regulations} : get all the locationRegulations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationRegulations in body.
     */
    @GetMapping("/location-regulations")
    public ResponseEntity<List<LocationRegulationDTO>> getAllLocationRegulations(LocationRegulationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LocationRegulations by criteria: {}", criteria);
        Page<LocationRegulationDTO> page = locationRegulationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /location-regulations/count} : count all the locationRegulations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/location-regulations/count")
    public ResponseEntity<Long> countLocationRegulations(LocationRegulationCriteria criteria) {
        log.debug("REST request to count LocationRegulations by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationRegulationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-regulations/:id} : get the "id" locationRegulation.
     *
     * @param id the id of the locationRegulationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationRegulationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-regulations/{id}")
    public ResponseEntity<LocationRegulationDTO> getLocationRegulation(@PathVariable Long id) {
        log.debug("REST request to get LocationRegulation : {}", id);
        Optional<LocationRegulationDTO> locationRegulationDTO = locationRegulationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationRegulationDTO);
    }

    /**
     * {@code DELETE  /location-regulations/:id} : delete the "id" locationRegulation.
     *
     * @param id the id of the locationRegulationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-regulations/{id}")
    public ResponseEntity<Void> deleteLocationRegulation(@PathVariable Long id) {
        log.debug("REST request to delete LocationRegulation : {}", id);
        locationRegulationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
