package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.ReglementType;
import com.fininfo.java.domain.LocationRegulation;
import com.fininfo.java.repository.ReglementTypeRepository;
import com.fininfo.java.service.ReglementTypeService;
import com.fininfo.java.service.dto.ReglementTypeDTO;
import com.fininfo.java.service.mapper.ReglementTypeMapper;
import com.fininfo.java.service.dto.ReglementTypeCriteria;
import com.fininfo.java.service.ReglementTypeQueryService;

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
 * Integration tests for the {@link ReglementTypeResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReglementTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReglementTypeRepository reglementTypeRepository;

    @Autowired
    private ReglementTypeMapper reglementTypeMapper;

    @Autowired
    private ReglementTypeService reglementTypeService;

    @Autowired
    private ReglementTypeQueryService reglementTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReglementTypeMockMvc;

    private ReglementType reglementType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReglementType createEntity(EntityManager em) {
        ReglementType reglementType = new ReglementType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return reglementType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReglementType createUpdatedEntity(EntityManager em) {
        ReglementType reglementType = new ReglementType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return reglementType;
    }

    @BeforeEach
    public void initTest() {
        reglementType = createEntity(em);
    }

    @Test
    @Transactional
    public void createReglementType() throws Exception {
        int databaseSizeBeforeCreate = reglementTypeRepository.findAll().size();
        // Create the ReglementType
        ReglementTypeDTO reglementTypeDTO = reglementTypeMapper.toDto(reglementType);
        restReglementTypeMockMvc.perform(post("/api/reglement-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ReglementType in the database
        List<ReglementType> reglementTypeList = reglementTypeRepository.findAll();
        assertThat(reglementTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ReglementType testReglementType = reglementTypeList.get(reglementTypeList.size() - 1);
        assertThat(testReglementType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReglementType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReglementTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reglementTypeRepository.findAll().size();

        // Create the ReglementType with an existing ID
        reglementType.setId(1L);
        ReglementTypeDTO reglementTypeDTO = reglementTypeMapper.toDto(reglementType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReglementTypeMockMvc.perform(post("/api/reglement-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReglementType in the database
        List<ReglementType> reglementTypeList = reglementTypeRepository.findAll();
        assertThat(reglementTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReglementTypes() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList
        restReglementTypeMockMvc.perform(get("/api/reglement-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reglementType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getReglementType() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get the reglementType
        restReglementTypeMockMvc.perform(get("/api/reglement-types/{id}", reglementType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reglementType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getReglementTypesByIdFiltering() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        Long id = reglementType.getId();

        defaultReglementTypeShouldBeFound("id.equals=" + id);
        defaultReglementTypeShouldNotBeFound("id.notEquals=" + id);

        defaultReglementTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReglementTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultReglementTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReglementTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReglementTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where name equals to DEFAULT_NAME
        defaultReglementTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the reglementTypeList where name equals to UPDATED_NAME
        defaultReglementTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where name not equals to DEFAULT_NAME
        defaultReglementTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the reglementTypeList where name not equals to UPDATED_NAME
        defaultReglementTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReglementTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the reglementTypeList where name equals to UPDATED_NAME
        defaultReglementTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where name is not null
        defaultReglementTypeShouldBeFound("name.specified=true");

        // Get all the reglementTypeList where name is null
        defaultReglementTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllReglementTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where name contains DEFAULT_NAME
        defaultReglementTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the reglementTypeList where name contains UPDATED_NAME
        defaultReglementTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where name does not contain DEFAULT_NAME
        defaultReglementTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the reglementTypeList where name does not contain UPDATED_NAME
        defaultReglementTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllReglementTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where description equals to DEFAULT_DESCRIPTION
        defaultReglementTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the reglementTypeList where description equals to UPDATED_DESCRIPTION
        defaultReglementTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultReglementTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the reglementTypeList where description not equals to UPDATED_DESCRIPTION
        defaultReglementTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReglementTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the reglementTypeList where description equals to UPDATED_DESCRIPTION
        defaultReglementTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where description is not null
        defaultReglementTypeShouldBeFound("description.specified=true");

        // Get all the reglementTypeList where description is null
        defaultReglementTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllReglementTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where description contains DEFAULT_DESCRIPTION
        defaultReglementTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the reglementTypeList where description contains UPDATED_DESCRIPTION
        defaultReglementTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReglementTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        // Get all the reglementTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultReglementTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the reglementTypeList where description does not contain UPDATED_DESCRIPTION
        defaultReglementTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllReglementTypesByLocationRegulationIsEqualToSomething() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);
        LocationRegulation locationRegulation = LocationRegulationResourceIT.createEntity(em);
        em.persist(locationRegulation);
        em.flush();
        reglementType.setLocationRegulation(locationRegulation);
        reglementTypeRepository.saveAndFlush(reglementType);
        Long locationRegulationId = locationRegulation.getId();

        // Get all the reglementTypeList where locationRegulation equals to locationRegulationId
        defaultReglementTypeShouldBeFound("locationRegulationId.equals=" + locationRegulationId);

        // Get all the reglementTypeList where locationRegulation equals to locationRegulationId + 1
        defaultReglementTypeShouldNotBeFound("locationRegulationId.equals=" + (locationRegulationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReglementTypeShouldBeFound(String filter) throws Exception {
        restReglementTypeMockMvc.perform(get("/api/reglement-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reglementType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restReglementTypeMockMvc.perform(get("/api/reglement-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReglementTypeShouldNotBeFound(String filter) throws Exception {
        restReglementTypeMockMvc.perform(get("/api/reglement-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReglementTypeMockMvc.perform(get("/api/reglement-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingReglementType() throws Exception {
        // Get the reglementType
        restReglementTypeMockMvc.perform(get("/api/reglement-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReglementType() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        int databaseSizeBeforeUpdate = reglementTypeRepository.findAll().size();

        // Update the reglementType
        ReglementType updatedReglementType = reglementTypeRepository.findById(reglementType.getId()).get();
        // Disconnect from session so that the updates on updatedReglementType are not directly saved in db
        em.detach(updatedReglementType);
        updatedReglementType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ReglementTypeDTO reglementTypeDTO = reglementTypeMapper.toDto(updatedReglementType);

        restReglementTypeMockMvc.perform(put("/api/reglement-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ReglementType in the database
        List<ReglementType> reglementTypeList = reglementTypeRepository.findAll();
        assertThat(reglementTypeList).hasSize(databaseSizeBeforeUpdate);
        ReglementType testReglementType = reglementTypeList.get(reglementTypeList.size() - 1);
        assertThat(testReglementType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReglementType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReglementType() throws Exception {
        int databaseSizeBeforeUpdate = reglementTypeRepository.findAll().size();

        // Create the ReglementType
        ReglementTypeDTO reglementTypeDTO = reglementTypeMapper.toDto(reglementType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReglementTypeMockMvc.perform(put("/api/reglement-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reglementTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReglementType in the database
        List<ReglementType> reglementTypeList = reglementTypeRepository.findAll();
        assertThat(reglementTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReglementType() throws Exception {
        // Initialize the database
        reglementTypeRepository.saveAndFlush(reglementType);

        int databaseSizeBeforeDelete = reglementTypeRepository.findAll().size();

        // Delete the reglementType
        restReglementTypeMockMvc.perform(delete("/api/reglement-types/{id}", reglementType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReglementType> reglementTypeList = reglementTypeRepository.findAll();
        assertThat(reglementTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
