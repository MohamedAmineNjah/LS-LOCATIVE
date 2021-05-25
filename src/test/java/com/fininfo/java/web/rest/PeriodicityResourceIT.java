package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Periodicity;
import com.fininfo.java.repository.PeriodicityRepository;
import com.fininfo.java.service.PeriodicityService;
import com.fininfo.java.service.dto.PeriodicityDTO;
import com.fininfo.java.service.mapper.PeriodicityMapper;
import com.fininfo.java.service.dto.PeriodicityCriteria;
import com.fininfo.java.service.PeriodicityQueryService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fininfo.java.domain.enumeration.IEnumperiodicityType;
/**
 * Integration tests for the {@link PeriodicityResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PeriodicityResourceIT {

    private static final IEnumperiodicityType DEFAULT_PERIODICITY_TYPE = IEnumperiodicityType.ANNULE;
    private static final IEnumperiodicityType UPDATED_PERIODICITY_TYPE = IEnumperiodicityType.MONSUEL;

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALUE = new BigDecimal(1 - 1);

    @Autowired
    private PeriodicityRepository periodicityRepository;

    @Autowired
    private PeriodicityMapper periodicityMapper;

    @Autowired
    private PeriodicityService periodicityService;

    @Autowired
    private PeriodicityQueryService periodicityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodicityMockMvc;

    private Periodicity periodicity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodicity createEntity(EntityManager em) {
        Periodicity periodicity = new Periodicity()
            .periodicityType(DEFAULT_PERIODICITY_TYPE)
            .value(DEFAULT_VALUE);
        return periodicity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodicity createUpdatedEntity(EntityManager em) {
        Periodicity periodicity = new Periodicity()
            .periodicityType(UPDATED_PERIODICITY_TYPE)
            .value(UPDATED_VALUE);
        return periodicity;
    }

    @BeforeEach
    public void initTest() {
        periodicity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodicity() throws Exception {
        int databaseSizeBeforeCreate = periodicityRepository.findAll().size();
        // Create the Periodicity
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);
        restPeriodicityMockMvc.perform(post("/api/periodicities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isCreated());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeCreate + 1);
        Periodicity testPeriodicity = periodicityList.get(periodicityList.size() - 1);
        assertThat(testPeriodicity.getPeriodicityType()).isEqualTo(DEFAULT_PERIODICITY_TYPE);
        assertThat(testPeriodicity.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPeriodicityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodicityRepository.findAll().size();

        // Create the Periodicity with an existing ID
        periodicity.setId(1L);
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodicityMockMvc.perform(post("/api/periodicities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPeriodicities() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList
        restPeriodicityMockMvc.perform(get("/api/periodicities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodicity.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodicityType").value(hasItem(DEFAULT_PERIODICITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getPeriodicity() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get the periodicity
        restPeriodicityMockMvc.perform(get("/api/periodicities/{id}", periodicity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodicity.getId().intValue()))
            .andExpect(jsonPath("$.periodicityType").value(DEFAULT_PERIODICITY_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }


    @Test
    @Transactional
    public void getPeriodicitiesByIdFiltering() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        Long id = periodicity.getId();

        defaultPeriodicityShouldBeFound("id.equals=" + id);
        defaultPeriodicityShouldNotBeFound("id.notEquals=" + id);

        defaultPeriodicityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPeriodicityShouldNotBeFound("id.greaterThan=" + id);

        defaultPeriodicityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPeriodicityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPeriodicitiesByPeriodicityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where periodicityType equals to DEFAULT_PERIODICITY_TYPE
        defaultPeriodicityShouldBeFound("periodicityType.equals=" + DEFAULT_PERIODICITY_TYPE);

        // Get all the periodicityList where periodicityType equals to UPDATED_PERIODICITY_TYPE
        defaultPeriodicityShouldNotBeFound("periodicityType.equals=" + UPDATED_PERIODICITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByPeriodicityTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where periodicityType not equals to DEFAULT_PERIODICITY_TYPE
        defaultPeriodicityShouldNotBeFound("periodicityType.notEquals=" + DEFAULT_PERIODICITY_TYPE);

        // Get all the periodicityList where periodicityType not equals to UPDATED_PERIODICITY_TYPE
        defaultPeriodicityShouldBeFound("periodicityType.notEquals=" + UPDATED_PERIODICITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByPeriodicityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where periodicityType in DEFAULT_PERIODICITY_TYPE or UPDATED_PERIODICITY_TYPE
        defaultPeriodicityShouldBeFound("periodicityType.in=" + DEFAULT_PERIODICITY_TYPE + "," + UPDATED_PERIODICITY_TYPE);

        // Get all the periodicityList where periodicityType equals to UPDATED_PERIODICITY_TYPE
        defaultPeriodicityShouldNotBeFound("periodicityType.in=" + UPDATED_PERIODICITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByPeriodicityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where periodicityType is not null
        defaultPeriodicityShouldBeFound("periodicityType.specified=true");

        // Get all the periodicityList where periodicityType is null
        defaultPeriodicityShouldNotBeFound("periodicityType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value equals to DEFAULT_VALUE
        defaultPeriodicityShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the periodicityList where value equals to UPDATED_VALUE
        defaultPeriodicityShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value not equals to DEFAULT_VALUE
        defaultPeriodicityShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the periodicityList where value not equals to UPDATED_VALUE
        defaultPeriodicityShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPeriodicityShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the periodicityList where value equals to UPDATED_VALUE
        defaultPeriodicityShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value is not null
        defaultPeriodicityShouldBeFound("value.specified=true");

        // Get all the periodicityList where value is null
        defaultPeriodicityShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value is greater than or equal to DEFAULT_VALUE
        defaultPeriodicityShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the periodicityList where value is greater than or equal to UPDATED_VALUE
        defaultPeriodicityShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value is less than or equal to DEFAULT_VALUE
        defaultPeriodicityShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the periodicityList where value is less than or equal to SMALLER_VALUE
        defaultPeriodicityShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value is less than DEFAULT_VALUE
        defaultPeriodicityShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the periodicityList where value is less than UPDATED_VALUE
        defaultPeriodicityShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPeriodicitiesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList where value is greater than DEFAULT_VALUE
        defaultPeriodicityShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the periodicityList where value is greater than SMALLER_VALUE
        defaultPeriodicityShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPeriodicityShouldBeFound(String filter) throws Exception {
        restPeriodicityMockMvc.perform(get("/api/periodicities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodicity.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodicityType").value(hasItem(DEFAULT_PERIODICITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));

        // Check, that the count call also returns 1
        restPeriodicityMockMvc.perform(get("/api/periodicities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPeriodicityShouldNotBeFound(String filter) throws Exception {
        restPeriodicityMockMvc.perform(get("/api/periodicities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeriodicityMockMvc.perform(get("/api/periodicities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodicity() throws Exception {
        // Get the periodicity
        restPeriodicityMockMvc.perform(get("/api/periodicities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodicity() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        int databaseSizeBeforeUpdate = periodicityRepository.findAll().size();

        // Update the periodicity
        Periodicity updatedPeriodicity = periodicityRepository.findById(periodicity.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodicity are not directly saved in db
        em.detach(updatedPeriodicity);
        updatedPeriodicity
            .periodicityType(UPDATED_PERIODICITY_TYPE)
            .value(UPDATED_VALUE);
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(updatedPeriodicity);

        restPeriodicityMockMvc.perform(put("/api/periodicities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isOk());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeUpdate);
        Periodicity testPeriodicity = periodicityList.get(periodicityList.size() - 1);
        assertThat(testPeriodicity.getPeriodicityType()).isEqualTo(UPDATED_PERIODICITY_TYPE);
        assertThat(testPeriodicity.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodicity() throws Exception {
        int databaseSizeBeforeUpdate = periodicityRepository.findAll().size();

        // Create the Periodicity
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodicityMockMvc.perform(put("/api/periodicities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodicity() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        int databaseSizeBeforeDelete = periodicityRepository.findAll().size();

        // Delete the periodicity
        restPeriodicityMockMvc.perform(delete("/api/periodicities/{id}", periodicity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
