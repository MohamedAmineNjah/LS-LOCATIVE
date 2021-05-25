package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.ClosingEvent;
import com.fininfo.java.repository.ClosingEventRepository;
import com.fininfo.java.service.ClosingEventService;
import com.fininfo.java.service.dto.ClosingEventDTO;
import com.fininfo.java.service.mapper.ClosingEventMapper;
import com.fininfo.java.service.dto.ClosingEventCriteria;
import com.fininfo.java.service.ClosingEventQueryService;

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

/**
 * Integration tests for the {@link ClosingEventResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClosingEventResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CLOSING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CLOSING_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ClosingEventRepository closingEventRepository;

    @Autowired
    private ClosingEventMapper closingEventMapper;

    @Autowired
    private ClosingEventService closingEventService;

    @Autowired
    private ClosingEventQueryService closingEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClosingEventMockMvc;

    private ClosingEvent closingEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClosingEvent createEntity(EntityManager em) {
        ClosingEvent closingEvent = new ClosingEvent()
            .description(DEFAULT_DESCRIPTION)
            .closingDate(DEFAULT_CLOSING_DATE)
            .endDate(DEFAULT_END_DATE);
        return closingEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClosingEvent createUpdatedEntity(EntityManager em) {
        ClosingEvent closingEvent = new ClosingEvent()
            .description(UPDATED_DESCRIPTION)
            .closingDate(UPDATED_CLOSING_DATE)
            .endDate(UPDATED_END_DATE);
        return closingEvent;
    }

    @BeforeEach
    public void initTest() {
        closingEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createClosingEvent() throws Exception {
        int databaseSizeBeforeCreate = closingEventRepository.findAll().size();
        // Create the ClosingEvent
        ClosingEventDTO closingEventDTO = closingEventMapper.toDto(closingEvent);
        restClosingEventMockMvc.perform(post("/api/closing-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(closingEventDTO)))
            .andExpect(status().isCreated());

        // Validate the ClosingEvent in the database
        List<ClosingEvent> closingEventList = closingEventRepository.findAll();
        assertThat(closingEventList).hasSize(databaseSizeBeforeCreate + 1);
        ClosingEvent testClosingEvent = closingEventList.get(closingEventList.size() - 1);
        assertThat(testClosingEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClosingEvent.getClosingDate()).isEqualTo(DEFAULT_CLOSING_DATE);
        assertThat(testClosingEvent.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createClosingEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = closingEventRepository.findAll().size();

        // Create the ClosingEvent with an existing ID
        closingEvent.setId(1L);
        ClosingEventDTO closingEventDTO = closingEventMapper.toDto(closingEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClosingEventMockMvc.perform(post("/api/closing-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(closingEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClosingEvent in the database
        List<ClosingEvent> closingEventList = closingEventRepository.findAll();
        assertThat(closingEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClosingEvents() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList
        restClosingEventMockMvc.perform(get("/api/closing-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(closingEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].closingDate").value(hasItem(DEFAULT_CLOSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getClosingEvent() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get the closingEvent
        restClosingEventMockMvc.perform(get("/api/closing-events/{id}", closingEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(closingEvent.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.closingDate").value(DEFAULT_CLOSING_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }


    @Test
    @Transactional
    public void getClosingEventsByIdFiltering() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        Long id = closingEvent.getId();

        defaultClosingEventShouldBeFound("id.equals=" + id);
        defaultClosingEventShouldNotBeFound("id.notEquals=" + id);

        defaultClosingEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClosingEventShouldNotBeFound("id.greaterThan=" + id);

        defaultClosingEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClosingEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClosingEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where description equals to DEFAULT_DESCRIPTION
        defaultClosingEventShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the closingEventList where description equals to UPDATED_DESCRIPTION
        defaultClosingEventShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where description not equals to DEFAULT_DESCRIPTION
        defaultClosingEventShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the closingEventList where description not equals to UPDATED_DESCRIPTION
        defaultClosingEventShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultClosingEventShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the closingEventList where description equals to UPDATED_DESCRIPTION
        defaultClosingEventShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where description is not null
        defaultClosingEventShouldBeFound("description.specified=true");

        // Get all the closingEventList where description is null
        defaultClosingEventShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllClosingEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where description contains DEFAULT_DESCRIPTION
        defaultClosingEventShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the closingEventList where description contains UPDATED_DESCRIPTION
        defaultClosingEventShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where description does not contain DEFAULT_DESCRIPTION
        defaultClosingEventShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the closingEventList where description does not contain UPDATED_DESCRIPTION
        defaultClosingEventShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate equals to DEFAULT_CLOSING_DATE
        defaultClosingEventShouldBeFound("closingDate.equals=" + DEFAULT_CLOSING_DATE);

        // Get all the closingEventList where closingDate equals to UPDATED_CLOSING_DATE
        defaultClosingEventShouldNotBeFound("closingDate.equals=" + UPDATED_CLOSING_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate not equals to DEFAULT_CLOSING_DATE
        defaultClosingEventShouldNotBeFound("closingDate.notEquals=" + DEFAULT_CLOSING_DATE);

        // Get all the closingEventList where closingDate not equals to UPDATED_CLOSING_DATE
        defaultClosingEventShouldBeFound("closingDate.notEquals=" + UPDATED_CLOSING_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsInShouldWork() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate in DEFAULT_CLOSING_DATE or UPDATED_CLOSING_DATE
        defaultClosingEventShouldBeFound("closingDate.in=" + DEFAULT_CLOSING_DATE + "," + UPDATED_CLOSING_DATE);

        // Get all the closingEventList where closingDate equals to UPDATED_CLOSING_DATE
        defaultClosingEventShouldNotBeFound("closingDate.in=" + UPDATED_CLOSING_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate is not null
        defaultClosingEventShouldBeFound("closingDate.specified=true");

        // Get all the closingEventList where closingDate is null
        defaultClosingEventShouldNotBeFound("closingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate is greater than or equal to DEFAULT_CLOSING_DATE
        defaultClosingEventShouldBeFound("closingDate.greaterThanOrEqual=" + DEFAULT_CLOSING_DATE);

        // Get all the closingEventList where closingDate is greater than or equal to UPDATED_CLOSING_DATE
        defaultClosingEventShouldNotBeFound("closingDate.greaterThanOrEqual=" + UPDATED_CLOSING_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate is less than or equal to DEFAULT_CLOSING_DATE
        defaultClosingEventShouldBeFound("closingDate.lessThanOrEqual=" + DEFAULT_CLOSING_DATE);

        // Get all the closingEventList where closingDate is less than or equal to SMALLER_CLOSING_DATE
        defaultClosingEventShouldNotBeFound("closingDate.lessThanOrEqual=" + SMALLER_CLOSING_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate is less than DEFAULT_CLOSING_DATE
        defaultClosingEventShouldNotBeFound("closingDate.lessThan=" + DEFAULT_CLOSING_DATE);

        // Get all the closingEventList where closingDate is less than UPDATED_CLOSING_DATE
        defaultClosingEventShouldBeFound("closingDate.lessThan=" + UPDATED_CLOSING_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByClosingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where closingDate is greater than DEFAULT_CLOSING_DATE
        defaultClosingEventShouldNotBeFound("closingDate.greaterThan=" + DEFAULT_CLOSING_DATE);

        // Get all the closingEventList where closingDate is greater than SMALLER_CLOSING_DATE
        defaultClosingEventShouldBeFound("closingDate.greaterThan=" + SMALLER_CLOSING_DATE);
    }


    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate equals to DEFAULT_END_DATE
        defaultClosingEventShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the closingEventList where endDate equals to UPDATED_END_DATE
        defaultClosingEventShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate not equals to DEFAULT_END_DATE
        defaultClosingEventShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the closingEventList where endDate not equals to UPDATED_END_DATE
        defaultClosingEventShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultClosingEventShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the closingEventList where endDate equals to UPDATED_END_DATE
        defaultClosingEventShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate is not null
        defaultClosingEventShouldBeFound("endDate.specified=true");

        // Get all the closingEventList where endDate is null
        defaultClosingEventShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultClosingEventShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the closingEventList where endDate is greater than or equal to UPDATED_END_DATE
        defaultClosingEventShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate is less than or equal to DEFAULT_END_DATE
        defaultClosingEventShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the closingEventList where endDate is less than or equal to SMALLER_END_DATE
        defaultClosingEventShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate is less than DEFAULT_END_DATE
        defaultClosingEventShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the closingEventList where endDate is less than UPDATED_END_DATE
        defaultClosingEventShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllClosingEventsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        // Get all the closingEventList where endDate is greater than DEFAULT_END_DATE
        defaultClosingEventShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the closingEventList where endDate is greater than SMALLER_END_DATE
        defaultClosingEventShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClosingEventShouldBeFound(String filter) throws Exception {
        restClosingEventMockMvc.perform(get("/api/closing-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(closingEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].closingDate").value(hasItem(DEFAULT_CLOSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restClosingEventMockMvc.perform(get("/api/closing-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClosingEventShouldNotBeFound(String filter) throws Exception {
        restClosingEventMockMvc.perform(get("/api/closing-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClosingEventMockMvc.perform(get("/api/closing-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClosingEvent() throws Exception {
        // Get the closingEvent
        restClosingEventMockMvc.perform(get("/api/closing-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClosingEvent() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        int databaseSizeBeforeUpdate = closingEventRepository.findAll().size();

        // Update the closingEvent
        ClosingEvent updatedClosingEvent = closingEventRepository.findById(closingEvent.getId()).get();
        // Disconnect from session so that the updates on updatedClosingEvent are not directly saved in db
        em.detach(updatedClosingEvent);
        updatedClosingEvent
            .description(UPDATED_DESCRIPTION)
            .closingDate(UPDATED_CLOSING_DATE)
            .endDate(UPDATED_END_DATE);
        ClosingEventDTO closingEventDTO = closingEventMapper.toDto(updatedClosingEvent);

        restClosingEventMockMvc.perform(put("/api/closing-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(closingEventDTO)))
            .andExpect(status().isOk());

        // Validate the ClosingEvent in the database
        List<ClosingEvent> closingEventList = closingEventRepository.findAll();
        assertThat(closingEventList).hasSize(databaseSizeBeforeUpdate);
        ClosingEvent testClosingEvent = closingEventList.get(closingEventList.size() - 1);
        assertThat(testClosingEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClosingEvent.getClosingDate()).isEqualTo(UPDATED_CLOSING_DATE);
        assertThat(testClosingEvent.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingClosingEvent() throws Exception {
        int databaseSizeBeforeUpdate = closingEventRepository.findAll().size();

        // Create the ClosingEvent
        ClosingEventDTO closingEventDTO = closingEventMapper.toDto(closingEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClosingEventMockMvc.perform(put("/api/closing-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(closingEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClosingEvent in the database
        List<ClosingEvent> closingEventList = closingEventRepository.findAll();
        assertThat(closingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClosingEvent() throws Exception {
        // Initialize the database
        closingEventRepository.saveAndFlush(closingEvent);

        int databaseSizeBeforeDelete = closingEventRepository.findAll().size();

        // Delete the closingEvent
        restClosingEventMockMvc.perform(delete("/api/closing-events/{id}", closingEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClosingEvent> closingEventList = closingEventRepository.findAll();
        assertThat(closingEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
