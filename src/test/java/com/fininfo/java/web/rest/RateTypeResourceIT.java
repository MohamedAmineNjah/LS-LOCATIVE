package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.RateType;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.RateTypeRepository;
import com.fininfo.java.service.RateTypeService;
import com.fininfo.java.service.dto.RateTypeDTO;
import com.fininfo.java.service.mapper.RateTypeMapper;
import com.fininfo.java.service.dto.RateTypeCriteria;
import com.fininfo.java.service.RateTypeQueryService;

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
 * Integration tests for the {@link RateTypeResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RateTypeResourceIT {

    @Autowired
    private RateTypeRepository rateTypeRepository;

    @Autowired
    private RateTypeMapper rateTypeMapper;

    @Autowired
    private RateTypeService rateTypeService;

    @Autowired
    private RateTypeQueryService rateTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRateTypeMockMvc;

    private RateType rateType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateType createEntity(EntityManager em) {
        RateType rateType = new RateType();
        return rateType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateType createUpdatedEntity(EntityManager em) {
        RateType rateType = new RateType();
        return rateType;
    }

    @BeforeEach
    public void initTest() {
        rateType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateType() throws Exception {
        int databaseSizeBeforeCreate = rateTypeRepository.findAll().size();
        // Create the RateType
        RateTypeDTO rateTypeDTO = rateTypeMapper.toDto(rateType);
        restRateTypeMockMvc.perform(post("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rateTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RateType testRateType = rateTypeList.get(rateTypeList.size() - 1);
    }

    @Test
    @Transactional
    public void createRateTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateTypeRepository.findAll().size();

        // Create the RateType with an existing ID
        rateType.setId(1L);
        RateTypeDTO rateTypeDTO = rateTypeMapper.toDto(rateType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateTypeMockMvc.perform(post("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rateTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRateTypes() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        // Get all the rateTypeList
        restRateTypeMockMvc.perform(get("/api/rate-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateType.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getRateType() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        // Get the rateType
        restRateTypeMockMvc.perform(get("/api/rate-types/{id}", rateType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rateType.getId().intValue()));
    }


    @Test
    @Transactional
    public void getRateTypesByIdFiltering() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        Long id = rateType.getId();

        defaultRateTypeShouldBeFound("id.equals=" + id);
        defaultRateTypeShouldNotBeFound("id.notEquals=" + id);

        defaultRateTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRateTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultRateTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRateTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRateTypesByRentIsEqualToSomething() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);
        Rent rent = RentResourceIT.createEntity(em);
        em.persist(rent);
        em.flush();
        rateType.setRent(rent);
        rateTypeRepository.saveAndFlush(rateType);
        Long rentId = rent.getId();

        // Get all the rateTypeList where rent equals to rentId
        defaultRateTypeShouldBeFound("rentId.equals=" + rentId);

        // Get all the rateTypeList where rent equals to rentId + 1
        defaultRateTypeShouldNotBeFound("rentId.equals=" + (rentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRateTypeShouldBeFound(String filter) throws Exception {
        restRateTypeMockMvc.perform(get("/api/rate-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateType.getId().intValue())));

        // Check, that the count call also returns 1
        restRateTypeMockMvc.perform(get("/api/rate-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRateTypeShouldNotBeFound(String filter) throws Exception {
        restRateTypeMockMvc.perform(get("/api/rate-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRateTypeMockMvc.perform(get("/api/rate-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRateType() throws Exception {
        // Get the rateType
        restRateTypeMockMvc.perform(get("/api/rate-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateType() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        int databaseSizeBeforeUpdate = rateTypeRepository.findAll().size();

        // Update the rateType
        RateType updatedRateType = rateTypeRepository.findById(rateType.getId()).get();
        // Disconnect from session so that the updates on updatedRateType are not directly saved in db
        em.detach(updatedRateType);
        RateTypeDTO rateTypeDTO = rateTypeMapper.toDto(updatedRateType);

        restRateTypeMockMvc.perform(put("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rateTypeDTO)))
            .andExpect(status().isOk());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeUpdate);
        RateType testRateType = rateTypeList.get(rateTypeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRateType() throws Exception {
        int databaseSizeBeforeUpdate = rateTypeRepository.findAll().size();

        // Create the RateType
        RateTypeDTO rateTypeDTO = rateTypeMapper.toDto(rateType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRateTypeMockMvc.perform(put("/api/rate-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rateTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RateType in the database
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRateType() throws Exception {
        // Initialize the database
        rateTypeRepository.saveAndFlush(rateType);

        int databaseSizeBeforeDelete = rateTypeRepository.findAll().size();

        // Delete the rateType
        restRateTypeMockMvc.perform(delete("/api/rate-types/{id}", rateType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RateType> rateTypeList = rateTypeRepository.findAll();
        assertThat(rateTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
