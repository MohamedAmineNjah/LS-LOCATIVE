package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Frequency;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.FrequencyRepository;
import com.fininfo.java.service.FrequencyService;
import com.fininfo.java.service.dto.FrequencyDTO;
import com.fininfo.java.service.mapper.FrequencyMapper;
import com.fininfo.java.service.dto.FrequencyCriteria;
import com.fininfo.java.service.FrequencyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FrequencyResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FrequencyResourceIT {

    @Autowired
    private FrequencyRepository frequencyRepository;

    @Autowired
    private FrequencyMapper frequencyMapper;

    @Autowired
    private FrequencyService frequencyService;

    @Autowired
    private FrequencyQueryService frequencyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrequencyMockMvc;

    private Frequency frequency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frequency createEntity(EntityManager em) {
        Frequency frequency = new Frequency();
        return frequency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frequency createUpdatedEntity(EntityManager em) {
        Frequency frequency = new Frequency();
        return frequency;
    }

    @BeforeEach
    public void initTest() {
        frequency = createEntity(em);
    }

    @Test
    @Transactional
    public void createFrequency() throws Exception {
        int databaseSizeBeforeCreate = frequencyRepository.findAll().size();
        // Create the Frequency
        FrequencyDTO frequencyDTO = frequencyMapper.toDto(frequency);
        restFrequencyMockMvc.perform(post("/api/frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frequencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Frequency in the database
        List<Frequency> frequencyList = frequencyRepository.findAll();
        assertThat(frequencyList).hasSize(databaseSizeBeforeCreate + 1);
        Frequency testFrequency = frequencyList.get(frequencyList.size() - 1);
    }

    @Test
    @Transactional
    public void createFrequencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = frequencyRepository.findAll().size();

        // Create the Frequency with an existing ID
        frequency.setId(1L);
        FrequencyDTO frequencyDTO = frequencyMapper.toDto(frequency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrequencyMockMvc.perform(post("/api/frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Frequency in the database
        List<Frequency> frequencyList = frequencyRepository.findAll();
        assertThat(frequencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFrequencies() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

        // Get all the frequencyList
        restFrequencyMockMvc.perform(get("/api/frequencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frequency.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFrequency() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

        // Get the frequency
        restFrequencyMockMvc.perform(get("/api/frequencies/{id}", frequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frequency.getId().intValue()));
    }


    @Test
    @Transactional
    public void getFrequenciesByIdFiltering() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

        Long id = frequency.getId();

        defaultFrequencyShouldBeFound("id.equals=" + id);
        defaultFrequencyShouldNotBeFound("id.notEquals=" + id);

        defaultFrequencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFrequencyShouldNotBeFound("id.greaterThan=" + id);

        defaultFrequencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFrequencyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFrequenciesByRentIsEqualToSomething() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);
        Rent rent = RentResourceIT.createEntity(em);
        em.persist(rent);
        em.flush();
        frequency.setRent(rent);
        frequencyRepository.saveAndFlush(frequency);
        Long rentId = rent.getId();

        // Get all the frequencyList where rent equals to rentId
        defaultFrequencyShouldBeFound("rentId.equals=" + rentId);

        // Get all the frequencyList where rent equals to rentId + 1
        defaultFrequencyShouldNotBeFound("rentId.equals=" + (rentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFrequencyShouldBeFound(String filter) throws Exception {
        restFrequencyMockMvc.perform(get("/api/frequencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frequency.getId().intValue())));

        // Check, that the count call also returns 1
        restFrequencyMockMvc.perform(get("/api/frequencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFrequencyShouldNotBeFound(String filter) throws Exception {
        restFrequencyMockMvc.perform(get("/api/frequencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFrequencyMockMvc.perform(get("/api/frequencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFrequency() throws Exception {
        // Get the frequency
        restFrequencyMockMvc.perform(get("/api/frequencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFrequency() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

        int databaseSizeBeforeUpdate = frequencyRepository.findAll().size();

        // Update the frequency
        Frequency updatedFrequency = frequencyRepository.findById(frequency.getId()).get();
        // Disconnect from session so that the updates on updatedFrequency are not directly saved in db
        em.detach(updatedFrequency);
        FrequencyDTO frequencyDTO = frequencyMapper.toDto(updatedFrequency);

        restFrequencyMockMvc.perform(put("/api/frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frequencyDTO)))
            .andExpect(status().isOk());

        // Validate the Frequency in the database
        List<Frequency> frequencyList = frequencyRepository.findAll();
        assertThat(frequencyList).hasSize(databaseSizeBeforeUpdate);
        Frequency testFrequency = frequencyList.get(frequencyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFrequency() throws Exception {
        int databaseSizeBeforeUpdate = frequencyRepository.findAll().size();

        // Create the Frequency
        FrequencyDTO frequencyDTO = frequencyMapper.toDto(frequency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrequencyMockMvc.perform(put("/api/frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Frequency in the database
        List<Frequency> frequencyList = frequencyRepository.findAll();
        assertThat(frequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFrequency() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

        int databaseSizeBeforeDelete = frequencyRepository.findAll().size();

        // Delete the frequency
        restFrequencyMockMvc.perform(delete("/api/frequencies/{id}", frequency.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Frequency> frequencyList = frequencyRepository.findAll();
        assertThat(frequencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
