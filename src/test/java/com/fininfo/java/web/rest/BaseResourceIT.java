package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Base;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.BaseRepository;
import com.fininfo.java.service.BaseService;
import com.fininfo.java.service.dto.BaseDTO;
import com.fininfo.java.service.mapper.BaseMapper;
import com.fininfo.java.service.dto.BaseCriteria;
import com.fininfo.java.service.BaseQueryService;

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
 * Integration tests for the {@link BaseResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BaseResourceIT {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private BaseMapper baseMapper;

    @Autowired
    private BaseService baseService;

    @Autowired
    private BaseQueryService baseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBaseMockMvc;

    private Base base;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Base createEntity(EntityManager em) {
        Base base = new Base();
        return base;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Base createUpdatedEntity(EntityManager em) {
        Base base = new Base();
        return base;
    }

    @BeforeEach
    public void initTest() {
        base = createEntity(em);
    }

    @Test
    @Transactional
    public void createBase() throws Exception {
        int databaseSizeBeforeCreate = baseRepository.findAll().size();
        // Create the Base
        BaseDTO baseDTO = baseMapper.toDto(base);
        restBaseMockMvc.perform(post("/api/bases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(baseDTO)))
            .andExpect(status().isCreated());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeCreate + 1);
        Base testBase = baseList.get(baseList.size() - 1);
    }

    @Test
    @Transactional
    public void createBaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseRepository.findAll().size();

        // Create the Base with an existing ID
        base.setId(1L);
        BaseDTO baseDTO = baseMapper.toDto(base);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseMockMvc.perform(post("/api/bases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(baseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBases() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList
        restBaseMockMvc.perform(get("/api/bases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(base.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBase() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get the base
        restBaseMockMvc.perform(get("/api/bases/{id}", base.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(base.getId().intValue()));
    }


    @Test
    @Transactional
    public void getBasesByIdFiltering() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        Long id = base.getId();

        defaultBaseShouldBeFound("id.equals=" + id);
        defaultBaseShouldNotBeFound("id.notEquals=" + id);

        defaultBaseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBaseShouldNotBeFound("id.greaterThan=" + id);

        defaultBaseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBaseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBasesByRentIsEqualToSomething() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);
        Rent rent = RentResourceIT.createEntity(em);
        em.persist(rent);
        em.flush();
        base.setRent(rent);
        baseRepository.saveAndFlush(base);
        Long rentId = rent.getId();

        // Get all the baseList where rent equals to rentId
        defaultBaseShouldBeFound("rentId.equals=" + rentId);

        // Get all the baseList where rent equals to rentId + 1
        defaultBaseShouldNotBeFound("rentId.equals=" + (rentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBaseShouldBeFound(String filter) throws Exception {
        restBaseMockMvc.perform(get("/api/bases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(base.getId().intValue())));

        // Check, that the count call also returns 1
        restBaseMockMvc.perform(get("/api/bases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBaseShouldNotBeFound(String filter) throws Exception {
        restBaseMockMvc.perform(get("/api/bases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBaseMockMvc.perform(get("/api/bases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBase() throws Exception {
        // Get the base
        restBaseMockMvc.perform(get("/api/bases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBase() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        int databaseSizeBeforeUpdate = baseRepository.findAll().size();

        // Update the base
        Base updatedBase = baseRepository.findById(base.getId()).get();
        // Disconnect from session so that the updates on updatedBase are not directly saved in db
        em.detach(updatedBase);
        BaseDTO baseDTO = baseMapper.toDto(updatedBase);

        restBaseMockMvc.perform(put("/api/bases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(baseDTO)))
            .andExpect(status().isOk());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeUpdate);
        Base testBase = baseList.get(baseList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBase() throws Exception {
        int databaseSizeBeforeUpdate = baseRepository.findAll().size();

        // Create the Base
        BaseDTO baseDTO = baseMapper.toDto(base);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseMockMvc.perform(put("/api/bases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(baseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBase() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        int databaseSizeBeforeDelete = baseRepository.findAll().size();

        // Delete the base
        restBaseMockMvc.perform(delete("/api/bases/{id}", base.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
