package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.LeaveProcess;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.LeaveProcessRepository;
import com.fininfo.java.service.LeaveProcessService;
import com.fininfo.java.service.dto.LeaveProcessDTO;
import com.fininfo.java.service.mapper.LeaveProcessMapper;
import com.fininfo.java.service.dto.LeaveProcessCriteria;
import com.fininfo.java.service.LeaveProcessQueryService;

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
 * Integration tests for the {@link LeaveProcessResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeaveProcessResourceIT {

    @Autowired
    private LeaveProcessRepository leaveProcessRepository;

    @Autowired
    private LeaveProcessMapper leaveProcessMapper;

    @Autowired
    private LeaveProcessService leaveProcessService;

    @Autowired
    private LeaveProcessQueryService leaveProcessQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveProcessMockMvc;

    private LeaveProcess leaveProcess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveProcess createEntity(EntityManager em) {
        LeaveProcess leaveProcess = new LeaveProcess();
        return leaveProcess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveProcess createUpdatedEntity(EntityManager em) {
        LeaveProcess leaveProcess = new LeaveProcess();
        return leaveProcess;
    }

    @BeforeEach
    public void initTest() {
        leaveProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveProcess() throws Exception {
        int databaseSizeBeforeCreate = leaveProcessRepository.findAll().size();
        // Create the LeaveProcess
        LeaveProcessDTO leaveProcessDTO = leaveProcessMapper.toDto(leaveProcess);
        restLeaveProcessMockMvc.perform(post("/api/leave-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveProcess in the database
        List<LeaveProcess> leaveProcessList = leaveProcessRepository.findAll();
        assertThat(leaveProcessList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveProcess testLeaveProcess = leaveProcessList.get(leaveProcessList.size() - 1);
    }

    @Test
    @Transactional
    public void createLeaveProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveProcessRepository.findAll().size();

        // Create the LeaveProcess with an existing ID
        leaveProcess.setId(1L);
        LeaveProcessDTO leaveProcessDTO = leaveProcessMapper.toDto(leaveProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveProcessMockMvc.perform(post("/api/leave-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveProcess in the database
        List<LeaveProcess> leaveProcessList = leaveProcessRepository.findAll();
        assertThat(leaveProcessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLeaveProcesses() throws Exception {
        // Initialize the database
        leaveProcessRepository.saveAndFlush(leaveProcess);

        // Get all the leaveProcessList
        restLeaveProcessMockMvc.perform(get("/api/leave-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveProcess.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getLeaveProcess() throws Exception {
        // Initialize the database
        leaveProcessRepository.saveAndFlush(leaveProcess);

        // Get the leaveProcess
        restLeaveProcessMockMvc.perform(get("/api/leave-processes/{id}", leaveProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveProcess.getId().intValue()));
    }


    @Test
    @Transactional
    public void getLeaveProcessesByIdFiltering() throws Exception {
        // Initialize the database
        leaveProcessRepository.saveAndFlush(leaveProcess);

        Long id = leaveProcess.getId();

        defaultLeaveProcessShouldBeFound("id.equals=" + id);
        defaultLeaveProcessShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveProcessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveProcessShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveProcessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveProcessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeaveProcessesByRentIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveProcessRepository.saveAndFlush(leaveProcess);
        Rent rent = RentResourceIT.createEntity(em);
        em.persist(rent);
        em.flush();
        leaveProcess.setRent(rent);
        leaveProcessRepository.saveAndFlush(leaveProcess);
        Long rentId = rent.getId();

        // Get all the leaveProcessList where rent equals to rentId
        defaultLeaveProcessShouldBeFound("rentId.equals=" + rentId);

        // Get all the leaveProcessList where rent equals to rentId + 1
        defaultLeaveProcessShouldNotBeFound("rentId.equals=" + (rentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveProcessShouldBeFound(String filter) throws Exception {
        restLeaveProcessMockMvc.perform(get("/api/leave-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveProcess.getId().intValue())));

        // Check, that the count call also returns 1
        restLeaveProcessMockMvc.perform(get("/api/leave-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveProcessShouldNotBeFound(String filter) throws Exception {
        restLeaveProcessMockMvc.perform(get("/api/leave-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveProcessMockMvc.perform(get("/api/leave-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLeaveProcess() throws Exception {
        // Get the leaveProcess
        restLeaveProcessMockMvc.perform(get("/api/leave-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveProcess() throws Exception {
        // Initialize the database
        leaveProcessRepository.saveAndFlush(leaveProcess);

        int databaseSizeBeforeUpdate = leaveProcessRepository.findAll().size();

        // Update the leaveProcess
        LeaveProcess updatedLeaveProcess = leaveProcessRepository.findById(leaveProcess.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveProcess are not directly saved in db
        em.detach(updatedLeaveProcess);
        LeaveProcessDTO leaveProcessDTO = leaveProcessMapper.toDto(updatedLeaveProcess);

        restLeaveProcessMockMvc.perform(put("/api/leave-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveProcessDTO)))
            .andExpect(status().isOk());

        // Validate the LeaveProcess in the database
        List<LeaveProcess> leaveProcessList = leaveProcessRepository.findAll();
        assertThat(leaveProcessList).hasSize(databaseSizeBeforeUpdate);
        LeaveProcess testLeaveProcess = leaveProcessList.get(leaveProcessList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveProcess() throws Exception {
        int databaseSizeBeforeUpdate = leaveProcessRepository.findAll().size();

        // Create the LeaveProcess
        LeaveProcessDTO leaveProcessDTO = leaveProcessMapper.toDto(leaveProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveProcessMockMvc.perform(put("/api/leave-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveProcess in the database
        List<LeaveProcess> leaveProcessList = leaveProcessRepository.findAll();
        assertThat(leaveProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeaveProcess() throws Exception {
        // Initialize the database
        leaveProcessRepository.saveAndFlush(leaveProcess);

        int databaseSizeBeforeDelete = leaveProcessRepository.findAll().size();

        // Delete the leaveProcess
        restLeaveProcessMockMvc.perform(delete("/api/leave-processes/{id}", leaveProcess.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveProcess> leaveProcessList = leaveProcessRepository.findAll();
        assertThat(leaveProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
