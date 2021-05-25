package com.fininfo.java.web.rest;

import com.fininfo.java.service.FrequencyService;
import com.fininfo.java.web.rest.errors.BadRequestAlertException;
import com.fininfo.java.service.dto.FrequencyDTO;
import com.fininfo.java.service.dto.FrequencyCriteria;
import com.fininfo.java.service.FrequencyQueryService;

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
 * REST controller for managing {@link com.fininfo.java.domain.Frequency}.
 */
@RestController
@RequestMapping("/api")
public class FrequencyResource {

    private final Logger log = LoggerFactory.getLogger(FrequencyResource.class);

    private static final String ENTITY_NAME = "locativeFrequency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrequencyService frequencyService;

    private final FrequencyQueryService frequencyQueryService;

    public FrequencyResource(FrequencyService frequencyService, FrequencyQueryService frequencyQueryService) {
        this.frequencyService = frequencyService;
        this.frequencyQueryService = frequencyQueryService;
    }

    /**
     * {@code POST  /frequencies} : Create a new frequency.
     *
     * @param frequencyDTO the frequencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frequencyDTO, or with status {@code 400 (Bad Request)} if the frequency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frequencies")
    public ResponseEntity<FrequencyDTO> createFrequency(@RequestBody FrequencyDTO frequencyDTO) throws URISyntaxException {
        log.debug("REST request to save Frequency : {}", frequencyDTO);
        if (frequencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new frequency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FrequencyDTO result = frequencyService.save(frequencyDTO);
        return ResponseEntity.created(new URI("/api/frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frequencies} : Updates an existing frequency.
     *
     * @param frequencyDTO the frequencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frequencyDTO,
     * or with status {@code 400 (Bad Request)} if the frequencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frequencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frequencies")
    public ResponseEntity<FrequencyDTO> updateFrequency(@RequestBody FrequencyDTO frequencyDTO) throws URISyntaxException {
        log.debug("REST request to update Frequency : {}", frequencyDTO);
        if (frequencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FrequencyDTO result = frequencyService.save(frequencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, frequencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /frequencies} : get all the frequencies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frequencies in body.
     */
    @GetMapping("/frequencies")
    public ResponseEntity<List<FrequencyDTO>> getAllFrequencies(FrequencyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Frequencies by criteria: {}", criteria);
        Page<FrequencyDTO> page = frequencyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frequencies/count} : count all the frequencies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/frequencies/count")
    public ResponseEntity<Long> countFrequencies(FrequencyCriteria criteria) {
        log.debug("REST request to count Frequencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(frequencyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /frequencies/:id} : get the "id" frequency.
     *
     * @param id the id of the frequencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frequencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frequencies/{id}")
    public ResponseEntity<FrequencyDTO> getFrequency(@PathVariable Long id) {
        log.debug("REST request to get Frequency : {}", id);
        Optional<FrequencyDTO> frequencyDTO = frequencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frequencyDTO);
    }

    /**
     * {@code DELETE  /frequencies/:id} : delete the "id" frequency.
     *
     * @param id the id of the frequencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frequencies/{id}")
    public ResponseEntity<Void> deleteFrequency(@PathVariable Long id) {
        log.debug("REST request to delete Frequency : {}", id);
        frequencyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
