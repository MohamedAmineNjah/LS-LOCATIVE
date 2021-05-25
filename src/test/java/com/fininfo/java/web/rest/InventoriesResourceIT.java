package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Inventories;
import com.fininfo.java.domain.LotBail;
import com.fininfo.java.repository.InventoriesRepository;
import com.fininfo.java.service.InventoriesService;
import com.fininfo.java.service.dto.InventoriesDTO;
import com.fininfo.java.service.mapper.InventoriesMapper;
import com.fininfo.java.service.dto.InventoriesCriteria;
import com.fininfo.java.service.InventoriesQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fininfo.java.domain.enumeration.IEnumInventory;
/**
 * Integration tests for the {@link InventoriesResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoriesResourceIT {

    private static final IEnumInventory DEFAULT_INVENTORIES_TYPE = IEnumInventory.ENTRE;
    private static final IEnumInventory UPDATED_INVENTORIES_TYPE = IEnumInventory.SORTIE;

    private static final LocalDate DEFAULT_INVENTORIES_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVENTORIES_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INVENTORIES_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_INVENTORIES_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_INVENTORIES_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_GENERAL_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_GENERAL_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Autowired
    private InventoriesMapper inventoriesMapper;

    @Autowired
    private InventoriesService inventoriesService;

    @Autowired
    private InventoriesQueryService inventoriesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoriesMockMvc;

    private Inventories inventories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventories createEntity(EntityManager em) {
        Inventories inventories = new Inventories()
            .inventoriesType(DEFAULT_INVENTORIES_TYPE)
            .inventoriesDate(DEFAULT_INVENTORIES_DATE)
            .inventoriesStatus(DEFAULT_INVENTORIES_STATUS)
            .generalObservation(DEFAULT_GENERAL_OBSERVATION);
        return inventories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventories createUpdatedEntity(EntityManager em) {
        Inventories inventories = new Inventories()
            .inventoriesType(UPDATED_INVENTORIES_TYPE)
            .inventoriesDate(UPDATED_INVENTORIES_DATE)
            .inventoriesStatus(UPDATED_INVENTORIES_STATUS)
            .generalObservation(UPDATED_GENERAL_OBSERVATION);
        return inventories;
    }

    @BeforeEach
    public void initTest() {
        inventories = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventories() throws Exception {
        int databaseSizeBeforeCreate = inventoriesRepository.findAll().size();
        // Create the Inventories
        InventoriesDTO inventoriesDTO = inventoriesMapper.toDto(inventories);
        restInventoriesMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventories in the database
        List<Inventories> inventoriesList = inventoriesRepository.findAll();
        assertThat(inventoriesList).hasSize(databaseSizeBeforeCreate + 1);
        Inventories testInventories = inventoriesList.get(inventoriesList.size() - 1);
        assertThat(testInventories.getInventoriesType()).isEqualTo(DEFAULT_INVENTORIES_TYPE);
        assertThat(testInventories.getInventoriesDate()).isEqualTo(DEFAULT_INVENTORIES_DATE);
        assertThat(testInventories.getInventoriesStatus()).isEqualTo(DEFAULT_INVENTORIES_STATUS);
        assertThat(testInventories.getGeneralObservation()).isEqualTo(DEFAULT_GENERAL_OBSERVATION);
    }

    @Test
    @Transactional
    public void createInventoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoriesRepository.findAll().size();

        // Create the Inventories with an existing ID
        inventories.setId(1L);
        InventoriesDTO inventoriesDTO = inventoriesMapper.toDto(inventories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoriesMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventories in the database
        List<Inventories> inventoriesList = inventoriesRepository.findAll();
        assertThat(inventoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventories() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList
        restInventoriesMockMvc.perform(get("/api/inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventories.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoriesType").value(hasItem(DEFAULT_INVENTORIES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].inventoriesDate").value(hasItem(DEFAULT_INVENTORIES_DATE.toString())))
            .andExpect(jsonPath("$.[*].inventoriesStatus").value(hasItem(DEFAULT_INVENTORIES_STATUS)))
            .andExpect(jsonPath("$.[*].generalObservation").value(hasItem(DEFAULT_GENERAL_OBSERVATION)));
    }
    
    @Test
    @Transactional
    public void getInventories() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get the inventories
        restInventoriesMockMvc.perform(get("/api/inventories/{id}", inventories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventories.getId().intValue()))
            .andExpect(jsonPath("$.inventoriesType").value(DEFAULT_INVENTORIES_TYPE.toString()))
            .andExpect(jsonPath("$.inventoriesDate").value(DEFAULT_INVENTORIES_DATE.toString()))
            .andExpect(jsonPath("$.inventoriesStatus").value(DEFAULT_INVENTORIES_STATUS))
            .andExpect(jsonPath("$.generalObservation").value(DEFAULT_GENERAL_OBSERVATION));
    }


    @Test
    @Transactional
    public void getInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        Long id = inventories.getId();

        defaultInventoriesShouldBeFound("id.equals=" + id);
        defaultInventoriesShouldNotBeFound("id.notEquals=" + id);

        defaultInventoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoriesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInventoriesByInventoriesTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesType equals to DEFAULT_INVENTORIES_TYPE
        defaultInventoriesShouldBeFound("inventoriesType.equals=" + DEFAULT_INVENTORIES_TYPE);

        // Get all the inventoriesList where inventoriesType equals to UPDATED_INVENTORIES_TYPE
        defaultInventoriesShouldNotBeFound("inventoriesType.equals=" + UPDATED_INVENTORIES_TYPE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesType not equals to DEFAULT_INVENTORIES_TYPE
        defaultInventoriesShouldNotBeFound("inventoriesType.notEquals=" + DEFAULT_INVENTORIES_TYPE);

        // Get all the inventoriesList where inventoriesType not equals to UPDATED_INVENTORIES_TYPE
        defaultInventoriesShouldBeFound("inventoriesType.notEquals=" + UPDATED_INVENTORIES_TYPE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesTypeIsInShouldWork() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesType in DEFAULT_INVENTORIES_TYPE or UPDATED_INVENTORIES_TYPE
        defaultInventoriesShouldBeFound("inventoriesType.in=" + DEFAULT_INVENTORIES_TYPE + "," + UPDATED_INVENTORIES_TYPE);

        // Get all the inventoriesList where inventoriesType equals to UPDATED_INVENTORIES_TYPE
        defaultInventoriesShouldNotBeFound("inventoriesType.in=" + UPDATED_INVENTORIES_TYPE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesType is not null
        defaultInventoriesShouldBeFound("inventoriesType.specified=true");

        // Get all the inventoriesList where inventoriesType is null
        defaultInventoriesShouldNotBeFound("inventoriesType.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate equals to DEFAULT_INVENTORIES_DATE
        defaultInventoriesShouldBeFound("inventoriesDate.equals=" + DEFAULT_INVENTORIES_DATE);

        // Get all the inventoriesList where inventoriesDate equals to UPDATED_INVENTORIES_DATE
        defaultInventoriesShouldNotBeFound("inventoriesDate.equals=" + UPDATED_INVENTORIES_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate not equals to DEFAULT_INVENTORIES_DATE
        defaultInventoriesShouldNotBeFound("inventoriesDate.notEquals=" + DEFAULT_INVENTORIES_DATE);

        // Get all the inventoriesList where inventoriesDate not equals to UPDATED_INVENTORIES_DATE
        defaultInventoriesShouldBeFound("inventoriesDate.notEquals=" + UPDATED_INVENTORIES_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate in DEFAULT_INVENTORIES_DATE or UPDATED_INVENTORIES_DATE
        defaultInventoriesShouldBeFound("inventoriesDate.in=" + DEFAULT_INVENTORIES_DATE + "," + UPDATED_INVENTORIES_DATE);

        // Get all the inventoriesList where inventoriesDate equals to UPDATED_INVENTORIES_DATE
        defaultInventoriesShouldNotBeFound("inventoriesDate.in=" + UPDATED_INVENTORIES_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate is not null
        defaultInventoriesShouldBeFound("inventoriesDate.specified=true");

        // Get all the inventoriesList where inventoriesDate is null
        defaultInventoriesShouldNotBeFound("inventoriesDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate is greater than or equal to DEFAULT_INVENTORIES_DATE
        defaultInventoriesShouldBeFound("inventoriesDate.greaterThanOrEqual=" + DEFAULT_INVENTORIES_DATE);

        // Get all the inventoriesList where inventoriesDate is greater than or equal to UPDATED_INVENTORIES_DATE
        defaultInventoriesShouldNotBeFound("inventoriesDate.greaterThanOrEqual=" + UPDATED_INVENTORIES_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate is less than or equal to DEFAULT_INVENTORIES_DATE
        defaultInventoriesShouldBeFound("inventoriesDate.lessThanOrEqual=" + DEFAULT_INVENTORIES_DATE);

        // Get all the inventoriesList where inventoriesDate is less than or equal to SMALLER_INVENTORIES_DATE
        defaultInventoriesShouldNotBeFound("inventoriesDate.lessThanOrEqual=" + SMALLER_INVENTORIES_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate is less than DEFAULT_INVENTORIES_DATE
        defaultInventoriesShouldNotBeFound("inventoriesDate.lessThan=" + DEFAULT_INVENTORIES_DATE);

        // Get all the inventoriesList where inventoriesDate is less than UPDATED_INVENTORIES_DATE
        defaultInventoriesShouldBeFound("inventoriesDate.lessThan=" + UPDATED_INVENTORIES_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesDate is greater than DEFAULT_INVENTORIES_DATE
        defaultInventoriesShouldNotBeFound("inventoriesDate.greaterThan=" + DEFAULT_INVENTORIES_DATE);

        // Get all the inventoriesList where inventoriesDate is greater than SMALLER_INVENTORIES_DATE
        defaultInventoriesShouldBeFound("inventoriesDate.greaterThan=" + SMALLER_INVENTORIES_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoriesByInventoriesStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesStatus equals to DEFAULT_INVENTORIES_STATUS
        defaultInventoriesShouldBeFound("inventoriesStatus.equals=" + DEFAULT_INVENTORIES_STATUS);

        // Get all the inventoriesList where inventoriesStatus equals to UPDATED_INVENTORIES_STATUS
        defaultInventoriesShouldNotBeFound("inventoriesStatus.equals=" + UPDATED_INVENTORIES_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesStatus not equals to DEFAULT_INVENTORIES_STATUS
        defaultInventoriesShouldNotBeFound("inventoriesStatus.notEquals=" + DEFAULT_INVENTORIES_STATUS);

        // Get all the inventoriesList where inventoriesStatus not equals to UPDATED_INVENTORIES_STATUS
        defaultInventoriesShouldBeFound("inventoriesStatus.notEquals=" + UPDATED_INVENTORIES_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesStatusIsInShouldWork() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesStatus in DEFAULT_INVENTORIES_STATUS or UPDATED_INVENTORIES_STATUS
        defaultInventoriesShouldBeFound("inventoriesStatus.in=" + DEFAULT_INVENTORIES_STATUS + "," + UPDATED_INVENTORIES_STATUS);

        // Get all the inventoriesList where inventoriesStatus equals to UPDATED_INVENTORIES_STATUS
        defaultInventoriesShouldNotBeFound("inventoriesStatus.in=" + UPDATED_INVENTORIES_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesStatus is not null
        defaultInventoriesShouldBeFound("inventoriesStatus.specified=true");

        // Get all the inventoriesList where inventoriesStatus is null
        defaultInventoriesShouldNotBeFound("inventoriesStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoriesByInventoriesStatusContainsSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesStatus contains DEFAULT_INVENTORIES_STATUS
        defaultInventoriesShouldBeFound("inventoriesStatus.contains=" + DEFAULT_INVENTORIES_STATUS);

        // Get all the inventoriesList where inventoriesStatus contains UPDATED_INVENTORIES_STATUS
        defaultInventoriesShouldNotBeFound("inventoriesStatus.contains=" + UPDATED_INVENTORIES_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoriesStatusNotContainsSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where inventoriesStatus does not contain DEFAULT_INVENTORIES_STATUS
        defaultInventoriesShouldNotBeFound("inventoriesStatus.doesNotContain=" + DEFAULT_INVENTORIES_STATUS);

        // Get all the inventoriesList where inventoriesStatus does not contain UPDATED_INVENTORIES_STATUS
        defaultInventoriesShouldBeFound("inventoriesStatus.doesNotContain=" + UPDATED_INVENTORIES_STATUS);
    }


    @Test
    @Transactional
    public void getAllInventoriesByGeneralObservationIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where generalObservation equals to DEFAULT_GENERAL_OBSERVATION
        defaultInventoriesShouldBeFound("generalObservation.equals=" + DEFAULT_GENERAL_OBSERVATION);

        // Get all the inventoriesList where generalObservation equals to UPDATED_GENERAL_OBSERVATION
        defaultInventoriesShouldNotBeFound("generalObservation.equals=" + UPDATED_GENERAL_OBSERVATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByGeneralObservationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where generalObservation not equals to DEFAULT_GENERAL_OBSERVATION
        defaultInventoriesShouldNotBeFound("generalObservation.notEquals=" + DEFAULT_GENERAL_OBSERVATION);

        // Get all the inventoriesList where generalObservation not equals to UPDATED_GENERAL_OBSERVATION
        defaultInventoriesShouldBeFound("generalObservation.notEquals=" + UPDATED_GENERAL_OBSERVATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByGeneralObservationIsInShouldWork() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where generalObservation in DEFAULT_GENERAL_OBSERVATION or UPDATED_GENERAL_OBSERVATION
        defaultInventoriesShouldBeFound("generalObservation.in=" + DEFAULT_GENERAL_OBSERVATION + "," + UPDATED_GENERAL_OBSERVATION);

        // Get all the inventoriesList where generalObservation equals to UPDATED_GENERAL_OBSERVATION
        defaultInventoriesShouldNotBeFound("generalObservation.in=" + UPDATED_GENERAL_OBSERVATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByGeneralObservationIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where generalObservation is not null
        defaultInventoriesShouldBeFound("generalObservation.specified=true");

        // Get all the inventoriesList where generalObservation is null
        defaultInventoriesShouldNotBeFound("generalObservation.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoriesByGeneralObservationContainsSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where generalObservation contains DEFAULT_GENERAL_OBSERVATION
        defaultInventoriesShouldBeFound("generalObservation.contains=" + DEFAULT_GENERAL_OBSERVATION);

        // Get all the inventoriesList where generalObservation contains UPDATED_GENERAL_OBSERVATION
        defaultInventoriesShouldNotBeFound("generalObservation.contains=" + UPDATED_GENERAL_OBSERVATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByGeneralObservationNotContainsSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        // Get all the inventoriesList where generalObservation does not contain DEFAULT_GENERAL_OBSERVATION
        defaultInventoriesShouldNotBeFound("generalObservation.doesNotContain=" + DEFAULT_GENERAL_OBSERVATION);

        // Get all the inventoriesList where generalObservation does not contain UPDATED_GENERAL_OBSERVATION
        defaultInventoriesShouldBeFound("generalObservation.doesNotContain=" + UPDATED_GENERAL_OBSERVATION);
    }


    @Test
    @Transactional
    public void getAllInventoriesByLotBailIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);
        LotBail lotBail = LotBailResourceIT.createEntity(em);
        em.persist(lotBail);
        em.flush();
        inventories.setLotBail(lotBail);
        inventoriesRepository.saveAndFlush(inventories);
        Long lotBailId = lotBail.getId();

        // Get all the inventoriesList where lotBail equals to lotBailId
        defaultInventoriesShouldBeFound("lotBailId.equals=" + lotBailId);

        // Get all the inventoriesList where lotBail equals to lotBailId + 1
        defaultInventoriesShouldNotBeFound("lotBailId.equals=" + (lotBailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoriesShouldBeFound(String filter) throws Exception {
        restInventoriesMockMvc.perform(get("/api/inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventories.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoriesType").value(hasItem(DEFAULT_INVENTORIES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].inventoriesDate").value(hasItem(DEFAULT_INVENTORIES_DATE.toString())))
            .andExpect(jsonPath("$.[*].inventoriesStatus").value(hasItem(DEFAULT_INVENTORIES_STATUS)))
            .andExpect(jsonPath("$.[*].generalObservation").value(hasItem(DEFAULT_GENERAL_OBSERVATION)));

        // Check, that the count call also returns 1
        restInventoriesMockMvc.perform(get("/api/inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoriesShouldNotBeFound(String filter) throws Exception {
        restInventoriesMockMvc.perform(get("/api/inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoriesMockMvc.perform(get("/api/inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventories() throws Exception {
        // Get the inventories
        restInventoriesMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventories() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        int databaseSizeBeforeUpdate = inventoriesRepository.findAll().size();

        // Update the inventories
        Inventories updatedInventories = inventoriesRepository.findById(inventories.getId()).get();
        // Disconnect from session so that the updates on updatedInventories are not directly saved in db
        em.detach(updatedInventories);
        updatedInventories
            .inventoriesType(UPDATED_INVENTORIES_TYPE)
            .inventoriesDate(UPDATED_INVENTORIES_DATE)
            .inventoriesStatus(UPDATED_INVENTORIES_STATUS)
            .generalObservation(UPDATED_GENERAL_OBSERVATION);
        InventoriesDTO inventoriesDTO = inventoriesMapper.toDto(updatedInventories);

        restInventoriesMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoriesDTO)))
            .andExpect(status().isOk());

        // Validate the Inventories in the database
        List<Inventories> inventoriesList = inventoriesRepository.findAll();
        assertThat(inventoriesList).hasSize(databaseSizeBeforeUpdate);
        Inventories testInventories = inventoriesList.get(inventoriesList.size() - 1);
        assertThat(testInventories.getInventoriesType()).isEqualTo(UPDATED_INVENTORIES_TYPE);
        assertThat(testInventories.getInventoriesDate()).isEqualTo(UPDATED_INVENTORIES_DATE);
        assertThat(testInventories.getInventoriesStatus()).isEqualTo(UPDATED_INVENTORIES_STATUS);
        assertThat(testInventories.getGeneralObservation()).isEqualTo(UPDATED_GENERAL_OBSERVATION);
    }

    @Test
    @Transactional
    public void updateNonExistingInventories() throws Exception {
        int databaseSizeBeforeUpdate = inventoriesRepository.findAll().size();

        // Create the Inventories
        InventoriesDTO inventoriesDTO = inventoriesMapper.toDto(inventories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoriesMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventories in the database
        List<Inventories> inventoriesList = inventoriesRepository.findAll();
        assertThat(inventoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventories() throws Exception {
        // Initialize the database
        inventoriesRepository.saveAndFlush(inventories);

        int databaseSizeBeforeDelete = inventoriesRepository.findAll().size();

        // Delete the inventories
        restInventoriesMockMvc.perform(delete("/api/inventories/{id}", inventories.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventories> inventoriesList = inventoriesRepository.findAll();
        assertThat(inventoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
