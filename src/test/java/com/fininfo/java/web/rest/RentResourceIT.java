package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Rent;
import com.fininfo.java.domain.Periodicity;
import com.fininfo.java.domain.Schedule;
import com.fininfo.java.repository.RentRepository;
import com.fininfo.java.service.RentService;
import com.fininfo.java.service.dto.RentDTO;
import com.fininfo.java.service.mapper.RentMapper;
import com.fininfo.java.service.dto.RentCriteria;
import com.fininfo.java.service.RentQueryService;

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
 * Integration tests for the {@link RentResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RentResourceIT {

    private static final BigDecimal DEFAULT_NOMINAL_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NOMINAL_VALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_NOMINAL_VALUE = new BigDecimal(1 - 1);

    private static final Double DEFAULT_TAUX = 1D;
    private static final Double UPDATED_TAUX = 2D;
    private static final Double SMALLER_TAUX = 1D - 1D;

    private static final String DEFAULT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_MODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPITAL = 1;
    private static final Integer UPDATED_CAPITAL = 2;
    private static final Integer SMALLER_CAPITAL = 1 - 1;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_REFUND_AMOUNT = 1;
    private static final Integer UPDATED_REFUND_AMOUNT = 2;
    private static final Integer SMALLER_REFUND_AMOUNT = 1 - 1;

    private static final Boolean DEFAULT_START_EXCLUDED = false;
    private static final Boolean UPDATED_START_EXCLUDED = true;

    private static final Boolean DEFAULT_MONTH_END = false;
    private static final Boolean UPDATED_MONTH_END = true;

    private static final Boolean DEFAULT_END_EXCLUDED = false;
    private static final Boolean UPDATED_END_EXCLUDED = true;

    private static final Integer DEFAULT_RATE_VALUE = 1;
    private static final Integer UPDATED_RATE_VALUE = 2;
    private static final Integer SMALLER_RATE_VALUE = 1 - 1;

    private static final Integer DEFAULT_COUPON_DECIMAL_NUMBER = 1;
    private static final Integer UPDATED_COUPON_DECIMAL_NUMBER = 2;
    private static final Integer SMALLER_COUPON_DECIMAL_NUMBER = 1 - 1;

    private static final LocalDate DEFAULT_COUPON_FIRST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COUPON_FIRST_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COUPON_FIRST_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_COUPON_LAST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COUPON_LAST_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COUPON_LAST_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_REFUND_FIRST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REFUND_FIRST_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REFUND_FIRST_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_REFUND_DECIMAL_NUMBER = 1;
    private static final Integer UPDATED_REFUND_DECIMAL_NUMBER = 2;
    private static final Integer SMALLER_REFUND_DECIMAL_NUMBER = 1 - 1;

    private static final LocalDate DEFAULT_REFUND_LAST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REFUND_LAST_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REFUND_LAST_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RentMapper rentMapper;

    @Autowired
    private RentService rentService;

    @Autowired
    private RentQueryService rentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRentMockMvc;

    private Rent rent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rent createEntity(EntityManager em) {
        Rent rent = new Rent()
            .nominalValue(DEFAULT_NOMINAL_VALUE)
            .taux(DEFAULT_TAUX)
            .mode(DEFAULT_MODE)
            .capital(DEFAULT_CAPITAL)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .refundAmount(DEFAULT_REFUND_AMOUNT)
            .startExcluded(DEFAULT_START_EXCLUDED)
            .monthEnd(DEFAULT_MONTH_END)
            .endExcluded(DEFAULT_END_EXCLUDED)
            .rateValue(DEFAULT_RATE_VALUE)
            .couponDecimalNumber(DEFAULT_COUPON_DECIMAL_NUMBER)
            .couponFirstDate(DEFAULT_COUPON_FIRST_DATE)
            .couponLastDate(DEFAULT_COUPON_LAST_DATE)
            .refundFirstDate(DEFAULT_REFUND_FIRST_DATE)
            .refundDecimalNumber(DEFAULT_REFUND_DECIMAL_NUMBER)
            .refundLastDate(DEFAULT_REFUND_LAST_DATE);
        return rent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rent createUpdatedEntity(EntityManager em) {
        Rent rent = new Rent()
            .nominalValue(UPDATED_NOMINAL_VALUE)
            .taux(UPDATED_TAUX)
            .mode(UPDATED_MODE)
            .capital(UPDATED_CAPITAL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .refundAmount(UPDATED_REFUND_AMOUNT)
            .startExcluded(UPDATED_START_EXCLUDED)
            .monthEnd(UPDATED_MONTH_END)
            .endExcluded(UPDATED_END_EXCLUDED)
            .rateValue(UPDATED_RATE_VALUE)
            .couponDecimalNumber(UPDATED_COUPON_DECIMAL_NUMBER)
            .couponFirstDate(UPDATED_COUPON_FIRST_DATE)
            .couponLastDate(UPDATED_COUPON_LAST_DATE)
            .refundFirstDate(UPDATED_REFUND_FIRST_DATE)
            .refundDecimalNumber(UPDATED_REFUND_DECIMAL_NUMBER)
            .refundLastDate(UPDATED_REFUND_LAST_DATE);
        return rent;
    }

    @BeforeEach
    public void initTest() {
        rent = createEntity(em);
    }

    @Test
    @Transactional
    public void createRent() throws Exception {
        int databaseSizeBeforeCreate = rentRepository.findAll().size();
        // Create the Rent
        RentDTO rentDTO = rentMapper.toDto(rent);
        restRentMockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rentDTO)))
            .andExpect(status().isCreated());

        // Validate the Rent in the database
        List<Rent> rentList = rentRepository.findAll();
        assertThat(rentList).hasSize(databaseSizeBeforeCreate + 1);
        Rent testRent = rentList.get(rentList.size() - 1);
        assertThat(testRent.getNominalValue()).isEqualTo(DEFAULT_NOMINAL_VALUE);
        assertThat(testRent.getTaux()).isEqualTo(DEFAULT_TAUX);
        assertThat(testRent.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testRent.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testRent.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRent.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRent.getRefundAmount()).isEqualTo(DEFAULT_REFUND_AMOUNT);
        assertThat(testRent.isStartExcluded()).isEqualTo(DEFAULT_START_EXCLUDED);
        assertThat(testRent.isMonthEnd()).isEqualTo(DEFAULT_MONTH_END);
        assertThat(testRent.isEndExcluded()).isEqualTo(DEFAULT_END_EXCLUDED);
        assertThat(testRent.getRateValue()).isEqualTo(DEFAULT_RATE_VALUE);
        assertThat(testRent.getCouponDecimalNumber()).isEqualTo(DEFAULT_COUPON_DECIMAL_NUMBER);
        assertThat(testRent.getCouponFirstDate()).isEqualTo(DEFAULT_COUPON_FIRST_DATE);
        assertThat(testRent.getCouponLastDate()).isEqualTo(DEFAULT_COUPON_LAST_DATE);
        assertThat(testRent.getRefundFirstDate()).isEqualTo(DEFAULT_REFUND_FIRST_DATE);
        assertThat(testRent.getRefundDecimalNumber()).isEqualTo(DEFAULT_REFUND_DECIMAL_NUMBER);
        assertThat(testRent.getRefundLastDate()).isEqualTo(DEFAULT_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void createRentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rentRepository.findAll().size();

        // Create the Rent with an existing ID
        rent.setId(1L);
        RentDTO rentDTO = rentMapper.toDto(rent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentMockMvc.perform(post("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rent in the database
        List<Rent> rentList = rentRepository.findAll();
        assertThat(rentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRents() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList
        restRentMockMvc.perform(get("/api/rents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rent.getId().intValue())))
            .andExpect(jsonPath("$.[*].nominalValue").value(hasItem(DEFAULT_NOMINAL_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX.doubleValue())))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].refundAmount").value(hasItem(DEFAULT_REFUND_AMOUNT)))
            .andExpect(jsonPath("$.[*].startExcluded").value(hasItem(DEFAULT_START_EXCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].monthEnd").value(hasItem(DEFAULT_MONTH_END.booleanValue())))
            .andExpect(jsonPath("$.[*].endExcluded").value(hasItem(DEFAULT_END_EXCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].rateValue").value(hasItem(DEFAULT_RATE_VALUE)))
            .andExpect(jsonPath("$.[*].couponDecimalNumber").value(hasItem(DEFAULT_COUPON_DECIMAL_NUMBER)))
            .andExpect(jsonPath("$.[*].couponFirstDate").value(hasItem(DEFAULT_COUPON_FIRST_DATE.toString())))
            .andExpect(jsonPath("$.[*].couponLastDate").value(hasItem(DEFAULT_COUPON_LAST_DATE.toString())))
            .andExpect(jsonPath("$.[*].refundFirstDate").value(hasItem(DEFAULT_REFUND_FIRST_DATE.toString())))
            .andExpect(jsonPath("$.[*].refundDecimalNumber").value(hasItem(DEFAULT_REFUND_DECIMAL_NUMBER)))
            .andExpect(jsonPath("$.[*].refundLastDate").value(hasItem(DEFAULT_REFUND_LAST_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getRent() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get the rent
        restRentMockMvc.perform(get("/api/rents/{id}", rent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rent.getId().intValue()))
            .andExpect(jsonPath("$.nominalValue").value(DEFAULT_NOMINAL_VALUE.intValue()))
            .andExpect(jsonPath("$.taux").value(DEFAULT_TAUX.doubleValue()))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.refundAmount").value(DEFAULT_REFUND_AMOUNT))
            .andExpect(jsonPath("$.startExcluded").value(DEFAULT_START_EXCLUDED.booleanValue()))
            .andExpect(jsonPath("$.monthEnd").value(DEFAULT_MONTH_END.booleanValue()))
            .andExpect(jsonPath("$.endExcluded").value(DEFAULT_END_EXCLUDED.booleanValue()))
            .andExpect(jsonPath("$.rateValue").value(DEFAULT_RATE_VALUE))
            .andExpect(jsonPath("$.couponDecimalNumber").value(DEFAULT_COUPON_DECIMAL_NUMBER))
            .andExpect(jsonPath("$.couponFirstDate").value(DEFAULT_COUPON_FIRST_DATE.toString()))
            .andExpect(jsonPath("$.couponLastDate").value(DEFAULT_COUPON_LAST_DATE.toString()))
            .andExpect(jsonPath("$.refundFirstDate").value(DEFAULT_REFUND_FIRST_DATE.toString()))
            .andExpect(jsonPath("$.refundDecimalNumber").value(DEFAULT_REFUND_DECIMAL_NUMBER))
            .andExpect(jsonPath("$.refundLastDate").value(DEFAULT_REFUND_LAST_DATE.toString()));
    }


    @Test
    @Transactional
    public void getRentsByIdFiltering() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        Long id = rent.getId();

        defaultRentShouldBeFound("id.equals=" + id);
        defaultRentShouldNotBeFound("id.notEquals=" + id);

        defaultRentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRentShouldNotBeFound("id.greaterThan=" + id);

        defaultRentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRentsByNominalValueIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue equals to DEFAULT_NOMINAL_VALUE
        defaultRentShouldBeFound("nominalValue.equals=" + DEFAULT_NOMINAL_VALUE);

        // Get all the rentList where nominalValue equals to UPDATED_NOMINAL_VALUE
        defaultRentShouldNotBeFound("nominalValue.equals=" + UPDATED_NOMINAL_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByNominalValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue not equals to DEFAULT_NOMINAL_VALUE
        defaultRentShouldNotBeFound("nominalValue.notEquals=" + DEFAULT_NOMINAL_VALUE);

        // Get all the rentList where nominalValue not equals to UPDATED_NOMINAL_VALUE
        defaultRentShouldBeFound("nominalValue.notEquals=" + UPDATED_NOMINAL_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByNominalValueIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue in DEFAULT_NOMINAL_VALUE or UPDATED_NOMINAL_VALUE
        defaultRentShouldBeFound("nominalValue.in=" + DEFAULT_NOMINAL_VALUE + "," + UPDATED_NOMINAL_VALUE);

        // Get all the rentList where nominalValue equals to UPDATED_NOMINAL_VALUE
        defaultRentShouldNotBeFound("nominalValue.in=" + UPDATED_NOMINAL_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByNominalValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue is not null
        defaultRentShouldBeFound("nominalValue.specified=true");

        // Get all the rentList where nominalValue is null
        defaultRentShouldNotBeFound("nominalValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByNominalValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue is greater than or equal to DEFAULT_NOMINAL_VALUE
        defaultRentShouldBeFound("nominalValue.greaterThanOrEqual=" + DEFAULT_NOMINAL_VALUE);

        // Get all the rentList where nominalValue is greater than or equal to UPDATED_NOMINAL_VALUE
        defaultRentShouldNotBeFound("nominalValue.greaterThanOrEqual=" + UPDATED_NOMINAL_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByNominalValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue is less than or equal to DEFAULT_NOMINAL_VALUE
        defaultRentShouldBeFound("nominalValue.lessThanOrEqual=" + DEFAULT_NOMINAL_VALUE);

        // Get all the rentList where nominalValue is less than or equal to SMALLER_NOMINAL_VALUE
        defaultRentShouldNotBeFound("nominalValue.lessThanOrEqual=" + SMALLER_NOMINAL_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByNominalValueIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue is less than DEFAULT_NOMINAL_VALUE
        defaultRentShouldNotBeFound("nominalValue.lessThan=" + DEFAULT_NOMINAL_VALUE);

        // Get all the rentList where nominalValue is less than UPDATED_NOMINAL_VALUE
        defaultRentShouldBeFound("nominalValue.lessThan=" + UPDATED_NOMINAL_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByNominalValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where nominalValue is greater than DEFAULT_NOMINAL_VALUE
        defaultRentShouldNotBeFound("nominalValue.greaterThan=" + DEFAULT_NOMINAL_VALUE);

        // Get all the rentList where nominalValue is greater than SMALLER_NOMINAL_VALUE
        defaultRentShouldBeFound("nominalValue.greaterThan=" + SMALLER_NOMINAL_VALUE);
    }


    @Test
    @Transactional
    public void getAllRentsByTauxIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux equals to DEFAULT_TAUX
        defaultRentShouldBeFound("taux.equals=" + DEFAULT_TAUX);

        // Get all the rentList where taux equals to UPDATED_TAUX
        defaultRentShouldNotBeFound("taux.equals=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllRentsByTauxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux not equals to DEFAULT_TAUX
        defaultRentShouldNotBeFound("taux.notEquals=" + DEFAULT_TAUX);

        // Get all the rentList where taux not equals to UPDATED_TAUX
        defaultRentShouldBeFound("taux.notEquals=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllRentsByTauxIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux in DEFAULT_TAUX or UPDATED_TAUX
        defaultRentShouldBeFound("taux.in=" + DEFAULT_TAUX + "," + UPDATED_TAUX);

        // Get all the rentList where taux equals to UPDATED_TAUX
        defaultRentShouldNotBeFound("taux.in=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllRentsByTauxIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux is not null
        defaultRentShouldBeFound("taux.specified=true");

        // Get all the rentList where taux is null
        defaultRentShouldNotBeFound("taux.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByTauxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux is greater than or equal to DEFAULT_TAUX
        defaultRentShouldBeFound("taux.greaterThanOrEqual=" + DEFAULT_TAUX);

        // Get all the rentList where taux is greater than or equal to UPDATED_TAUX
        defaultRentShouldNotBeFound("taux.greaterThanOrEqual=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllRentsByTauxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux is less than or equal to DEFAULT_TAUX
        defaultRentShouldBeFound("taux.lessThanOrEqual=" + DEFAULT_TAUX);

        // Get all the rentList where taux is less than or equal to SMALLER_TAUX
        defaultRentShouldNotBeFound("taux.lessThanOrEqual=" + SMALLER_TAUX);
    }

    @Test
    @Transactional
    public void getAllRentsByTauxIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux is less than DEFAULT_TAUX
        defaultRentShouldNotBeFound("taux.lessThan=" + DEFAULT_TAUX);

        // Get all the rentList where taux is less than UPDATED_TAUX
        defaultRentShouldBeFound("taux.lessThan=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllRentsByTauxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where taux is greater than DEFAULT_TAUX
        defaultRentShouldNotBeFound("taux.greaterThan=" + DEFAULT_TAUX);

        // Get all the rentList where taux is greater than SMALLER_TAUX
        defaultRentShouldBeFound("taux.greaterThan=" + SMALLER_TAUX);
    }


    @Test
    @Transactional
    public void getAllRentsByModeIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where mode equals to DEFAULT_MODE
        defaultRentShouldBeFound("mode.equals=" + DEFAULT_MODE);

        // Get all the rentList where mode equals to UPDATED_MODE
        defaultRentShouldNotBeFound("mode.equals=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    public void getAllRentsByModeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where mode not equals to DEFAULT_MODE
        defaultRentShouldNotBeFound("mode.notEquals=" + DEFAULT_MODE);

        // Get all the rentList where mode not equals to UPDATED_MODE
        defaultRentShouldBeFound("mode.notEquals=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    public void getAllRentsByModeIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where mode in DEFAULT_MODE or UPDATED_MODE
        defaultRentShouldBeFound("mode.in=" + DEFAULT_MODE + "," + UPDATED_MODE);

        // Get all the rentList where mode equals to UPDATED_MODE
        defaultRentShouldNotBeFound("mode.in=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    public void getAllRentsByModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where mode is not null
        defaultRentShouldBeFound("mode.specified=true");

        // Get all the rentList where mode is null
        defaultRentShouldNotBeFound("mode.specified=false");
    }
                @Test
    @Transactional
    public void getAllRentsByModeContainsSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where mode contains DEFAULT_MODE
        defaultRentShouldBeFound("mode.contains=" + DEFAULT_MODE);

        // Get all the rentList where mode contains UPDATED_MODE
        defaultRentShouldNotBeFound("mode.contains=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    public void getAllRentsByModeNotContainsSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where mode does not contain DEFAULT_MODE
        defaultRentShouldNotBeFound("mode.doesNotContain=" + DEFAULT_MODE);

        // Get all the rentList where mode does not contain UPDATED_MODE
        defaultRentShouldBeFound("mode.doesNotContain=" + UPDATED_MODE);
    }


    @Test
    @Transactional
    public void getAllRentsByCapitalIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital equals to DEFAULT_CAPITAL
        defaultRentShouldBeFound("capital.equals=" + DEFAULT_CAPITAL);

        // Get all the rentList where capital equals to UPDATED_CAPITAL
        defaultRentShouldNotBeFound("capital.equals=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRentsByCapitalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital not equals to DEFAULT_CAPITAL
        defaultRentShouldNotBeFound("capital.notEquals=" + DEFAULT_CAPITAL);

        // Get all the rentList where capital not equals to UPDATED_CAPITAL
        defaultRentShouldBeFound("capital.notEquals=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRentsByCapitalIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital in DEFAULT_CAPITAL or UPDATED_CAPITAL
        defaultRentShouldBeFound("capital.in=" + DEFAULT_CAPITAL + "," + UPDATED_CAPITAL);

        // Get all the rentList where capital equals to UPDATED_CAPITAL
        defaultRentShouldNotBeFound("capital.in=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRentsByCapitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital is not null
        defaultRentShouldBeFound("capital.specified=true");

        // Get all the rentList where capital is null
        defaultRentShouldNotBeFound("capital.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByCapitalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital is greater than or equal to DEFAULT_CAPITAL
        defaultRentShouldBeFound("capital.greaterThanOrEqual=" + DEFAULT_CAPITAL);

        // Get all the rentList where capital is greater than or equal to UPDATED_CAPITAL
        defaultRentShouldNotBeFound("capital.greaterThanOrEqual=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRentsByCapitalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital is less than or equal to DEFAULT_CAPITAL
        defaultRentShouldBeFound("capital.lessThanOrEqual=" + DEFAULT_CAPITAL);

        // Get all the rentList where capital is less than or equal to SMALLER_CAPITAL
        defaultRentShouldNotBeFound("capital.lessThanOrEqual=" + SMALLER_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRentsByCapitalIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital is less than DEFAULT_CAPITAL
        defaultRentShouldNotBeFound("capital.lessThan=" + DEFAULT_CAPITAL);

        // Get all the rentList where capital is less than UPDATED_CAPITAL
        defaultRentShouldBeFound("capital.lessThan=" + UPDATED_CAPITAL);
    }

    @Test
    @Transactional
    public void getAllRentsByCapitalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where capital is greater than DEFAULT_CAPITAL
        defaultRentShouldNotBeFound("capital.greaterThan=" + DEFAULT_CAPITAL);

        // Get all the rentList where capital is greater than SMALLER_CAPITAL
        defaultRentShouldBeFound("capital.greaterThan=" + SMALLER_CAPITAL);
    }


    @Test
    @Transactional
    public void getAllRentsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate equals to DEFAULT_START_DATE
        defaultRentShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the rentList where startDate equals to UPDATED_START_DATE
        defaultRentShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate not equals to DEFAULT_START_DATE
        defaultRentShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the rentList where startDate not equals to UPDATED_START_DATE
        defaultRentShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultRentShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the rentList where startDate equals to UPDATED_START_DATE
        defaultRentShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate is not null
        defaultRentShouldBeFound("startDate.specified=true");

        // Get all the rentList where startDate is null
        defaultRentShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultRentShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the rentList where startDate is greater than or equal to UPDATED_START_DATE
        defaultRentShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate is less than or equal to DEFAULT_START_DATE
        defaultRentShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the rentList where startDate is less than or equal to SMALLER_START_DATE
        defaultRentShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate is less than DEFAULT_START_DATE
        defaultRentShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the rentList where startDate is less than UPDATED_START_DATE
        defaultRentShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startDate is greater than DEFAULT_START_DATE
        defaultRentShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the rentList where startDate is greater than SMALLER_START_DATE
        defaultRentShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllRentsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate equals to DEFAULT_END_DATE
        defaultRentShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the rentList where endDate equals to UPDATED_END_DATE
        defaultRentShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate not equals to DEFAULT_END_DATE
        defaultRentShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the rentList where endDate not equals to UPDATED_END_DATE
        defaultRentShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultRentShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the rentList where endDate equals to UPDATED_END_DATE
        defaultRentShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate is not null
        defaultRentShouldBeFound("endDate.specified=true");

        // Get all the rentList where endDate is null
        defaultRentShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultRentShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the rentList where endDate is greater than or equal to UPDATED_END_DATE
        defaultRentShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate is less than or equal to DEFAULT_END_DATE
        defaultRentShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the rentList where endDate is less than or equal to SMALLER_END_DATE
        defaultRentShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate is less than DEFAULT_END_DATE
        defaultRentShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the rentList where endDate is less than UPDATED_END_DATE
        defaultRentShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endDate is greater than DEFAULT_END_DATE
        defaultRentShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the rentList where endDate is greater than SMALLER_END_DATE
        defaultRentShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount equals to DEFAULT_REFUND_AMOUNT
        defaultRentShouldBeFound("refundAmount.equals=" + DEFAULT_REFUND_AMOUNT);

        // Get all the rentList where refundAmount equals to UPDATED_REFUND_AMOUNT
        defaultRentShouldNotBeFound("refundAmount.equals=" + UPDATED_REFUND_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount not equals to DEFAULT_REFUND_AMOUNT
        defaultRentShouldNotBeFound("refundAmount.notEquals=" + DEFAULT_REFUND_AMOUNT);

        // Get all the rentList where refundAmount not equals to UPDATED_REFUND_AMOUNT
        defaultRentShouldBeFound("refundAmount.notEquals=" + UPDATED_REFUND_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount in DEFAULT_REFUND_AMOUNT or UPDATED_REFUND_AMOUNT
        defaultRentShouldBeFound("refundAmount.in=" + DEFAULT_REFUND_AMOUNT + "," + UPDATED_REFUND_AMOUNT);

        // Get all the rentList where refundAmount equals to UPDATED_REFUND_AMOUNT
        defaultRentShouldNotBeFound("refundAmount.in=" + UPDATED_REFUND_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount is not null
        defaultRentShouldBeFound("refundAmount.specified=true");

        // Get all the rentList where refundAmount is null
        defaultRentShouldNotBeFound("refundAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount is greater than or equal to DEFAULT_REFUND_AMOUNT
        defaultRentShouldBeFound("refundAmount.greaterThanOrEqual=" + DEFAULT_REFUND_AMOUNT);

        // Get all the rentList where refundAmount is greater than or equal to UPDATED_REFUND_AMOUNT
        defaultRentShouldNotBeFound("refundAmount.greaterThanOrEqual=" + UPDATED_REFUND_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount is less than or equal to DEFAULT_REFUND_AMOUNT
        defaultRentShouldBeFound("refundAmount.lessThanOrEqual=" + DEFAULT_REFUND_AMOUNT);

        // Get all the rentList where refundAmount is less than or equal to SMALLER_REFUND_AMOUNT
        defaultRentShouldNotBeFound("refundAmount.lessThanOrEqual=" + SMALLER_REFUND_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount is less than DEFAULT_REFUND_AMOUNT
        defaultRentShouldNotBeFound("refundAmount.lessThan=" + DEFAULT_REFUND_AMOUNT);

        // Get all the rentList where refundAmount is less than UPDATED_REFUND_AMOUNT
        defaultRentShouldBeFound("refundAmount.lessThan=" + UPDATED_REFUND_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundAmount is greater than DEFAULT_REFUND_AMOUNT
        defaultRentShouldNotBeFound("refundAmount.greaterThan=" + DEFAULT_REFUND_AMOUNT);

        // Get all the rentList where refundAmount is greater than SMALLER_REFUND_AMOUNT
        defaultRentShouldBeFound("refundAmount.greaterThan=" + SMALLER_REFUND_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllRentsByStartExcludedIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startExcluded equals to DEFAULT_START_EXCLUDED
        defaultRentShouldBeFound("startExcluded.equals=" + DEFAULT_START_EXCLUDED);

        // Get all the rentList where startExcluded equals to UPDATED_START_EXCLUDED
        defaultRentShouldNotBeFound("startExcluded.equals=" + UPDATED_START_EXCLUDED);
    }

    @Test
    @Transactional
    public void getAllRentsByStartExcludedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startExcluded not equals to DEFAULT_START_EXCLUDED
        defaultRentShouldNotBeFound("startExcluded.notEquals=" + DEFAULT_START_EXCLUDED);

        // Get all the rentList where startExcluded not equals to UPDATED_START_EXCLUDED
        defaultRentShouldBeFound("startExcluded.notEquals=" + UPDATED_START_EXCLUDED);
    }

    @Test
    @Transactional
    public void getAllRentsByStartExcludedIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startExcluded in DEFAULT_START_EXCLUDED or UPDATED_START_EXCLUDED
        defaultRentShouldBeFound("startExcluded.in=" + DEFAULT_START_EXCLUDED + "," + UPDATED_START_EXCLUDED);

        // Get all the rentList where startExcluded equals to UPDATED_START_EXCLUDED
        defaultRentShouldNotBeFound("startExcluded.in=" + UPDATED_START_EXCLUDED);
    }

    @Test
    @Transactional
    public void getAllRentsByStartExcludedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where startExcluded is not null
        defaultRentShouldBeFound("startExcluded.specified=true");

        // Get all the rentList where startExcluded is null
        defaultRentShouldNotBeFound("startExcluded.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByMonthEndIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where monthEnd equals to DEFAULT_MONTH_END
        defaultRentShouldBeFound("monthEnd.equals=" + DEFAULT_MONTH_END);

        // Get all the rentList where monthEnd equals to UPDATED_MONTH_END
        defaultRentShouldNotBeFound("monthEnd.equals=" + UPDATED_MONTH_END);
    }

    @Test
    @Transactional
    public void getAllRentsByMonthEndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where monthEnd not equals to DEFAULT_MONTH_END
        defaultRentShouldNotBeFound("monthEnd.notEquals=" + DEFAULT_MONTH_END);

        // Get all the rentList where monthEnd not equals to UPDATED_MONTH_END
        defaultRentShouldBeFound("monthEnd.notEquals=" + UPDATED_MONTH_END);
    }

    @Test
    @Transactional
    public void getAllRentsByMonthEndIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where monthEnd in DEFAULT_MONTH_END or UPDATED_MONTH_END
        defaultRentShouldBeFound("monthEnd.in=" + DEFAULT_MONTH_END + "," + UPDATED_MONTH_END);

        // Get all the rentList where monthEnd equals to UPDATED_MONTH_END
        defaultRentShouldNotBeFound("monthEnd.in=" + UPDATED_MONTH_END);
    }

    @Test
    @Transactional
    public void getAllRentsByMonthEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where monthEnd is not null
        defaultRentShouldBeFound("monthEnd.specified=true");

        // Get all the rentList where monthEnd is null
        defaultRentShouldNotBeFound("monthEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByEndExcludedIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endExcluded equals to DEFAULT_END_EXCLUDED
        defaultRentShouldBeFound("endExcluded.equals=" + DEFAULT_END_EXCLUDED);

        // Get all the rentList where endExcluded equals to UPDATED_END_EXCLUDED
        defaultRentShouldNotBeFound("endExcluded.equals=" + UPDATED_END_EXCLUDED);
    }

    @Test
    @Transactional
    public void getAllRentsByEndExcludedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endExcluded not equals to DEFAULT_END_EXCLUDED
        defaultRentShouldNotBeFound("endExcluded.notEquals=" + DEFAULT_END_EXCLUDED);

        // Get all the rentList where endExcluded not equals to UPDATED_END_EXCLUDED
        defaultRentShouldBeFound("endExcluded.notEquals=" + UPDATED_END_EXCLUDED);
    }

    @Test
    @Transactional
    public void getAllRentsByEndExcludedIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endExcluded in DEFAULT_END_EXCLUDED or UPDATED_END_EXCLUDED
        defaultRentShouldBeFound("endExcluded.in=" + DEFAULT_END_EXCLUDED + "," + UPDATED_END_EXCLUDED);

        // Get all the rentList where endExcluded equals to UPDATED_END_EXCLUDED
        defaultRentShouldNotBeFound("endExcluded.in=" + UPDATED_END_EXCLUDED);
    }

    @Test
    @Transactional
    public void getAllRentsByEndExcludedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where endExcluded is not null
        defaultRentShouldBeFound("endExcluded.specified=true");

        // Get all the rentList where endExcluded is null
        defaultRentShouldNotBeFound("endExcluded.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue equals to DEFAULT_RATE_VALUE
        defaultRentShouldBeFound("rateValue.equals=" + DEFAULT_RATE_VALUE);

        // Get all the rentList where rateValue equals to UPDATED_RATE_VALUE
        defaultRentShouldNotBeFound("rateValue.equals=" + UPDATED_RATE_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue not equals to DEFAULT_RATE_VALUE
        defaultRentShouldNotBeFound("rateValue.notEquals=" + DEFAULT_RATE_VALUE);

        // Get all the rentList where rateValue not equals to UPDATED_RATE_VALUE
        defaultRentShouldBeFound("rateValue.notEquals=" + UPDATED_RATE_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue in DEFAULT_RATE_VALUE or UPDATED_RATE_VALUE
        defaultRentShouldBeFound("rateValue.in=" + DEFAULT_RATE_VALUE + "," + UPDATED_RATE_VALUE);

        // Get all the rentList where rateValue equals to UPDATED_RATE_VALUE
        defaultRentShouldNotBeFound("rateValue.in=" + UPDATED_RATE_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue is not null
        defaultRentShouldBeFound("rateValue.specified=true");

        // Get all the rentList where rateValue is null
        defaultRentShouldNotBeFound("rateValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue is greater than or equal to DEFAULT_RATE_VALUE
        defaultRentShouldBeFound("rateValue.greaterThanOrEqual=" + DEFAULT_RATE_VALUE);

        // Get all the rentList where rateValue is greater than or equal to UPDATED_RATE_VALUE
        defaultRentShouldNotBeFound("rateValue.greaterThanOrEqual=" + UPDATED_RATE_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue is less than or equal to DEFAULT_RATE_VALUE
        defaultRentShouldBeFound("rateValue.lessThanOrEqual=" + DEFAULT_RATE_VALUE);

        // Get all the rentList where rateValue is less than or equal to SMALLER_RATE_VALUE
        defaultRentShouldNotBeFound("rateValue.lessThanOrEqual=" + SMALLER_RATE_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue is less than DEFAULT_RATE_VALUE
        defaultRentShouldNotBeFound("rateValue.lessThan=" + DEFAULT_RATE_VALUE);

        // Get all the rentList where rateValue is less than UPDATED_RATE_VALUE
        defaultRentShouldBeFound("rateValue.lessThan=" + UPDATED_RATE_VALUE);
    }

    @Test
    @Transactional
    public void getAllRentsByRateValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where rateValue is greater than DEFAULT_RATE_VALUE
        defaultRentShouldNotBeFound("rateValue.greaterThan=" + DEFAULT_RATE_VALUE);

        // Get all the rentList where rateValue is greater than SMALLER_RATE_VALUE
        defaultRentShouldBeFound("rateValue.greaterThan=" + SMALLER_RATE_VALUE);
    }


    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber equals to DEFAULT_COUPON_DECIMAL_NUMBER
        defaultRentShouldBeFound("couponDecimalNumber.equals=" + DEFAULT_COUPON_DECIMAL_NUMBER);

        // Get all the rentList where couponDecimalNumber equals to UPDATED_COUPON_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("couponDecimalNumber.equals=" + UPDATED_COUPON_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber not equals to DEFAULT_COUPON_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("couponDecimalNumber.notEquals=" + DEFAULT_COUPON_DECIMAL_NUMBER);

        // Get all the rentList where couponDecimalNumber not equals to UPDATED_COUPON_DECIMAL_NUMBER
        defaultRentShouldBeFound("couponDecimalNumber.notEquals=" + UPDATED_COUPON_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber in DEFAULT_COUPON_DECIMAL_NUMBER or UPDATED_COUPON_DECIMAL_NUMBER
        defaultRentShouldBeFound("couponDecimalNumber.in=" + DEFAULT_COUPON_DECIMAL_NUMBER + "," + UPDATED_COUPON_DECIMAL_NUMBER);

        // Get all the rentList where couponDecimalNumber equals to UPDATED_COUPON_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("couponDecimalNumber.in=" + UPDATED_COUPON_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber is not null
        defaultRentShouldBeFound("couponDecimalNumber.specified=true");

        // Get all the rentList where couponDecimalNumber is null
        defaultRentShouldNotBeFound("couponDecimalNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber is greater than or equal to DEFAULT_COUPON_DECIMAL_NUMBER
        defaultRentShouldBeFound("couponDecimalNumber.greaterThanOrEqual=" + DEFAULT_COUPON_DECIMAL_NUMBER);

        // Get all the rentList where couponDecimalNumber is greater than or equal to UPDATED_COUPON_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("couponDecimalNumber.greaterThanOrEqual=" + UPDATED_COUPON_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber is less than or equal to DEFAULT_COUPON_DECIMAL_NUMBER
        defaultRentShouldBeFound("couponDecimalNumber.lessThanOrEqual=" + DEFAULT_COUPON_DECIMAL_NUMBER);

        // Get all the rentList where couponDecimalNumber is less than or equal to SMALLER_COUPON_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("couponDecimalNumber.lessThanOrEqual=" + SMALLER_COUPON_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber is less than DEFAULT_COUPON_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("couponDecimalNumber.lessThan=" + DEFAULT_COUPON_DECIMAL_NUMBER);

        // Get all the rentList where couponDecimalNumber is less than UPDATED_COUPON_DECIMAL_NUMBER
        defaultRentShouldBeFound("couponDecimalNumber.lessThan=" + UPDATED_COUPON_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponDecimalNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponDecimalNumber is greater than DEFAULT_COUPON_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("couponDecimalNumber.greaterThan=" + DEFAULT_COUPON_DECIMAL_NUMBER);

        // Get all the rentList where couponDecimalNumber is greater than SMALLER_COUPON_DECIMAL_NUMBER
        defaultRentShouldBeFound("couponDecimalNumber.greaterThan=" + SMALLER_COUPON_DECIMAL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate equals to DEFAULT_COUPON_FIRST_DATE
        defaultRentShouldBeFound("couponFirstDate.equals=" + DEFAULT_COUPON_FIRST_DATE);

        // Get all the rentList where couponFirstDate equals to UPDATED_COUPON_FIRST_DATE
        defaultRentShouldNotBeFound("couponFirstDate.equals=" + UPDATED_COUPON_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate not equals to DEFAULT_COUPON_FIRST_DATE
        defaultRentShouldNotBeFound("couponFirstDate.notEquals=" + DEFAULT_COUPON_FIRST_DATE);

        // Get all the rentList where couponFirstDate not equals to UPDATED_COUPON_FIRST_DATE
        defaultRentShouldBeFound("couponFirstDate.notEquals=" + UPDATED_COUPON_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate in DEFAULT_COUPON_FIRST_DATE or UPDATED_COUPON_FIRST_DATE
        defaultRentShouldBeFound("couponFirstDate.in=" + DEFAULT_COUPON_FIRST_DATE + "," + UPDATED_COUPON_FIRST_DATE);

        // Get all the rentList where couponFirstDate equals to UPDATED_COUPON_FIRST_DATE
        defaultRentShouldNotBeFound("couponFirstDate.in=" + UPDATED_COUPON_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate is not null
        defaultRentShouldBeFound("couponFirstDate.specified=true");

        // Get all the rentList where couponFirstDate is null
        defaultRentShouldNotBeFound("couponFirstDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate is greater than or equal to DEFAULT_COUPON_FIRST_DATE
        defaultRentShouldBeFound("couponFirstDate.greaterThanOrEqual=" + DEFAULT_COUPON_FIRST_DATE);

        // Get all the rentList where couponFirstDate is greater than or equal to UPDATED_COUPON_FIRST_DATE
        defaultRentShouldNotBeFound("couponFirstDate.greaterThanOrEqual=" + UPDATED_COUPON_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate is less than or equal to DEFAULT_COUPON_FIRST_DATE
        defaultRentShouldBeFound("couponFirstDate.lessThanOrEqual=" + DEFAULT_COUPON_FIRST_DATE);

        // Get all the rentList where couponFirstDate is less than or equal to SMALLER_COUPON_FIRST_DATE
        defaultRentShouldNotBeFound("couponFirstDate.lessThanOrEqual=" + SMALLER_COUPON_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate is less than DEFAULT_COUPON_FIRST_DATE
        defaultRentShouldNotBeFound("couponFirstDate.lessThan=" + DEFAULT_COUPON_FIRST_DATE);

        // Get all the rentList where couponFirstDate is less than UPDATED_COUPON_FIRST_DATE
        defaultRentShouldBeFound("couponFirstDate.lessThan=" + UPDATED_COUPON_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponFirstDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponFirstDate is greater than DEFAULT_COUPON_FIRST_DATE
        defaultRentShouldNotBeFound("couponFirstDate.greaterThan=" + DEFAULT_COUPON_FIRST_DATE);

        // Get all the rentList where couponFirstDate is greater than SMALLER_COUPON_FIRST_DATE
        defaultRentShouldBeFound("couponFirstDate.greaterThan=" + SMALLER_COUPON_FIRST_DATE);
    }


    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate equals to DEFAULT_COUPON_LAST_DATE
        defaultRentShouldBeFound("couponLastDate.equals=" + DEFAULT_COUPON_LAST_DATE);

        // Get all the rentList where couponLastDate equals to UPDATED_COUPON_LAST_DATE
        defaultRentShouldNotBeFound("couponLastDate.equals=" + UPDATED_COUPON_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate not equals to DEFAULT_COUPON_LAST_DATE
        defaultRentShouldNotBeFound("couponLastDate.notEquals=" + DEFAULT_COUPON_LAST_DATE);

        // Get all the rentList where couponLastDate not equals to UPDATED_COUPON_LAST_DATE
        defaultRentShouldBeFound("couponLastDate.notEquals=" + UPDATED_COUPON_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate in DEFAULT_COUPON_LAST_DATE or UPDATED_COUPON_LAST_DATE
        defaultRentShouldBeFound("couponLastDate.in=" + DEFAULT_COUPON_LAST_DATE + "," + UPDATED_COUPON_LAST_DATE);

        // Get all the rentList where couponLastDate equals to UPDATED_COUPON_LAST_DATE
        defaultRentShouldNotBeFound("couponLastDate.in=" + UPDATED_COUPON_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate is not null
        defaultRentShouldBeFound("couponLastDate.specified=true");

        // Get all the rentList where couponLastDate is null
        defaultRentShouldNotBeFound("couponLastDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate is greater than or equal to DEFAULT_COUPON_LAST_DATE
        defaultRentShouldBeFound("couponLastDate.greaterThanOrEqual=" + DEFAULT_COUPON_LAST_DATE);

        // Get all the rentList where couponLastDate is greater than or equal to UPDATED_COUPON_LAST_DATE
        defaultRentShouldNotBeFound("couponLastDate.greaterThanOrEqual=" + UPDATED_COUPON_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate is less than or equal to DEFAULT_COUPON_LAST_DATE
        defaultRentShouldBeFound("couponLastDate.lessThanOrEqual=" + DEFAULT_COUPON_LAST_DATE);

        // Get all the rentList where couponLastDate is less than or equal to SMALLER_COUPON_LAST_DATE
        defaultRentShouldNotBeFound("couponLastDate.lessThanOrEqual=" + SMALLER_COUPON_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate is less than DEFAULT_COUPON_LAST_DATE
        defaultRentShouldNotBeFound("couponLastDate.lessThan=" + DEFAULT_COUPON_LAST_DATE);

        // Get all the rentList where couponLastDate is less than UPDATED_COUPON_LAST_DATE
        defaultRentShouldBeFound("couponLastDate.lessThan=" + UPDATED_COUPON_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByCouponLastDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where couponLastDate is greater than DEFAULT_COUPON_LAST_DATE
        defaultRentShouldNotBeFound("couponLastDate.greaterThan=" + DEFAULT_COUPON_LAST_DATE);

        // Get all the rentList where couponLastDate is greater than SMALLER_COUPON_LAST_DATE
        defaultRentShouldBeFound("couponLastDate.greaterThan=" + SMALLER_COUPON_LAST_DATE);
    }


    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate equals to DEFAULT_REFUND_FIRST_DATE
        defaultRentShouldBeFound("refundFirstDate.equals=" + DEFAULT_REFUND_FIRST_DATE);

        // Get all the rentList where refundFirstDate equals to UPDATED_REFUND_FIRST_DATE
        defaultRentShouldNotBeFound("refundFirstDate.equals=" + UPDATED_REFUND_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate not equals to DEFAULT_REFUND_FIRST_DATE
        defaultRentShouldNotBeFound("refundFirstDate.notEquals=" + DEFAULT_REFUND_FIRST_DATE);

        // Get all the rentList where refundFirstDate not equals to UPDATED_REFUND_FIRST_DATE
        defaultRentShouldBeFound("refundFirstDate.notEquals=" + UPDATED_REFUND_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate in DEFAULT_REFUND_FIRST_DATE or UPDATED_REFUND_FIRST_DATE
        defaultRentShouldBeFound("refundFirstDate.in=" + DEFAULT_REFUND_FIRST_DATE + "," + UPDATED_REFUND_FIRST_DATE);

        // Get all the rentList where refundFirstDate equals to UPDATED_REFUND_FIRST_DATE
        defaultRentShouldNotBeFound("refundFirstDate.in=" + UPDATED_REFUND_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate is not null
        defaultRentShouldBeFound("refundFirstDate.specified=true");

        // Get all the rentList where refundFirstDate is null
        defaultRentShouldNotBeFound("refundFirstDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate is greater than or equal to DEFAULT_REFUND_FIRST_DATE
        defaultRentShouldBeFound("refundFirstDate.greaterThanOrEqual=" + DEFAULT_REFUND_FIRST_DATE);

        // Get all the rentList where refundFirstDate is greater than or equal to UPDATED_REFUND_FIRST_DATE
        defaultRentShouldNotBeFound("refundFirstDate.greaterThanOrEqual=" + UPDATED_REFUND_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate is less than or equal to DEFAULT_REFUND_FIRST_DATE
        defaultRentShouldBeFound("refundFirstDate.lessThanOrEqual=" + DEFAULT_REFUND_FIRST_DATE);

        // Get all the rentList where refundFirstDate is less than or equal to SMALLER_REFUND_FIRST_DATE
        defaultRentShouldNotBeFound("refundFirstDate.lessThanOrEqual=" + SMALLER_REFUND_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate is less than DEFAULT_REFUND_FIRST_DATE
        defaultRentShouldNotBeFound("refundFirstDate.lessThan=" + DEFAULT_REFUND_FIRST_DATE);

        // Get all the rentList where refundFirstDate is less than UPDATED_REFUND_FIRST_DATE
        defaultRentShouldBeFound("refundFirstDate.lessThan=" + UPDATED_REFUND_FIRST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundFirstDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundFirstDate is greater than DEFAULT_REFUND_FIRST_DATE
        defaultRentShouldNotBeFound("refundFirstDate.greaterThan=" + DEFAULT_REFUND_FIRST_DATE);

        // Get all the rentList where refundFirstDate is greater than SMALLER_REFUND_FIRST_DATE
        defaultRentShouldBeFound("refundFirstDate.greaterThan=" + SMALLER_REFUND_FIRST_DATE);
    }


    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber equals to DEFAULT_REFUND_DECIMAL_NUMBER
        defaultRentShouldBeFound("refundDecimalNumber.equals=" + DEFAULT_REFUND_DECIMAL_NUMBER);

        // Get all the rentList where refundDecimalNumber equals to UPDATED_REFUND_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("refundDecimalNumber.equals=" + UPDATED_REFUND_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber not equals to DEFAULT_REFUND_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("refundDecimalNumber.notEquals=" + DEFAULT_REFUND_DECIMAL_NUMBER);

        // Get all the rentList where refundDecimalNumber not equals to UPDATED_REFUND_DECIMAL_NUMBER
        defaultRentShouldBeFound("refundDecimalNumber.notEquals=" + UPDATED_REFUND_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber in DEFAULT_REFUND_DECIMAL_NUMBER or UPDATED_REFUND_DECIMAL_NUMBER
        defaultRentShouldBeFound("refundDecimalNumber.in=" + DEFAULT_REFUND_DECIMAL_NUMBER + "," + UPDATED_REFUND_DECIMAL_NUMBER);

        // Get all the rentList where refundDecimalNumber equals to UPDATED_REFUND_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("refundDecimalNumber.in=" + UPDATED_REFUND_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber is not null
        defaultRentShouldBeFound("refundDecimalNumber.specified=true");

        // Get all the rentList where refundDecimalNumber is null
        defaultRentShouldNotBeFound("refundDecimalNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber is greater than or equal to DEFAULT_REFUND_DECIMAL_NUMBER
        defaultRentShouldBeFound("refundDecimalNumber.greaterThanOrEqual=" + DEFAULT_REFUND_DECIMAL_NUMBER);

        // Get all the rentList where refundDecimalNumber is greater than or equal to UPDATED_REFUND_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("refundDecimalNumber.greaterThanOrEqual=" + UPDATED_REFUND_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber is less than or equal to DEFAULT_REFUND_DECIMAL_NUMBER
        defaultRentShouldBeFound("refundDecimalNumber.lessThanOrEqual=" + DEFAULT_REFUND_DECIMAL_NUMBER);

        // Get all the rentList where refundDecimalNumber is less than or equal to SMALLER_REFUND_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("refundDecimalNumber.lessThanOrEqual=" + SMALLER_REFUND_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber is less than DEFAULT_REFUND_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("refundDecimalNumber.lessThan=" + DEFAULT_REFUND_DECIMAL_NUMBER);

        // Get all the rentList where refundDecimalNumber is less than UPDATED_REFUND_DECIMAL_NUMBER
        defaultRentShouldBeFound("refundDecimalNumber.lessThan=" + UPDATED_REFUND_DECIMAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundDecimalNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundDecimalNumber is greater than DEFAULT_REFUND_DECIMAL_NUMBER
        defaultRentShouldNotBeFound("refundDecimalNumber.greaterThan=" + DEFAULT_REFUND_DECIMAL_NUMBER);

        // Get all the rentList where refundDecimalNumber is greater than SMALLER_REFUND_DECIMAL_NUMBER
        defaultRentShouldBeFound("refundDecimalNumber.greaterThan=" + SMALLER_REFUND_DECIMAL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate equals to DEFAULT_REFUND_LAST_DATE
        defaultRentShouldBeFound("refundLastDate.equals=" + DEFAULT_REFUND_LAST_DATE);

        // Get all the rentList where refundLastDate equals to UPDATED_REFUND_LAST_DATE
        defaultRentShouldNotBeFound("refundLastDate.equals=" + UPDATED_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate not equals to DEFAULT_REFUND_LAST_DATE
        defaultRentShouldNotBeFound("refundLastDate.notEquals=" + DEFAULT_REFUND_LAST_DATE);

        // Get all the rentList where refundLastDate not equals to UPDATED_REFUND_LAST_DATE
        defaultRentShouldBeFound("refundLastDate.notEquals=" + UPDATED_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsInShouldWork() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate in DEFAULT_REFUND_LAST_DATE or UPDATED_REFUND_LAST_DATE
        defaultRentShouldBeFound("refundLastDate.in=" + DEFAULT_REFUND_LAST_DATE + "," + UPDATED_REFUND_LAST_DATE);

        // Get all the rentList where refundLastDate equals to UPDATED_REFUND_LAST_DATE
        defaultRentShouldNotBeFound("refundLastDate.in=" + UPDATED_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate is not null
        defaultRentShouldBeFound("refundLastDate.specified=true");

        // Get all the rentList where refundLastDate is null
        defaultRentShouldNotBeFound("refundLastDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate is greater than or equal to DEFAULT_REFUND_LAST_DATE
        defaultRentShouldBeFound("refundLastDate.greaterThanOrEqual=" + DEFAULT_REFUND_LAST_DATE);

        // Get all the rentList where refundLastDate is greater than or equal to UPDATED_REFUND_LAST_DATE
        defaultRentShouldNotBeFound("refundLastDate.greaterThanOrEqual=" + UPDATED_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate is less than or equal to DEFAULT_REFUND_LAST_DATE
        defaultRentShouldBeFound("refundLastDate.lessThanOrEqual=" + DEFAULT_REFUND_LAST_DATE);

        // Get all the rentList where refundLastDate is less than or equal to SMALLER_REFUND_LAST_DATE
        defaultRentShouldNotBeFound("refundLastDate.lessThanOrEqual=" + SMALLER_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsLessThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate is less than DEFAULT_REFUND_LAST_DATE
        defaultRentShouldNotBeFound("refundLastDate.lessThan=" + DEFAULT_REFUND_LAST_DATE);

        // Get all the rentList where refundLastDate is less than UPDATED_REFUND_LAST_DATE
        defaultRentShouldBeFound("refundLastDate.lessThan=" + UPDATED_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void getAllRentsByRefundLastDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        // Get all the rentList where refundLastDate is greater than DEFAULT_REFUND_LAST_DATE
        defaultRentShouldNotBeFound("refundLastDate.greaterThan=" + DEFAULT_REFUND_LAST_DATE);

        // Get all the rentList where refundLastDate is greater than SMALLER_REFUND_LAST_DATE
        defaultRentShouldBeFound("refundLastDate.greaterThan=" + SMALLER_REFUND_LAST_DATE);
    }


    @Test
    @Transactional
    public void getAllRentsByPeriodicityIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);
        Periodicity periodicity = PeriodicityResourceIT.createEntity(em);
        em.persist(periodicity);
        em.flush();
        rent.setPeriodicity(periodicity);
        rentRepository.saveAndFlush(rent);
        Long periodicityId = periodicity.getId();

        // Get all the rentList where periodicity equals to periodicityId
        defaultRentShouldBeFound("periodicityId.equals=" + periodicityId);

        // Get all the rentList where periodicity equals to periodicityId + 1
        defaultRentShouldNotBeFound("periodicityId.equals=" + (periodicityId + 1));
    }


    @Test
    @Transactional
    public void getAllRentsByScheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);
        Schedule schedule = ScheduleResourceIT.createEntity(em);
        em.persist(schedule);
        em.flush();
        rent.addSchedule(schedule);
        rentRepository.saveAndFlush(rent);
        Long scheduleId = schedule.getId();

        // Get all the rentList where schedule equals to scheduleId
        defaultRentShouldBeFound("scheduleId.equals=" + scheduleId);

        // Get all the rentList where schedule equals to scheduleId + 1
        defaultRentShouldNotBeFound("scheduleId.equals=" + (scheduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRentShouldBeFound(String filter) throws Exception {
        restRentMockMvc.perform(get("/api/rents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rent.getId().intValue())))
            .andExpect(jsonPath("$.[*].nominalValue").value(hasItem(DEFAULT_NOMINAL_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX.doubleValue())))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].refundAmount").value(hasItem(DEFAULT_REFUND_AMOUNT)))
            .andExpect(jsonPath("$.[*].startExcluded").value(hasItem(DEFAULT_START_EXCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].monthEnd").value(hasItem(DEFAULT_MONTH_END.booleanValue())))
            .andExpect(jsonPath("$.[*].endExcluded").value(hasItem(DEFAULT_END_EXCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].rateValue").value(hasItem(DEFAULT_RATE_VALUE)))
            .andExpect(jsonPath("$.[*].couponDecimalNumber").value(hasItem(DEFAULT_COUPON_DECIMAL_NUMBER)))
            .andExpect(jsonPath("$.[*].couponFirstDate").value(hasItem(DEFAULT_COUPON_FIRST_DATE.toString())))
            .andExpect(jsonPath("$.[*].couponLastDate").value(hasItem(DEFAULT_COUPON_LAST_DATE.toString())))
            .andExpect(jsonPath("$.[*].refundFirstDate").value(hasItem(DEFAULT_REFUND_FIRST_DATE.toString())))
            .andExpect(jsonPath("$.[*].refundDecimalNumber").value(hasItem(DEFAULT_REFUND_DECIMAL_NUMBER)))
            .andExpect(jsonPath("$.[*].refundLastDate").value(hasItem(DEFAULT_REFUND_LAST_DATE.toString())));

        // Check, that the count call also returns 1
        restRentMockMvc.perform(get("/api/rents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRentShouldNotBeFound(String filter) throws Exception {
        restRentMockMvc.perform(get("/api/rents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRentMockMvc.perform(get("/api/rents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRent() throws Exception {
        // Get the rent
        restRentMockMvc.perform(get("/api/rents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRent() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        int databaseSizeBeforeUpdate = rentRepository.findAll().size();

        // Update the rent
        Rent updatedRent = rentRepository.findById(rent.getId()).get();
        // Disconnect from session so that the updates on updatedRent are not directly saved in db
        em.detach(updatedRent);
        updatedRent
            .nominalValue(UPDATED_NOMINAL_VALUE)
            .taux(UPDATED_TAUX)
            .mode(UPDATED_MODE)
            .capital(UPDATED_CAPITAL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .refundAmount(UPDATED_REFUND_AMOUNT)
            .startExcluded(UPDATED_START_EXCLUDED)
            .monthEnd(UPDATED_MONTH_END)
            .endExcluded(UPDATED_END_EXCLUDED)
            .rateValue(UPDATED_RATE_VALUE)
            .couponDecimalNumber(UPDATED_COUPON_DECIMAL_NUMBER)
            .couponFirstDate(UPDATED_COUPON_FIRST_DATE)
            .couponLastDate(UPDATED_COUPON_LAST_DATE)
            .refundFirstDate(UPDATED_REFUND_FIRST_DATE)
            .refundDecimalNumber(UPDATED_REFUND_DECIMAL_NUMBER)
            .refundLastDate(UPDATED_REFUND_LAST_DATE);
        RentDTO rentDTO = rentMapper.toDto(updatedRent);

        restRentMockMvc.perform(put("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rentDTO)))
            .andExpect(status().isOk());

        // Validate the Rent in the database
        List<Rent> rentList = rentRepository.findAll();
        assertThat(rentList).hasSize(databaseSizeBeforeUpdate);
        Rent testRent = rentList.get(rentList.size() - 1);
        assertThat(testRent.getNominalValue()).isEqualTo(UPDATED_NOMINAL_VALUE);
        assertThat(testRent.getTaux()).isEqualTo(UPDATED_TAUX);
        assertThat(testRent.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testRent.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testRent.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRent.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRent.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
        assertThat(testRent.isStartExcluded()).isEqualTo(UPDATED_START_EXCLUDED);
        assertThat(testRent.isMonthEnd()).isEqualTo(UPDATED_MONTH_END);
        assertThat(testRent.isEndExcluded()).isEqualTo(UPDATED_END_EXCLUDED);
        assertThat(testRent.getRateValue()).isEqualTo(UPDATED_RATE_VALUE);
        assertThat(testRent.getCouponDecimalNumber()).isEqualTo(UPDATED_COUPON_DECIMAL_NUMBER);
        assertThat(testRent.getCouponFirstDate()).isEqualTo(UPDATED_COUPON_FIRST_DATE);
        assertThat(testRent.getCouponLastDate()).isEqualTo(UPDATED_COUPON_LAST_DATE);
        assertThat(testRent.getRefundFirstDate()).isEqualTo(UPDATED_REFUND_FIRST_DATE);
        assertThat(testRent.getRefundDecimalNumber()).isEqualTo(UPDATED_REFUND_DECIMAL_NUMBER);
        assertThat(testRent.getRefundLastDate()).isEqualTo(UPDATED_REFUND_LAST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRent() throws Exception {
        int databaseSizeBeforeUpdate = rentRepository.findAll().size();

        // Create the Rent
        RentDTO rentDTO = rentMapper.toDto(rent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentMockMvc.perform(put("/api/rents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rent in the database
        List<Rent> rentList = rentRepository.findAll();
        assertThat(rentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRent() throws Exception {
        // Initialize the database
        rentRepository.saveAndFlush(rent);

        int databaseSizeBeforeDelete = rentRepository.findAll().size();

        // Delete the rent
        restRentMockMvc.perform(delete("/api/rents/{id}", rent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rent> rentList = rentRepository.findAll();
        assertThat(rentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
