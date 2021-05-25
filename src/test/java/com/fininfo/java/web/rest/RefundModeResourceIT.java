package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.RefundMode;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.RefundModeRepository;
import com.fininfo.java.service.RefundModeService;
import com.fininfo.java.service.dto.RefundModeDTO;
import com.fininfo.java.service.mapper.RefundModeMapper;
import com.fininfo.java.service.dto.RefundModeCriteria;
import com.fininfo.java.service.RefundModeQueryService;

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
 * Integration tests for the {@link RefundModeResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RefundModeResourceIT {

    @Autowired
    private RefundModeRepository refundModeRepository;

    @Autowired
    private RefundModeMapper refundModeMapper;

    @Autowired
    private RefundModeService refundModeService;

    @Autowired
    private RefundModeQueryService refundModeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefundModeMockMvc;

    private RefundMode refundMode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefundMode createEntity(EntityManager em) {
        RefundMode refundMode = new RefundMode();
        return refundMode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefundMode createUpdatedEntity(EntityManager em) {
        RefundMode refundMode = new RefundMode();
        return refundMode;
    }

    @BeforeEach
    public void initTest() {
        refundMode = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefundMode() throws Exception {
        int databaseSizeBeforeCreate = refundModeRepository.findAll().size();
        // Create the RefundMode
        RefundModeDTO refundModeDTO = refundModeMapper.toDto(refundMode);
        restRefundModeMockMvc.perform(post("/api/refund-modes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundModeDTO)))
            .andExpect(status().isCreated());

        // Validate the RefundMode in the database
        List<RefundMode> refundModeList = refundModeRepository.findAll();
        assertThat(refundModeList).hasSize(databaseSizeBeforeCreate + 1);
        RefundMode testRefundMode = refundModeList.get(refundModeList.size() - 1);
    }

    @Test
    @Transactional
    public void createRefundModeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refundModeRepository.findAll().size();

        // Create the RefundMode with an existing ID
        refundMode.setId(1L);
        RefundModeDTO refundModeDTO = refundModeMapper.toDto(refundMode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundModeMockMvc.perform(post("/api/refund-modes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundModeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefundMode in the database
        List<RefundMode> refundModeList = refundModeRepository.findAll();
        assertThat(refundModeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRefundModes() throws Exception {
        // Initialize the database
        refundModeRepository.saveAndFlush(refundMode);

        // Get all the refundModeList
        restRefundModeMockMvc.perform(get("/api/refund-modes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refundMode.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getRefundMode() throws Exception {
        // Initialize the database
        refundModeRepository.saveAndFlush(refundMode);

        // Get the refundMode
        restRefundModeMockMvc.perform(get("/api/refund-modes/{id}", refundMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refundMode.getId().intValue()));
    }


    @Test
    @Transactional
    public void getRefundModesByIdFiltering() throws Exception {
        // Initialize the database
        refundModeRepository.saveAndFlush(refundMode);

        Long id = refundMode.getId();

        defaultRefundModeShouldBeFound("id.equals=" + id);
        defaultRefundModeShouldNotBeFound("id.notEquals=" + id);

        defaultRefundModeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRefundModeShouldNotBeFound("id.greaterThan=" + id);

        defaultRefundModeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRefundModeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRefundModesByRentIsEqualToSomething() throws Exception {
        // Initialize the database
        refundModeRepository.saveAndFlush(refundMode);
        Rent rent = RentResourceIT.createEntity(em);
        em.persist(rent);
        em.flush();
        refundMode.setRent(rent);
        refundModeRepository.saveAndFlush(refundMode);
        Long rentId = rent.getId();

        // Get all the refundModeList where rent equals to rentId
        defaultRefundModeShouldBeFound("rentId.equals=" + rentId);

        // Get all the refundModeList where rent equals to rentId + 1
        defaultRefundModeShouldNotBeFound("rentId.equals=" + (rentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRefundModeShouldBeFound(String filter) throws Exception {
        restRefundModeMockMvc.perform(get("/api/refund-modes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refundMode.getId().intValue())));

        // Check, that the count call also returns 1
        restRefundModeMockMvc.perform(get("/api/refund-modes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRefundModeShouldNotBeFound(String filter) throws Exception {
        restRefundModeMockMvc.perform(get("/api/refund-modes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRefundModeMockMvc.perform(get("/api/refund-modes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRefundMode() throws Exception {
        // Get the refundMode
        restRefundModeMockMvc.perform(get("/api/refund-modes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefundMode() throws Exception {
        // Initialize the database
        refundModeRepository.saveAndFlush(refundMode);

        int databaseSizeBeforeUpdate = refundModeRepository.findAll().size();

        // Update the refundMode
        RefundMode updatedRefundMode = refundModeRepository.findById(refundMode.getId()).get();
        // Disconnect from session so that the updates on updatedRefundMode are not directly saved in db
        em.detach(updatedRefundMode);
        RefundModeDTO refundModeDTO = refundModeMapper.toDto(updatedRefundMode);

        restRefundModeMockMvc.perform(put("/api/refund-modes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundModeDTO)))
            .andExpect(status().isOk());

        // Validate the RefundMode in the database
        List<RefundMode> refundModeList = refundModeRepository.findAll();
        assertThat(refundModeList).hasSize(databaseSizeBeforeUpdate);
        RefundMode testRefundMode = refundModeList.get(refundModeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRefundMode() throws Exception {
        int databaseSizeBeforeUpdate = refundModeRepository.findAll().size();

        // Create the RefundMode
        RefundModeDTO refundModeDTO = refundModeMapper.toDto(refundMode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundModeMockMvc.perform(put("/api/refund-modes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refundModeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefundMode in the database
        List<RefundMode> refundModeList = refundModeRepository.findAll();
        assertThat(refundModeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRefundMode() throws Exception {
        // Initialize the database
        refundModeRepository.saveAndFlush(refundMode);

        int databaseSizeBeforeDelete = refundModeRepository.findAll().size();

        // Delete the refundMode
        restRefundModeMockMvc.perform(delete("/api/refund-modes/{id}", refundMode.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefundMode> refundModeList = refundModeRepository.findAll();
        assertThat(refundModeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
