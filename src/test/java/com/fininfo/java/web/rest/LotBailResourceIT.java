package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.LotBail;
import com.fininfo.java.domain.Charge;
import com.fininfo.java.domain.Counter;
import com.fininfo.java.domain.Schedule;
import com.fininfo.java.domain.Bail;
import com.fininfo.java.repository.LotBailRepository;
import com.fininfo.java.service.LotBailService;
import com.fininfo.java.service.dto.LotBailDTO;
import com.fininfo.java.service.mapper.LotBailMapper;
import com.fininfo.java.service.dto.LotBailCriteria;
import com.fininfo.java.service.LotBailQueryService;

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
 * Integration tests for the {@link LotBailResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LotBailResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_LOT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_LOT = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING = "BBBBBBBBBB";

    private static final String DEFAULT_STAIRS = "AAAAAAAAAA";
    private static final String UPDATED_STAIRS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_INFORMATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ACQUISATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACQUISATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACQUISATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_SURFACE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SURFACE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SURFACE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_PARKINGS_NUMBER = 1;
    private static final Integer UPDATED_PARKINGS_NUMBER = 2;
    private static final Integer SMALLER_PARKINGS_NUMBER = 1 - 1;

    private static final Integer DEFAULT_FLOORS_NUMBER = 1;
    private static final Integer UPDATED_FLOORS_NUMBER = 2;
    private static final Integer SMALLER_FLOORS_NUMBER = 1 - 1;

    private static final Integer DEFAULT_REAL_NUMBER_OF_LOT = 1;
    private static final Integer UPDATED_REAL_NUMBER_OF_LOT = 2;
    private static final Integer SMALLER_REAL_NUMBER_OF_LOT = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_SECONDARY_UNITS = 1;
    private static final Integer UPDATED_NUMBER_OF_SECONDARY_UNITS = 2;
    private static final Integer SMALLER_NUMBER_OF_SECONDARY_UNITS = 1 - 1;

    private static final Boolean DEFAULT_OUT_DOOR_PARKING = false;
    private static final Boolean UPDATED_OUT_DOOR_PARKING = true;

    private static final Boolean DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION = false;
    private static final Boolean UPDATED_LOT_FOR_MULTIPLE_OCCUPATION = true;

    private static final BigDecimal DEFAULT_PRICE_OF_SQUARE_METER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_OF_SQUARE_METER = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE_OF_SQUARE_METER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENTAL_VALUE_FOR_SQUARE_METER = new BigDecimal(2);
    private static final BigDecimal SMALLER_RENTAL_VALUE_FOR_SQUARE_METER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TRANSFER_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSFER_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TRANSFER_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ACQUISATION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACQUISATION_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ACQUISATION_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_RENT_AMOUNT = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_POOL_FACTOR = 1;
    private static final Integer UPDATED_POOL_FACTOR = 2;
    private static final Integer SMALLER_POOL_FACTOR = 1 - 1;

    private static final LocalDate DEFAULT_MATURITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MATURITY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MATURITY_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_CONVERTIBILITY_INDICATOR = false;
    private static final Boolean UPDATED_CONVERTIBILITY_INDICATOR = true;

    private static final Boolean DEFAULT_SUBORDINATION_INDICATOR = false;
    private static final Boolean UPDATED_SUBORDINATION_INDICATOR = true;

    private static final Boolean DEFAULT_INDEXED = false;
    private static final Boolean UPDATED_INDEXED = true;

    private static final Boolean DEFAULT_ELIGIBILITY_INDICATOR = false;
    private static final Boolean UPDATED_ELIGIBILITY_INDICATOR = true;

    private static final Integer DEFAULT_RISK_PREMIUM = 1;
    private static final Integer UPDATED_RISK_PREMIUM = 2;
    private static final Integer SMALLER_RISK_PREMIUM = 1 - 1;

    private static final Integer DEFAULT_GOTOUARANTOR_CODE = 1;
    private static final Integer UPDATED_GOTOUARANTOR_CODE = 2;
    private static final Integer SMALLER_GOTOUARANTOR_CODE = 1 - 1;

    private static final String DEFAULT_GUARANTOR_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_GUARANTOR_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AMORTIZATION_TABLE_MANAGEMENT = false;
    private static final Boolean UPDATED_AMORTIZATION_TABLE_MANAGEMENT = true;

    private static final Boolean DEFAULT_VARIABLE_RATE = false;
    private static final Boolean UPDATED_VARIABLE_RATE = true;

    @Autowired
    private LotBailRepository lotBailRepository;

    @Autowired
    private LotBailMapper lotBailMapper;

    @Autowired
    private LotBailService lotBailService;

    @Autowired
    private LotBailQueryService lotBailQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLotBailMockMvc;

    private LotBail lotBail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotBail createEntity(EntityManager em) {
        LotBail lotBail = new LotBail()
            .name(DEFAULT_NAME)
            .codeLot(DEFAULT_CODE_LOT)
            .building(DEFAULT_BUILDING)
            .stairs(DEFAULT_STAIRS)
            .comments(DEFAULT_COMMENTS)
            .technicalInformation(DEFAULT_TECHNICAL_INFORMATION)
            .creationDate(DEFAULT_CREATION_DATE)
            .acquisationDate(DEFAULT_ACQUISATION_DATE)
            .surface(DEFAULT_SURFACE)
            .parkingsNumber(DEFAULT_PARKINGS_NUMBER)
            .floorsNumber(DEFAULT_FLOORS_NUMBER)
            .realNumberOfLot(DEFAULT_REAL_NUMBER_OF_LOT)
            .numberOfSecondaryUnits(DEFAULT_NUMBER_OF_SECONDARY_UNITS)
            .outDoorParking(DEFAULT_OUT_DOOR_PARKING)
            .lotForMultipleOccupation(DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION)
            .priceOfSquareMeter(DEFAULT_PRICE_OF_SQUARE_METER)
            .rentalValueForSquareMeter(DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER)
            .transferAmount(DEFAULT_TRANSFER_AMOUNT)
            .acquisationAmount(DEFAULT_ACQUISATION_AMOUNT)
            .rentAmount(DEFAULT_RENT_AMOUNT)
            .poolFactor(DEFAULT_POOL_FACTOR)
            .maturityDate(DEFAULT_MATURITY_DATE)
            .convertibilityIndicator(DEFAULT_CONVERTIBILITY_INDICATOR)
            .subordinationIndicator(DEFAULT_SUBORDINATION_INDICATOR)
            .indexed(DEFAULT_INDEXED)
            .eligibilityIndicator(DEFAULT_ELIGIBILITY_INDICATOR)
            .riskPremium(DEFAULT_RISK_PREMIUM)
            .gotouarantorCode(DEFAULT_GOTOUARANTOR_CODE)
            .guarantorDescription(DEFAULT_GUARANTOR_DESCRIPTION)
            .amortizationTableManagement(DEFAULT_AMORTIZATION_TABLE_MANAGEMENT)
            .variableRate(DEFAULT_VARIABLE_RATE);
        return lotBail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotBail createUpdatedEntity(EntityManager em) {
        LotBail lotBail = new LotBail()
            .name(UPDATED_NAME)
            .codeLot(UPDATED_CODE_LOT)
            .building(UPDATED_BUILDING)
            .stairs(UPDATED_STAIRS)
            .comments(UPDATED_COMMENTS)
            .technicalInformation(UPDATED_TECHNICAL_INFORMATION)
            .creationDate(UPDATED_CREATION_DATE)
            .acquisationDate(UPDATED_ACQUISATION_DATE)
            .surface(UPDATED_SURFACE)
            .parkingsNumber(UPDATED_PARKINGS_NUMBER)
            .floorsNumber(UPDATED_FLOORS_NUMBER)
            .realNumberOfLot(UPDATED_REAL_NUMBER_OF_LOT)
            .numberOfSecondaryUnits(UPDATED_NUMBER_OF_SECONDARY_UNITS)
            .outDoorParking(UPDATED_OUT_DOOR_PARKING)
            .lotForMultipleOccupation(UPDATED_LOT_FOR_MULTIPLE_OCCUPATION)
            .priceOfSquareMeter(UPDATED_PRICE_OF_SQUARE_METER)
            .rentalValueForSquareMeter(UPDATED_RENTAL_VALUE_FOR_SQUARE_METER)
            .transferAmount(UPDATED_TRANSFER_AMOUNT)
            .acquisationAmount(UPDATED_ACQUISATION_AMOUNT)
            .rentAmount(UPDATED_RENT_AMOUNT)
            .poolFactor(UPDATED_POOL_FACTOR)
            .maturityDate(UPDATED_MATURITY_DATE)
            .convertibilityIndicator(UPDATED_CONVERTIBILITY_INDICATOR)
            .subordinationIndicator(UPDATED_SUBORDINATION_INDICATOR)
            .indexed(UPDATED_INDEXED)
            .eligibilityIndicator(UPDATED_ELIGIBILITY_INDICATOR)
            .riskPremium(UPDATED_RISK_PREMIUM)
            .gotouarantorCode(UPDATED_GOTOUARANTOR_CODE)
            .guarantorDescription(UPDATED_GUARANTOR_DESCRIPTION)
            .amortizationTableManagement(UPDATED_AMORTIZATION_TABLE_MANAGEMENT)
            .variableRate(UPDATED_VARIABLE_RATE);
        return lotBail;
    }

    @BeforeEach
    public void initTest() {
        lotBail = createEntity(em);
    }

    @Test
    @Transactional
    public void createLotBail() throws Exception {
        int databaseSizeBeforeCreate = lotBailRepository.findAll().size();
        // Create the LotBail
        LotBailDTO lotBailDTO = lotBailMapper.toDto(lotBail);
        restLotBailMockMvc.perform(post("/api/lot-bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotBailDTO)))
            .andExpect(status().isCreated());

        // Validate the LotBail in the database
        List<LotBail> lotBailList = lotBailRepository.findAll();
        assertThat(lotBailList).hasSize(databaseSizeBeforeCreate + 1);
        LotBail testLotBail = lotBailList.get(lotBailList.size() - 1);
        assertThat(testLotBail.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLotBail.getCodeLot()).isEqualTo(DEFAULT_CODE_LOT);
        assertThat(testLotBail.getBuilding()).isEqualTo(DEFAULT_BUILDING);
        assertThat(testLotBail.getStairs()).isEqualTo(DEFAULT_STAIRS);
        assertThat(testLotBail.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testLotBail.getTechnicalInformation()).isEqualTo(DEFAULT_TECHNICAL_INFORMATION);
        assertThat(testLotBail.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testLotBail.getAcquisationDate()).isEqualTo(DEFAULT_ACQUISATION_DATE);
        assertThat(testLotBail.getSurface()).isEqualTo(DEFAULT_SURFACE);
        assertThat(testLotBail.getParkingsNumber()).isEqualTo(DEFAULT_PARKINGS_NUMBER);
        assertThat(testLotBail.getFloorsNumber()).isEqualTo(DEFAULT_FLOORS_NUMBER);
        assertThat(testLotBail.getRealNumberOfLot()).isEqualTo(DEFAULT_REAL_NUMBER_OF_LOT);
        assertThat(testLotBail.getNumberOfSecondaryUnits()).isEqualTo(DEFAULT_NUMBER_OF_SECONDARY_UNITS);
        assertThat(testLotBail.isOutDoorParking()).isEqualTo(DEFAULT_OUT_DOOR_PARKING);
        assertThat(testLotBail.isLotForMultipleOccupation()).isEqualTo(DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION);
        assertThat(testLotBail.getPriceOfSquareMeter()).isEqualTo(DEFAULT_PRICE_OF_SQUARE_METER);
        assertThat(testLotBail.getRentalValueForSquareMeter()).isEqualTo(DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER);
        assertThat(testLotBail.getTransferAmount()).isEqualTo(DEFAULT_TRANSFER_AMOUNT);
        assertThat(testLotBail.getAcquisationAmount()).isEqualTo(DEFAULT_ACQUISATION_AMOUNT);
        assertThat(testLotBail.getRentAmount()).isEqualTo(DEFAULT_RENT_AMOUNT);
        assertThat(testLotBail.getPoolFactor()).isEqualTo(DEFAULT_POOL_FACTOR);
        assertThat(testLotBail.getMaturityDate()).isEqualTo(DEFAULT_MATURITY_DATE);
        assertThat(testLotBail.isConvertibilityIndicator()).isEqualTo(DEFAULT_CONVERTIBILITY_INDICATOR);
        assertThat(testLotBail.isSubordinationIndicator()).isEqualTo(DEFAULT_SUBORDINATION_INDICATOR);
        assertThat(testLotBail.isIndexed()).isEqualTo(DEFAULT_INDEXED);
        assertThat(testLotBail.isEligibilityIndicator()).isEqualTo(DEFAULT_ELIGIBILITY_INDICATOR);
        assertThat(testLotBail.getRiskPremium()).isEqualTo(DEFAULT_RISK_PREMIUM);
        assertThat(testLotBail.getGotouarantorCode()).isEqualTo(DEFAULT_GOTOUARANTOR_CODE);
        assertThat(testLotBail.getGuarantorDescription()).isEqualTo(DEFAULT_GUARANTOR_DESCRIPTION);
        assertThat(testLotBail.isAmortizationTableManagement()).isEqualTo(DEFAULT_AMORTIZATION_TABLE_MANAGEMENT);
        assertThat(testLotBail.isVariableRate()).isEqualTo(DEFAULT_VARIABLE_RATE);
    }

    @Test
    @Transactional
    public void createLotBailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lotBailRepository.findAll().size();

        // Create the LotBail with an existing ID
        lotBail.setId(1L);
        LotBailDTO lotBailDTO = lotBailMapper.toDto(lotBail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotBailMockMvc.perform(post("/api/lot-bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotBailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LotBail in the database
        List<LotBail> lotBailList = lotBailRepository.findAll();
        assertThat(lotBailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLotBails() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList
        restLotBailMockMvc.perform(get("/api/lot-bails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotBail.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].codeLot").value(hasItem(DEFAULT_CODE_LOT)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
            .andExpect(jsonPath("$.[*].stairs").value(hasItem(DEFAULT_STAIRS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].technicalInformation").value(hasItem(DEFAULT_TECHNICAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].acquisationDate").value(hasItem(DEFAULT_ACQUISATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.intValue())))
            .andExpect(jsonPath("$.[*].parkingsNumber").value(hasItem(DEFAULT_PARKINGS_NUMBER)))
            .andExpect(jsonPath("$.[*].floorsNumber").value(hasItem(DEFAULT_FLOORS_NUMBER)))
            .andExpect(jsonPath("$.[*].realNumberOfLot").value(hasItem(DEFAULT_REAL_NUMBER_OF_LOT)))
            .andExpect(jsonPath("$.[*].numberOfSecondaryUnits").value(hasItem(DEFAULT_NUMBER_OF_SECONDARY_UNITS)))
            .andExpect(jsonPath("$.[*].outDoorParking").value(hasItem(DEFAULT_OUT_DOOR_PARKING.booleanValue())))
            .andExpect(jsonPath("$.[*].lotForMultipleOccupation").value(hasItem(DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION.booleanValue())))
            .andExpect(jsonPath("$.[*].priceOfSquareMeter").value(hasItem(DEFAULT_PRICE_OF_SQUARE_METER.intValue())))
            .andExpect(jsonPath("$.[*].rentalValueForSquareMeter").value(hasItem(DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER.intValue())))
            .andExpect(jsonPath("$.[*].transferAmount").value(hasItem(DEFAULT_TRANSFER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].acquisationAmount").value(hasItem(DEFAULT_ACQUISATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].rentAmount").value(hasItem(DEFAULT_RENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].poolFactor").value(hasItem(DEFAULT_POOL_FACTOR)))
            .andExpect(jsonPath("$.[*].maturityDate").value(hasItem(DEFAULT_MATURITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].convertibilityIndicator").value(hasItem(DEFAULT_CONVERTIBILITY_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].subordinationIndicator").value(hasItem(DEFAULT_SUBORDINATION_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].indexed").value(hasItem(DEFAULT_INDEXED.booleanValue())))
            .andExpect(jsonPath("$.[*].eligibilityIndicator").value(hasItem(DEFAULT_ELIGIBILITY_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].riskPremium").value(hasItem(DEFAULT_RISK_PREMIUM)))
            .andExpect(jsonPath("$.[*].gotouarantorCode").value(hasItem(DEFAULT_GOTOUARANTOR_CODE)))
            .andExpect(jsonPath("$.[*].guarantorDescription").value(hasItem(DEFAULT_GUARANTOR_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].amortizationTableManagement").value(hasItem(DEFAULT_AMORTIZATION_TABLE_MANAGEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].variableRate").value(hasItem(DEFAULT_VARIABLE_RATE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLotBail() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get the lotBail
        restLotBailMockMvc.perform(get("/api/lot-bails/{id}", lotBail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lotBail.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.codeLot").value(DEFAULT_CODE_LOT))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING))
            .andExpect(jsonPath("$.stairs").value(DEFAULT_STAIRS))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.technicalInformation").value(DEFAULT_TECHNICAL_INFORMATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.acquisationDate").value(DEFAULT_ACQUISATION_DATE.toString()))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE.intValue()))
            .andExpect(jsonPath("$.parkingsNumber").value(DEFAULT_PARKINGS_NUMBER))
            .andExpect(jsonPath("$.floorsNumber").value(DEFAULT_FLOORS_NUMBER))
            .andExpect(jsonPath("$.realNumberOfLot").value(DEFAULT_REAL_NUMBER_OF_LOT))
            .andExpect(jsonPath("$.numberOfSecondaryUnits").value(DEFAULT_NUMBER_OF_SECONDARY_UNITS))
            .andExpect(jsonPath("$.outDoorParking").value(DEFAULT_OUT_DOOR_PARKING.booleanValue()))
            .andExpect(jsonPath("$.lotForMultipleOccupation").value(DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION.booleanValue()))
            .andExpect(jsonPath("$.priceOfSquareMeter").value(DEFAULT_PRICE_OF_SQUARE_METER.intValue()))
            .andExpect(jsonPath("$.rentalValueForSquareMeter").value(DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER.intValue()))
            .andExpect(jsonPath("$.transferAmount").value(DEFAULT_TRANSFER_AMOUNT.intValue()))
            .andExpect(jsonPath("$.acquisationAmount").value(DEFAULT_ACQUISATION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.rentAmount").value(DEFAULT_RENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.poolFactor").value(DEFAULT_POOL_FACTOR))
            .andExpect(jsonPath("$.maturityDate").value(DEFAULT_MATURITY_DATE.toString()))
            .andExpect(jsonPath("$.convertibilityIndicator").value(DEFAULT_CONVERTIBILITY_INDICATOR.booleanValue()))
            .andExpect(jsonPath("$.subordinationIndicator").value(DEFAULT_SUBORDINATION_INDICATOR.booleanValue()))
            .andExpect(jsonPath("$.indexed").value(DEFAULT_INDEXED.booleanValue()))
            .andExpect(jsonPath("$.eligibilityIndicator").value(DEFAULT_ELIGIBILITY_INDICATOR.booleanValue()))
            .andExpect(jsonPath("$.riskPremium").value(DEFAULT_RISK_PREMIUM))
            .andExpect(jsonPath("$.gotouarantorCode").value(DEFAULT_GOTOUARANTOR_CODE))
            .andExpect(jsonPath("$.guarantorDescription").value(DEFAULT_GUARANTOR_DESCRIPTION))
            .andExpect(jsonPath("$.amortizationTableManagement").value(DEFAULT_AMORTIZATION_TABLE_MANAGEMENT.booleanValue()))
            .andExpect(jsonPath("$.variableRate").value(DEFAULT_VARIABLE_RATE.booleanValue()));
    }


    @Test
    @Transactional
    public void getLotBailsByIdFiltering() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        Long id = lotBail.getId();

        defaultLotBailShouldBeFound("id.equals=" + id);
        defaultLotBailShouldNotBeFound("id.notEquals=" + id);

        defaultLotBailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLotBailShouldNotBeFound("id.greaterThan=" + id);

        defaultLotBailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLotBailShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLotBailsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where name equals to DEFAULT_NAME
        defaultLotBailShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the lotBailList where name equals to UPDATED_NAME
        defaultLotBailShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where name not equals to DEFAULT_NAME
        defaultLotBailShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the lotBailList where name not equals to UPDATED_NAME
        defaultLotBailShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLotBailShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the lotBailList where name equals to UPDATED_NAME
        defaultLotBailShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where name is not null
        defaultLotBailShouldBeFound("name.specified=true");

        // Get all the lotBailList where name is null
        defaultLotBailShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllLotBailsByNameContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where name contains DEFAULT_NAME
        defaultLotBailShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the lotBailList where name contains UPDATED_NAME
        defaultLotBailShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where name does not contain DEFAULT_NAME
        defaultLotBailShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the lotBailList where name does not contain UPDATED_NAME
        defaultLotBailShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllLotBailsByCodeLotIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where codeLot equals to DEFAULT_CODE_LOT
        defaultLotBailShouldBeFound("codeLot.equals=" + DEFAULT_CODE_LOT);

        // Get all the lotBailList where codeLot equals to UPDATED_CODE_LOT
        defaultLotBailShouldNotBeFound("codeLot.equals=" + UPDATED_CODE_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCodeLotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where codeLot not equals to DEFAULT_CODE_LOT
        defaultLotBailShouldNotBeFound("codeLot.notEquals=" + DEFAULT_CODE_LOT);

        // Get all the lotBailList where codeLot not equals to UPDATED_CODE_LOT
        defaultLotBailShouldBeFound("codeLot.notEquals=" + UPDATED_CODE_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCodeLotIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where codeLot in DEFAULT_CODE_LOT or UPDATED_CODE_LOT
        defaultLotBailShouldBeFound("codeLot.in=" + DEFAULT_CODE_LOT + "," + UPDATED_CODE_LOT);

        // Get all the lotBailList where codeLot equals to UPDATED_CODE_LOT
        defaultLotBailShouldNotBeFound("codeLot.in=" + UPDATED_CODE_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCodeLotIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where codeLot is not null
        defaultLotBailShouldBeFound("codeLot.specified=true");

        // Get all the lotBailList where codeLot is null
        defaultLotBailShouldNotBeFound("codeLot.specified=false");
    }
                @Test
    @Transactional
    public void getAllLotBailsByCodeLotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where codeLot contains DEFAULT_CODE_LOT
        defaultLotBailShouldBeFound("codeLot.contains=" + DEFAULT_CODE_LOT);

        // Get all the lotBailList where codeLot contains UPDATED_CODE_LOT
        defaultLotBailShouldNotBeFound("codeLot.contains=" + UPDATED_CODE_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCodeLotNotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where codeLot does not contain DEFAULT_CODE_LOT
        defaultLotBailShouldNotBeFound("codeLot.doesNotContain=" + DEFAULT_CODE_LOT);

        // Get all the lotBailList where codeLot does not contain UPDATED_CODE_LOT
        defaultLotBailShouldBeFound("codeLot.doesNotContain=" + UPDATED_CODE_LOT);
    }


    @Test
    @Transactional
    public void getAllLotBailsByBuildingIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where building equals to DEFAULT_BUILDING
        defaultLotBailShouldBeFound("building.equals=" + DEFAULT_BUILDING);

        // Get all the lotBailList where building equals to UPDATED_BUILDING
        defaultLotBailShouldNotBeFound("building.equals=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllLotBailsByBuildingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where building not equals to DEFAULT_BUILDING
        defaultLotBailShouldNotBeFound("building.notEquals=" + DEFAULT_BUILDING);

        // Get all the lotBailList where building not equals to UPDATED_BUILDING
        defaultLotBailShouldBeFound("building.notEquals=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllLotBailsByBuildingIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where building in DEFAULT_BUILDING or UPDATED_BUILDING
        defaultLotBailShouldBeFound("building.in=" + DEFAULT_BUILDING + "," + UPDATED_BUILDING);

        // Get all the lotBailList where building equals to UPDATED_BUILDING
        defaultLotBailShouldNotBeFound("building.in=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllLotBailsByBuildingIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where building is not null
        defaultLotBailShouldBeFound("building.specified=true");

        // Get all the lotBailList where building is null
        defaultLotBailShouldNotBeFound("building.specified=false");
    }
                @Test
    @Transactional
    public void getAllLotBailsByBuildingContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where building contains DEFAULT_BUILDING
        defaultLotBailShouldBeFound("building.contains=" + DEFAULT_BUILDING);

        // Get all the lotBailList where building contains UPDATED_BUILDING
        defaultLotBailShouldNotBeFound("building.contains=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllLotBailsByBuildingNotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where building does not contain DEFAULT_BUILDING
        defaultLotBailShouldNotBeFound("building.doesNotContain=" + DEFAULT_BUILDING);

        // Get all the lotBailList where building does not contain UPDATED_BUILDING
        defaultLotBailShouldBeFound("building.doesNotContain=" + UPDATED_BUILDING);
    }


    @Test
    @Transactional
    public void getAllLotBailsByStairsIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where stairs equals to DEFAULT_STAIRS
        defaultLotBailShouldBeFound("stairs.equals=" + DEFAULT_STAIRS);

        // Get all the lotBailList where stairs equals to UPDATED_STAIRS
        defaultLotBailShouldNotBeFound("stairs.equals=" + UPDATED_STAIRS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByStairsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where stairs not equals to DEFAULT_STAIRS
        defaultLotBailShouldNotBeFound("stairs.notEquals=" + DEFAULT_STAIRS);

        // Get all the lotBailList where stairs not equals to UPDATED_STAIRS
        defaultLotBailShouldBeFound("stairs.notEquals=" + UPDATED_STAIRS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByStairsIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where stairs in DEFAULT_STAIRS or UPDATED_STAIRS
        defaultLotBailShouldBeFound("stairs.in=" + DEFAULT_STAIRS + "," + UPDATED_STAIRS);

        // Get all the lotBailList where stairs equals to UPDATED_STAIRS
        defaultLotBailShouldNotBeFound("stairs.in=" + UPDATED_STAIRS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByStairsIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where stairs is not null
        defaultLotBailShouldBeFound("stairs.specified=true");

        // Get all the lotBailList where stairs is null
        defaultLotBailShouldNotBeFound("stairs.specified=false");
    }
                @Test
    @Transactional
    public void getAllLotBailsByStairsContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where stairs contains DEFAULT_STAIRS
        defaultLotBailShouldBeFound("stairs.contains=" + DEFAULT_STAIRS);

        // Get all the lotBailList where stairs contains UPDATED_STAIRS
        defaultLotBailShouldNotBeFound("stairs.contains=" + UPDATED_STAIRS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByStairsNotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where stairs does not contain DEFAULT_STAIRS
        defaultLotBailShouldNotBeFound("stairs.doesNotContain=" + DEFAULT_STAIRS);

        // Get all the lotBailList where stairs does not contain UPDATED_STAIRS
        defaultLotBailShouldBeFound("stairs.doesNotContain=" + UPDATED_STAIRS);
    }


    @Test
    @Transactional
    public void getAllLotBailsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where comments equals to DEFAULT_COMMENTS
        defaultLotBailShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the lotBailList where comments equals to UPDATED_COMMENTS
        defaultLotBailShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where comments not equals to DEFAULT_COMMENTS
        defaultLotBailShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the lotBailList where comments not equals to UPDATED_COMMENTS
        defaultLotBailShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultLotBailShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the lotBailList where comments equals to UPDATED_COMMENTS
        defaultLotBailShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where comments is not null
        defaultLotBailShouldBeFound("comments.specified=true");

        // Get all the lotBailList where comments is null
        defaultLotBailShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllLotBailsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where comments contains DEFAULT_COMMENTS
        defaultLotBailShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the lotBailList where comments contains UPDATED_COMMENTS
        defaultLotBailShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where comments does not contain DEFAULT_COMMENTS
        defaultLotBailShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the lotBailList where comments does not contain UPDATED_COMMENTS
        defaultLotBailShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllLotBailsByTechnicalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where technicalInformation equals to DEFAULT_TECHNICAL_INFORMATION
        defaultLotBailShouldBeFound("technicalInformation.equals=" + DEFAULT_TECHNICAL_INFORMATION);

        // Get all the lotBailList where technicalInformation equals to UPDATED_TECHNICAL_INFORMATION
        defaultLotBailShouldNotBeFound("technicalInformation.equals=" + UPDATED_TECHNICAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTechnicalInformationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where technicalInformation not equals to DEFAULT_TECHNICAL_INFORMATION
        defaultLotBailShouldNotBeFound("technicalInformation.notEquals=" + DEFAULT_TECHNICAL_INFORMATION);

        // Get all the lotBailList where technicalInformation not equals to UPDATED_TECHNICAL_INFORMATION
        defaultLotBailShouldBeFound("technicalInformation.notEquals=" + UPDATED_TECHNICAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTechnicalInformationIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where technicalInformation in DEFAULT_TECHNICAL_INFORMATION or UPDATED_TECHNICAL_INFORMATION
        defaultLotBailShouldBeFound("technicalInformation.in=" + DEFAULT_TECHNICAL_INFORMATION + "," + UPDATED_TECHNICAL_INFORMATION);

        // Get all the lotBailList where technicalInformation equals to UPDATED_TECHNICAL_INFORMATION
        defaultLotBailShouldNotBeFound("technicalInformation.in=" + UPDATED_TECHNICAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTechnicalInformationIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where technicalInformation is not null
        defaultLotBailShouldBeFound("technicalInformation.specified=true");

        // Get all the lotBailList where technicalInformation is null
        defaultLotBailShouldNotBeFound("technicalInformation.specified=false");
    }
                @Test
    @Transactional
    public void getAllLotBailsByTechnicalInformationContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where technicalInformation contains DEFAULT_TECHNICAL_INFORMATION
        defaultLotBailShouldBeFound("technicalInformation.contains=" + DEFAULT_TECHNICAL_INFORMATION);

        // Get all the lotBailList where technicalInformation contains UPDATED_TECHNICAL_INFORMATION
        defaultLotBailShouldNotBeFound("technicalInformation.contains=" + UPDATED_TECHNICAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTechnicalInformationNotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where technicalInformation does not contain DEFAULT_TECHNICAL_INFORMATION
        defaultLotBailShouldNotBeFound("technicalInformation.doesNotContain=" + DEFAULT_TECHNICAL_INFORMATION);

        // Get all the lotBailList where technicalInformation does not contain UPDATED_TECHNICAL_INFORMATION
        defaultLotBailShouldBeFound("technicalInformation.doesNotContain=" + UPDATED_TECHNICAL_INFORMATION);
    }


    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate equals to DEFAULT_CREATION_DATE
        defaultLotBailShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the lotBailList where creationDate equals to UPDATED_CREATION_DATE
        defaultLotBailShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultLotBailShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the lotBailList where creationDate not equals to UPDATED_CREATION_DATE
        defaultLotBailShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultLotBailShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the lotBailList where creationDate equals to UPDATED_CREATION_DATE
        defaultLotBailShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate is not null
        defaultLotBailShouldBeFound("creationDate.specified=true");

        // Get all the lotBailList where creationDate is null
        defaultLotBailShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate is greater than or equal to DEFAULT_CREATION_DATE
        defaultLotBailShouldBeFound("creationDate.greaterThanOrEqual=" + DEFAULT_CREATION_DATE);

        // Get all the lotBailList where creationDate is greater than or equal to UPDATED_CREATION_DATE
        defaultLotBailShouldNotBeFound("creationDate.greaterThanOrEqual=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate is less than or equal to DEFAULT_CREATION_DATE
        defaultLotBailShouldBeFound("creationDate.lessThanOrEqual=" + DEFAULT_CREATION_DATE);

        // Get all the lotBailList where creationDate is less than or equal to SMALLER_CREATION_DATE
        defaultLotBailShouldNotBeFound("creationDate.lessThanOrEqual=" + SMALLER_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate is less than DEFAULT_CREATION_DATE
        defaultLotBailShouldNotBeFound("creationDate.lessThan=" + DEFAULT_CREATION_DATE);

        // Get all the lotBailList where creationDate is less than UPDATED_CREATION_DATE
        defaultLotBailShouldBeFound("creationDate.lessThan=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where creationDate is greater than DEFAULT_CREATION_DATE
        defaultLotBailShouldNotBeFound("creationDate.greaterThan=" + DEFAULT_CREATION_DATE);

        // Get all the lotBailList where creationDate is greater than SMALLER_CREATION_DATE
        defaultLotBailShouldBeFound("creationDate.greaterThan=" + SMALLER_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate equals to DEFAULT_ACQUISATION_DATE
        defaultLotBailShouldBeFound("acquisationDate.equals=" + DEFAULT_ACQUISATION_DATE);

        // Get all the lotBailList where acquisationDate equals to UPDATED_ACQUISATION_DATE
        defaultLotBailShouldNotBeFound("acquisationDate.equals=" + UPDATED_ACQUISATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate not equals to DEFAULT_ACQUISATION_DATE
        defaultLotBailShouldNotBeFound("acquisationDate.notEquals=" + DEFAULT_ACQUISATION_DATE);

        // Get all the lotBailList where acquisationDate not equals to UPDATED_ACQUISATION_DATE
        defaultLotBailShouldBeFound("acquisationDate.notEquals=" + UPDATED_ACQUISATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate in DEFAULT_ACQUISATION_DATE or UPDATED_ACQUISATION_DATE
        defaultLotBailShouldBeFound("acquisationDate.in=" + DEFAULT_ACQUISATION_DATE + "," + UPDATED_ACQUISATION_DATE);

        // Get all the lotBailList where acquisationDate equals to UPDATED_ACQUISATION_DATE
        defaultLotBailShouldNotBeFound("acquisationDate.in=" + UPDATED_ACQUISATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate is not null
        defaultLotBailShouldBeFound("acquisationDate.specified=true");

        // Get all the lotBailList where acquisationDate is null
        defaultLotBailShouldNotBeFound("acquisationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate is greater than or equal to DEFAULT_ACQUISATION_DATE
        defaultLotBailShouldBeFound("acquisationDate.greaterThanOrEqual=" + DEFAULT_ACQUISATION_DATE);

        // Get all the lotBailList where acquisationDate is greater than or equal to UPDATED_ACQUISATION_DATE
        defaultLotBailShouldNotBeFound("acquisationDate.greaterThanOrEqual=" + UPDATED_ACQUISATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate is less than or equal to DEFAULT_ACQUISATION_DATE
        defaultLotBailShouldBeFound("acquisationDate.lessThanOrEqual=" + DEFAULT_ACQUISATION_DATE);

        // Get all the lotBailList where acquisationDate is less than or equal to SMALLER_ACQUISATION_DATE
        defaultLotBailShouldNotBeFound("acquisationDate.lessThanOrEqual=" + SMALLER_ACQUISATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate is less than DEFAULT_ACQUISATION_DATE
        defaultLotBailShouldNotBeFound("acquisationDate.lessThan=" + DEFAULT_ACQUISATION_DATE);

        // Get all the lotBailList where acquisationDate is less than UPDATED_ACQUISATION_DATE
        defaultLotBailShouldBeFound("acquisationDate.lessThan=" + UPDATED_ACQUISATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationDate is greater than DEFAULT_ACQUISATION_DATE
        defaultLotBailShouldNotBeFound("acquisationDate.greaterThan=" + DEFAULT_ACQUISATION_DATE);

        // Get all the lotBailList where acquisationDate is greater than SMALLER_ACQUISATION_DATE
        defaultLotBailShouldBeFound("acquisationDate.greaterThan=" + SMALLER_ACQUISATION_DATE);
    }


    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface equals to DEFAULT_SURFACE
        defaultLotBailShouldBeFound("surface.equals=" + DEFAULT_SURFACE);

        // Get all the lotBailList where surface equals to UPDATED_SURFACE
        defaultLotBailShouldNotBeFound("surface.equals=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface not equals to DEFAULT_SURFACE
        defaultLotBailShouldNotBeFound("surface.notEquals=" + DEFAULT_SURFACE);

        // Get all the lotBailList where surface not equals to UPDATED_SURFACE
        defaultLotBailShouldBeFound("surface.notEquals=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface in DEFAULT_SURFACE or UPDATED_SURFACE
        defaultLotBailShouldBeFound("surface.in=" + DEFAULT_SURFACE + "," + UPDATED_SURFACE);

        // Get all the lotBailList where surface equals to UPDATED_SURFACE
        defaultLotBailShouldNotBeFound("surface.in=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface is not null
        defaultLotBailShouldBeFound("surface.specified=true");

        // Get all the lotBailList where surface is null
        defaultLotBailShouldNotBeFound("surface.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface is greater than or equal to DEFAULT_SURFACE
        defaultLotBailShouldBeFound("surface.greaterThanOrEqual=" + DEFAULT_SURFACE);

        // Get all the lotBailList where surface is greater than or equal to UPDATED_SURFACE
        defaultLotBailShouldNotBeFound("surface.greaterThanOrEqual=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface is less than or equal to DEFAULT_SURFACE
        defaultLotBailShouldBeFound("surface.lessThanOrEqual=" + DEFAULT_SURFACE);

        // Get all the lotBailList where surface is less than or equal to SMALLER_SURFACE
        defaultLotBailShouldNotBeFound("surface.lessThanOrEqual=" + SMALLER_SURFACE);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface is less than DEFAULT_SURFACE
        defaultLotBailShouldNotBeFound("surface.lessThan=" + DEFAULT_SURFACE);

        // Get all the lotBailList where surface is less than UPDATED_SURFACE
        defaultLotBailShouldBeFound("surface.lessThan=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySurfaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where surface is greater than DEFAULT_SURFACE
        defaultLotBailShouldNotBeFound("surface.greaterThan=" + DEFAULT_SURFACE);

        // Get all the lotBailList where surface is greater than SMALLER_SURFACE
        defaultLotBailShouldBeFound("surface.greaterThan=" + SMALLER_SURFACE);
    }


    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber equals to DEFAULT_PARKINGS_NUMBER
        defaultLotBailShouldBeFound("parkingsNumber.equals=" + DEFAULT_PARKINGS_NUMBER);

        // Get all the lotBailList where parkingsNumber equals to UPDATED_PARKINGS_NUMBER
        defaultLotBailShouldNotBeFound("parkingsNumber.equals=" + UPDATED_PARKINGS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber not equals to DEFAULT_PARKINGS_NUMBER
        defaultLotBailShouldNotBeFound("parkingsNumber.notEquals=" + DEFAULT_PARKINGS_NUMBER);

        // Get all the lotBailList where parkingsNumber not equals to UPDATED_PARKINGS_NUMBER
        defaultLotBailShouldBeFound("parkingsNumber.notEquals=" + UPDATED_PARKINGS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber in DEFAULT_PARKINGS_NUMBER or UPDATED_PARKINGS_NUMBER
        defaultLotBailShouldBeFound("parkingsNumber.in=" + DEFAULT_PARKINGS_NUMBER + "," + UPDATED_PARKINGS_NUMBER);

        // Get all the lotBailList where parkingsNumber equals to UPDATED_PARKINGS_NUMBER
        defaultLotBailShouldNotBeFound("parkingsNumber.in=" + UPDATED_PARKINGS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber is not null
        defaultLotBailShouldBeFound("parkingsNumber.specified=true");

        // Get all the lotBailList where parkingsNumber is null
        defaultLotBailShouldNotBeFound("parkingsNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber is greater than or equal to DEFAULT_PARKINGS_NUMBER
        defaultLotBailShouldBeFound("parkingsNumber.greaterThanOrEqual=" + DEFAULT_PARKINGS_NUMBER);

        // Get all the lotBailList where parkingsNumber is greater than or equal to UPDATED_PARKINGS_NUMBER
        defaultLotBailShouldNotBeFound("parkingsNumber.greaterThanOrEqual=" + UPDATED_PARKINGS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber is less than or equal to DEFAULT_PARKINGS_NUMBER
        defaultLotBailShouldBeFound("parkingsNumber.lessThanOrEqual=" + DEFAULT_PARKINGS_NUMBER);

        // Get all the lotBailList where parkingsNumber is less than or equal to SMALLER_PARKINGS_NUMBER
        defaultLotBailShouldNotBeFound("parkingsNumber.lessThanOrEqual=" + SMALLER_PARKINGS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber is less than DEFAULT_PARKINGS_NUMBER
        defaultLotBailShouldNotBeFound("parkingsNumber.lessThan=" + DEFAULT_PARKINGS_NUMBER);

        // Get all the lotBailList where parkingsNumber is less than UPDATED_PARKINGS_NUMBER
        defaultLotBailShouldBeFound("parkingsNumber.lessThan=" + UPDATED_PARKINGS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByParkingsNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where parkingsNumber is greater than DEFAULT_PARKINGS_NUMBER
        defaultLotBailShouldNotBeFound("parkingsNumber.greaterThan=" + DEFAULT_PARKINGS_NUMBER);

        // Get all the lotBailList where parkingsNumber is greater than SMALLER_PARKINGS_NUMBER
        defaultLotBailShouldBeFound("parkingsNumber.greaterThan=" + SMALLER_PARKINGS_NUMBER);
    }


    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber equals to DEFAULT_FLOORS_NUMBER
        defaultLotBailShouldBeFound("floorsNumber.equals=" + DEFAULT_FLOORS_NUMBER);

        // Get all the lotBailList where floorsNumber equals to UPDATED_FLOORS_NUMBER
        defaultLotBailShouldNotBeFound("floorsNumber.equals=" + UPDATED_FLOORS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber not equals to DEFAULT_FLOORS_NUMBER
        defaultLotBailShouldNotBeFound("floorsNumber.notEquals=" + DEFAULT_FLOORS_NUMBER);

        // Get all the lotBailList where floorsNumber not equals to UPDATED_FLOORS_NUMBER
        defaultLotBailShouldBeFound("floorsNumber.notEquals=" + UPDATED_FLOORS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber in DEFAULT_FLOORS_NUMBER or UPDATED_FLOORS_NUMBER
        defaultLotBailShouldBeFound("floorsNumber.in=" + DEFAULT_FLOORS_NUMBER + "," + UPDATED_FLOORS_NUMBER);

        // Get all the lotBailList where floorsNumber equals to UPDATED_FLOORS_NUMBER
        defaultLotBailShouldNotBeFound("floorsNumber.in=" + UPDATED_FLOORS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber is not null
        defaultLotBailShouldBeFound("floorsNumber.specified=true");

        // Get all the lotBailList where floorsNumber is null
        defaultLotBailShouldNotBeFound("floorsNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber is greater than or equal to DEFAULT_FLOORS_NUMBER
        defaultLotBailShouldBeFound("floorsNumber.greaterThanOrEqual=" + DEFAULT_FLOORS_NUMBER);

        // Get all the lotBailList where floorsNumber is greater than or equal to UPDATED_FLOORS_NUMBER
        defaultLotBailShouldNotBeFound("floorsNumber.greaterThanOrEqual=" + UPDATED_FLOORS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber is less than or equal to DEFAULT_FLOORS_NUMBER
        defaultLotBailShouldBeFound("floorsNumber.lessThanOrEqual=" + DEFAULT_FLOORS_NUMBER);

        // Get all the lotBailList where floorsNumber is less than or equal to SMALLER_FLOORS_NUMBER
        defaultLotBailShouldNotBeFound("floorsNumber.lessThanOrEqual=" + SMALLER_FLOORS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber is less than DEFAULT_FLOORS_NUMBER
        defaultLotBailShouldNotBeFound("floorsNumber.lessThan=" + DEFAULT_FLOORS_NUMBER);

        // Get all the lotBailList where floorsNumber is less than UPDATED_FLOORS_NUMBER
        defaultLotBailShouldBeFound("floorsNumber.lessThan=" + UPDATED_FLOORS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByFloorsNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where floorsNumber is greater than DEFAULT_FLOORS_NUMBER
        defaultLotBailShouldNotBeFound("floorsNumber.greaterThan=" + DEFAULT_FLOORS_NUMBER);

        // Get all the lotBailList where floorsNumber is greater than SMALLER_FLOORS_NUMBER
        defaultLotBailShouldBeFound("floorsNumber.greaterThan=" + SMALLER_FLOORS_NUMBER);
    }


    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot equals to DEFAULT_REAL_NUMBER_OF_LOT
        defaultLotBailShouldBeFound("realNumberOfLot.equals=" + DEFAULT_REAL_NUMBER_OF_LOT);

        // Get all the lotBailList where realNumberOfLot equals to UPDATED_REAL_NUMBER_OF_LOT
        defaultLotBailShouldNotBeFound("realNumberOfLot.equals=" + UPDATED_REAL_NUMBER_OF_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot not equals to DEFAULT_REAL_NUMBER_OF_LOT
        defaultLotBailShouldNotBeFound("realNumberOfLot.notEquals=" + DEFAULT_REAL_NUMBER_OF_LOT);

        // Get all the lotBailList where realNumberOfLot not equals to UPDATED_REAL_NUMBER_OF_LOT
        defaultLotBailShouldBeFound("realNumberOfLot.notEquals=" + UPDATED_REAL_NUMBER_OF_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot in DEFAULT_REAL_NUMBER_OF_LOT or UPDATED_REAL_NUMBER_OF_LOT
        defaultLotBailShouldBeFound("realNumberOfLot.in=" + DEFAULT_REAL_NUMBER_OF_LOT + "," + UPDATED_REAL_NUMBER_OF_LOT);

        // Get all the lotBailList where realNumberOfLot equals to UPDATED_REAL_NUMBER_OF_LOT
        defaultLotBailShouldNotBeFound("realNumberOfLot.in=" + UPDATED_REAL_NUMBER_OF_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot is not null
        defaultLotBailShouldBeFound("realNumberOfLot.specified=true");

        // Get all the lotBailList where realNumberOfLot is null
        defaultLotBailShouldNotBeFound("realNumberOfLot.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot is greater than or equal to DEFAULT_REAL_NUMBER_OF_LOT
        defaultLotBailShouldBeFound("realNumberOfLot.greaterThanOrEqual=" + DEFAULT_REAL_NUMBER_OF_LOT);

        // Get all the lotBailList where realNumberOfLot is greater than or equal to UPDATED_REAL_NUMBER_OF_LOT
        defaultLotBailShouldNotBeFound("realNumberOfLot.greaterThanOrEqual=" + UPDATED_REAL_NUMBER_OF_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot is less than or equal to DEFAULT_REAL_NUMBER_OF_LOT
        defaultLotBailShouldBeFound("realNumberOfLot.lessThanOrEqual=" + DEFAULT_REAL_NUMBER_OF_LOT);

        // Get all the lotBailList where realNumberOfLot is less than or equal to SMALLER_REAL_NUMBER_OF_LOT
        defaultLotBailShouldNotBeFound("realNumberOfLot.lessThanOrEqual=" + SMALLER_REAL_NUMBER_OF_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot is less than DEFAULT_REAL_NUMBER_OF_LOT
        defaultLotBailShouldNotBeFound("realNumberOfLot.lessThan=" + DEFAULT_REAL_NUMBER_OF_LOT);

        // Get all the lotBailList where realNumberOfLot is less than UPDATED_REAL_NUMBER_OF_LOT
        defaultLotBailShouldBeFound("realNumberOfLot.lessThan=" + UPDATED_REAL_NUMBER_OF_LOT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRealNumberOfLotIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where realNumberOfLot is greater than DEFAULT_REAL_NUMBER_OF_LOT
        defaultLotBailShouldNotBeFound("realNumberOfLot.greaterThan=" + DEFAULT_REAL_NUMBER_OF_LOT);

        // Get all the lotBailList where realNumberOfLot is greater than SMALLER_REAL_NUMBER_OF_LOT
        defaultLotBailShouldBeFound("realNumberOfLot.greaterThan=" + SMALLER_REAL_NUMBER_OF_LOT);
    }


    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits equals to DEFAULT_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.equals=" + DEFAULT_NUMBER_OF_SECONDARY_UNITS);

        // Get all the lotBailList where numberOfSecondaryUnits equals to UPDATED_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.equals=" + UPDATED_NUMBER_OF_SECONDARY_UNITS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits not equals to DEFAULT_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.notEquals=" + DEFAULT_NUMBER_OF_SECONDARY_UNITS);

        // Get all the lotBailList where numberOfSecondaryUnits not equals to UPDATED_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.notEquals=" + UPDATED_NUMBER_OF_SECONDARY_UNITS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits in DEFAULT_NUMBER_OF_SECONDARY_UNITS or UPDATED_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.in=" + DEFAULT_NUMBER_OF_SECONDARY_UNITS + "," + UPDATED_NUMBER_OF_SECONDARY_UNITS);

        // Get all the lotBailList where numberOfSecondaryUnits equals to UPDATED_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.in=" + UPDATED_NUMBER_OF_SECONDARY_UNITS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits is not null
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.specified=true");

        // Get all the lotBailList where numberOfSecondaryUnits is null
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits is greater than or equal to DEFAULT_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_SECONDARY_UNITS);

        // Get all the lotBailList where numberOfSecondaryUnits is greater than or equal to UPDATED_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.greaterThanOrEqual=" + UPDATED_NUMBER_OF_SECONDARY_UNITS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits is less than or equal to DEFAULT_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.lessThanOrEqual=" + DEFAULT_NUMBER_OF_SECONDARY_UNITS);

        // Get all the lotBailList where numberOfSecondaryUnits is less than or equal to SMALLER_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.lessThanOrEqual=" + SMALLER_NUMBER_OF_SECONDARY_UNITS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits is less than DEFAULT_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.lessThan=" + DEFAULT_NUMBER_OF_SECONDARY_UNITS);

        // Get all the lotBailList where numberOfSecondaryUnits is less than UPDATED_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.lessThan=" + UPDATED_NUMBER_OF_SECONDARY_UNITS);
    }

    @Test
    @Transactional
    public void getAllLotBailsByNumberOfSecondaryUnitsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where numberOfSecondaryUnits is greater than DEFAULT_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldNotBeFound("numberOfSecondaryUnits.greaterThan=" + DEFAULT_NUMBER_OF_SECONDARY_UNITS);

        // Get all the lotBailList where numberOfSecondaryUnits is greater than SMALLER_NUMBER_OF_SECONDARY_UNITS
        defaultLotBailShouldBeFound("numberOfSecondaryUnits.greaterThan=" + SMALLER_NUMBER_OF_SECONDARY_UNITS);
    }


    @Test
    @Transactional
    public void getAllLotBailsByOutDoorParkingIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where outDoorParking equals to DEFAULT_OUT_DOOR_PARKING
        defaultLotBailShouldBeFound("outDoorParking.equals=" + DEFAULT_OUT_DOOR_PARKING);

        // Get all the lotBailList where outDoorParking equals to UPDATED_OUT_DOOR_PARKING
        defaultLotBailShouldNotBeFound("outDoorParking.equals=" + UPDATED_OUT_DOOR_PARKING);
    }

    @Test
    @Transactional
    public void getAllLotBailsByOutDoorParkingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where outDoorParking not equals to DEFAULT_OUT_DOOR_PARKING
        defaultLotBailShouldNotBeFound("outDoorParking.notEquals=" + DEFAULT_OUT_DOOR_PARKING);

        // Get all the lotBailList where outDoorParking not equals to UPDATED_OUT_DOOR_PARKING
        defaultLotBailShouldBeFound("outDoorParking.notEquals=" + UPDATED_OUT_DOOR_PARKING);
    }

    @Test
    @Transactional
    public void getAllLotBailsByOutDoorParkingIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where outDoorParking in DEFAULT_OUT_DOOR_PARKING or UPDATED_OUT_DOOR_PARKING
        defaultLotBailShouldBeFound("outDoorParking.in=" + DEFAULT_OUT_DOOR_PARKING + "," + UPDATED_OUT_DOOR_PARKING);

        // Get all the lotBailList where outDoorParking equals to UPDATED_OUT_DOOR_PARKING
        defaultLotBailShouldNotBeFound("outDoorParking.in=" + UPDATED_OUT_DOOR_PARKING);
    }

    @Test
    @Transactional
    public void getAllLotBailsByOutDoorParkingIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where outDoorParking is not null
        defaultLotBailShouldBeFound("outDoorParking.specified=true");

        // Get all the lotBailList where outDoorParking is null
        defaultLotBailShouldNotBeFound("outDoorParking.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByLotForMultipleOccupationIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where lotForMultipleOccupation equals to DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION
        defaultLotBailShouldBeFound("lotForMultipleOccupation.equals=" + DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION);

        // Get all the lotBailList where lotForMultipleOccupation equals to UPDATED_LOT_FOR_MULTIPLE_OCCUPATION
        defaultLotBailShouldNotBeFound("lotForMultipleOccupation.equals=" + UPDATED_LOT_FOR_MULTIPLE_OCCUPATION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByLotForMultipleOccupationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where lotForMultipleOccupation not equals to DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION
        defaultLotBailShouldNotBeFound("lotForMultipleOccupation.notEquals=" + DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION);

        // Get all the lotBailList where lotForMultipleOccupation not equals to UPDATED_LOT_FOR_MULTIPLE_OCCUPATION
        defaultLotBailShouldBeFound("lotForMultipleOccupation.notEquals=" + UPDATED_LOT_FOR_MULTIPLE_OCCUPATION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByLotForMultipleOccupationIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where lotForMultipleOccupation in DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION or UPDATED_LOT_FOR_MULTIPLE_OCCUPATION
        defaultLotBailShouldBeFound("lotForMultipleOccupation.in=" + DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION + "," + UPDATED_LOT_FOR_MULTIPLE_OCCUPATION);

        // Get all the lotBailList where lotForMultipleOccupation equals to UPDATED_LOT_FOR_MULTIPLE_OCCUPATION
        defaultLotBailShouldNotBeFound("lotForMultipleOccupation.in=" + UPDATED_LOT_FOR_MULTIPLE_OCCUPATION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByLotForMultipleOccupationIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where lotForMultipleOccupation is not null
        defaultLotBailShouldBeFound("lotForMultipleOccupation.specified=true");

        // Get all the lotBailList where lotForMultipleOccupation is null
        defaultLotBailShouldNotBeFound("lotForMultipleOccupation.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter equals to DEFAULT_PRICE_OF_SQUARE_METER
        defaultLotBailShouldBeFound("priceOfSquareMeter.equals=" + DEFAULT_PRICE_OF_SQUARE_METER);

        // Get all the lotBailList where priceOfSquareMeter equals to UPDATED_PRICE_OF_SQUARE_METER
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.equals=" + UPDATED_PRICE_OF_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter not equals to DEFAULT_PRICE_OF_SQUARE_METER
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.notEquals=" + DEFAULT_PRICE_OF_SQUARE_METER);

        // Get all the lotBailList where priceOfSquareMeter not equals to UPDATED_PRICE_OF_SQUARE_METER
        defaultLotBailShouldBeFound("priceOfSquareMeter.notEquals=" + UPDATED_PRICE_OF_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter in DEFAULT_PRICE_OF_SQUARE_METER or UPDATED_PRICE_OF_SQUARE_METER
        defaultLotBailShouldBeFound("priceOfSquareMeter.in=" + DEFAULT_PRICE_OF_SQUARE_METER + "," + UPDATED_PRICE_OF_SQUARE_METER);

        // Get all the lotBailList where priceOfSquareMeter equals to UPDATED_PRICE_OF_SQUARE_METER
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.in=" + UPDATED_PRICE_OF_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter is not null
        defaultLotBailShouldBeFound("priceOfSquareMeter.specified=true");

        // Get all the lotBailList where priceOfSquareMeter is null
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter is greater than or equal to DEFAULT_PRICE_OF_SQUARE_METER
        defaultLotBailShouldBeFound("priceOfSquareMeter.greaterThanOrEqual=" + DEFAULT_PRICE_OF_SQUARE_METER);

        // Get all the lotBailList where priceOfSquareMeter is greater than or equal to UPDATED_PRICE_OF_SQUARE_METER
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.greaterThanOrEqual=" + UPDATED_PRICE_OF_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter is less than or equal to DEFAULT_PRICE_OF_SQUARE_METER
        defaultLotBailShouldBeFound("priceOfSquareMeter.lessThanOrEqual=" + DEFAULT_PRICE_OF_SQUARE_METER);

        // Get all the lotBailList where priceOfSquareMeter is less than or equal to SMALLER_PRICE_OF_SQUARE_METER
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.lessThanOrEqual=" + SMALLER_PRICE_OF_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter is less than DEFAULT_PRICE_OF_SQUARE_METER
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.lessThan=" + DEFAULT_PRICE_OF_SQUARE_METER);

        // Get all the lotBailList where priceOfSquareMeter is less than UPDATED_PRICE_OF_SQUARE_METER
        defaultLotBailShouldBeFound("priceOfSquareMeter.lessThan=" + UPDATED_PRICE_OF_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPriceOfSquareMeterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where priceOfSquareMeter is greater than DEFAULT_PRICE_OF_SQUARE_METER
        defaultLotBailShouldNotBeFound("priceOfSquareMeter.greaterThan=" + DEFAULT_PRICE_OF_SQUARE_METER);

        // Get all the lotBailList where priceOfSquareMeter is greater than SMALLER_PRICE_OF_SQUARE_METER
        defaultLotBailShouldBeFound("priceOfSquareMeter.greaterThan=" + SMALLER_PRICE_OF_SQUARE_METER);
    }


    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter equals to DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.equals=" + DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER);

        // Get all the lotBailList where rentalValueForSquareMeter equals to UPDATED_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.equals=" + UPDATED_RENTAL_VALUE_FOR_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter not equals to DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.notEquals=" + DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER);

        // Get all the lotBailList where rentalValueForSquareMeter not equals to UPDATED_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.notEquals=" + UPDATED_RENTAL_VALUE_FOR_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter in DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER or UPDATED_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.in=" + DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER + "," + UPDATED_RENTAL_VALUE_FOR_SQUARE_METER);

        // Get all the lotBailList where rentalValueForSquareMeter equals to UPDATED_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.in=" + UPDATED_RENTAL_VALUE_FOR_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter is not null
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.specified=true");

        // Get all the lotBailList where rentalValueForSquareMeter is null
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter is greater than or equal to DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.greaterThanOrEqual=" + DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER);

        // Get all the lotBailList where rentalValueForSquareMeter is greater than or equal to UPDATED_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.greaterThanOrEqual=" + UPDATED_RENTAL_VALUE_FOR_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter is less than or equal to DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.lessThanOrEqual=" + DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER);

        // Get all the lotBailList where rentalValueForSquareMeter is less than or equal to SMALLER_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.lessThanOrEqual=" + SMALLER_RENTAL_VALUE_FOR_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter is less than DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.lessThan=" + DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER);

        // Get all the lotBailList where rentalValueForSquareMeter is less than UPDATED_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.lessThan=" + UPDATED_RENTAL_VALUE_FOR_SQUARE_METER);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentalValueForSquareMeterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentalValueForSquareMeter is greater than DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldNotBeFound("rentalValueForSquareMeter.greaterThan=" + DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER);

        // Get all the lotBailList where rentalValueForSquareMeter is greater than SMALLER_RENTAL_VALUE_FOR_SQUARE_METER
        defaultLotBailShouldBeFound("rentalValueForSquareMeter.greaterThan=" + SMALLER_RENTAL_VALUE_FOR_SQUARE_METER);
    }


    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount equals to DEFAULT_TRANSFER_AMOUNT
        defaultLotBailShouldBeFound("transferAmount.equals=" + DEFAULT_TRANSFER_AMOUNT);

        // Get all the lotBailList where transferAmount equals to UPDATED_TRANSFER_AMOUNT
        defaultLotBailShouldNotBeFound("transferAmount.equals=" + UPDATED_TRANSFER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount not equals to DEFAULT_TRANSFER_AMOUNT
        defaultLotBailShouldNotBeFound("transferAmount.notEquals=" + DEFAULT_TRANSFER_AMOUNT);

        // Get all the lotBailList where transferAmount not equals to UPDATED_TRANSFER_AMOUNT
        defaultLotBailShouldBeFound("transferAmount.notEquals=" + UPDATED_TRANSFER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount in DEFAULT_TRANSFER_AMOUNT or UPDATED_TRANSFER_AMOUNT
        defaultLotBailShouldBeFound("transferAmount.in=" + DEFAULT_TRANSFER_AMOUNT + "," + UPDATED_TRANSFER_AMOUNT);

        // Get all the lotBailList where transferAmount equals to UPDATED_TRANSFER_AMOUNT
        defaultLotBailShouldNotBeFound("transferAmount.in=" + UPDATED_TRANSFER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount is not null
        defaultLotBailShouldBeFound("transferAmount.specified=true");

        // Get all the lotBailList where transferAmount is null
        defaultLotBailShouldNotBeFound("transferAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount is greater than or equal to DEFAULT_TRANSFER_AMOUNT
        defaultLotBailShouldBeFound("transferAmount.greaterThanOrEqual=" + DEFAULT_TRANSFER_AMOUNT);

        // Get all the lotBailList where transferAmount is greater than or equal to UPDATED_TRANSFER_AMOUNT
        defaultLotBailShouldNotBeFound("transferAmount.greaterThanOrEqual=" + UPDATED_TRANSFER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount is less than or equal to DEFAULT_TRANSFER_AMOUNT
        defaultLotBailShouldBeFound("transferAmount.lessThanOrEqual=" + DEFAULT_TRANSFER_AMOUNT);

        // Get all the lotBailList where transferAmount is less than or equal to SMALLER_TRANSFER_AMOUNT
        defaultLotBailShouldNotBeFound("transferAmount.lessThanOrEqual=" + SMALLER_TRANSFER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount is less than DEFAULT_TRANSFER_AMOUNT
        defaultLotBailShouldNotBeFound("transferAmount.lessThan=" + DEFAULT_TRANSFER_AMOUNT);

        // Get all the lotBailList where transferAmount is less than UPDATED_TRANSFER_AMOUNT
        defaultLotBailShouldBeFound("transferAmount.lessThan=" + UPDATED_TRANSFER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByTransferAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where transferAmount is greater than DEFAULT_TRANSFER_AMOUNT
        defaultLotBailShouldNotBeFound("transferAmount.greaterThan=" + DEFAULT_TRANSFER_AMOUNT);

        // Get all the lotBailList where transferAmount is greater than SMALLER_TRANSFER_AMOUNT
        defaultLotBailShouldBeFound("transferAmount.greaterThan=" + SMALLER_TRANSFER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount equals to DEFAULT_ACQUISATION_AMOUNT
        defaultLotBailShouldBeFound("acquisationAmount.equals=" + DEFAULT_ACQUISATION_AMOUNT);

        // Get all the lotBailList where acquisationAmount equals to UPDATED_ACQUISATION_AMOUNT
        defaultLotBailShouldNotBeFound("acquisationAmount.equals=" + UPDATED_ACQUISATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount not equals to DEFAULT_ACQUISATION_AMOUNT
        defaultLotBailShouldNotBeFound("acquisationAmount.notEquals=" + DEFAULT_ACQUISATION_AMOUNT);

        // Get all the lotBailList where acquisationAmount not equals to UPDATED_ACQUISATION_AMOUNT
        defaultLotBailShouldBeFound("acquisationAmount.notEquals=" + UPDATED_ACQUISATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount in DEFAULT_ACQUISATION_AMOUNT or UPDATED_ACQUISATION_AMOUNT
        defaultLotBailShouldBeFound("acquisationAmount.in=" + DEFAULT_ACQUISATION_AMOUNT + "," + UPDATED_ACQUISATION_AMOUNT);

        // Get all the lotBailList where acquisationAmount equals to UPDATED_ACQUISATION_AMOUNT
        defaultLotBailShouldNotBeFound("acquisationAmount.in=" + UPDATED_ACQUISATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount is not null
        defaultLotBailShouldBeFound("acquisationAmount.specified=true");

        // Get all the lotBailList where acquisationAmount is null
        defaultLotBailShouldNotBeFound("acquisationAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount is greater than or equal to DEFAULT_ACQUISATION_AMOUNT
        defaultLotBailShouldBeFound("acquisationAmount.greaterThanOrEqual=" + DEFAULT_ACQUISATION_AMOUNT);

        // Get all the lotBailList where acquisationAmount is greater than or equal to UPDATED_ACQUISATION_AMOUNT
        defaultLotBailShouldNotBeFound("acquisationAmount.greaterThanOrEqual=" + UPDATED_ACQUISATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount is less than or equal to DEFAULT_ACQUISATION_AMOUNT
        defaultLotBailShouldBeFound("acquisationAmount.lessThanOrEqual=" + DEFAULT_ACQUISATION_AMOUNT);

        // Get all the lotBailList where acquisationAmount is less than or equal to SMALLER_ACQUISATION_AMOUNT
        defaultLotBailShouldNotBeFound("acquisationAmount.lessThanOrEqual=" + SMALLER_ACQUISATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount is less than DEFAULT_ACQUISATION_AMOUNT
        defaultLotBailShouldNotBeFound("acquisationAmount.lessThan=" + DEFAULT_ACQUISATION_AMOUNT);

        // Get all the lotBailList where acquisationAmount is less than UPDATED_ACQUISATION_AMOUNT
        defaultLotBailShouldBeFound("acquisationAmount.lessThan=" + UPDATED_ACQUISATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAcquisationAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where acquisationAmount is greater than DEFAULT_ACQUISATION_AMOUNT
        defaultLotBailShouldNotBeFound("acquisationAmount.greaterThan=" + DEFAULT_ACQUISATION_AMOUNT);

        // Get all the lotBailList where acquisationAmount is greater than SMALLER_ACQUISATION_AMOUNT
        defaultLotBailShouldBeFound("acquisationAmount.greaterThan=" + SMALLER_ACQUISATION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount equals to DEFAULT_RENT_AMOUNT
        defaultLotBailShouldBeFound("rentAmount.equals=" + DEFAULT_RENT_AMOUNT);

        // Get all the lotBailList where rentAmount equals to UPDATED_RENT_AMOUNT
        defaultLotBailShouldNotBeFound("rentAmount.equals=" + UPDATED_RENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount not equals to DEFAULT_RENT_AMOUNT
        defaultLotBailShouldNotBeFound("rentAmount.notEquals=" + DEFAULT_RENT_AMOUNT);

        // Get all the lotBailList where rentAmount not equals to UPDATED_RENT_AMOUNT
        defaultLotBailShouldBeFound("rentAmount.notEquals=" + UPDATED_RENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount in DEFAULT_RENT_AMOUNT or UPDATED_RENT_AMOUNT
        defaultLotBailShouldBeFound("rentAmount.in=" + DEFAULT_RENT_AMOUNT + "," + UPDATED_RENT_AMOUNT);

        // Get all the lotBailList where rentAmount equals to UPDATED_RENT_AMOUNT
        defaultLotBailShouldNotBeFound("rentAmount.in=" + UPDATED_RENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount is not null
        defaultLotBailShouldBeFound("rentAmount.specified=true");

        // Get all the lotBailList where rentAmount is null
        defaultLotBailShouldNotBeFound("rentAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount is greater than or equal to DEFAULT_RENT_AMOUNT
        defaultLotBailShouldBeFound("rentAmount.greaterThanOrEqual=" + DEFAULT_RENT_AMOUNT);

        // Get all the lotBailList where rentAmount is greater than or equal to UPDATED_RENT_AMOUNT
        defaultLotBailShouldNotBeFound("rentAmount.greaterThanOrEqual=" + UPDATED_RENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount is less than or equal to DEFAULT_RENT_AMOUNT
        defaultLotBailShouldBeFound("rentAmount.lessThanOrEqual=" + DEFAULT_RENT_AMOUNT);

        // Get all the lotBailList where rentAmount is less than or equal to SMALLER_RENT_AMOUNT
        defaultLotBailShouldNotBeFound("rentAmount.lessThanOrEqual=" + SMALLER_RENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount is less than DEFAULT_RENT_AMOUNT
        defaultLotBailShouldNotBeFound("rentAmount.lessThan=" + DEFAULT_RENT_AMOUNT);

        // Get all the lotBailList where rentAmount is less than UPDATED_RENT_AMOUNT
        defaultLotBailShouldBeFound("rentAmount.lessThan=" + UPDATED_RENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where rentAmount is greater than DEFAULT_RENT_AMOUNT
        defaultLotBailShouldNotBeFound("rentAmount.greaterThan=" + DEFAULT_RENT_AMOUNT);

        // Get all the lotBailList where rentAmount is greater than SMALLER_RENT_AMOUNT
        defaultLotBailShouldBeFound("rentAmount.greaterThan=" + SMALLER_RENT_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor equals to DEFAULT_POOL_FACTOR
        defaultLotBailShouldBeFound("poolFactor.equals=" + DEFAULT_POOL_FACTOR);

        // Get all the lotBailList where poolFactor equals to UPDATED_POOL_FACTOR
        defaultLotBailShouldNotBeFound("poolFactor.equals=" + UPDATED_POOL_FACTOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor not equals to DEFAULT_POOL_FACTOR
        defaultLotBailShouldNotBeFound("poolFactor.notEquals=" + DEFAULT_POOL_FACTOR);

        // Get all the lotBailList where poolFactor not equals to UPDATED_POOL_FACTOR
        defaultLotBailShouldBeFound("poolFactor.notEquals=" + UPDATED_POOL_FACTOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor in DEFAULT_POOL_FACTOR or UPDATED_POOL_FACTOR
        defaultLotBailShouldBeFound("poolFactor.in=" + DEFAULT_POOL_FACTOR + "," + UPDATED_POOL_FACTOR);

        // Get all the lotBailList where poolFactor equals to UPDATED_POOL_FACTOR
        defaultLotBailShouldNotBeFound("poolFactor.in=" + UPDATED_POOL_FACTOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor is not null
        defaultLotBailShouldBeFound("poolFactor.specified=true");

        // Get all the lotBailList where poolFactor is null
        defaultLotBailShouldNotBeFound("poolFactor.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor is greater than or equal to DEFAULT_POOL_FACTOR
        defaultLotBailShouldBeFound("poolFactor.greaterThanOrEqual=" + DEFAULT_POOL_FACTOR);

        // Get all the lotBailList where poolFactor is greater than or equal to UPDATED_POOL_FACTOR
        defaultLotBailShouldNotBeFound("poolFactor.greaterThanOrEqual=" + UPDATED_POOL_FACTOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor is less than or equal to DEFAULT_POOL_FACTOR
        defaultLotBailShouldBeFound("poolFactor.lessThanOrEqual=" + DEFAULT_POOL_FACTOR);

        // Get all the lotBailList where poolFactor is less than or equal to SMALLER_POOL_FACTOR
        defaultLotBailShouldNotBeFound("poolFactor.lessThanOrEqual=" + SMALLER_POOL_FACTOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor is less than DEFAULT_POOL_FACTOR
        defaultLotBailShouldNotBeFound("poolFactor.lessThan=" + DEFAULT_POOL_FACTOR);

        // Get all the lotBailList where poolFactor is less than UPDATED_POOL_FACTOR
        defaultLotBailShouldBeFound("poolFactor.lessThan=" + UPDATED_POOL_FACTOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByPoolFactorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where poolFactor is greater than DEFAULT_POOL_FACTOR
        defaultLotBailShouldNotBeFound("poolFactor.greaterThan=" + DEFAULT_POOL_FACTOR);

        // Get all the lotBailList where poolFactor is greater than SMALLER_POOL_FACTOR
        defaultLotBailShouldBeFound("poolFactor.greaterThan=" + SMALLER_POOL_FACTOR);
    }


    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate equals to DEFAULT_MATURITY_DATE
        defaultLotBailShouldBeFound("maturityDate.equals=" + DEFAULT_MATURITY_DATE);

        // Get all the lotBailList where maturityDate equals to UPDATED_MATURITY_DATE
        defaultLotBailShouldNotBeFound("maturityDate.equals=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate not equals to DEFAULT_MATURITY_DATE
        defaultLotBailShouldNotBeFound("maturityDate.notEquals=" + DEFAULT_MATURITY_DATE);

        // Get all the lotBailList where maturityDate not equals to UPDATED_MATURITY_DATE
        defaultLotBailShouldBeFound("maturityDate.notEquals=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate in DEFAULT_MATURITY_DATE or UPDATED_MATURITY_DATE
        defaultLotBailShouldBeFound("maturityDate.in=" + DEFAULT_MATURITY_DATE + "," + UPDATED_MATURITY_DATE);

        // Get all the lotBailList where maturityDate equals to UPDATED_MATURITY_DATE
        defaultLotBailShouldNotBeFound("maturityDate.in=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate is not null
        defaultLotBailShouldBeFound("maturityDate.specified=true");

        // Get all the lotBailList where maturityDate is null
        defaultLotBailShouldNotBeFound("maturityDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate is greater than or equal to DEFAULT_MATURITY_DATE
        defaultLotBailShouldBeFound("maturityDate.greaterThanOrEqual=" + DEFAULT_MATURITY_DATE);

        // Get all the lotBailList where maturityDate is greater than or equal to UPDATED_MATURITY_DATE
        defaultLotBailShouldNotBeFound("maturityDate.greaterThanOrEqual=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate is less than or equal to DEFAULT_MATURITY_DATE
        defaultLotBailShouldBeFound("maturityDate.lessThanOrEqual=" + DEFAULT_MATURITY_DATE);

        // Get all the lotBailList where maturityDate is less than or equal to SMALLER_MATURITY_DATE
        defaultLotBailShouldNotBeFound("maturityDate.lessThanOrEqual=" + SMALLER_MATURITY_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate is less than DEFAULT_MATURITY_DATE
        defaultLotBailShouldNotBeFound("maturityDate.lessThan=" + DEFAULT_MATURITY_DATE);

        // Get all the lotBailList where maturityDate is less than UPDATED_MATURITY_DATE
        defaultLotBailShouldBeFound("maturityDate.lessThan=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByMaturityDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where maturityDate is greater than DEFAULT_MATURITY_DATE
        defaultLotBailShouldNotBeFound("maturityDate.greaterThan=" + DEFAULT_MATURITY_DATE);

        // Get all the lotBailList where maturityDate is greater than SMALLER_MATURITY_DATE
        defaultLotBailShouldBeFound("maturityDate.greaterThan=" + SMALLER_MATURITY_DATE);
    }


    @Test
    @Transactional
    public void getAllLotBailsByConvertibilityIndicatorIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where convertibilityIndicator equals to DEFAULT_CONVERTIBILITY_INDICATOR
        defaultLotBailShouldBeFound("convertibilityIndicator.equals=" + DEFAULT_CONVERTIBILITY_INDICATOR);

        // Get all the lotBailList where convertibilityIndicator equals to UPDATED_CONVERTIBILITY_INDICATOR
        defaultLotBailShouldNotBeFound("convertibilityIndicator.equals=" + UPDATED_CONVERTIBILITY_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByConvertibilityIndicatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where convertibilityIndicator not equals to DEFAULT_CONVERTIBILITY_INDICATOR
        defaultLotBailShouldNotBeFound("convertibilityIndicator.notEquals=" + DEFAULT_CONVERTIBILITY_INDICATOR);

        // Get all the lotBailList where convertibilityIndicator not equals to UPDATED_CONVERTIBILITY_INDICATOR
        defaultLotBailShouldBeFound("convertibilityIndicator.notEquals=" + UPDATED_CONVERTIBILITY_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByConvertibilityIndicatorIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where convertibilityIndicator in DEFAULT_CONVERTIBILITY_INDICATOR or UPDATED_CONVERTIBILITY_INDICATOR
        defaultLotBailShouldBeFound("convertibilityIndicator.in=" + DEFAULT_CONVERTIBILITY_INDICATOR + "," + UPDATED_CONVERTIBILITY_INDICATOR);

        // Get all the lotBailList where convertibilityIndicator equals to UPDATED_CONVERTIBILITY_INDICATOR
        defaultLotBailShouldNotBeFound("convertibilityIndicator.in=" + UPDATED_CONVERTIBILITY_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByConvertibilityIndicatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where convertibilityIndicator is not null
        defaultLotBailShouldBeFound("convertibilityIndicator.specified=true");

        // Get all the lotBailList where convertibilityIndicator is null
        defaultLotBailShouldNotBeFound("convertibilityIndicator.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsBySubordinationIndicatorIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where subordinationIndicator equals to DEFAULT_SUBORDINATION_INDICATOR
        defaultLotBailShouldBeFound("subordinationIndicator.equals=" + DEFAULT_SUBORDINATION_INDICATOR);

        // Get all the lotBailList where subordinationIndicator equals to UPDATED_SUBORDINATION_INDICATOR
        defaultLotBailShouldNotBeFound("subordinationIndicator.equals=" + UPDATED_SUBORDINATION_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySubordinationIndicatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where subordinationIndicator not equals to DEFAULT_SUBORDINATION_INDICATOR
        defaultLotBailShouldNotBeFound("subordinationIndicator.notEquals=" + DEFAULT_SUBORDINATION_INDICATOR);

        // Get all the lotBailList where subordinationIndicator not equals to UPDATED_SUBORDINATION_INDICATOR
        defaultLotBailShouldBeFound("subordinationIndicator.notEquals=" + UPDATED_SUBORDINATION_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySubordinationIndicatorIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where subordinationIndicator in DEFAULT_SUBORDINATION_INDICATOR or UPDATED_SUBORDINATION_INDICATOR
        defaultLotBailShouldBeFound("subordinationIndicator.in=" + DEFAULT_SUBORDINATION_INDICATOR + "," + UPDATED_SUBORDINATION_INDICATOR);

        // Get all the lotBailList where subordinationIndicator equals to UPDATED_SUBORDINATION_INDICATOR
        defaultLotBailShouldNotBeFound("subordinationIndicator.in=" + UPDATED_SUBORDINATION_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsBySubordinationIndicatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where subordinationIndicator is not null
        defaultLotBailShouldBeFound("subordinationIndicator.specified=true");

        // Get all the lotBailList where subordinationIndicator is null
        defaultLotBailShouldNotBeFound("subordinationIndicator.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByIndexedIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where indexed equals to DEFAULT_INDEXED
        defaultLotBailShouldBeFound("indexed.equals=" + DEFAULT_INDEXED);

        // Get all the lotBailList where indexed equals to UPDATED_INDEXED
        defaultLotBailShouldNotBeFound("indexed.equals=" + UPDATED_INDEXED);
    }

    @Test
    @Transactional
    public void getAllLotBailsByIndexedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where indexed not equals to DEFAULT_INDEXED
        defaultLotBailShouldNotBeFound("indexed.notEquals=" + DEFAULT_INDEXED);

        // Get all the lotBailList where indexed not equals to UPDATED_INDEXED
        defaultLotBailShouldBeFound("indexed.notEquals=" + UPDATED_INDEXED);
    }

    @Test
    @Transactional
    public void getAllLotBailsByIndexedIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where indexed in DEFAULT_INDEXED or UPDATED_INDEXED
        defaultLotBailShouldBeFound("indexed.in=" + DEFAULT_INDEXED + "," + UPDATED_INDEXED);

        // Get all the lotBailList where indexed equals to UPDATED_INDEXED
        defaultLotBailShouldNotBeFound("indexed.in=" + UPDATED_INDEXED);
    }

    @Test
    @Transactional
    public void getAllLotBailsByIndexedIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where indexed is not null
        defaultLotBailShouldBeFound("indexed.specified=true");

        // Get all the lotBailList where indexed is null
        defaultLotBailShouldNotBeFound("indexed.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByEligibilityIndicatorIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where eligibilityIndicator equals to DEFAULT_ELIGIBILITY_INDICATOR
        defaultLotBailShouldBeFound("eligibilityIndicator.equals=" + DEFAULT_ELIGIBILITY_INDICATOR);

        // Get all the lotBailList where eligibilityIndicator equals to UPDATED_ELIGIBILITY_INDICATOR
        defaultLotBailShouldNotBeFound("eligibilityIndicator.equals=" + UPDATED_ELIGIBILITY_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByEligibilityIndicatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where eligibilityIndicator not equals to DEFAULT_ELIGIBILITY_INDICATOR
        defaultLotBailShouldNotBeFound("eligibilityIndicator.notEquals=" + DEFAULT_ELIGIBILITY_INDICATOR);

        // Get all the lotBailList where eligibilityIndicator not equals to UPDATED_ELIGIBILITY_INDICATOR
        defaultLotBailShouldBeFound("eligibilityIndicator.notEquals=" + UPDATED_ELIGIBILITY_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByEligibilityIndicatorIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where eligibilityIndicator in DEFAULT_ELIGIBILITY_INDICATOR or UPDATED_ELIGIBILITY_INDICATOR
        defaultLotBailShouldBeFound("eligibilityIndicator.in=" + DEFAULT_ELIGIBILITY_INDICATOR + "," + UPDATED_ELIGIBILITY_INDICATOR);

        // Get all the lotBailList where eligibilityIndicator equals to UPDATED_ELIGIBILITY_INDICATOR
        defaultLotBailShouldNotBeFound("eligibilityIndicator.in=" + UPDATED_ELIGIBILITY_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllLotBailsByEligibilityIndicatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where eligibilityIndicator is not null
        defaultLotBailShouldBeFound("eligibilityIndicator.specified=true");

        // Get all the lotBailList where eligibilityIndicator is null
        defaultLotBailShouldNotBeFound("eligibilityIndicator.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium equals to DEFAULT_RISK_PREMIUM
        defaultLotBailShouldBeFound("riskPremium.equals=" + DEFAULT_RISK_PREMIUM);

        // Get all the lotBailList where riskPremium equals to UPDATED_RISK_PREMIUM
        defaultLotBailShouldNotBeFound("riskPremium.equals=" + UPDATED_RISK_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium not equals to DEFAULT_RISK_PREMIUM
        defaultLotBailShouldNotBeFound("riskPremium.notEquals=" + DEFAULT_RISK_PREMIUM);

        // Get all the lotBailList where riskPremium not equals to UPDATED_RISK_PREMIUM
        defaultLotBailShouldBeFound("riskPremium.notEquals=" + UPDATED_RISK_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium in DEFAULT_RISK_PREMIUM or UPDATED_RISK_PREMIUM
        defaultLotBailShouldBeFound("riskPremium.in=" + DEFAULT_RISK_PREMIUM + "," + UPDATED_RISK_PREMIUM);

        // Get all the lotBailList where riskPremium equals to UPDATED_RISK_PREMIUM
        defaultLotBailShouldNotBeFound("riskPremium.in=" + UPDATED_RISK_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium is not null
        defaultLotBailShouldBeFound("riskPremium.specified=true");

        // Get all the lotBailList where riskPremium is null
        defaultLotBailShouldNotBeFound("riskPremium.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium is greater than or equal to DEFAULT_RISK_PREMIUM
        defaultLotBailShouldBeFound("riskPremium.greaterThanOrEqual=" + DEFAULT_RISK_PREMIUM);

        // Get all the lotBailList where riskPremium is greater than or equal to UPDATED_RISK_PREMIUM
        defaultLotBailShouldNotBeFound("riskPremium.greaterThanOrEqual=" + UPDATED_RISK_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium is less than or equal to DEFAULT_RISK_PREMIUM
        defaultLotBailShouldBeFound("riskPremium.lessThanOrEqual=" + DEFAULT_RISK_PREMIUM);

        // Get all the lotBailList where riskPremium is less than or equal to SMALLER_RISK_PREMIUM
        defaultLotBailShouldNotBeFound("riskPremium.lessThanOrEqual=" + SMALLER_RISK_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium is less than DEFAULT_RISK_PREMIUM
        defaultLotBailShouldNotBeFound("riskPremium.lessThan=" + DEFAULT_RISK_PREMIUM);

        // Get all the lotBailList where riskPremium is less than UPDATED_RISK_PREMIUM
        defaultLotBailShouldBeFound("riskPremium.lessThan=" + UPDATED_RISK_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllLotBailsByRiskPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where riskPremium is greater than DEFAULT_RISK_PREMIUM
        defaultLotBailShouldNotBeFound("riskPremium.greaterThan=" + DEFAULT_RISK_PREMIUM);

        // Get all the lotBailList where riskPremium is greater than SMALLER_RISK_PREMIUM
        defaultLotBailShouldBeFound("riskPremium.greaterThan=" + SMALLER_RISK_PREMIUM);
    }


    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode equals to DEFAULT_GOTOUARANTOR_CODE
        defaultLotBailShouldBeFound("gotouarantorCode.equals=" + DEFAULT_GOTOUARANTOR_CODE);

        // Get all the lotBailList where gotouarantorCode equals to UPDATED_GOTOUARANTOR_CODE
        defaultLotBailShouldNotBeFound("gotouarantorCode.equals=" + UPDATED_GOTOUARANTOR_CODE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode not equals to DEFAULT_GOTOUARANTOR_CODE
        defaultLotBailShouldNotBeFound("gotouarantorCode.notEquals=" + DEFAULT_GOTOUARANTOR_CODE);

        // Get all the lotBailList where gotouarantorCode not equals to UPDATED_GOTOUARANTOR_CODE
        defaultLotBailShouldBeFound("gotouarantorCode.notEquals=" + UPDATED_GOTOUARANTOR_CODE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode in DEFAULT_GOTOUARANTOR_CODE or UPDATED_GOTOUARANTOR_CODE
        defaultLotBailShouldBeFound("gotouarantorCode.in=" + DEFAULT_GOTOUARANTOR_CODE + "," + UPDATED_GOTOUARANTOR_CODE);

        // Get all the lotBailList where gotouarantorCode equals to UPDATED_GOTOUARANTOR_CODE
        defaultLotBailShouldNotBeFound("gotouarantorCode.in=" + UPDATED_GOTOUARANTOR_CODE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode is not null
        defaultLotBailShouldBeFound("gotouarantorCode.specified=true");

        // Get all the lotBailList where gotouarantorCode is null
        defaultLotBailShouldNotBeFound("gotouarantorCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode is greater than or equal to DEFAULT_GOTOUARANTOR_CODE
        defaultLotBailShouldBeFound("gotouarantorCode.greaterThanOrEqual=" + DEFAULT_GOTOUARANTOR_CODE);

        // Get all the lotBailList where gotouarantorCode is greater than or equal to UPDATED_GOTOUARANTOR_CODE
        defaultLotBailShouldNotBeFound("gotouarantorCode.greaterThanOrEqual=" + UPDATED_GOTOUARANTOR_CODE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode is less than or equal to DEFAULT_GOTOUARANTOR_CODE
        defaultLotBailShouldBeFound("gotouarantorCode.lessThanOrEqual=" + DEFAULT_GOTOUARANTOR_CODE);

        // Get all the lotBailList where gotouarantorCode is less than or equal to SMALLER_GOTOUARANTOR_CODE
        defaultLotBailShouldNotBeFound("gotouarantorCode.lessThanOrEqual=" + SMALLER_GOTOUARANTOR_CODE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode is less than DEFAULT_GOTOUARANTOR_CODE
        defaultLotBailShouldNotBeFound("gotouarantorCode.lessThan=" + DEFAULT_GOTOUARANTOR_CODE);

        // Get all the lotBailList where gotouarantorCode is less than UPDATED_GOTOUARANTOR_CODE
        defaultLotBailShouldBeFound("gotouarantorCode.lessThan=" + UPDATED_GOTOUARANTOR_CODE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGotouarantorCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where gotouarantorCode is greater than DEFAULT_GOTOUARANTOR_CODE
        defaultLotBailShouldNotBeFound("gotouarantorCode.greaterThan=" + DEFAULT_GOTOUARANTOR_CODE);

        // Get all the lotBailList where gotouarantorCode is greater than SMALLER_GOTOUARANTOR_CODE
        defaultLotBailShouldBeFound("gotouarantorCode.greaterThan=" + SMALLER_GOTOUARANTOR_CODE);
    }


    @Test
    @Transactional
    public void getAllLotBailsByGuarantorDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where guarantorDescription equals to DEFAULT_GUARANTOR_DESCRIPTION
        defaultLotBailShouldBeFound("guarantorDescription.equals=" + DEFAULT_GUARANTOR_DESCRIPTION);

        // Get all the lotBailList where guarantorDescription equals to UPDATED_GUARANTOR_DESCRIPTION
        defaultLotBailShouldNotBeFound("guarantorDescription.equals=" + UPDATED_GUARANTOR_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGuarantorDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where guarantorDescription not equals to DEFAULT_GUARANTOR_DESCRIPTION
        defaultLotBailShouldNotBeFound("guarantorDescription.notEquals=" + DEFAULT_GUARANTOR_DESCRIPTION);

        // Get all the lotBailList where guarantorDescription not equals to UPDATED_GUARANTOR_DESCRIPTION
        defaultLotBailShouldBeFound("guarantorDescription.notEquals=" + UPDATED_GUARANTOR_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGuarantorDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where guarantorDescription in DEFAULT_GUARANTOR_DESCRIPTION or UPDATED_GUARANTOR_DESCRIPTION
        defaultLotBailShouldBeFound("guarantorDescription.in=" + DEFAULT_GUARANTOR_DESCRIPTION + "," + UPDATED_GUARANTOR_DESCRIPTION);

        // Get all the lotBailList where guarantorDescription equals to UPDATED_GUARANTOR_DESCRIPTION
        defaultLotBailShouldNotBeFound("guarantorDescription.in=" + UPDATED_GUARANTOR_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGuarantorDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where guarantorDescription is not null
        defaultLotBailShouldBeFound("guarantorDescription.specified=true");

        // Get all the lotBailList where guarantorDescription is null
        defaultLotBailShouldNotBeFound("guarantorDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllLotBailsByGuarantorDescriptionContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where guarantorDescription contains DEFAULT_GUARANTOR_DESCRIPTION
        defaultLotBailShouldBeFound("guarantorDescription.contains=" + DEFAULT_GUARANTOR_DESCRIPTION);

        // Get all the lotBailList where guarantorDescription contains UPDATED_GUARANTOR_DESCRIPTION
        defaultLotBailShouldNotBeFound("guarantorDescription.contains=" + UPDATED_GUARANTOR_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLotBailsByGuarantorDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where guarantorDescription does not contain DEFAULT_GUARANTOR_DESCRIPTION
        defaultLotBailShouldNotBeFound("guarantorDescription.doesNotContain=" + DEFAULT_GUARANTOR_DESCRIPTION);

        // Get all the lotBailList where guarantorDescription does not contain UPDATED_GUARANTOR_DESCRIPTION
        defaultLotBailShouldBeFound("guarantorDescription.doesNotContain=" + UPDATED_GUARANTOR_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllLotBailsByAmortizationTableManagementIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where amortizationTableManagement equals to DEFAULT_AMORTIZATION_TABLE_MANAGEMENT
        defaultLotBailShouldBeFound("amortizationTableManagement.equals=" + DEFAULT_AMORTIZATION_TABLE_MANAGEMENT);

        // Get all the lotBailList where amortizationTableManagement equals to UPDATED_AMORTIZATION_TABLE_MANAGEMENT
        defaultLotBailShouldNotBeFound("amortizationTableManagement.equals=" + UPDATED_AMORTIZATION_TABLE_MANAGEMENT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAmortizationTableManagementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where amortizationTableManagement not equals to DEFAULT_AMORTIZATION_TABLE_MANAGEMENT
        defaultLotBailShouldNotBeFound("amortizationTableManagement.notEquals=" + DEFAULT_AMORTIZATION_TABLE_MANAGEMENT);

        // Get all the lotBailList where amortizationTableManagement not equals to UPDATED_AMORTIZATION_TABLE_MANAGEMENT
        defaultLotBailShouldBeFound("amortizationTableManagement.notEquals=" + UPDATED_AMORTIZATION_TABLE_MANAGEMENT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAmortizationTableManagementIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where amortizationTableManagement in DEFAULT_AMORTIZATION_TABLE_MANAGEMENT or UPDATED_AMORTIZATION_TABLE_MANAGEMENT
        defaultLotBailShouldBeFound("amortizationTableManagement.in=" + DEFAULT_AMORTIZATION_TABLE_MANAGEMENT + "," + UPDATED_AMORTIZATION_TABLE_MANAGEMENT);

        // Get all the lotBailList where amortizationTableManagement equals to UPDATED_AMORTIZATION_TABLE_MANAGEMENT
        defaultLotBailShouldNotBeFound("amortizationTableManagement.in=" + UPDATED_AMORTIZATION_TABLE_MANAGEMENT);
    }

    @Test
    @Transactional
    public void getAllLotBailsByAmortizationTableManagementIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where amortizationTableManagement is not null
        defaultLotBailShouldBeFound("amortizationTableManagement.specified=true");

        // Get all the lotBailList where amortizationTableManagement is null
        defaultLotBailShouldNotBeFound("amortizationTableManagement.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByVariableRateIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where variableRate equals to DEFAULT_VARIABLE_RATE
        defaultLotBailShouldBeFound("variableRate.equals=" + DEFAULT_VARIABLE_RATE);

        // Get all the lotBailList where variableRate equals to UPDATED_VARIABLE_RATE
        defaultLotBailShouldNotBeFound("variableRate.equals=" + UPDATED_VARIABLE_RATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByVariableRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where variableRate not equals to DEFAULT_VARIABLE_RATE
        defaultLotBailShouldNotBeFound("variableRate.notEquals=" + DEFAULT_VARIABLE_RATE);

        // Get all the lotBailList where variableRate not equals to UPDATED_VARIABLE_RATE
        defaultLotBailShouldBeFound("variableRate.notEquals=" + UPDATED_VARIABLE_RATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByVariableRateIsInShouldWork() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where variableRate in DEFAULT_VARIABLE_RATE or UPDATED_VARIABLE_RATE
        defaultLotBailShouldBeFound("variableRate.in=" + DEFAULT_VARIABLE_RATE + "," + UPDATED_VARIABLE_RATE);

        // Get all the lotBailList where variableRate equals to UPDATED_VARIABLE_RATE
        defaultLotBailShouldNotBeFound("variableRate.in=" + UPDATED_VARIABLE_RATE);
    }

    @Test
    @Transactional
    public void getAllLotBailsByVariableRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        // Get all the lotBailList where variableRate is not null
        defaultLotBailShouldBeFound("variableRate.specified=true");

        // Get all the lotBailList where variableRate is null
        defaultLotBailShouldNotBeFound("variableRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLotBailsByChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);
        Charge charge = ChargeResourceIT.createEntity(em);
        em.persist(charge);
        em.flush();
        lotBail.addCharge(charge);
        lotBailRepository.saveAndFlush(lotBail);
        Long chargeId = charge.getId();

        // Get all the lotBailList where charge equals to chargeId
        defaultLotBailShouldBeFound("chargeId.equals=" + chargeId);

        // Get all the lotBailList where charge equals to chargeId + 1
        defaultLotBailShouldNotBeFound("chargeId.equals=" + (chargeId + 1));
    }


    @Test
    @Transactional
    public void getAllLotBailsByCounterIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);
        Counter counter = CounterResourceIT.createEntity(em);
        em.persist(counter);
        em.flush();
        lotBail.addCounter(counter);
        lotBailRepository.saveAndFlush(lotBail);
        Long counterId = counter.getId();

        // Get all the lotBailList where counter equals to counterId
        defaultLotBailShouldBeFound("counterId.equals=" + counterId);

        // Get all the lotBailList where counter equals to counterId + 1
        defaultLotBailShouldNotBeFound("counterId.equals=" + (counterId + 1));
    }


    @Test
    @Transactional
    public void getAllLotBailsByScheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);
        Schedule schedule = ScheduleResourceIT.createEntity(em);
        em.persist(schedule);
        em.flush();
        lotBail.addSchedule(schedule);
        lotBailRepository.saveAndFlush(lotBail);
        Long scheduleId = schedule.getId();

        // Get all the lotBailList where schedule equals to scheduleId
        defaultLotBailShouldBeFound("scheduleId.equals=" + scheduleId);

        // Get all the lotBailList where schedule equals to scheduleId + 1
        defaultLotBailShouldNotBeFound("scheduleId.equals=" + (scheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllLotBailsByBailIsEqualToSomething() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);
        Bail bail = BailResourceIT.createEntity(em);
        em.persist(bail);
        em.flush();
        lotBail.setBail(bail);
        lotBailRepository.saveAndFlush(lotBail);
        Long bailId = bail.getId();

        // Get all the lotBailList where bail equals to bailId
        defaultLotBailShouldBeFound("bailId.equals=" + bailId);

        // Get all the lotBailList where bail equals to bailId + 1
        defaultLotBailShouldNotBeFound("bailId.equals=" + (bailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLotBailShouldBeFound(String filter) throws Exception {
        restLotBailMockMvc.perform(get("/api/lot-bails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotBail.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].codeLot").value(hasItem(DEFAULT_CODE_LOT)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
            .andExpect(jsonPath("$.[*].stairs").value(hasItem(DEFAULT_STAIRS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].technicalInformation").value(hasItem(DEFAULT_TECHNICAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].acquisationDate").value(hasItem(DEFAULT_ACQUISATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.intValue())))
            .andExpect(jsonPath("$.[*].parkingsNumber").value(hasItem(DEFAULT_PARKINGS_NUMBER)))
            .andExpect(jsonPath("$.[*].floorsNumber").value(hasItem(DEFAULT_FLOORS_NUMBER)))
            .andExpect(jsonPath("$.[*].realNumberOfLot").value(hasItem(DEFAULT_REAL_NUMBER_OF_LOT)))
            .andExpect(jsonPath("$.[*].numberOfSecondaryUnits").value(hasItem(DEFAULT_NUMBER_OF_SECONDARY_UNITS)))
            .andExpect(jsonPath("$.[*].outDoorParking").value(hasItem(DEFAULT_OUT_DOOR_PARKING.booleanValue())))
            .andExpect(jsonPath("$.[*].lotForMultipleOccupation").value(hasItem(DEFAULT_LOT_FOR_MULTIPLE_OCCUPATION.booleanValue())))
            .andExpect(jsonPath("$.[*].priceOfSquareMeter").value(hasItem(DEFAULT_PRICE_OF_SQUARE_METER.intValue())))
            .andExpect(jsonPath("$.[*].rentalValueForSquareMeter").value(hasItem(DEFAULT_RENTAL_VALUE_FOR_SQUARE_METER.intValue())))
            .andExpect(jsonPath("$.[*].transferAmount").value(hasItem(DEFAULT_TRANSFER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].acquisationAmount").value(hasItem(DEFAULT_ACQUISATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].rentAmount").value(hasItem(DEFAULT_RENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].poolFactor").value(hasItem(DEFAULT_POOL_FACTOR)))
            .andExpect(jsonPath("$.[*].maturityDate").value(hasItem(DEFAULT_MATURITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].convertibilityIndicator").value(hasItem(DEFAULT_CONVERTIBILITY_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].subordinationIndicator").value(hasItem(DEFAULT_SUBORDINATION_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].indexed").value(hasItem(DEFAULT_INDEXED.booleanValue())))
            .andExpect(jsonPath("$.[*].eligibilityIndicator").value(hasItem(DEFAULT_ELIGIBILITY_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].riskPremium").value(hasItem(DEFAULT_RISK_PREMIUM)))
            .andExpect(jsonPath("$.[*].gotouarantorCode").value(hasItem(DEFAULT_GOTOUARANTOR_CODE)))
            .andExpect(jsonPath("$.[*].guarantorDescription").value(hasItem(DEFAULT_GUARANTOR_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].amortizationTableManagement").value(hasItem(DEFAULT_AMORTIZATION_TABLE_MANAGEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].variableRate").value(hasItem(DEFAULT_VARIABLE_RATE.booleanValue())));

        // Check, that the count call also returns 1
        restLotBailMockMvc.perform(get("/api/lot-bails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLotBailShouldNotBeFound(String filter) throws Exception {
        restLotBailMockMvc.perform(get("/api/lot-bails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLotBailMockMvc.perform(get("/api/lot-bails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLotBail() throws Exception {
        // Get the lotBail
        restLotBailMockMvc.perform(get("/api/lot-bails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLotBail() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        int databaseSizeBeforeUpdate = lotBailRepository.findAll().size();

        // Update the lotBail
        LotBail updatedLotBail = lotBailRepository.findById(lotBail.getId()).get();
        // Disconnect from session so that the updates on updatedLotBail are not directly saved in db
        em.detach(updatedLotBail);
        updatedLotBail
            .name(UPDATED_NAME)
            .codeLot(UPDATED_CODE_LOT)
            .building(UPDATED_BUILDING)
            .stairs(UPDATED_STAIRS)
            .comments(UPDATED_COMMENTS)
            .technicalInformation(UPDATED_TECHNICAL_INFORMATION)
            .creationDate(UPDATED_CREATION_DATE)
            .acquisationDate(UPDATED_ACQUISATION_DATE)
            .surface(UPDATED_SURFACE)
            .parkingsNumber(UPDATED_PARKINGS_NUMBER)
            .floorsNumber(UPDATED_FLOORS_NUMBER)
            .realNumberOfLot(UPDATED_REAL_NUMBER_OF_LOT)
            .numberOfSecondaryUnits(UPDATED_NUMBER_OF_SECONDARY_UNITS)
            .outDoorParking(UPDATED_OUT_DOOR_PARKING)
            .lotForMultipleOccupation(UPDATED_LOT_FOR_MULTIPLE_OCCUPATION)
            .priceOfSquareMeter(UPDATED_PRICE_OF_SQUARE_METER)
            .rentalValueForSquareMeter(UPDATED_RENTAL_VALUE_FOR_SQUARE_METER)
            .transferAmount(UPDATED_TRANSFER_AMOUNT)
            .acquisationAmount(UPDATED_ACQUISATION_AMOUNT)
            .rentAmount(UPDATED_RENT_AMOUNT)
            .poolFactor(UPDATED_POOL_FACTOR)
            .maturityDate(UPDATED_MATURITY_DATE)
            .convertibilityIndicator(UPDATED_CONVERTIBILITY_INDICATOR)
            .subordinationIndicator(UPDATED_SUBORDINATION_INDICATOR)
            .indexed(UPDATED_INDEXED)
            .eligibilityIndicator(UPDATED_ELIGIBILITY_INDICATOR)
            .riskPremium(UPDATED_RISK_PREMIUM)
            .gotouarantorCode(UPDATED_GOTOUARANTOR_CODE)
            .guarantorDescription(UPDATED_GUARANTOR_DESCRIPTION)
            .amortizationTableManagement(UPDATED_AMORTIZATION_TABLE_MANAGEMENT)
            .variableRate(UPDATED_VARIABLE_RATE);
        LotBailDTO lotBailDTO = lotBailMapper.toDto(updatedLotBail);

        restLotBailMockMvc.perform(put("/api/lot-bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotBailDTO)))
            .andExpect(status().isOk());

        // Validate the LotBail in the database
        List<LotBail> lotBailList = lotBailRepository.findAll();
        assertThat(lotBailList).hasSize(databaseSizeBeforeUpdate);
        LotBail testLotBail = lotBailList.get(lotBailList.size() - 1);
        assertThat(testLotBail.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLotBail.getCodeLot()).isEqualTo(UPDATED_CODE_LOT);
        assertThat(testLotBail.getBuilding()).isEqualTo(UPDATED_BUILDING);
        assertThat(testLotBail.getStairs()).isEqualTo(UPDATED_STAIRS);
        assertThat(testLotBail.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLotBail.getTechnicalInformation()).isEqualTo(UPDATED_TECHNICAL_INFORMATION);
        assertThat(testLotBail.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testLotBail.getAcquisationDate()).isEqualTo(UPDATED_ACQUISATION_DATE);
        assertThat(testLotBail.getSurface()).isEqualTo(UPDATED_SURFACE);
        assertThat(testLotBail.getParkingsNumber()).isEqualTo(UPDATED_PARKINGS_NUMBER);
        assertThat(testLotBail.getFloorsNumber()).isEqualTo(UPDATED_FLOORS_NUMBER);
        assertThat(testLotBail.getRealNumberOfLot()).isEqualTo(UPDATED_REAL_NUMBER_OF_LOT);
        assertThat(testLotBail.getNumberOfSecondaryUnits()).isEqualTo(UPDATED_NUMBER_OF_SECONDARY_UNITS);
        assertThat(testLotBail.isOutDoorParking()).isEqualTo(UPDATED_OUT_DOOR_PARKING);
        assertThat(testLotBail.isLotForMultipleOccupation()).isEqualTo(UPDATED_LOT_FOR_MULTIPLE_OCCUPATION);
        assertThat(testLotBail.getPriceOfSquareMeter()).isEqualTo(UPDATED_PRICE_OF_SQUARE_METER);
        assertThat(testLotBail.getRentalValueForSquareMeter()).isEqualTo(UPDATED_RENTAL_VALUE_FOR_SQUARE_METER);
        assertThat(testLotBail.getTransferAmount()).isEqualTo(UPDATED_TRANSFER_AMOUNT);
        assertThat(testLotBail.getAcquisationAmount()).isEqualTo(UPDATED_ACQUISATION_AMOUNT);
        assertThat(testLotBail.getRentAmount()).isEqualTo(UPDATED_RENT_AMOUNT);
        assertThat(testLotBail.getPoolFactor()).isEqualTo(UPDATED_POOL_FACTOR);
        assertThat(testLotBail.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testLotBail.isConvertibilityIndicator()).isEqualTo(UPDATED_CONVERTIBILITY_INDICATOR);
        assertThat(testLotBail.isSubordinationIndicator()).isEqualTo(UPDATED_SUBORDINATION_INDICATOR);
        assertThat(testLotBail.isIndexed()).isEqualTo(UPDATED_INDEXED);
        assertThat(testLotBail.isEligibilityIndicator()).isEqualTo(UPDATED_ELIGIBILITY_INDICATOR);
        assertThat(testLotBail.getRiskPremium()).isEqualTo(UPDATED_RISK_PREMIUM);
        assertThat(testLotBail.getGotouarantorCode()).isEqualTo(UPDATED_GOTOUARANTOR_CODE);
        assertThat(testLotBail.getGuarantorDescription()).isEqualTo(UPDATED_GUARANTOR_DESCRIPTION);
        assertThat(testLotBail.isAmortizationTableManagement()).isEqualTo(UPDATED_AMORTIZATION_TABLE_MANAGEMENT);
        assertThat(testLotBail.isVariableRate()).isEqualTo(UPDATED_VARIABLE_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLotBail() throws Exception {
        int databaseSizeBeforeUpdate = lotBailRepository.findAll().size();

        // Create the LotBail
        LotBailDTO lotBailDTO = lotBailMapper.toDto(lotBail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotBailMockMvc.perform(put("/api/lot-bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotBailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LotBail in the database
        List<LotBail> lotBailList = lotBailRepository.findAll();
        assertThat(lotBailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLotBail() throws Exception {
        // Initialize the database
        lotBailRepository.saveAndFlush(lotBail);

        int databaseSizeBeforeDelete = lotBailRepository.findAll().size();

        // Delete the lotBail
        restLotBailMockMvc.perform(delete("/api/lot-bails/{id}", lotBail.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LotBail> lotBailList = lotBailRepository.findAll();
        assertThat(lotBailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
