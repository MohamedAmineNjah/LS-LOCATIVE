package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.LocationEvent;
import com.fininfo.java.domain.LocationRegulation;
import com.fininfo.java.repository.LocationEventRepository;
import com.fininfo.java.service.LocationEventService;
import com.fininfo.java.service.dto.LocationEventDTO;
import com.fininfo.java.service.mapper.LocationEventMapper;
import com.fininfo.java.service.dto.LocationEventCriteria;
import com.fininfo.java.service.LocationEventQueryService;

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
 * Integration tests for the {@link LocationEventResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocationEventResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_COST_PRICE = 1D;
    private static final Double UPDATED_COST_PRICE = 2D;
    private static final Double SMALLER_COST_PRICE = 1D - 1D;

    private static final LocalDate DEFAULT_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEADLINE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DEADLINE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_ASSET_CODE = 1;
    private static final Integer UPDATED_ASSET_CODE = 2;
    private static final Integer SMALLER_ASSET_CODE = 1 - 1;

    @Autowired
    private LocationEventRepository locationEventRepository;

    @Autowired
    private LocationEventMapper locationEventMapper;

    @Autowired
    private LocationEventService locationEventService;

    @Autowired
    private LocationEventQueryService locationEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationEventMockMvc;

    private LocationEvent locationEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationEvent createEntity(EntityManager em) {
        LocationEvent locationEvent = new LocationEvent()
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .costPrice(DEFAULT_COST_PRICE)
            .deadline(DEFAULT_DEADLINE)
            .assetCode(DEFAULT_ASSET_CODE);
        return locationEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationEvent createUpdatedEntity(EntityManager em) {
        LocationEvent locationEvent = new LocationEvent()
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .costPrice(UPDATED_COST_PRICE)
            .deadline(UPDATED_DEADLINE)
            .assetCode(UPDATED_ASSET_CODE);
        return locationEvent;
    }

    @BeforeEach
    public void initTest() {
        locationEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationEvent() throws Exception {
        int databaseSizeBeforeCreate = locationEventRepository.findAll().size();
        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);
        restLocationEventMockMvc.perform(post("/api/location-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationEventDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationEvent in the database
        List<LocationEvent> locationEventList = locationEventRepository.findAll();
        assertThat(locationEventList).hasSize(databaseSizeBeforeCreate + 1);
        LocationEvent testLocationEvent = locationEventList.get(locationEventList.size() - 1);
        assertThat(testLocationEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLocationEvent.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLocationEvent.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLocationEvent.getCostPrice()).isEqualTo(DEFAULT_COST_PRICE);
        assertThat(testLocationEvent.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
        assertThat(testLocationEvent.getAssetCode()).isEqualTo(DEFAULT_ASSET_CODE);
    }

    @Test
    @Transactional
    public void createLocationEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationEventRepository.findAll().size();

        // Create the LocationEvent with an existing ID
        locationEvent.setId(1L);
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationEventMockMvc.perform(post("/api/location-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationEvent in the database
        List<LocationEvent> locationEventList = locationEventRepository.findAll();
        assertThat(locationEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocationEvents() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList
        restLocationEventMockMvc.perform(get("/api/location-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].costPrice").value(hasItem(DEFAULT_COST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].assetCode").value(hasItem(DEFAULT_ASSET_CODE)));
    }
    
    @Test
    @Transactional
    public void getLocationEvent() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get the locationEvent
        restLocationEventMockMvc.perform(get("/api/location-events/{id}", locationEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationEvent.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.costPrice").value(DEFAULT_COST_PRICE.doubleValue()))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE.toString()))
            .andExpect(jsonPath("$.assetCode").value(DEFAULT_ASSET_CODE));
    }


    @Test
    @Transactional
    public void getLocationEventsByIdFiltering() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        Long id = locationEvent.getId();

        defaultLocationEventShouldBeFound("id.equals=" + id);
        defaultLocationEventShouldNotBeFound("id.notEquals=" + id);

        defaultLocationEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLocationEventShouldNotBeFound("id.greaterThan=" + id);

        defaultLocationEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLocationEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLocationEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where description equals to DEFAULT_DESCRIPTION
        defaultLocationEventShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the locationEventList where description equals to UPDATED_DESCRIPTION
        defaultLocationEventShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where description not equals to DEFAULT_DESCRIPTION
        defaultLocationEventShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the locationEventList where description not equals to UPDATED_DESCRIPTION
        defaultLocationEventShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLocationEventShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the locationEventList where description equals to UPDATED_DESCRIPTION
        defaultLocationEventShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where description is not null
        defaultLocationEventShouldBeFound("description.specified=true");

        // Get all the locationEventList where description is null
        defaultLocationEventShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllLocationEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where description contains DEFAULT_DESCRIPTION
        defaultLocationEventShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the locationEventList where description contains UPDATED_DESCRIPTION
        defaultLocationEventShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where description does not contain DEFAULT_DESCRIPTION
        defaultLocationEventShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the locationEventList where description does not contain UPDATED_DESCRIPTION
        defaultLocationEventShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate equals to DEFAULT_START_DATE
        defaultLocationEventShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the locationEventList where startDate equals to UPDATED_START_DATE
        defaultLocationEventShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate not equals to DEFAULT_START_DATE
        defaultLocationEventShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the locationEventList where startDate not equals to UPDATED_START_DATE
        defaultLocationEventShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultLocationEventShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the locationEventList where startDate equals to UPDATED_START_DATE
        defaultLocationEventShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate is not null
        defaultLocationEventShouldBeFound("startDate.specified=true");

        // Get all the locationEventList where startDate is null
        defaultLocationEventShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultLocationEventShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the locationEventList where startDate is greater than or equal to UPDATED_START_DATE
        defaultLocationEventShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate is less than or equal to DEFAULT_START_DATE
        defaultLocationEventShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the locationEventList where startDate is less than or equal to SMALLER_START_DATE
        defaultLocationEventShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate is less than DEFAULT_START_DATE
        defaultLocationEventShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the locationEventList where startDate is less than UPDATED_START_DATE
        defaultLocationEventShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where startDate is greater than DEFAULT_START_DATE
        defaultLocationEventShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the locationEventList where startDate is greater than SMALLER_START_DATE
        defaultLocationEventShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate equals to DEFAULT_END_DATE
        defaultLocationEventShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the locationEventList where endDate equals to UPDATED_END_DATE
        defaultLocationEventShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate not equals to DEFAULT_END_DATE
        defaultLocationEventShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the locationEventList where endDate not equals to UPDATED_END_DATE
        defaultLocationEventShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultLocationEventShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the locationEventList where endDate equals to UPDATED_END_DATE
        defaultLocationEventShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate is not null
        defaultLocationEventShouldBeFound("endDate.specified=true");

        // Get all the locationEventList where endDate is null
        defaultLocationEventShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultLocationEventShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the locationEventList where endDate is greater than or equal to UPDATED_END_DATE
        defaultLocationEventShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate is less than or equal to DEFAULT_END_DATE
        defaultLocationEventShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the locationEventList where endDate is less than or equal to SMALLER_END_DATE
        defaultLocationEventShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate is less than DEFAULT_END_DATE
        defaultLocationEventShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the locationEventList where endDate is less than UPDATED_END_DATE
        defaultLocationEventShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where endDate is greater than DEFAULT_END_DATE
        defaultLocationEventShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the locationEventList where endDate is greater than SMALLER_END_DATE
        defaultLocationEventShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice equals to DEFAULT_COST_PRICE
        defaultLocationEventShouldBeFound("costPrice.equals=" + DEFAULT_COST_PRICE);

        // Get all the locationEventList where costPrice equals to UPDATED_COST_PRICE
        defaultLocationEventShouldNotBeFound("costPrice.equals=" + UPDATED_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice not equals to DEFAULT_COST_PRICE
        defaultLocationEventShouldNotBeFound("costPrice.notEquals=" + DEFAULT_COST_PRICE);

        // Get all the locationEventList where costPrice not equals to UPDATED_COST_PRICE
        defaultLocationEventShouldBeFound("costPrice.notEquals=" + UPDATED_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsInShouldWork() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice in DEFAULT_COST_PRICE or UPDATED_COST_PRICE
        defaultLocationEventShouldBeFound("costPrice.in=" + DEFAULT_COST_PRICE + "," + UPDATED_COST_PRICE);

        // Get all the locationEventList where costPrice equals to UPDATED_COST_PRICE
        defaultLocationEventShouldNotBeFound("costPrice.in=" + UPDATED_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice is not null
        defaultLocationEventShouldBeFound("costPrice.specified=true");

        // Get all the locationEventList where costPrice is null
        defaultLocationEventShouldNotBeFound("costPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice is greater than or equal to DEFAULT_COST_PRICE
        defaultLocationEventShouldBeFound("costPrice.greaterThanOrEqual=" + DEFAULT_COST_PRICE);

        // Get all the locationEventList where costPrice is greater than or equal to UPDATED_COST_PRICE
        defaultLocationEventShouldNotBeFound("costPrice.greaterThanOrEqual=" + UPDATED_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice is less than or equal to DEFAULT_COST_PRICE
        defaultLocationEventShouldBeFound("costPrice.lessThanOrEqual=" + DEFAULT_COST_PRICE);

        // Get all the locationEventList where costPrice is less than or equal to SMALLER_COST_PRICE
        defaultLocationEventShouldNotBeFound("costPrice.lessThanOrEqual=" + SMALLER_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice is less than DEFAULT_COST_PRICE
        defaultLocationEventShouldNotBeFound("costPrice.lessThan=" + DEFAULT_COST_PRICE);

        // Get all the locationEventList where costPrice is less than UPDATED_COST_PRICE
        defaultLocationEventShouldBeFound("costPrice.lessThan=" + UPDATED_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByCostPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where costPrice is greater than DEFAULT_COST_PRICE
        defaultLocationEventShouldNotBeFound("costPrice.greaterThan=" + DEFAULT_COST_PRICE);

        // Get all the locationEventList where costPrice is greater than SMALLER_COST_PRICE
        defaultLocationEventShouldBeFound("costPrice.greaterThan=" + SMALLER_COST_PRICE);
    }


    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline equals to DEFAULT_DEADLINE
        defaultLocationEventShouldBeFound("deadline.equals=" + DEFAULT_DEADLINE);

        // Get all the locationEventList where deadline equals to UPDATED_DEADLINE
        defaultLocationEventShouldNotBeFound("deadline.equals=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline not equals to DEFAULT_DEADLINE
        defaultLocationEventShouldNotBeFound("deadline.notEquals=" + DEFAULT_DEADLINE);

        // Get all the locationEventList where deadline not equals to UPDATED_DEADLINE
        defaultLocationEventShouldBeFound("deadline.notEquals=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline in DEFAULT_DEADLINE or UPDATED_DEADLINE
        defaultLocationEventShouldBeFound("deadline.in=" + DEFAULT_DEADLINE + "," + UPDATED_DEADLINE);

        // Get all the locationEventList where deadline equals to UPDATED_DEADLINE
        defaultLocationEventShouldNotBeFound("deadline.in=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline is not null
        defaultLocationEventShouldBeFound("deadline.specified=true");

        // Get all the locationEventList where deadline is null
        defaultLocationEventShouldNotBeFound("deadline.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline is greater than or equal to DEFAULT_DEADLINE
        defaultLocationEventShouldBeFound("deadline.greaterThanOrEqual=" + DEFAULT_DEADLINE);

        // Get all the locationEventList where deadline is greater than or equal to UPDATED_DEADLINE
        defaultLocationEventShouldNotBeFound("deadline.greaterThanOrEqual=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline is less than or equal to DEFAULT_DEADLINE
        defaultLocationEventShouldBeFound("deadline.lessThanOrEqual=" + DEFAULT_DEADLINE);

        // Get all the locationEventList where deadline is less than or equal to SMALLER_DEADLINE
        defaultLocationEventShouldNotBeFound("deadline.lessThanOrEqual=" + SMALLER_DEADLINE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsLessThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline is less than DEFAULT_DEADLINE
        defaultLocationEventShouldNotBeFound("deadline.lessThan=" + DEFAULT_DEADLINE);

        // Get all the locationEventList where deadline is less than UPDATED_DEADLINE
        defaultLocationEventShouldBeFound("deadline.lessThan=" + UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByDeadlineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where deadline is greater than DEFAULT_DEADLINE
        defaultLocationEventShouldNotBeFound("deadline.greaterThan=" + DEFAULT_DEADLINE);

        // Get all the locationEventList where deadline is greater than SMALLER_DEADLINE
        defaultLocationEventShouldBeFound("deadline.greaterThan=" + SMALLER_DEADLINE);
    }


    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode equals to DEFAULT_ASSET_CODE
        defaultLocationEventShouldBeFound("assetCode.equals=" + DEFAULT_ASSET_CODE);

        // Get all the locationEventList where assetCode equals to UPDATED_ASSET_CODE
        defaultLocationEventShouldNotBeFound("assetCode.equals=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode not equals to DEFAULT_ASSET_CODE
        defaultLocationEventShouldNotBeFound("assetCode.notEquals=" + DEFAULT_ASSET_CODE);

        // Get all the locationEventList where assetCode not equals to UPDATED_ASSET_CODE
        defaultLocationEventShouldBeFound("assetCode.notEquals=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsInShouldWork() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode in DEFAULT_ASSET_CODE or UPDATED_ASSET_CODE
        defaultLocationEventShouldBeFound("assetCode.in=" + DEFAULT_ASSET_CODE + "," + UPDATED_ASSET_CODE);

        // Get all the locationEventList where assetCode equals to UPDATED_ASSET_CODE
        defaultLocationEventShouldNotBeFound("assetCode.in=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode is not null
        defaultLocationEventShouldBeFound("assetCode.specified=true");

        // Get all the locationEventList where assetCode is null
        defaultLocationEventShouldNotBeFound("assetCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode is greater than or equal to DEFAULT_ASSET_CODE
        defaultLocationEventShouldBeFound("assetCode.greaterThanOrEqual=" + DEFAULT_ASSET_CODE);

        // Get all the locationEventList where assetCode is greater than or equal to UPDATED_ASSET_CODE
        defaultLocationEventShouldNotBeFound("assetCode.greaterThanOrEqual=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode is less than or equal to DEFAULT_ASSET_CODE
        defaultLocationEventShouldBeFound("assetCode.lessThanOrEqual=" + DEFAULT_ASSET_CODE);

        // Get all the locationEventList where assetCode is less than or equal to SMALLER_ASSET_CODE
        defaultLocationEventShouldNotBeFound("assetCode.lessThanOrEqual=" + SMALLER_ASSET_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode is less than DEFAULT_ASSET_CODE
        defaultLocationEventShouldNotBeFound("assetCode.lessThan=" + DEFAULT_ASSET_CODE);

        // Get all the locationEventList where assetCode is less than UPDATED_ASSET_CODE
        defaultLocationEventShouldBeFound("assetCode.lessThan=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    public void getAllLocationEventsByAssetCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where assetCode is greater than DEFAULT_ASSET_CODE
        defaultLocationEventShouldNotBeFound("assetCode.greaterThan=" + DEFAULT_ASSET_CODE);

        // Get all the locationEventList where assetCode is greater than SMALLER_ASSET_CODE
        defaultLocationEventShouldBeFound("assetCode.greaterThan=" + SMALLER_ASSET_CODE);
    }


    @Test
    @Transactional
    public void getAllLocationEventsByLocationRegulationIsEqualToSomething() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);
        LocationRegulation locationRegulation = LocationRegulationResourceIT.createEntity(em);
        em.persist(locationRegulation);
        em.flush();
        locationEvent.setLocationRegulation(locationRegulation);
        locationEventRepository.saveAndFlush(locationEvent);
        Long locationRegulationId = locationRegulation.getId();

        // Get all the locationEventList where locationRegulation equals to locationRegulationId
        defaultLocationEventShouldBeFound("locationRegulationId.equals=" + locationRegulationId);

        // Get all the locationEventList where locationRegulation equals to locationRegulationId + 1
        defaultLocationEventShouldNotBeFound("locationRegulationId.equals=" + (locationRegulationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationEventShouldBeFound(String filter) throws Exception {
        restLocationEventMockMvc.perform(get("/api/location-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].costPrice").value(hasItem(DEFAULT_COST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].assetCode").value(hasItem(DEFAULT_ASSET_CODE)));

        // Check, that the count call also returns 1
        restLocationEventMockMvc.perform(get("/api/location-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationEventShouldNotBeFound(String filter) throws Exception {
        restLocationEventMockMvc.perform(get("/api/location-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationEventMockMvc.perform(get("/api/location-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLocationEvent() throws Exception {
        // Get the locationEvent
        restLocationEventMockMvc.perform(get("/api/location-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationEvent() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        int databaseSizeBeforeUpdate = locationEventRepository.findAll().size();

        // Update the locationEvent
        LocationEvent updatedLocationEvent = locationEventRepository.findById(locationEvent.getId()).get();
        // Disconnect from session so that the updates on updatedLocationEvent are not directly saved in db
        em.detach(updatedLocationEvent);
        updatedLocationEvent
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .costPrice(UPDATED_COST_PRICE)
            .deadline(UPDATED_DEADLINE)
            .assetCode(UPDATED_ASSET_CODE);
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(updatedLocationEvent);

        restLocationEventMockMvc.perform(put("/api/location-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationEventDTO)))
            .andExpect(status().isOk());

        // Validate the LocationEvent in the database
        List<LocationEvent> locationEventList = locationEventRepository.findAll();
        assertThat(locationEventList).hasSize(databaseSizeBeforeUpdate);
        LocationEvent testLocationEvent = locationEventList.get(locationEventList.size() - 1);
        assertThat(testLocationEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLocationEvent.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLocationEvent.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLocationEvent.getCostPrice()).isEqualTo(UPDATED_COST_PRICE);
        assertThat(testLocationEvent.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testLocationEvent.getAssetCode()).isEqualTo(UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationEvent() throws Exception {
        int databaseSizeBeforeUpdate = locationEventRepository.findAll().size();

        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationEventMockMvc.perform(put("/api/location-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locationEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationEvent in the database
        List<LocationEvent> locationEventList = locationEventRepository.findAll();
        assertThat(locationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocationEvent() throws Exception {
        // Initialize the database
        locationEventRepository.saveAndFlush(locationEvent);

        int databaseSizeBeforeDelete = locationEventRepository.findAll().size();

        // Delete the locationEvent
        restLocationEventMockMvc.perform(delete("/api/location-events/{id}", locationEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationEvent> locationEventList = locationEventRepository.findAll();
        assertThat(locationEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
