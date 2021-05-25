package com.fininfo.java.web.rest;

import com.fininfo.java.service.InventoriesService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.InventoriesDTO;
import com.fininfo.java.service.dto.InventoriesCriteria;
import com.fininfo.java.service.InventoriesQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.Inventories}.
 */
@RestController
@RequestMapping("/api")
public class InventoriesResource {

    private final Logger log = LoggerFactory.getLogger(InventoriesResource.class);

    private static final String ENTITY_NAME = "locativeInventories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoriesService inventoriesService;

    private final InventoriesQueryService inventoriesQueryService;

    public InventoriesResource(InventoriesService inventoriesService, InventoriesQueryService inventoriesQueryService) {
        this.inventoriesService = inventoriesService;
        this.inventoriesQueryService = inventoriesQueryService;
    }

    /**
     * {@code POST  /inventories} : Create a new inventories.
     *
     * @param inventoriesDTO the inventoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoriesDTO, or with status {@code 400 (Bad Request)} if the inventories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventories")
    public ResponseEntity<InventoriesDTO> createInventories(@RequestBody InventoriesDTO inventoriesDTO) throws URISyntaxException {
        log.debug("REST request to save Inventories : {}", inventoriesDTO);
        if (inventoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoriesDTO result = inventoriesService.save(inventoriesDTO);
        return ResponseEntity.created(new URI("/api/inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventories} : Updates an existing inventories.
     *
     * @param inventoriesDTO the inventoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoriesDTO,
     * or with status {@code 400 (Bad Request)} if the inventoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventories")
    public ResponseEntity<InventoriesDTO> updateInventories(@RequestBody InventoriesDTO inventoriesDTO) throws URISyntaxException {
        log.debug("REST request to update Inventories : {}", inventoriesDTO);
        if (inventoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoriesDTO result = inventoriesService.save(inventoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inventoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventories} : get all the inventories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventories in body.
     */
    @GetMapping("/inventories")
    public ResponseEntity<List<InventoriesDTO>> getAllInventories(InventoriesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Inventories by criteria: {}", criteria);
        Page<InventoriesDTO> page = inventoriesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventories/count} : count all the inventories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventories/count")
    public ResponseEntity<Long> countInventories(InventoriesCriteria criteria) {
        log.debug("REST request to count Inventories by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventories/:id} : get the "id" inventories.
     *
     * @param id the id of the inventoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventories/{id}")
    public ResponseEntity<InventoriesDTO> getInventories(@PathVariable Long id) {
        log.debug("REST request to get Inventories : {}", id);
        Optional<InventoriesDTO> inventoriesDTO = inventoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoriesDTO);
    }

    /**
     * {@code DELETE  /inventories/:id} : delete the "id" inventories.
     *
     * @param id the id of the inventoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventories/{id}")
    public ResponseEntity<Void> deleteInventories(@PathVariable Long id) {
        log.debug("REST request to delete Inventories : {}", id);
        inventoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
