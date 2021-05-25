package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Counter;
import com.fininfo.java.domain.LotBail;
import com.fininfo.java.repository.CounterRepository;
import com.fininfo.java.service.CounterService;
import com.fininfo.java.service.dto.CounterDTO;
import com.fininfo.java.service.mapper.CounterMapper;
import com.fininfo.java.service.dto.CounterCriteria;
import com.fininfo.java.service.CounterQueryService;

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

import com.fininfo.java.domain.enumeration.IEnumTitle;
import com.fininfo.java.domain.enumeration.IEnumUnity;
/**
 * Integration tests for the {@link CounterResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CounterResourceIT {

    private static final IEnumTitle DEFAULT_COUNTER_TITLE = IEnumTitle.EAU;
    private static final IEnumTitle UPDATED_COUNTER_TITLE = IEnumTitle.EAUCHAUDE;

    private static final IEnumUnity DEFAULT_UNITY = IEnumUnity.M3;
    private static final IEnumUnity UPDATED_UNITY = IEnumUnity.CALORIES;

    private static final Integer DEFAULT_DECIMAL = 1;
    private static final Integer UPDATED_DECIMAL = 2;
    private static final Integer SMALLER_DECIMAL = 1 - 1;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private CounterMapper counterMapper;

    @Autowired
    private CounterService counterService;

    @Autowired
    private CounterQueryService counterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCounterMockMvc;

    private Counter counter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Counter createEntity(EntityManager em) {
        Counter counter = new Counter()
            .counterTitle(DEFAULT_COUNTER_TITLE)
            .unity(DEFAULT_UNITY)
            .decimal(DEFAULT_DECIMAL)
            .comment(DEFAULT_COMMENT);
        return counter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Counter createUpdatedEntity(EntityManager em) {
        Counter counter = new Counter()
            .counterTitle(UPDATED_COUNTER_TITLE)
            .unity(UPDATED_UNITY)
            .decimal(UPDATED_DECIMAL)
            .comment(UPDATED_COMMENT);
        return counter;
    }

    @BeforeEach
    public void initTest() {
        counter = createEntity(em);
    }

    @Test
    @Transactional
    public void createCounter() throws Exception {
        int databaseSizeBeforeCreate = counterRepository.findAll().size();
        // Create the Counter
        CounterDTO counterDTO = counterMapper.toDto(counter);
        restCounterMockMvc.perform(post("/api/counters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterDTO)))
            .andExpect(status().isCreated());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeCreate + 1);
        Counter testCounter = counterList.get(counterList.size() - 1);
        assertThat(testCounter.getCounterTitle()).isEqualTo(DEFAULT_COUNTER_TITLE);
        assertThat(testCounter.getUnity()).isEqualTo(DEFAULT_UNITY);
        assertThat(testCounter.getDecimal()).isEqualTo(DEFAULT_DECIMAL);
        assertThat(testCounter.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createCounterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = counterRepository.findAll().size();

        // Create the Counter with an existing ID
        counter.setId(1L);
        CounterDTO counterDTO = counterMapper.toDto(counter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCounterMockMvc.perform(post("/api/counters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCounters() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList
        restCounterMockMvc.perform(get("/api/counters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(counter.getId().intValue())))
            .andExpect(jsonPath("$.[*].counterTitle").value(hasItem(DEFAULT_COUNTER_TITLE.toString())))
            .andExpect(jsonPath("$.[*].unity").value(hasItem(DEFAULT_UNITY.toString())))
            .andExpect(jsonPath("$.[*].decimal").value(hasItem(DEFAULT_DECIMAL)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getCounter() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get the counter
        restCounterMockMvc.perform(get("/api/counters/{id}", counter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(counter.getId().intValue()))
            .andExpect(jsonPath("$.counterTitle").value(DEFAULT_COUNTER_TITLE.toString()))
            .andExpect(jsonPath("$.unity").value(DEFAULT_UNITY.toString()))
            .andExpect(jsonPath("$.decimal").value(DEFAULT_DECIMAL))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }


    @Test
    @Transactional
    public void getCountersByIdFiltering() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        Long id = counter.getId();

        defaultCounterShouldBeFound("id.equals=" + id);
        defaultCounterShouldNotBeFound("id.notEquals=" + id);

        defaultCounterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCounterShouldNotBeFound("id.greaterThan=" + id);

        defaultCounterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCounterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCountersByCounterTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where counterTitle equals to DEFAULT_COUNTER_TITLE
        defaultCounterShouldBeFound("counterTitle.equals=" + DEFAULT_COUNTER_TITLE);

        // Get all the counterList where counterTitle equals to UPDATED_COUNTER_TITLE
        defaultCounterShouldNotBeFound("counterTitle.equals=" + UPDATED_COUNTER_TITLE);
    }

    @Test
    @Transactional
    public void getAllCountersByCounterTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where counterTitle not equals to DEFAULT_COUNTER_TITLE
        defaultCounterShouldNotBeFound("counterTitle.notEquals=" + DEFAULT_COUNTER_TITLE);

        // Get all the counterList where counterTitle not equals to UPDATED_COUNTER_TITLE
        defaultCounterShouldBeFound("counterTitle.notEquals=" + UPDATED_COUNTER_TITLE);
    }

    @Test
    @Transactional
    public void getAllCountersByCounterTitleIsInShouldWork() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where counterTitle in DEFAULT_COUNTER_TITLE or UPDATED_COUNTER_TITLE
        defaultCounterShouldBeFound("counterTitle.in=" + DEFAULT_COUNTER_TITLE + "," + UPDATED_COUNTER_TITLE);

        // Get all the counterList where counterTitle equals to UPDATED_COUNTER_TITLE
        defaultCounterShouldNotBeFound("counterTitle.in=" + UPDATED_COUNTER_TITLE);
    }

    @Test
    @Transactional
    public void getAllCountersByCounterTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where counterTitle is not null
        defaultCounterShouldBeFound("counterTitle.specified=true");

        // Get all the counterList where counterTitle is null
        defaultCounterShouldNotBeFound("counterTitle.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountersByUnityIsEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where unity equals to DEFAULT_UNITY
        defaultCounterShouldBeFound("unity.equals=" + DEFAULT_UNITY);

        // Get all the counterList where unity equals to UPDATED_UNITY
        defaultCounterShouldNotBeFound("unity.equals=" + UPDATED_UNITY);
    }

    @Test
    @Transactional
    public void getAllCountersByUnityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where unity not equals to DEFAULT_UNITY
        defaultCounterShouldNotBeFound("unity.notEquals=" + DEFAULT_UNITY);

        // Get all the counterList where unity not equals to UPDATED_UNITY
        defaultCounterShouldBeFound("unity.notEquals=" + UPDATED_UNITY);
    }

    @Test
    @Transactional
    public void getAllCountersByUnityIsInShouldWork() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where unity in DEFAULT_UNITY or UPDATED_UNITY
        defaultCounterShouldBeFound("unity.in=" + DEFAULT_UNITY + "," + UPDATED_UNITY);

        // Get all the counterList where unity equals to UPDATED_UNITY
        defaultCounterShouldNotBeFound("unity.in=" + UPDATED_UNITY);
    }

    @Test
    @Transactional
    public void getAllCountersByUnityIsNullOrNotNull() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where unity is not null
        defaultCounterShouldBeFound("unity.specified=true");

        // Get all the counterList where unity is null
        defaultCounterShouldNotBeFound("unity.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal equals to DEFAULT_DECIMAL
        defaultCounterShouldBeFound("decimal.equals=" + DEFAULT_DECIMAL);

        // Get all the counterList where decimal equals to UPDATED_DECIMAL
        defaultCounterShouldNotBeFound("decimal.equals=" + UPDATED_DECIMAL);
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal not equals to DEFAULT_DECIMAL
        defaultCounterShouldNotBeFound("decimal.notEquals=" + DEFAULT_DECIMAL);

        // Get all the counterList where decimal not equals to UPDATED_DECIMAL
        defaultCounterShouldBeFound("decimal.notEquals=" + UPDATED_DECIMAL);
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsInShouldWork() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal in DEFAULT_DECIMAL or UPDATED_DECIMAL
        defaultCounterShouldBeFound("decimal.in=" + DEFAULT_DECIMAL + "," + UPDATED_DECIMAL);

        // Get all the counterList where decimal equals to UPDATED_DECIMAL
        defaultCounterShouldNotBeFound("decimal.in=" + UPDATED_DECIMAL);
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsNullOrNotNull() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal is not null
        defaultCounterShouldBeFound("decimal.specified=true");

        // Get all the counterList where decimal is null
        defaultCounterShouldNotBeFound("decimal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal is greater than or equal to DEFAULT_DECIMAL
        defaultCounterShouldBeFound("decimal.greaterThanOrEqual=" + DEFAULT_DECIMAL);

        // Get all the counterList where decimal is greater than or equal to UPDATED_DECIMAL
        defaultCounterShouldNotBeFound("decimal.greaterThanOrEqual=" + UPDATED_DECIMAL);
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal is less than or equal to DEFAULT_DECIMAL
        defaultCounterShouldBeFound("decimal.lessThanOrEqual=" + DEFAULT_DECIMAL);

        // Get all the counterList where decimal is less than or equal to SMALLER_DECIMAL
        defaultCounterShouldNotBeFound("decimal.lessThanOrEqual=" + SMALLER_DECIMAL);
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsLessThanSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal is less than DEFAULT_DECIMAL
        defaultCounterShouldNotBeFound("decimal.lessThan=" + DEFAULT_DECIMAL);

        // Get all the counterList where decimal is less than UPDATED_DECIMAL
        defaultCounterShouldBeFound("decimal.lessThan=" + UPDATED_DECIMAL);
    }

    @Test
    @Transactional
    public void getAllCountersByDecimalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where decimal is greater than DEFAULT_DECIMAL
        defaultCounterShouldNotBeFound("decimal.greaterThan=" + DEFAULT_DECIMAL);

        // Get all the counterList where decimal is greater than SMALLER_DECIMAL
        defaultCounterShouldBeFound("decimal.greaterThan=" + SMALLER_DECIMAL);
    }


    @Test
    @Transactional
    public void getAllCountersByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where comment equals to DEFAULT_COMMENT
        defaultCounterShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the counterList where comment equals to UPDATED_COMMENT
        defaultCounterShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllCountersByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where comment not equals to DEFAULT_COMMENT
        defaultCounterShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the counterList where comment not equals to UPDATED_COMMENT
        defaultCounterShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllCountersByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultCounterShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the counterList where comment equals to UPDATED_COMMENT
        defaultCounterShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllCountersByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where comment is not null
        defaultCounterShouldBeFound("comment.specified=true");

        // Get all the counterList where comment is null
        defaultCounterShouldNotBeFound("comment.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountersByCommentContainsSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where comment contains DEFAULT_COMMENT
        defaultCounterShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the counterList where comment contains UPDATED_COMMENT
        defaultCounterShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllCountersByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList where comment does not contain DEFAULT_COMMENT
        defaultCounterShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the counterList where comment does not contain UPDATED_COMMENT
        defaultCounterShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }


    @Test
    @Transactional
    public void getAllCountersByLotBailIsEqualToSomething() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);
        LotBail lotBail = LotBailResourceIT.createEntity(em);
        em.persist(lotBail);
        em.flush();
        counter.setLotBail(lotBail);
        counterRepository.saveAndFlush(counter);
        Long lotBailId = lotBail.getId();

        // Get all the counterList where lotBail equals to lotBailId
        defaultCounterShouldBeFound("lotBailId.equals=" + lotBailId);

        // Get all the counterList where lotBail equals to lotBailId + 1
        defaultCounterShouldNotBeFound("lotBailId.equals=" + (lotBailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCounterShouldBeFound(String filter) throws Exception {
        restCounterMockMvc.perform(get("/api/counters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(counter.getId().intValue())))
            .andExpect(jsonPath("$.[*].counterTitle").value(hasItem(DEFAULT_COUNTER_TITLE.toString())))
            .andExpect(jsonPath("$.[*].unity").value(hasItem(DEFAULT_UNITY.toString())))
            .andExpect(jsonPath("$.[*].decimal").value(hasItem(DEFAULT_DECIMAL)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

        // Check, that the count call also returns 1
        restCounterMockMvc.perform(get("/api/counters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCounterShouldNotBeFound(String filter) throws Exception {
        restCounterMockMvc.perform(get("/api/counters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCounterMockMvc.perform(get("/api/counters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCounter() throws Exception {
        // Get the counter
        restCounterMockMvc.perform(get("/api/counters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCounter() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        int databaseSizeBeforeUpdate = counterRepository.findAll().size();

        // Update the counter
        Counter updatedCounter = counterRepository.findById(counter.getId()).get();
        // Disconnect from session so that the updates on updatedCounter are not directly saved in db
        em.detach(updatedCounter);
        updatedCounter
            .counterTitle(UPDATED_COUNTER_TITLE)
            .unity(UPDATED_UNITY)
            .decimal(UPDATED_DECIMAL)
            .comment(UPDATED_COMMENT);
        CounterDTO counterDTO = counterMapper.toDto(updatedCounter);

        restCounterMockMvc.perform(put("/api/counters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterDTO)))
            .andExpect(status().isOk());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeUpdate);
        Counter testCounter = counterList.get(counterList.size() - 1);
        assertThat(testCounter.getCounterTitle()).isEqualTo(UPDATED_COUNTER_TITLE);
        assertThat(testCounter.getUnity()).isEqualTo(UPDATED_UNITY);
        assertThat(testCounter.getDecimal()).isEqualTo(UPDATED_DECIMAL);
        assertThat(testCounter.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCounter() throws Exception {
        int databaseSizeBeforeUpdate = counterRepository.findAll().size();

        // Create the Counter
        CounterDTO counterDTO = counterMapper.toDto(counter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCounterMockMvc.perform(put("/api/counters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCounter() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        int databaseSizeBeforeDelete = counterRepository.findAll().size();

        // Delete the counter
        restCounterMockMvc.perform(delete("/api/counters/{id}", counter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
