package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Schedule;
import com.fininfo.java.domain.Bill;
import com.fininfo.java.domain.LotBail;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.ScheduleRepository;
import com.fininfo.java.service.ScheduleService;
import com.fininfo.java.service.dto.ScheduleDTO;
import com.fininfo.java.service.mapper.ScheduleMapper;
import com.fininfo.java.service.dto.ScheduleCriteria;
import com.fininfo.java.service.ScheduleQueryService;

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

/**
 * Integration tests for the {@link ScheduleResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduleResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT_SCHEDULE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_SCHEDULE = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_SCHEDULE = new BigDecimal(1 - 1);

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleQueryService scheduleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleMockMvc;

    private Schedule schedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createEntity(EntityManager em) {
        Schedule schedule = new Schedule()
            .amountSchedule(DEFAULT_AMOUNT_SCHEDULE);
        return schedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createUpdatedEntity(EntityManager em) {
        Schedule schedule = new Schedule()
            .amountSchedule(UPDATED_AMOUNT_SCHEDULE);
        return schedule;
    }

    @BeforeEach
    public void initTest() {
        schedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedule() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();
        // Create the Schedule
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(schedule);
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate + 1);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getAmountSchedule()).isEqualTo(DEFAULT_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void createScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();

        // Create the Schedule with an existing ID
        schedule.setId(1L);
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(schedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSchedules() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList
        restScheduleMockMvc.perform(get("/api/schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountSchedule").value(hasItem(DEFAULT_AMOUNT_SCHEDULE.intValue())));
    }
    
    @Test
    @Transactional
    public void getSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", schedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedule.getId().intValue()))
            .andExpect(jsonPath("$.amountSchedule").value(DEFAULT_AMOUNT_SCHEDULE.intValue()));
    }


    @Test
    @Transactional
    public void getSchedulesByIdFiltering() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        Long id = schedule.getId();

        defaultScheduleShouldBeFound("id.equals=" + id);
        defaultScheduleShouldNotBeFound("id.notEquals=" + id);

        defaultScheduleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScheduleShouldNotBeFound("id.greaterThan=" + id);

        defaultScheduleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScheduleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule equals to DEFAULT_AMOUNT_SCHEDULE
        defaultScheduleShouldBeFound("amountSchedule.equals=" + DEFAULT_AMOUNT_SCHEDULE);

        // Get all the scheduleList where amountSchedule equals to UPDATED_AMOUNT_SCHEDULE
        defaultScheduleShouldNotBeFound("amountSchedule.equals=" + UPDATED_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule not equals to DEFAULT_AMOUNT_SCHEDULE
        defaultScheduleShouldNotBeFound("amountSchedule.notEquals=" + DEFAULT_AMOUNT_SCHEDULE);

        // Get all the scheduleList where amountSchedule not equals to UPDATED_AMOUNT_SCHEDULE
        defaultScheduleShouldBeFound("amountSchedule.notEquals=" + UPDATED_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsInShouldWork() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule in DEFAULT_AMOUNT_SCHEDULE or UPDATED_AMOUNT_SCHEDULE
        defaultScheduleShouldBeFound("amountSchedule.in=" + DEFAULT_AMOUNT_SCHEDULE + "," + UPDATED_AMOUNT_SCHEDULE);

        // Get all the scheduleList where amountSchedule equals to UPDATED_AMOUNT_SCHEDULE
        defaultScheduleShouldNotBeFound("amountSchedule.in=" + UPDATED_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsNullOrNotNull() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule is not null
        defaultScheduleShouldBeFound("amountSchedule.specified=true");

        // Get all the scheduleList where amountSchedule is null
        defaultScheduleShouldNotBeFound("amountSchedule.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule is greater than or equal to DEFAULT_AMOUNT_SCHEDULE
        defaultScheduleShouldBeFound("amountSchedule.greaterThanOrEqual=" + DEFAULT_AMOUNT_SCHEDULE);

        // Get all the scheduleList where amountSchedule is greater than or equal to UPDATED_AMOUNT_SCHEDULE
        defaultScheduleShouldNotBeFound("amountSchedule.greaterThanOrEqual=" + UPDATED_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule is less than or equal to DEFAULT_AMOUNT_SCHEDULE
        defaultScheduleShouldBeFound("amountSchedule.lessThanOrEqual=" + DEFAULT_AMOUNT_SCHEDULE);

        // Get all the scheduleList where amountSchedule is less than or equal to SMALLER_AMOUNT_SCHEDULE
        defaultScheduleShouldNotBeFound("amountSchedule.lessThanOrEqual=" + SMALLER_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsLessThanSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule is less than DEFAULT_AMOUNT_SCHEDULE
        defaultScheduleShouldNotBeFound("amountSchedule.lessThan=" + DEFAULT_AMOUNT_SCHEDULE);

        // Get all the scheduleList where amountSchedule is less than UPDATED_AMOUNT_SCHEDULE
        defaultScheduleShouldBeFound("amountSchedule.lessThan=" + UPDATED_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void getAllSchedulesByAmountScheduleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList where amountSchedule is greater than DEFAULT_AMOUNT_SCHEDULE
        defaultScheduleShouldNotBeFound("amountSchedule.greaterThan=" + DEFAULT_AMOUNT_SCHEDULE);

        // Get all the scheduleList where amountSchedule is greater than SMALLER_AMOUNT_SCHEDULE
        defaultScheduleShouldBeFound("amountSchedule.greaterThan=" + SMALLER_AMOUNT_SCHEDULE);
    }


    @Test
    @Transactional
    public void getAllSchedulesByBillIsEqualToSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);
        Bill bill = BillResourceIT.createEntity(em);
        em.persist(bill);
        em.flush();
        schedule.addBill(bill);
        scheduleRepository.saveAndFlush(schedule);
        Long billId = bill.getId();

        // Get all the scheduleList where bill equals to billId
        defaultScheduleShouldBeFound("billId.equals=" + billId);

        // Get all the scheduleList where bill equals to billId + 1
        defaultScheduleShouldNotBeFound("billId.equals=" + (billId + 1));
    }


    @Test
    @Transactional
    public void getAllSchedulesByLotBailIsEqualToSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);
        LotBail lotBail = LotBailResourceIT.createEntity(em);
        em.persist(lotBail);
        em.flush();
        schedule.setLotBail(lotBail);
        scheduleRepository.saveAndFlush(schedule);
        Long lotBailId = lotBail.getId();

        // Get all the scheduleList where lotBail equals to lotBailId
        defaultScheduleShouldBeFound("lotBailId.equals=" + lotBailId);

        // Get all the scheduleList where lotBail equals to lotBailId + 1
        defaultScheduleShouldNotBeFound("lotBailId.equals=" + (lotBailId + 1));
    }


    @Test
    @Transactional
    public void getAllSchedulesByRentIsEqualToSomething() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);
        Rent rent = RentResourceIT.createEntity(em);
        em.persist(rent);
        em.flush();
        schedule.setRent(rent);
        scheduleRepository.saveAndFlush(schedule);
        Long rentId = rent.getId();

        // Get all the scheduleList where rent equals to rentId
        defaultScheduleShouldBeFound("rentId.equals=" + rentId);

        // Get all the scheduleList where rent equals to rentId + 1
        defaultScheduleShouldNotBeFound("rentId.equals=" + (rentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScheduleShouldBeFound(String filter) throws Exception {
        restScheduleMockMvc.perform(get("/api/schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountSchedule").value(hasItem(DEFAULT_AMOUNT_SCHEDULE.intValue())));

        // Check, that the count call also returns 1
        restScheduleMockMvc.perform(get("/api/schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScheduleShouldNotBeFound(String filter) throws Exception {
        restScheduleMockMvc.perform(get("/api/schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScheduleMockMvc.perform(get("/api/schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSchedule() throws Exception {
        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Update the schedule
        Schedule updatedSchedule = scheduleRepository.findById(schedule.getId()).get();
        // Disconnect from session so that the updates on updatedSchedule are not directly saved in db
        em.detach(updatedSchedule);
        updatedSchedule
            .amountSchedule(UPDATED_AMOUNT_SCHEDULE);
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(updatedSchedule);

        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isOk());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getAmountSchedule()).isEqualTo(UPDATED_AMOUNT_SCHEDULE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Create the Schedule
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(schedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        int databaseSizeBeforeDelete = scheduleRepository.findAll().size();

        // Delete the schedule
        restScheduleMockMvc.perform(delete("/api/schedules/{id}", schedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
