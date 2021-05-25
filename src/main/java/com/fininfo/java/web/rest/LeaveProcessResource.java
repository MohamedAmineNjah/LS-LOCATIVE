package com.fininfo.java.web.rest;

import com.fininfo.java.service.LeaveProcessService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.LeaveProcessDTO;
import com.fininfo.java.service.dto.LeaveProcessCriteria;
import com.fininfo.java.service.LeaveProcessQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.LeaveProcess}.
 */
@RestController
@RequestMapping("/api")
public class LeaveProcessResource {

    private final Logger log = LoggerFactory.getLogger(LeaveProcessResource.class);

    private static final String ENTITY_NAME = "locativeLeaveProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveProcessService leaveProcessService;

    private final LeaveProcessQueryService leaveProcessQueryService;

    public LeaveProcessResource(LeaveProcessService leaveProcessService, LeaveProcessQueryService leaveProcessQueryService) {
        this.leaveProcessService = leaveProcessService;
        this.leaveProcessQueryService = leaveProcessQueryService;
    }

    /**
     * {@code POST  /leave-processes} : Create a new leaveProcess.
     *
     * @param leaveProcessDTO the leaveProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveProcessDTO, or with status {@code 400 (Bad Request)} if the leaveProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-processes")
    public ResponseEntity<LeaveProcessDTO> createLeaveProcess(@RequestBody LeaveProcessDTO leaveProcessDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveProcess : {}", leaveProcessDTO);
        if (leaveProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveProcessDTO result = leaveProcessService.save(leaveProcessDTO);
        return ResponseEntity.created(new URI("/api/leave-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-processes} : Updates an existing leaveProcess.
     *
     * @param leaveProcessDTO the leaveProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveProcessDTO,
     * or with status {@code 400 (Bad Request)} if the leaveProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-processes")
    public ResponseEntity<LeaveProcessDTO> updateLeaveProcess(@RequestBody LeaveProcessDTO leaveProcessDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveProcess : {}", leaveProcessDTO);
        if (leaveProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveProcessDTO result = leaveProcessService.save(leaveProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leave-processes} : get all the leaveProcesses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveProcesses in body.
     */
    @GetMapping("/leave-processes")
    public ResponseEntity<List<LeaveProcessDTO>> getAllLeaveProcesses(LeaveProcessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeaveProcesses by criteria: {}", criteria);
        Page<LeaveProcessDTO> page = leaveProcessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-processes/count} : count all the leaveProcesses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-processes/count")
    public ResponseEntity<Long> countLeaveProcesses(LeaveProcessCriteria criteria) {
        log.debug("REST request to count LeaveProcesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveProcessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-processes/:id} : get the "id" leaveProcess.
     *
     * @param id the id of the leaveProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-processes/{id}")
    public ResponseEntity<LeaveProcessDTO> getLeaveProcess(@PathVariable Long id) {
        log.debug("REST request to get LeaveProcess : {}", id);
        Optional<LeaveProcessDTO> leaveProcessDTO = leaveProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveProcessDTO);
    }

    /**
     * {@code DELETE  /leave-processes/:id} : delete the "id" leaveProcess.
     *
     * @param id the id of the leaveProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-processes/{id}")
    public ResponseEntity<Void> deleteLeaveProcess(@PathVariable Long id) {
        log.debug("REST request to delete LeaveProcess : {}", id);
        leaveProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
