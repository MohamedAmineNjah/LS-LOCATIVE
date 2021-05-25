package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.RefundFrequency;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.RefundFrequencyRepository;
import com.fininfo.java.service.RefundFrequencyService;
import com.fininfo.java.service.dto.RefundFrequencyDTO;
import com.fininfo.java.service.mapper.RefundFrequencyMapper;
import com.fininfo.java.service.dto.RefundFrequencyCriteria;
import com.fininfo.java.service.RefundFrequencyQueryService;

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
 * Integration tests for the {@link RefundFrequencyResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RefundFrequencyResourceIT {

    @Autowired
    private RefundFrequencyRepository refundFrequencyRepository;

    @Autowired
    private RefundFrequencyMapper refundFrequencyMapper;

    @Autowired
    private RefundFrequencyService refundFrequencyService;

    @Autowired
    private RefundFrequencyQueryService refundFrequencyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefundFrequencyMockMvc;

    private RefundFrequency refundFrequency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefundFrequency createEntity(EntityManager em) {
        RefundFrequency refundFrequency = new RefundFrequency();
        return refundFrequency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefundFrequency createUpdatedEntity(EntityManager em) {
        RefundFrequency refundFrequency = new RefundFrequency();
        return refundFrequency;
    }

    @BeforeEach
    public void initTest() {
        refundFrequency = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefundFrequency() throws Exception {
        int databaseSizeBeforeCreate = refundFrequencyRepository.findAll().size();
        // Create the RefundFrequency
        RefundFrequencyDTO refundFrequencyDTO = refundFrequencyMapper.toDto(refundFrequency);
        restRefundFrequencyMockMvc.perform(post("/api/refund-frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundFrequencyDTO)))
            .andExpect(status().isCreated());

        // Validate the RefundFrequency in the database
        List<RefundFrequency> refundFrequencyList = refundFrequencyRepository.findAll();
        assertThat(refundFrequencyList).hasSize(databaseSizeBeforeCreate + 1);
        RefundFrequency testRefundFrequency = refundFrequencyList.get(refundFrequencyList.size() - 1);
    }

    @Test
    @Transactional
    public void createRefundFrequencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refundFrequencyRepository.findAll().size();

        // Create the RefundFrequency with an existing ID
        refundFrequency.setId(1L);
        RefundFrequencyDTO refundFrequencyDTO = refundFrequencyMapper.toDto(refundFrequency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundFrequencyMockMvc.perform(post("/api/refund-frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundFrequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefundFrequency in the database
        List<RefundFrequency> refundFrequencyList = refundFrequencyRepository.findAll();
        assertThat(refundFrequencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRefundFrequencies() throws Exception {
        // Initialize the database
        refundFrequencyRepository.saveAndFlush(refundFrequency);

        // Get all the refundFrequencyList
        restRefundFrequencyMockMvc.perform(get("/api/refund-frequencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refundFrequency.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getRefundFrequency() throws Exception {
        // Initialize the database
        refundFrequencyRepository.saveAndFlush(refundFrequency);

        // Get the refundFrequency
        restRefundFrequencyMockMvc.perform(get("/api/refund-frequencies/{id}", refundFrequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refundFrequency.getId().intValue()));
    }


    @Test
    @Transactional
    public void getRefundFrequenciesByIdFiltering() throws Exception {
        // Initialize the database
        refundFrequencyRepository.saveAndFlush(refundFrequency);

        Long id = refundFrequency.getId();

        defaultRefundFrequencyShouldBeFound("id.equals=" + id);
        defaultRefundFrequencyShouldNotBeFound("id.notEquals=" + id);

        defaultRefundFrequencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRefundFrequencyShouldNotBeFound("id.greaterThan=" + id);

        defaultRefundFrequencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRefundFrequencyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRefundFrequenciesByRentIsEqualToSomething() throws Exception {
        // Initialize the database
        refundFrequencyRepository.saveAndFlush(refundFrequency);
        Rent rent = RentResourceIT.createEntity(em);
        em.persist(rent);
        em.flush();
        refundFrequency.setRent(rent);
        refundFrequencyRepository.saveAndFlush(refundFrequency);
        Long rentId = rent.getId();

        // Get all the refundFrequencyList where rent equals to rentId
        defaultRefundFrequencyShouldBeFound("rentId.equals=" + rentId);

        // Get all the refundFrequencyList where rent equals to rentId + 1
        defaultRefundFrequencyShouldNotBeFound("rentId.equals=" + (rentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRefundFrequencyShouldBeFound(String filter) throws Exception {
        restRefundFrequencyMockMvc.perform(get("/api/refund-frequencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refundFrequency.getId().intValue())));

        // Check, that the count call also returns 1
        restRefundFrequencyMockMvc.perform(get("/api/refund-frequencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRefundFrequencyShouldNotBeFound(String filter) throws Exception {
        restRefundFrequencyMockMvc.perform(get("/api/refund-frequencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRefundFrequencyMockMvc.perform(get("/api/refund-frequencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRefundFrequency() throws Exception {
        // Get the refundFrequency
        restRefundFrequencyMockMvc.perform(get("/api/refund-frequencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefundFrequency() throws Exception {
        // Initialize the database
        refundFrequencyRepository.saveAndFlush(refundFrequency);

        int databaseSizeBeforeUpdate = refundFrequencyRepository.findAll().size();

        // Update the refundFrequency
        RefundFrequency updatedRefundFrequency = refundFrequencyRepository.findById(refundFrequency.getId()).get();
        // Disconnect from session so that the updates on updatedRefundFrequency are not directly saved in db
        em.detach(updatedRefundFrequency);
        RefundFrequencyDTO refundFrequencyDTO = refundFrequencyMapper.toDto(updatedRefundFrequency);

        restRefundFrequencyMockMvc.perform(put("/api/refund-frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundFrequencyDTO)))
            .andExpect(status().isOk());

        // Validate the RefundFrequency in the database
        List<RefundFrequency> refundFrequencyList = refundFrequencyRepository.findAll();
        assertThat(refundFrequencyList).hasSize(databaseSizeBeforeUpdate);
        RefundFrequency testRefundFrequency = refundFrequencyList.get(refundFrequencyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRefundFrequency() throws Exception {
        int databaseSizeBeforeUpdate = refundFrequencyRepository.findAll().size();

        // Create the RefundFrequency
        RefundFrequencyDTO refundFrequencyDTO = refundFrequencyMapper.toDto(refundFrequency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundFrequencyMockMvc.perform(put("/api/refund-frequencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundFrequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefundFrequency in the database
        List<RefundFrequency> refundFrequencyList = refundFrequencyRepository.findAll();
        assertThat(refundFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRefundFrequency() throws Exception {
        // Initialize the database
        refundFrequencyRepository.saveAndFlush(refundFrequency);

        int databaseSizeBeforeDelete = refundFrequencyRepository.findAll().size();

        // Delete the refundFrequency
        restRefundFrequencyMockMvc.perform(delete("/api/refund-frequencies/{id}", refundFrequency.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefundFrequency> refundFrequencyList = refundFrequencyRepository.findAll();
        assertThat(refundFrequencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
