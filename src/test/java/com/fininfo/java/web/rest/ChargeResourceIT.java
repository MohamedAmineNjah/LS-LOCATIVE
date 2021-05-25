package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Charge;
import com.fininfo.java.domain.LotBail;
import com.fininfo.java.repository.ChargeRepository;
import com.fininfo.java.service.ChargeService;
import com.fininfo.java.service.dto.ChargeDTO;
import com.fininfo.java.service.mapper.ChargeMapper;
import com.fininfo.java.service.dto.ChargeCriteria;
import com.fininfo.java.service.ChargeQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChargeResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChargeResourceIT {

    private static final String DEFAULT_STATUS_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_CHARGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CHARGE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHARGE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CHARGE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_CHARGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_CHARGE = new BigDecimal(1 - 1);

    @Autowired
    private ChargeRepository chargeRepository;

    @Autowired
    private ChargeMapper chargeMapper;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private ChargeQueryService chargeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChargeMockMvc;

    private Charge charge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charge createEntity(EntityManager em) {
        Charge charge = new Charge()
            .statusCharge(DEFAULT_STATUS_CHARGE)
            .chargeDate(DEFAULT_CHARGE_DATE)
            .designation(DEFAULT_DESIGNATION)
            .reference(DEFAULT_REFERENCE)
            .amountCharge(DEFAULT_AMOUNT_CHARGE);
        return charge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charge createUpdatedEntity(EntityManager em) {
        Charge charge = new Charge()
            .statusCharge(UPDATED_STATUS_CHARGE)
            .chargeDate(UPDATED_CHARGE_DATE)
            .designation(UPDATED_DESIGNATION)
            .reference(UPDATED_REFERENCE)
            .amountCharge(UPDATED_AMOUNT_CHARGE);
        return charge;
    }

    @BeforeEach
    public void initTest() {
        charge = createEntity(em);
    }

    @Test
    @Transactional
    public void createCharge() throws Exception {
        int databaseSizeBeforeCreate = chargeRepository.findAll().size();
        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);
        restChargeMockMvc.perform(post("/api/charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chargeDTO)))
            .andExpect(status().isCreated());

        // Validate the Charge in the database
        List<Charge> chargeList = chargeRepository.findAll();
        assertThat(chargeList).hasSize(databaseSizeBeforeCreate + 1);
        Charge testCharge = chargeList.get(chargeList.size() - 1);
        assertThat(testCharge.getStatusCharge()).isEqualTo(DEFAULT_STATUS_CHARGE);
        assertThat(testCharge.getChargeDate()).isEqualTo(DEFAULT_CHARGE_DATE);
        assertThat(testCharge.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCharge.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCharge.getAmountCharge()).isEqualTo(DEFAULT_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void createChargeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chargeRepository.findAll().size();

        // Create the Charge with an existing ID
        charge.setId(1L);
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargeMockMvc.perform(post("/api/charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Charge in the database
        List<Charge> chargeList = chargeRepository.findAll();
        assertThat(chargeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCharges() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList
        restChargeMockMvc.perform(get("/api/charges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(charge.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusCharge").value(hasItem(DEFAULT_STATUS_CHARGE)))
            .andExpect(jsonPath("$.[*].chargeDate").value(hasItem(DEFAULT_CHARGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].amountCharge").value(hasItem(DEFAULT_AMOUNT_CHARGE.intValue())));
    }
    
    @Test
    @Transactional
    public void getCharge() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get the charge
        restChargeMockMvc.perform(get("/api/charges/{id}", charge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(charge.getId().intValue()))
            .andExpect(jsonPath("$.statusCharge").value(DEFAULT_STATUS_CHARGE))
            .andExpect(jsonPath("$.chargeDate").value(DEFAULT_CHARGE_DATE.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.amountCharge").value(DEFAULT_AMOUNT_CHARGE.intValue()));
    }


    @Test
    @Transactional
    public void getChargesByIdFiltering() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        Long id = charge.getId();

        defaultChargeShouldBeFound("id.equals=" + id);
        defaultChargeShouldNotBeFound("id.notEquals=" + id);

        defaultChargeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChargeShouldNotBeFound("id.greaterThan=" + id);

        defaultChargeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChargeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChargesByStatusChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where statusCharge equals to DEFAULT_STATUS_CHARGE
        defaultChargeShouldBeFound("statusCharge.equals=" + DEFAULT_STATUS_CHARGE);

        // Get all the chargeList where statusCharge equals to UPDATED_STATUS_CHARGE
        defaultChargeShouldNotBeFound("statusCharge.equals=" + UPDATED_STATUS_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByStatusChargeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where statusCharge not equals to DEFAULT_STATUS_CHARGE
        defaultChargeShouldNotBeFound("statusCharge.notEquals=" + DEFAULT_STATUS_CHARGE);

        // Get all the chargeList where statusCharge not equals to UPDATED_STATUS_CHARGE
        defaultChargeShouldBeFound("statusCharge.notEquals=" + UPDATED_STATUS_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByStatusChargeIsInShouldWork() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where statusCharge in DEFAULT_STATUS_CHARGE or UPDATED_STATUS_CHARGE
        defaultChargeShouldBeFound("statusCharge.in=" + DEFAULT_STATUS_CHARGE + "," + UPDATED_STATUS_CHARGE);

        // Get all the chargeList where statusCharge equals to UPDATED_STATUS_CHARGE
        defaultChargeShouldNotBeFound("statusCharge.in=" + UPDATED_STATUS_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByStatusChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where statusCharge is not null
        defaultChargeShouldBeFound("statusCharge.specified=true");

        // Get all the chargeList where statusCharge is null
        defaultChargeShouldNotBeFound("statusCharge.specified=false");
    }
                @Test
    @Transactional
    public void getAllChargesByStatusChargeContainsSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where statusCharge contains DEFAULT_STATUS_CHARGE
        defaultChargeShouldBeFound("statusCharge.contains=" + DEFAULT_STATUS_CHARGE);

        // Get all the chargeList where statusCharge contains UPDATED_STATUS_CHARGE
        defaultChargeShouldNotBeFound("statusCharge.contains=" + UPDATED_STATUS_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByStatusChargeNotContainsSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where statusCharge does not contain DEFAULT_STATUS_CHARGE
        defaultChargeShouldNotBeFound("statusCharge.doesNotContain=" + DEFAULT_STATUS_CHARGE);

        // Get all the chargeList where statusCharge does not contain UPDATED_STATUS_CHARGE
        defaultChargeShouldBeFound("statusCharge.doesNotContain=" + UPDATED_STATUS_CHARGE);
    }


    @Test
    @Transactional
    public void getAllChargesByChargeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate equals to DEFAULT_CHARGE_DATE
        defaultChargeShouldBeFound("chargeDate.equals=" + DEFAULT_CHARGE_DATE);

        // Get all the chargeList where chargeDate equals to UPDATED_CHARGE_DATE
        defaultChargeShouldNotBeFound("chargeDate.equals=" + UPDATED_CHARGE_DATE);
    }

    @Test
    @Transactional
    public void getAllChargesByChargeDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate not equals to DEFAULT_CHARGE_DATE
        defaultChargeShouldNotBeFound("chargeDate.notEquals=" + DEFAULT_CHARGE_DATE);

        // Get all the chargeList where chargeDate not equals to UPDATED_CHARGE_DATE
        defaultChargeShouldBeFound("chargeDate.notEquals=" + UPDATED_CHARGE_DATE);
    }

    @Test
    @Transactional
    public void getAllChargesByChargeDateIsInShouldWork() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate in DEFAULT_CHARGE_DATE or UPDATED_CHARGE_DATE
        defaultChargeShouldBeFound("chargeDate.in=" + DEFAULT_CHARGE_DATE + "," + UPDATED_CHARGE_DATE);

        // Get all the chargeList where chargeDate equals to UPDATED_CHARGE_DATE
        defaultChargeShouldNotBeFound("chargeDate.in=" + UPDATED_CHARGE_DATE);
    }

    @Test
    @Transactional
    public void getAllChargesByChargeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate is not null
        defaultChargeShouldBeFound("chargeDate.specified=true");

        // Get all the chargeList where chargeDate is null
        defaultChargeShouldNotBeFound("chargeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllChargesByChargeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate is greater than or equal to DEFAULT_CHARGE_DATE
        defaultChargeShouldBeFound("chargeDate.greaterThanOrEqual=" + DEFAULT_CHARGE_DATE);

        // Get all the chargeList where chargeDate is greater than or equal to UPDATED_CHARGE_DATE
        defaultChargeShouldNotBeFound("chargeDate.greaterThanOrEqual=" + UPDATED_CHARGE_DATE);
    }

    @Test
    @Transactional
    public void getAllChargesByChargeDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate is less than or equal to DEFAULT_CHARGE_DATE
        defaultChargeShouldBeFound("chargeDate.lessThanOrEqual=" + DEFAULT_CHARGE_DATE);

        // Get all the chargeList where chargeDate is less than or equal to SMALLER_CHARGE_DATE
        defaultChargeShouldNotBeFound("chargeDate.lessThanOrEqual=" + SMALLER_CHARGE_DATE);
    }

    @Test
    @Transactional
    public void getAllChargesByChargeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate is less than DEFAULT_CHARGE_DATE
        defaultChargeShouldNotBeFound("chargeDate.lessThan=" + DEFAULT_CHARGE_DATE);

        // Get all the chargeList where chargeDate is less than UPDATED_CHARGE_DATE
        defaultChargeShouldBeFound("chargeDate.lessThan=" + UPDATED_CHARGE_DATE);
    }

    @Test
    @Transactional
    public void getAllChargesByChargeDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where chargeDate is greater than DEFAULT_CHARGE_DATE
        defaultChargeShouldNotBeFound("chargeDate.greaterThan=" + DEFAULT_CHARGE_DATE);

        // Get all the chargeList where chargeDate is greater than SMALLER_CHARGE_DATE
        defaultChargeShouldBeFound("chargeDate.greaterThan=" + SMALLER_CHARGE_DATE);
    }


    @Test
    @Transactional
    public void getAllChargesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where designation equals to DEFAULT_DESIGNATION
        defaultChargeShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the chargeList where designation equals to UPDATED_DESIGNATION
        defaultChargeShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllChargesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where designation not equals to DEFAULT_DESIGNATION
        defaultChargeShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the chargeList where designation not equals to UPDATED_DESIGNATION
        defaultChargeShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllChargesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultChargeShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the chargeList where designation equals to UPDATED_DESIGNATION
        defaultChargeShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllChargesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where designation is not null
        defaultChargeShouldBeFound("designation.specified=true");

        // Get all the chargeList where designation is null
        defaultChargeShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllChargesByDesignationContainsSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where designation contains DEFAULT_DESIGNATION
        defaultChargeShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the chargeList where designation contains UPDATED_DESIGNATION
        defaultChargeShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllChargesByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where designation does not contain DEFAULT_DESIGNATION
        defaultChargeShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the chargeList where designation does not contain UPDATED_DESIGNATION
        defaultChargeShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllChargesByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where reference equals to DEFAULT_REFERENCE
        defaultChargeShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the chargeList where reference equals to UPDATED_REFERENCE
        defaultChargeShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllChargesByReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where reference not equals to DEFAULT_REFERENCE
        defaultChargeShouldNotBeFound("reference.notEquals=" + DEFAULT_REFERENCE);

        // Get all the chargeList where reference not equals to UPDATED_REFERENCE
        defaultChargeShouldBeFound("reference.notEquals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllChargesByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultChargeShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the chargeList where reference equals to UPDATED_REFERENCE
        defaultChargeShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllChargesByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where reference is not null
        defaultChargeShouldBeFound("reference.specified=true");

        // Get all the chargeList where reference is null
        defaultChargeShouldNotBeFound("reference.specified=false");
    }
                @Test
    @Transactional
    public void getAllChargesByReferenceContainsSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where reference contains DEFAULT_REFERENCE
        defaultChargeShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the chargeList where reference contains UPDATED_REFERENCE
        defaultChargeShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllChargesByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where reference does not contain DEFAULT_REFERENCE
        defaultChargeShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the chargeList where reference does not contain UPDATED_REFERENCE
        defaultChargeShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge equals to DEFAULT_AMOUNT_CHARGE
        defaultChargeShouldBeFound("amountCharge.equals=" + DEFAULT_AMOUNT_CHARGE);

        // Get all the chargeList where amountCharge equals to UPDATED_AMOUNT_CHARGE
        defaultChargeShouldNotBeFound("amountCharge.equals=" + UPDATED_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge not equals to DEFAULT_AMOUNT_CHARGE
        defaultChargeShouldNotBeFound("amountCharge.notEquals=" + DEFAULT_AMOUNT_CHARGE);

        // Get all the chargeList where amountCharge not equals to UPDATED_AMOUNT_CHARGE
        defaultChargeShouldBeFound("amountCharge.notEquals=" + UPDATED_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsInShouldWork() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge in DEFAULT_AMOUNT_CHARGE or UPDATED_AMOUNT_CHARGE
        defaultChargeShouldBeFound("amountCharge.in=" + DEFAULT_AMOUNT_CHARGE + "," + UPDATED_AMOUNT_CHARGE);

        // Get all the chargeList where amountCharge equals to UPDATED_AMOUNT_CHARGE
        defaultChargeShouldNotBeFound("amountCharge.in=" + UPDATED_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge is not null
        defaultChargeShouldBeFound("amountCharge.specified=true");

        // Get all the chargeList where amountCharge is null
        defaultChargeShouldNotBeFound("amountCharge.specified=false");
    }

    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge is greater than or equal to DEFAULT_AMOUNT_CHARGE
        defaultChargeShouldBeFound("amountCharge.greaterThanOrEqual=" + DEFAULT_AMOUNT_CHARGE);

        // Get all the chargeList where amountCharge is greater than or equal to UPDATED_AMOUNT_CHARGE
        defaultChargeShouldNotBeFound("amountCharge.greaterThanOrEqual=" + UPDATED_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge is less than or equal to DEFAULT_AMOUNT_CHARGE
        defaultChargeShouldBeFound("amountCharge.lessThanOrEqual=" + DEFAULT_AMOUNT_CHARGE);

        // Get all the chargeList where amountCharge is less than or equal to SMALLER_AMOUNT_CHARGE
        defaultChargeShouldNotBeFound("amountCharge.lessThanOrEqual=" + SMALLER_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsLessThanSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge is less than DEFAULT_AMOUNT_CHARGE
        defaultChargeShouldNotBeFound("amountCharge.lessThan=" + DEFAULT_AMOUNT_CHARGE);

        // Get all the chargeList where amountCharge is less than UPDATED_AMOUNT_CHARGE
        defaultChargeShouldBeFound("amountCharge.lessThan=" + UPDATED_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void getAllChargesByAmountChargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        // Get all the chargeList where amountCharge is greater than DEFAULT_AMOUNT_CHARGE
        defaultChargeShouldNotBeFound("amountCharge.greaterThan=" + DEFAULT_AMOUNT_CHARGE);

        // Get all the chargeList where amountCharge is greater than SMALLER_AMOUNT_CHARGE
        defaultChargeShouldBeFound("amountCharge.greaterThan=" + SMALLER_AMOUNT_CHARGE);
    }


    @Test
    @Transactional
    public void getAllChargesByLotBailIsEqualToSomething() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);
        LotBail lotBail = LotBailResourceIT.createEntity(em);
        em.persist(lotBail);
        em.flush();
        charge.setLotBail(lotBail);
        chargeRepository.saveAndFlush(charge);
        Long lotBailId = lotBail.getId();

        // Get all the chargeList where lotBail equals to lotBailId
        defaultChargeShouldBeFound("lotBailId.equals=" + lotBailId);

        // Get all the chargeList where lotBail equals to lotBailId + 1
        defaultChargeShouldNotBeFound("lotBailId.equals=" + (lotBailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChargeShouldBeFound(String filter) throws Exception {
        restChargeMockMvc.perform(get("/api/charges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(charge.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusCharge").value(hasItem(DEFAULT_STATUS_CHARGE)))
            .andExpect(jsonPath("$.[*].chargeDate").value(hasItem(DEFAULT_CHARGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].amountCharge").value(hasItem(DEFAULT_AMOUNT_CHARGE.intValue())));

        // Check, that the count call also returns 1
        restChargeMockMvc.perform(get("/api/charges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChargeShouldNotBeFound(String filter) throws Exception {
        restChargeMockMvc.perform(get("/api/charges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChargeMockMvc.perform(get("/api/charges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCharge() throws Exception {
        // Get the charge
        restChargeMockMvc.perform(get("/api/charges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharge() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        int databaseSizeBeforeUpdate = chargeRepository.findAll().size();

        // Update the charge
        Charge updatedCharge = chargeRepository.findById(charge.getId()).get();
        // Disconnect from session so that the updates on updatedCharge are not directly saved in db
        em.detach(updatedCharge);
        updatedCharge
            .statusCharge(UPDATED_STATUS_CHARGE)
            .chargeDate(UPDATED_CHARGE_DATE)
            .designation(UPDATED_DESIGNATION)
            .reference(UPDATED_REFERENCE)
            .amountCharge(UPDATED_AMOUNT_CHARGE);
        ChargeDTO chargeDTO = chargeMapper.toDto(updatedCharge);

        restChargeMockMvc.perform(put("/api/charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chargeDTO)))
            .andExpect(status().isOk());

        // Validate the Charge in the database
        List<Charge> chargeList = chargeRepository.findAll();
        assertThat(chargeList).hasSize(databaseSizeBeforeUpdate);
        Charge testCharge = chargeList.get(chargeList.size() - 1);
        assertThat(testCharge.getStatusCharge()).isEqualTo(UPDATED_STATUS_CHARGE);
        assertThat(testCharge.getChargeDate()).isEqualTo(UPDATED_CHARGE_DATE);
        assertThat(testCharge.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCharge.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCharge.getAmountCharge()).isEqualTo(UPDATED_AMOUNT_CHARGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCharge() throws Exception {
        int databaseSizeBeforeUpdate = chargeRepository.findAll().size();

        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargeMockMvc.perform(put("/api/charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Charge in the database
        List<Charge> chargeList = chargeRepository.findAll();
        assertThat(chargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCharge() throws Exception {
        // Initialize the database
        chargeRepository.saveAndFlush(charge);

        int databaseSizeBeforeDelete = chargeRepository.findAll().size();

        // Delete the charge
        restChargeMockMvc.perform(delete("/api/charges/{id}", charge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Charge> chargeList = chargeRepository.findAll();
        assertThat(chargeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
