package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Bail;
import com.fininfo.java.domain.Locataire;
import com.fininfo.java.domain.Garant;
import com.fininfo.java.repository.BailRepository;
import com.fininfo.java.service.BailService;
import com.fininfo.java.service.dto.BailDTO;
import com.fininfo.java.service.mapper.BailMapper;
import com.fininfo.java.service.dto.BailCriteria;
import com.fininfo.java.service.BailQueryService;

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

import com.fininfo.java.domain.enumeration.IEnumTypeBail;
import com.fininfo.java.domain.enumeration.Bailstatus;
/**
 * Integration tests for the {@link BailResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BailResourceIT {

    private static final String DEFAULT_CODE_BAIL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION_BAIL = "AAAAAAAAAA";
    private static final String UPDATED_DURATION_BAIL = "BBBBBBBBBB";

    private static final IEnumTypeBail DEFAULT_TYPE_BAIL = IEnumTypeBail.BAILLOGEMENTNU;
    private static final IEnumTypeBail UPDATED_TYPE_BAIL = IEnumTypeBail.BAILLOGEMENTMEUBLE;

    private static final LocalDate DEFAULT_SIGNATURE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SIGNATURE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SIGNATURE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FIRST_RENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_RENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FIRST_RENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESTINATION_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_LOCAL = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_OPCI = 1L;
    private static final Long UPDATED_ID_OPCI = 2L;
    private static final Long SMALLER_ID_OPCI = 1L - 1L;

    private static final Bailstatus DEFAULT_STATUS = Bailstatus.BAILWAITINGLOTS;
    private static final Bailstatus UPDATED_STATUS = Bailstatus.BAILWAITINGLOCATAIRE;

    @Autowired
    private BailRepository bailRepository;

    @Autowired
    private BailMapper bailMapper;

    @Autowired
    private BailService bailService;

    @Autowired
    private BailQueryService bailQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBailMockMvc;

    private Bail bail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bail createEntity(EntityManager em) {
        Bail bail = new Bail()
            .codeBail(DEFAULT_CODE_BAIL)
            .durationBail(DEFAULT_DURATION_BAIL)
            .typeBail(DEFAULT_TYPE_BAIL)
            .signatureDate(DEFAULT_SIGNATURE_DATE)
            .firstRentDate(DEFAULT_FIRST_RENT_DATE)
            .destinationLocal(DEFAULT_DESTINATION_LOCAL)
            .idOPCI(DEFAULT_ID_OPCI)
            .status(DEFAULT_STATUS);
        return bail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bail createUpdatedEntity(EntityManager em) {
        Bail bail = new Bail()
            .codeBail(UPDATED_CODE_BAIL)
            .durationBail(UPDATED_DURATION_BAIL)
            .typeBail(UPDATED_TYPE_BAIL)
            .signatureDate(UPDATED_SIGNATURE_DATE)
            .firstRentDate(UPDATED_FIRST_RENT_DATE)
            .destinationLocal(UPDATED_DESTINATION_LOCAL)
            .idOPCI(UPDATED_ID_OPCI)
            .status(UPDATED_STATUS);
        return bail;
    }

    @BeforeEach
    public void initTest() {
        bail = createEntity(em);
    }

    @Test
    @Transactional
    public void createBail() throws Exception {
        int databaseSizeBeforeCreate = bailRepository.findAll().size();
        // Create the Bail
        BailDTO bailDTO = bailMapper.toDto(bail);
        restBailMockMvc.perform(post("/api/bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailDTO)))
            .andExpect(status().isCreated());

        // Validate the Bail in the database
        List<Bail> bailList = bailRepository.findAll();
        assertThat(bailList).hasSize(databaseSizeBeforeCreate + 1);
        Bail testBail = bailList.get(bailList.size() - 1);
        assertThat(testBail.getCodeBail()).isEqualTo(DEFAULT_CODE_BAIL);
        assertThat(testBail.getDurationBail()).isEqualTo(DEFAULT_DURATION_BAIL);
        assertThat(testBail.getTypeBail()).isEqualTo(DEFAULT_TYPE_BAIL);
        assertThat(testBail.getSignatureDate()).isEqualTo(DEFAULT_SIGNATURE_DATE);
        assertThat(testBail.getFirstRentDate()).isEqualTo(DEFAULT_FIRST_RENT_DATE);
        assertThat(testBail.getDestinationLocal()).isEqualTo(DEFAULT_DESTINATION_LOCAL);
        assertThat(testBail.getIdOPCI()).isEqualTo(DEFAULT_ID_OPCI);
        assertThat(testBail.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bailRepository.findAll().size();

        // Create the Bail with an existing ID
        bail.setId(1L);
        BailDTO bailDTO = bailMapper.toDto(bail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBailMockMvc.perform(post("/api/bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bail in the database
        List<Bail> bailList = bailRepository.findAll();
        assertThat(bailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBails() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList
        restBailMockMvc.perform(get("/api/bails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bail.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeBail").value(hasItem(DEFAULT_CODE_BAIL)))
            .andExpect(jsonPath("$.[*].durationBail").value(hasItem(DEFAULT_DURATION_BAIL)))
            .andExpect(jsonPath("$.[*].typeBail").value(hasItem(DEFAULT_TYPE_BAIL.toString())))
            .andExpect(jsonPath("$.[*].signatureDate").value(hasItem(DEFAULT_SIGNATURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].firstRentDate").value(hasItem(DEFAULT_FIRST_RENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].destinationLocal").value(hasItem(DEFAULT_DESTINATION_LOCAL)))
            .andExpect(jsonPath("$.[*].idOPCI").value(hasItem(DEFAULT_ID_OPCI.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getBail() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get the bail
        restBailMockMvc.perform(get("/api/bails/{id}", bail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bail.getId().intValue()))
            .andExpect(jsonPath("$.codeBail").value(DEFAULT_CODE_BAIL))
            .andExpect(jsonPath("$.durationBail").value(DEFAULT_DURATION_BAIL))
            .andExpect(jsonPath("$.typeBail").value(DEFAULT_TYPE_BAIL.toString()))
            .andExpect(jsonPath("$.signatureDate").value(DEFAULT_SIGNATURE_DATE.toString()))
            .andExpect(jsonPath("$.firstRentDate").value(DEFAULT_FIRST_RENT_DATE.toString()))
            .andExpect(jsonPath("$.destinationLocal").value(DEFAULT_DESTINATION_LOCAL))
            .andExpect(jsonPath("$.idOPCI").value(DEFAULT_ID_OPCI.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getBailsByIdFiltering() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        Long id = bail.getId();

        defaultBailShouldBeFound("id.equals=" + id);
        defaultBailShouldNotBeFound("id.notEquals=" + id);

        defaultBailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBailShouldNotBeFound("id.greaterThan=" + id);

        defaultBailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBailShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBailsByCodeBailIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where codeBail equals to DEFAULT_CODE_BAIL
        defaultBailShouldBeFound("codeBail.equals=" + DEFAULT_CODE_BAIL);

        // Get all the bailList where codeBail equals to UPDATED_CODE_BAIL
        defaultBailShouldNotBeFound("codeBail.equals=" + UPDATED_CODE_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByCodeBailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where codeBail not equals to DEFAULT_CODE_BAIL
        defaultBailShouldNotBeFound("codeBail.notEquals=" + DEFAULT_CODE_BAIL);

        // Get all the bailList where codeBail not equals to UPDATED_CODE_BAIL
        defaultBailShouldBeFound("codeBail.notEquals=" + UPDATED_CODE_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByCodeBailIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where codeBail in DEFAULT_CODE_BAIL or UPDATED_CODE_BAIL
        defaultBailShouldBeFound("codeBail.in=" + DEFAULT_CODE_BAIL + "," + UPDATED_CODE_BAIL);

        // Get all the bailList where codeBail equals to UPDATED_CODE_BAIL
        defaultBailShouldNotBeFound("codeBail.in=" + UPDATED_CODE_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByCodeBailIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where codeBail is not null
        defaultBailShouldBeFound("codeBail.specified=true");

        // Get all the bailList where codeBail is null
        defaultBailShouldNotBeFound("codeBail.specified=false");
    }
                @Test
    @Transactional
    public void getAllBailsByCodeBailContainsSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where codeBail contains DEFAULT_CODE_BAIL
        defaultBailShouldBeFound("codeBail.contains=" + DEFAULT_CODE_BAIL);

        // Get all the bailList where codeBail contains UPDATED_CODE_BAIL
        defaultBailShouldNotBeFound("codeBail.contains=" + UPDATED_CODE_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByCodeBailNotContainsSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where codeBail does not contain DEFAULT_CODE_BAIL
        defaultBailShouldNotBeFound("codeBail.doesNotContain=" + DEFAULT_CODE_BAIL);

        // Get all the bailList where codeBail does not contain UPDATED_CODE_BAIL
        defaultBailShouldBeFound("codeBail.doesNotContain=" + UPDATED_CODE_BAIL);
    }


    @Test
    @Transactional
    public void getAllBailsByDurationBailIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where durationBail equals to DEFAULT_DURATION_BAIL
        defaultBailShouldBeFound("durationBail.equals=" + DEFAULT_DURATION_BAIL);

        // Get all the bailList where durationBail equals to UPDATED_DURATION_BAIL
        defaultBailShouldNotBeFound("durationBail.equals=" + UPDATED_DURATION_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByDurationBailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where durationBail not equals to DEFAULT_DURATION_BAIL
        defaultBailShouldNotBeFound("durationBail.notEquals=" + DEFAULT_DURATION_BAIL);

        // Get all the bailList where durationBail not equals to UPDATED_DURATION_BAIL
        defaultBailShouldBeFound("durationBail.notEquals=" + UPDATED_DURATION_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByDurationBailIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where durationBail in DEFAULT_DURATION_BAIL or UPDATED_DURATION_BAIL
        defaultBailShouldBeFound("durationBail.in=" + DEFAULT_DURATION_BAIL + "," + UPDATED_DURATION_BAIL);

        // Get all the bailList where durationBail equals to UPDATED_DURATION_BAIL
        defaultBailShouldNotBeFound("durationBail.in=" + UPDATED_DURATION_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByDurationBailIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where durationBail is not null
        defaultBailShouldBeFound("durationBail.specified=true");

        // Get all the bailList where durationBail is null
        defaultBailShouldNotBeFound("durationBail.specified=false");
    }
                @Test
    @Transactional
    public void getAllBailsByDurationBailContainsSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where durationBail contains DEFAULT_DURATION_BAIL
        defaultBailShouldBeFound("durationBail.contains=" + DEFAULT_DURATION_BAIL);

        // Get all the bailList where durationBail contains UPDATED_DURATION_BAIL
        defaultBailShouldNotBeFound("durationBail.contains=" + UPDATED_DURATION_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByDurationBailNotContainsSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where durationBail does not contain DEFAULT_DURATION_BAIL
        defaultBailShouldNotBeFound("durationBail.doesNotContain=" + DEFAULT_DURATION_BAIL);

        // Get all the bailList where durationBail does not contain UPDATED_DURATION_BAIL
        defaultBailShouldBeFound("durationBail.doesNotContain=" + UPDATED_DURATION_BAIL);
    }


    @Test
    @Transactional
    public void getAllBailsByTypeBailIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where typeBail equals to DEFAULT_TYPE_BAIL
        defaultBailShouldBeFound("typeBail.equals=" + DEFAULT_TYPE_BAIL);

        // Get all the bailList where typeBail equals to UPDATED_TYPE_BAIL
        defaultBailShouldNotBeFound("typeBail.equals=" + UPDATED_TYPE_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByTypeBailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where typeBail not equals to DEFAULT_TYPE_BAIL
        defaultBailShouldNotBeFound("typeBail.notEquals=" + DEFAULT_TYPE_BAIL);

        // Get all the bailList where typeBail not equals to UPDATED_TYPE_BAIL
        defaultBailShouldBeFound("typeBail.notEquals=" + UPDATED_TYPE_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByTypeBailIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where typeBail in DEFAULT_TYPE_BAIL or UPDATED_TYPE_BAIL
        defaultBailShouldBeFound("typeBail.in=" + DEFAULT_TYPE_BAIL + "," + UPDATED_TYPE_BAIL);

        // Get all the bailList where typeBail equals to UPDATED_TYPE_BAIL
        defaultBailShouldNotBeFound("typeBail.in=" + UPDATED_TYPE_BAIL);
    }

    @Test
    @Transactional
    public void getAllBailsByTypeBailIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where typeBail is not null
        defaultBailShouldBeFound("typeBail.specified=true");

        // Get all the bailList where typeBail is null
        defaultBailShouldNotBeFound("typeBail.specified=false");
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate equals to DEFAULT_SIGNATURE_DATE
        defaultBailShouldBeFound("signatureDate.equals=" + DEFAULT_SIGNATURE_DATE);

        // Get all the bailList where signatureDate equals to UPDATED_SIGNATURE_DATE
        defaultBailShouldNotBeFound("signatureDate.equals=" + UPDATED_SIGNATURE_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate not equals to DEFAULT_SIGNATURE_DATE
        defaultBailShouldNotBeFound("signatureDate.notEquals=" + DEFAULT_SIGNATURE_DATE);

        // Get all the bailList where signatureDate not equals to UPDATED_SIGNATURE_DATE
        defaultBailShouldBeFound("signatureDate.notEquals=" + UPDATED_SIGNATURE_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate in DEFAULT_SIGNATURE_DATE or UPDATED_SIGNATURE_DATE
        defaultBailShouldBeFound("signatureDate.in=" + DEFAULT_SIGNATURE_DATE + "," + UPDATED_SIGNATURE_DATE);

        // Get all the bailList where signatureDate equals to UPDATED_SIGNATURE_DATE
        defaultBailShouldNotBeFound("signatureDate.in=" + UPDATED_SIGNATURE_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate is not null
        defaultBailShouldBeFound("signatureDate.specified=true");

        // Get all the bailList where signatureDate is null
        defaultBailShouldNotBeFound("signatureDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate is greater than or equal to DEFAULT_SIGNATURE_DATE
        defaultBailShouldBeFound("signatureDate.greaterThanOrEqual=" + DEFAULT_SIGNATURE_DATE);

        // Get all the bailList where signatureDate is greater than or equal to UPDATED_SIGNATURE_DATE
        defaultBailShouldNotBeFound("signatureDate.greaterThanOrEqual=" + UPDATED_SIGNATURE_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate is less than or equal to DEFAULT_SIGNATURE_DATE
        defaultBailShouldBeFound("signatureDate.lessThanOrEqual=" + DEFAULT_SIGNATURE_DATE);

        // Get all the bailList where signatureDate is less than or equal to SMALLER_SIGNATURE_DATE
        defaultBailShouldNotBeFound("signatureDate.lessThanOrEqual=" + SMALLER_SIGNATURE_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsLessThanSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate is less than DEFAULT_SIGNATURE_DATE
        defaultBailShouldNotBeFound("signatureDate.lessThan=" + DEFAULT_SIGNATURE_DATE);

        // Get all the bailList where signatureDate is less than UPDATED_SIGNATURE_DATE
        defaultBailShouldBeFound("signatureDate.lessThan=" + UPDATED_SIGNATURE_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsBySignatureDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where signatureDate is greater than DEFAULT_SIGNATURE_DATE
        defaultBailShouldNotBeFound("signatureDate.greaterThan=" + DEFAULT_SIGNATURE_DATE);

        // Get all the bailList where signatureDate is greater than SMALLER_SIGNATURE_DATE
        defaultBailShouldBeFound("signatureDate.greaterThan=" + SMALLER_SIGNATURE_DATE);
    }


    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate equals to DEFAULT_FIRST_RENT_DATE
        defaultBailShouldBeFound("firstRentDate.equals=" + DEFAULT_FIRST_RENT_DATE);

        // Get all the bailList where firstRentDate equals to UPDATED_FIRST_RENT_DATE
        defaultBailShouldNotBeFound("firstRentDate.equals=" + UPDATED_FIRST_RENT_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate not equals to DEFAULT_FIRST_RENT_DATE
        defaultBailShouldNotBeFound("firstRentDate.notEquals=" + DEFAULT_FIRST_RENT_DATE);

        // Get all the bailList where firstRentDate not equals to UPDATED_FIRST_RENT_DATE
        defaultBailShouldBeFound("firstRentDate.notEquals=" + UPDATED_FIRST_RENT_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate in DEFAULT_FIRST_RENT_DATE or UPDATED_FIRST_RENT_DATE
        defaultBailShouldBeFound("firstRentDate.in=" + DEFAULT_FIRST_RENT_DATE + "," + UPDATED_FIRST_RENT_DATE);

        // Get all the bailList where firstRentDate equals to UPDATED_FIRST_RENT_DATE
        defaultBailShouldNotBeFound("firstRentDate.in=" + UPDATED_FIRST_RENT_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate is not null
        defaultBailShouldBeFound("firstRentDate.specified=true");

        // Get all the bailList where firstRentDate is null
        defaultBailShouldNotBeFound("firstRentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate is greater than or equal to DEFAULT_FIRST_RENT_DATE
        defaultBailShouldBeFound("firstRentDate.greaterThanOrEqual=" + DEFAULT_FIRST_RENT_DATE);

        // Get all the bailList where firstRentDate is greater than or equal to UPDATED_FIRST_RENT_DATE
        defaultBailShouldNotBeFound("firstRentDate.greaterThanOrEqual=" + UPDATED_FIRST_RENT_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate is less than or equal to DEFAULT_FIRST_RENT_DATE
        defaultBailShouldBeFound("firstRentDate.lessThanOrEqual=" + DEFAULT_FIRST_RENT_DATE);

        // Get all the bailList where firstRentDate is less than or equal to SMALLER_FIRST_RENT_DATE
        defaultBailShouldNotBeFound("firstRentDate.lessThanOrEqual=" + SMALLER_FIRST_RENT_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate is less than DEFAULT_FIRST_RENT_DATE
        defaultBailShouldNotBeFound("firstRentDate.lessThan=" + DEFAULT_FIRST_RENT_DATE);

        // Get all the bailList where firstRentDate is less than UPDATED_FIRST_RENT_DATE
        defaultBailShouldBeFound("firstRentDate.lessThan=" + UPDATED_FIRST_RENT_DATE);
    }

    @Test
    @Transactional
    public void getAllBailsByFirstRentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where firstRentDate is greater than DEFAULT_FIRST_RENT_DATE
        defaultBailShouldNotBeFound("firstRentDate.greaterThan=" + DEFAULT_FIRST_RENT_DATE);

        // Get all the bailList where firstRentDate is greater than SMALLER_FIRST_RENT_DATE
        defaultBailShouldBeFound("firstRentDate.greaterThan=" + SMALLER_FIRST_RENT_DATE);
    }


    @Test
    @Transactional
    public void getAllBailsByDestinationLocalIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where destinationLocal equals to DEFAULT_DESTINATION_LOCAL
        defaultBailShouldBeFound("destinationLocal.equals=" + DEFAULT_DESTINATION_LOCAL);

        // Get all the bailList where destinationLocal equals to UPDATED_DESTINATION_LOCAL
        defaultBailShouldNotBeFound("destinationLocal.equals=" + UPDATED_DESTINATION_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBailsByDestinationLocalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where destinationLocal not equals to DEFAULT_DESTINATION_LOCAL
        defaultBailShouldNotBeFound("destinationLocal.notEquals=" + DEFAULT_DESTINATION_LOCAL);

        // Get all the bailList where destinationLocal not equals to UPDATED_DESTINATION_LOCAL
        defaultBailShouldBeFound("destinationLocal.notEquals=" + UPDATED_DESTINATION_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBailsByDestinationLocalIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where destinationLocal in DEFAULT_DESTINATION_LOCAL or UPDATED_DESTINATION_LOCAL
        defaultBailShouldBeFound("destinationLocal.in=" + DEFAULT_DESTINATION_LOCAL + "," + UPDATED_DESTINATION_LOCAL);

        // Get all the bailList where destinationLocal equals to UPDATED_DESTINATION_LOCAL
        defaultBailShouldNotBeFound("destinationLocal.in=" + UPDATED_DESTINATION_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBailsByDestinationLocalIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where destinationLocal is not null
        defaultBailShouldBeFound("destinationLocal.specified=true");

        // Get all the bailList where destinationLocal is null
        defaultBailShouldNotBeFound("destinationLocal.specified=false");
    }
                @Test
    @Transactional
    public void getAllBailsByDestinationLocalContainsSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where destinationLocal contains DEFAULT_DESTINATION_LOCAL
        defaultBailShouldBeFound("destinationLocal.contains=" + DEFAULT_DESTINATION_LOCAL);

        // Get all the bailList where destinationLocal contains UPDATED_DESTINATION_LOCAL
        defaultBailShouldNotBeFound("destinationLocal.contains=" + UPDATED_DESTINATION_LOCAL);
    }

    @Test
    @Transactional
    public void getAllBailsByDestinationLocalNotContainsSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where destinationLocal does not contain DEFAULT_DESTINATION_LOCAL
        defaultBailShouldNotBeFound("destinationLocal.doesNotContain=" + DEFAULT_DESTINATION_LOCAL);

        // Get all the bailList where destinationLocal does not contain UPDATED_DESTINATION_LOCAL
        defaultBailShouldBeFound("destinationLocal.doesNotContain=" + UPDATED_DESTINATION_LOCAL);
    }


    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI equals to DEFAULT_ID_OPCI
        defaultBailShouldBeFound("idOPCI.equals=" + DEFAULT_ID_OPCI);

        // Get all the bailList where idOPCI equals to UPDATED_ID_OPCI
        defaultBailShouldNotBeFound("idOPCI.equals=" + UPDATED_ID_OPCI);
    }

    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI not equals to DEFAULT_ID_OPCI
        defaultBailShouldNotBeFound("idOPCI.notEquals=" + DEFAULT_ID_OPCI);

        // Get all the bailList where idOPCI not equals to UPDATED_ID_OPCI
        defaultBailShouldBeFound("idOPCI.notEquals=" + UPDATED_ID_OPCI);
    }

    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI in DEFAULT_ID_OPCI or UPDATED_ID_OPCI
        defaultBailShouldBeFound("idOPCI.in=" + DEFAULT_ID_OPCI + "," + UPDATED_ID_OPCI);

        // Get all the bailList where idOPCI equals to UPDATED_ID_OPCI
        defaultBailShouldNotBeFound("idOPCI.in=" + UPDATED_ID_OPCI);
    }

    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI is not null
        defaultBailShouldBeFound("idOPCI.specified=true");

        // Get all the bailList where idOPCI is null
        defaultBailShouldNotBeFound("idOPCI.specified=false");
    }

    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI is greater than or equal to DEFAULT_ID_OPCI
        defaultBailShouldBeFound("idOPCI.greaterThanOrEqual=" + DEFAULT_ID_OPCI);

        // Get all the bailList where idOPCI is greater than or equal to UPDATED_ID_OPCI
        defaultBailShouldNotBeFound("idOPCI.greaterThanOrEqual=" + UPDATED_ID_OPCI);
    }

    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI is less than or equal to DEFAULT_ID_OPCI
        defaultBailShouldBeFound("idOPCI.lessThanOrEqual=" + DEFAULT_ID_OPCI);

        // Get all the bailList where idOPCI is less than or equal to SMALLER_ID_OPCI
        defaultBailShouldNotBeFound("idOPCI.lessThanOrEqual=" + SMALLER_ID_OPCI);
    }

    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsLessThanSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI is less than DEFAULT_ID_OPCI
        defaultBailShouldNotBeFound("idOPCI.lessThan=" + DEFAULT_ID_OPCI);

        // Get all the bailList where idOPCI is less than UPDATED_ID_OPCI
        defaultBailShouldBeFound("idOPCI.lessThan=" + UPDATED_ID_OPCI);
    }

    @Test
    @Transactional
    public void getAllBailsByIdOPCIIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where idOPCI is greater than DEFAULT_ID_OPCI
        defaultBailShouldNotBeFound("idOPCI.greaterThan=" + DEFAULT_ID_OPCI);

        // Get all the bailList where idOPCI is greater than SMALLER_ID_OPCI
        defaultBailShouldBeFound("idOPCI.greaterThan=" + SMALLER_ID_OPCI);
    }


    @Test
    @Transactional
    public void getAllBailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where status equals to DEFAULT_STATUS
        defaultBailShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the bailList where status equals to UPDATED_STATUS
        defaultBailShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBailsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where status not equals to DEFAULT_STATUS
        defaultBailShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the bailList where status not equals to UPDATED_STATUS
        defaultBailShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBailShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the bailList where status equals to UPDATED_STATUS
        defaultBailShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        // Get all the bailList where status is not null
        defaultBailShouldBeFound("status.specified=true");

        // Get all the bailList where status is null
        defaultBailShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllBailsByLocataireIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);
        Locataire locataire = LocataireResourceIT.createEntity(em);
        em.persist(locataire);
        em.flush();
        bail.setLocataire(locataire);
        bailRepository.saveAndFlush(bail);
        Long locataireId = locataire.getId();

        // Get all the bailList where locataire equals to locataireId
        defaultBailShouldBeFound("locataireId.equals=" + locataireId);

        // Get all the bailList where locataire equals to locataireId + 1
        defaultBailShouldNotBeFound("locataireId.equals=" + (locataireId + 1));
    }


    @Test
    @Transactional
    public void getAllBailsByGarantIsEqualToSomething() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);
        Garant garant = GarantResourceIT.createEntity(em);
        em.persist(garant);
        em.flush();
        bail.setGarant(garant);
        bailRepository.saveAndFlush(bail);
        Long garantId = garant.getId();

        // Get all the bailList where garant equals to garantId
        defaultBailShouldBeFound("garantId.equals=" + garantId);

        // Get all the bailList where garant equals to garantId + 1
        defaultBailShouldNotBeFound("garantId.equals=" + (garantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBailShouldBeFound(String filter) throws Exception {
        restBailMockMvc.perform(get("/api/bails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bail.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeBail").value(hasItem(DEFAULT_CODE_BAIL)))
            .andExpect(jsonPath("$.[*].durationBail").value(hasItem(DEFAULT_DURATION_BAIL)))
            .andExpect(jsonPath("$.[*].typeBail").value(hasItem(DEFAULT_TYPE_BAIL.toString())))
            .andExpect(jsonPath("$.[*].signatureDate").value(hasItem(DEFAULT_SIGNATURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].firstRentDate").value(hasItem(DEFAULT_FIRST_RENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].destinationLocal").value(hasItem(DEFAULT_DESTINATION_LOCAL)))
            .andExpect(jsonPath("$.[*].idOPCI").value(hasItem(DEFAULT_ID_OPCI.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restBailMockMvc.perform(get("/api/bails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBailShouldNotBeFound(String filter) throws Exception {
        restBailMockMvc.perform(get("/api/bails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBailMockMvc.perform(get("/api/bails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBail() throws Exception {
        // Get the bail
        restBailMockMvc.perform(get("/api/bails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBail() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        int databaseSizeBeforeUpdate = bailRepository.findAll().size();

        // Update the bail
        Bail updatedBail = bailRepository.findById(bail.getId()).get();
        // Disconnect from session so that the updates on updatedBail are not directly saved in db
        em.detach(updatedBail);
        updatedBail
            .codeBail(UPDATED_CODE_BAIL)
            .durationBail(UPDATED_DURATION_BAIL)
            .typeBail(UPDATED_TYPE_BAIL)
            .signatureDate(UPDATED_SIGNATURE_DATE)
            .firstRentDate(UPDATED_FIRST_RENT_DATE)
            .destinationLocal(UPDATED_DESTINATION_LOCAL)
            .idOPCI(UPDATED_ID_OPCI)
            .status(UPDATED_STATUS);
        BailDTO bailDTO = bailMapper.toDto(updatedBail);

        restBailMockMvc.perform(put("/api/bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailDTO)))
            .andExpect(status().isOk());

        // Validate the Bail in the database
        List<Bail> bailList = bailRepository.findAll();
        assertThat(bailList).hasSize(databaseSizeBeforeUpdate);
        Bail testBail = bailList.get(bailList.size() - 1);
        assertThat(testBail.getCodeBail()).isEqualTo(UPDATED_CODE_BAIL);
        assertThat(testBail.getDurationBail()).isEqualTo(UPDATED_DURATION_BAIL);
        assertThat(testBail.getTypeBail()).isEqualTo(UPDATED_TYPE_BAIL);
        assertThat(testBail.getSignatureDate()).isEqualTo(UPDATED_SIGNATURE_DATE);
        assertThat(testBail.getFirstRentDate()).isEqualTo(UPDATED_FIRST_RENT_DATE);
        assertThat(testBail.getDestinationLocal()).isEqualTo(UPDATED_DESTINATION_LOCAL);
        assertThat(testBail.getIdOPCI()).isEqualTo(UPDATED_ID_OPCI);
        assertThat(testBail.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBail() throws Exception {
        int databaseSizeBeforeUpdate = bailRepository.findAll().size();

        // Create the Bail
        BailDTO bailDTO = bailMapper.toDto(bail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBailMockMvc.perform(put("/api/bails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bail in the database
        List<Bail> bailList = bailRepository.findAll();
        assertThat(bailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBail() throws Exception {
        // Initialize the database
        bailRepository.saveAndFlush(bail);

        int databaseSizeBeforeDelete = bailRepository.findAll().size();

        // Delete the bail
        restBailMockMvc.perform(delete("/api/bails/{id}", bail.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bail> bailList = bailRepository.findAll();
        assertThat(bailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
