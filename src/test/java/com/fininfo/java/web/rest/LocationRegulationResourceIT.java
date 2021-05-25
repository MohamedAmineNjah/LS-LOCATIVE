package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.LocationRegulation;
import com.fininfo.java.domain.ReglementType;
import com.fininfo.java.repository.LocationRegulationRepository;
import com.fininfo.java.service.LocationRegulationService;
import com.fininfo.java.service.dto.LocationRegulationDTO;
import com.fininfo.java.service.mapper.LocationRegulationMapper;
import com.fininfo.java.service.dto.LocationRegulationCriteria;
import com.fininfo.java.service.LocationRegulationQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LocationRegulationResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocationRegulationResourceIT {

    private static final LocalDate DEFAULT_REGULATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGULATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REGULATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    @Autowired
    private LocationRegulationRepository locationRegulationRepository;

    @Autowired
    private LocationRegulationMapper locationRegulationMapper;

    @Autowired
    private LocationRegulationService locationRegulationService;

    @Autowired
    private LocationRegulationQueryService locationRegulationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationRegulationMockMvc;

    private LocationRegulation locationRegulation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationRegulation createEntity(EntityManager em) {
        LocationRegulation locationRegulation = new LocationRegulation()
            .regulationDate(DEFAULT_REGULATION_DATE)
            .amount(DEFAULT_AMOUNT);
        return locationRegulation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationRegulation createUpdatedEntity(EntityManager em) {
        LocationRegulation locationRegulation = new LocationRegulation()
            .regulationDate(UPDATED_REGULATION_DATE)
            .amount(UPDATED_AMOUNT);
        return locationRegulation;
    }

    @BeforeEach
    public void initTest() {
        locationRegulation = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationRegulation() throws Exception {
        int databaseSizeBeforeCreate = locationRegulationRepository.findAll().size();
        // Create the LocationRegulation
        LocationRegulationDTO locationRegulationDTO = locationRegulationMapper.toDto(locationRegulation);
        restLocationRegulationMockMvc.perform(post("/api/location-regulations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationRegulationDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationRegulation in the database
        List<LocationRegulation> locationRegulationList = locationRegulationRepository.findAll();
        assertThat(locationRegulationList).hasSize(databaseSizeBeforeCreate + 1);
        LocationRegulation testLocationRegulation = locationRegulationList.get(locationRegulationList.size() - 1);
        assertThat(testLocationRegulation.getRegulationDate()).isEqualTo(DEFAULT_REGULATION_DATE);
        assertThat(testLocationRegulation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createLocationRegulationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationRegulationRepository.findAll().size();

        // Create the LocationRegulation with an existing ID
        locationRegulation.setId(1L);
        LocationRegulationDTO locationRegulationDTO = locationRegulationMapper.toDto(locationRegulation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationRegulationMockMvc.perform(post("/api/location-regulations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationRegulationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationRegulation in the database
        List<LocationRegulation> locationRegulationList = locationRegulationRepository.findAll();
        assertThat(locationRegulationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocationRegulations() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList
        restLocationRegulationMockMvc.perform(get("/api/location-regulations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationRegulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].regulationDate").value(hasItem(DEFAULT_REGULATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getLocationRegulation() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get the locationRegulation
        restLocationRegulationMockMvc.perform(get("/api/location-regulations/{id}", locationRegulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationRegulation.getId().intValue()))
            .andExpect(jsonPath("$.regulationDate").value(DEFAULT_REGULATION_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }


    @Test
    @Transactional
    public void getLocationRegulationsByIdFiltering() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        Long id = locationRegulation.getId();

        defaultLocationRegulationShouldBeFound("id.equals=" + id);
        defaultLocationRegulationShouldNotBeFound("id.notEquals=" + id);

        defaultLocationRegulationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLocationRegulationShouldNotBeFound("id.greaterThan=" + id);

        defaultLocationRegulationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLocationRegulationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate equals to DEFAULT_REGULATION_DATE
        defaultLocationRegulationShouldBeFound("regulationDate.equals=" + DEFAULT_REGULATION_DATE);

        // Get all the locationRegulationList where regulationDate equals to UPDATED_REGULATION_DATE
        defaultLocationRegulationShouldNotBeFound("regulationDate.equals=" + UPDATED_REGULATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate not equals to DEFAULT_REGULATION_DATE
        defaultLocationRegulationShouldNotBeFound("regulationDate.notEquals=" + DEFAULT_REGULATION_DATE);

        // Get all the locationRegulationList where regulationDate not equals to UPDATED_REGULATION_DATE
        defaultLocationRegulationShouldBeFound("regulationDate.notEquals=" + UPDATED_REGULATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsInShouldWork() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate in DEFAULT_REGULATION_DATE or UPDATED_REGULATION_DATE
        defaultLocationRegulationShouldBeFound("regulationDate.in=" + DEFAULT_REGULATION_DATE + "," + UPDATED_REGULATION_DATE);

        // Get all the locationRegulationList where regulationDate equals to UPDATED_REGULATION_DATE
        defaultLocationRegulationShouldNotBeFound("regulationDate.in=" + UPDATED_REGULATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate is not null
        defaultLocationRegulationShouldBeFound("regulationDate.specified=true");

        // Get all the locationRegulationList where regulationDate is null
        defaultLocationRegulationShouldNotBeFound("regulationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate is greater than or equal to DEFAULT_REGULATION_DATE
        defaultLocationRegulationShouldBeFound("regulationDate.greaterThanOrEqual=" + DEFAULT_REGULATION_DATE);

        // Get all the locationRegulationList where regulationDate is greater than or equal to UPDATED_REGULATION_DATE
        defaultLocationRegulationShouldNotBeFound("regulationDate.greaterThanOrEqual=" + UPDATED_REGULATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate is less than or equal to DEFAULT_REGULATION_DATE
        defaultLocationRegulationShouldBeFound("regulationDate.lessThanOrEqual=" + DEFAULT_REGULATION_DATE);

        // Get all the locationRegulationList where regulationDate is less than or equal to SMALLER_REGULATION_DATE
        defaultLocationRegulationShouldNotBeFound("regulationDate.lessThanOrEqual=" + SMALLER_REGULATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate is less than DEFAULT_REGULATION_DATE
        defaultLocationRegulationShouldNotBeFound("regulationDate.lessThan=" + DEFAULT_REGULATION_DATE);

        // Get all the locationRegulationList where regulationDate is less than UPDATED_REGULATION_DATE
        defaultLocationRegulationShouldBeFound("regulationDate.lessThan=" + UPDATED_REGULATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByRegulationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where regulationDate is greater than DEFAULT_REGULATION_DATE
        defaultLocationRegulationShouldNotBeFound("regulationDate.greaterThan=" + DEFAULT_REGULATION_DATE);

        // Get all the locationRegulationList where regulationDate is greater than SMALLER_REGULATION_DATE
        defaultLocationRegulationShouldBeFound("regulationDate.greaterThan=" + SMALLER_REGULATION_DATE);
    }


    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount equals to DEFAULT_AMOUNT
        defaultLocationRegulationShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the locationRegulationList where amount equals to UPDATED_AMOUNT
        defaultLocationRegulationShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount not equals to DEFAULT_AMOUNT
        defaultLocationRegulationShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the locationRegulationList where amount not equals to UPDATED_AMOUNT
        defaultLocationRegulationShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultLocationRegulationShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the locationRegulationList where amount equals to UPDATED_AMOUNT
        defaultLocationRegulationShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount is not null
        defaultLocationRegulationShouldBeFound("amount.specified=true");

        // Get all the locationRegulationList where amount is null
        defaultLocationRegulationShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultLocationRegulationShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the locationRegulationList where amount is greater than or equal to UPDATED_AMOUNT
        defaultLocationRegulationShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount is less than or equal to DEFAULT_AMOUNT
        defaultLocationRegulationShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the locationRegulationList where amount is less than or equal to SMALLER_AMOUNT
        defaultLocationRegulationShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount is less than DEFAULT_AMOUNT
        defaultLocationRegulationShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the locationRegulationList where amount is less than UPDATED_AMOUNT
        defaultLocationRegulationShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLocationRegulationsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        // Get all the locationRegulationList where amount is greater than DEFAULT_AMOUNT
        defaultLocationRegulationShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the locationRegulationList where amount is greater than SMALLER_AMOUNT
        defaultLocationRegulationShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllLocationRegulationsByReglementTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);
        ReglementType reglementType = ReglementTypeResourceIT.createEntity(em);
        em.persist(reglementType);
        em.flush();
        locationRegulation.addReglementType(reglementType);
        locationRegulationRepository.saveAndFlush(locationRegulation);
        Long reglementTypeId = reglementType.getId();

        // Get all the locationRegulationList where reglementType equals to reglementTypeId
        defaultLocationRegulationShouldBeFound("reglementTypeId.equals=" + reglementTypeId);

        // Get all the locationRegulationList where reglementType equals to reglementTypeId + 1
        defaultLocationRegulationShouldNotBeFound("reglementTypeId.equals=" + (reglementTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationRegulationShouldBeFound(String filter) throws Exception {
        restLocationRegulationMockMvc.perform(get("/api/location-regulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationRegulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].regulationDate").value(hasItem(DEFAULT_REGULATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restLocationRegulationMockMvc.perform(get("/api/location-regulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationRegulationShouldNotBeFound(String filter) throws Exception {
        restLocationRegulationMockMvc.perform(get("/api/location-regulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationRegulationMockMvc.perform(get("/api/location-regulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLocationRegulation() throws Exception {
        // Get the locationRegulation
        restLocationRegulationMockMvc.perform(get("/api/location-regulations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationRegulation() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        int databaseSizeBeforeUpdate = locationRegulationRepository.findAll().size();

        // Update the locationRegulation
        LocationRegulation updatedLocationRegulation = locationRegulationRepository.findById(locationRegulation.getId()).get();
        // Disconnect from session so that the updates on updatedLocationRegulation are not directly saved in db
        em.detach(updatedLocationRegulation);
        updatedLocationRegulation
            .regulationDate(UPDATED_REGULATION_DATE)
            .amount(UPDATED_AMOUNT);
        LocationRegulationDTO locationRegulationDTO = locationRegulationMapper.toDto(updatedLocationRegulation);

        restLocationRegulationMockMvc.perform(put("/api/location-regulations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationRegulationDTO)))
            .andExpect(status().isOk());

        // Validate the LocationRegulation in the database
        List<LocationRegulation> locationRegulationList = locationRegulationRepository.findAll();
        assertThat(locationRegulationList).hasSize(databaseSizeBeforeUpdate);
        LocationRegulation testLocationRegulation = locationRegulationList.get(locationRegulationList.size() - 1);
        assertThat(testLocationRegulation.getRegulationDate()).isEqualTo(UPDATED_REGULATION_DATE);
        assertThat(testLocationRegulation.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationRegulation() throws Exception {
        int databaseSizeBeforeUpdate = locationRegulationRepository.findAll().size();

        // Create the LocationRegulation
        LocationRegulationDTO locationRegulationDTO = locationRegulationMapper.toDto(locationRegulation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationRegulationMockMvc.perform(put("/api/location-regulations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationRegulationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationRegulation in the database
        List<LocationRegulation> locationRegulationList = locationRegulationRepository.findAll();
        assertThat(locationRegulationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocationRegulation() throws Exception {
        // Initialize the database
        locationRegulationRepository.saveAndFlush(locationRegulation);

        int databaseSizeBeforeDelete = locationRegulationRepository.findAll().size();

        // Delete the locationRegulation
        restLocationRegulationMockMvc.perform(delete("/api/location-regulations/{id}", locationRegulation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationRegulation> locationRegulationList = locationRegulationRepository.findAll();
        assertThat(locationRegulationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
