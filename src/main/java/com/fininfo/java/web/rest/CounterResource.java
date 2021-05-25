package com.fininfo.java.web.rest;

import com.fininfo.java.service.CounterService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.CounterDTO;
import com.fininfo.java.service.dto.CounterCriteria;
import com.fininfo.java.service.CounterQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.Counter}.
 */
@RestController
@RequestMapping("/api")
public class CounterResource {

    private final Logger log = LoggerFactory.getLogger(CounterResource.class);

    private static final String ENTITY_NAME = "locativeCounter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CounterService counterService;

    private final CounterQueryService counterQueryService;

    public CounterResource(CounterService counterService, CounterQueryService counterQueryService) {
        this.counterService = counterService;
        this.counterQueryService = counterQueryService;
    }

    /**
     * {@code POST  /counters} : Create a new counter.
     *
     * @param counterDTO the counterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new counterDTO, or with status {@code 400 (Bad Request)} if the counter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/counters")
    public ResponseEntity<CounterDTO> createCounter(@RequestBody CounterDTO counterDTO) throws URISyntaxException {
        log.debug("REST request to save Counter : {}", counterDTO);
        if (counterDTO.getId() != null) {
            throw new BadRequestAlertException("A new counter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CounterDTO result = counterService.save(counterDTO);
        return ResponseEntity.created(new URI("/api/counters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /counters} : Updates an existing counter.
     *
     * @param counterDTO the counterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated counterDTO,
     * or with status {@code 400 (Bad Request)} if the counterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the counterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/counters")
    public ResponseEntity<CounterDTO> updateCounter(@RequestBody CounterDTO counterDTO) throws URISyntaxException {
        log.debug("REST request to update Counter : {}", counterDTO);
        if (counterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CounterDTO result = counterService.save(counterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, counterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /counters} : get all the counters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of counters in body.
     */
    @GetMapping("/counters")
    public ResponseEntity<List<CounterDTO>> getAllCounters(CounterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Counters by criteria: {}", criteria);
        Page<CounterDTO> page = counterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /counters/count} : count all the counters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/counters/count")
    public ResponseEntity<Long> countCounters(CounterCriteria criteria) {
        log.debug("REST request to count Counters by criteria: {}", criteria);
        return ResponseEntity.ok().body(counterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /counters/:id} : get the "id" counter.
     *
     * @param id the id of the counterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the counterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/counters/{id}")
    public ResponseEntity<CounterDTO> getCounter(@PathVariable Long id) {
        log.debug("REST request to get Counter : {}", id);
        Optional<CounterDTO> counterDTO = counterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(counterDTO);
    }

    /**
     * {@code DELETE  /counters/:id} : delete the "id" counter.
     *
     * @param id the id of the counterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/counters/{id}")
    public ResponseEntity<Void> deleteCounter(@PathVariable Long id) {
        log.debug("REST request to delete Counter : {}", id);
        counterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
