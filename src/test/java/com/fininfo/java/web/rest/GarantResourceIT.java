package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Garant;
import com.fininfo.java.repository.GarantRepository;
import com.fininfo.java.service.GarantService;
import com.fininfo.java.service.dto.GarantDTO;
import com.fininfo.java.service.mapper.GarantMapper;
import com.fininfo.java.service.dto.GarantCriteria;
import com.fininfo.java.service.GarantQueryService;

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
 * Integration tests for the {@link GarantResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GarantResourceIT {

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_N_ALLOCATAIRE_CAF = 1;
    private static final Integer UPDATED_N_ALLOCATAIRE_CAF = 2;
    private static final Integer SMALLER_N_ALLOCATAIRE_CAF = 1 - 1;

    @Autowired
    private GarantRepository garantRepository;

    @Autowired
    private GarantMapper garantMapper;

    @Autowired
    private GarantService garantService;

    @Autowired
    private GarantQueryService garantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGarantMockMvc;

    private Garant garant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Garant createEntity(EntityManager em) {
        Garant garant = new Garant()
            .birthDate(DEFAULT_BIRTH_DATE)
            .profession(DEFAULT_PROFESSION)
            .nAllocataireCAF(DEFAULT_N_ALLOCATAIRE_CAF);
        return garant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Garant createUpdatedEntity(EntityManager em) {
        Garant garant = new Garant()
            .birthDate(UPDATED_BIRTH_DATE)
            .profession(UPDATED_PROFESSION)
            .nAllocataireCAF(UPDATED_N_ALLOCATAIRE_CAF);
        return garant;
    }

    @BeforeEach
    public void initTest() {
        garant = createEntity(em);
    }

    @Test
    @Transactional
    public void createGarant() throws Exception {
        int databaseSizeBeforeCreate = garantRepository.findAll().size();
        // Create the Garant
        GarantDTO garantDTO = garantMapper.toDto(garant);
        restGarantMockMvc.perform(post("/api/garants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(garantDTO)))
            .andExpect(status().isCreated());

        // Validate the Garant in the database
        List<Garant> garantList = garantRepository.findAll();
        assertThat(garantList).hasSize(databaseSizeBeforeCreate + 1);
        Garant testGarant = garantList.get(garantList.size() - 1);
        assertThat(testGarant.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testGarant.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testGarant.getnAllocataireCAF()).isEqualTo(DEFAULT_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void createGarantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = garantRepository.findAll().size();

        // Create the Garant with an existing ID
        garant.setId(1L);
        GarantDTO garantDTO = garantMapper.toDto(garant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGarantMockMvc.perform(post("/api/garants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(garantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Garant in the database
        List<Garant> garantList = garantRepository.findAll();
        assertThat(garantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGarants() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList
        restGarantMockMvc.perform(get("/api/garants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garant.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nAllocataireCAF").value(hasItem(DEFAULT_N_ALLOCATAIRE_CAF)));
    }
    
    @Test
    @Transactional
    public void getGarant() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get the garant
        restGarantMockMvc.perform(get("/api/garants/{id}", garant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(garant.getId().intValue()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.nAllocataireCAF").value(DEFAULT_N_ALLOCATAIRE_CAF));
    }


    @Test
    @Transactional
    public void getGarantsByIdFiltering() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        Long id = garant.getId();

        defaultGarantShouldBeFound("id.equals=" + id);
        defaultGarantShouldNotBeFound("id.notEquals=" + id);

        defaultGarantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGarantShouldNotBeFound("id.greaterThan=" + id);

        defaultGarantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGarantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultGarantShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the garantList where birthDate equals to UPDATED_BIRTH_DATE
        defaultGarantShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultGarantShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the garantList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultGarantShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultGarantShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the garantList where birthDate equals to UPDATED_BIRTH_DATE
        defaultGarantShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate is not null
        defaultGarantShouldBeFound("birthDate.specified=true");

        // Get all the garantList where birthDate is null
        defaultGarantShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultGarantShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the garantList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultGarantShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultGarantShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the garantList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultGarantShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultGarantShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the garantList where birthDate is less than UPDATED_BIRTH_DATE
        defaultGarantShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllGarantsByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultGarantShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the garantList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultGarantShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllGarantsByProfessionIsEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where profession equals to DEFAULT_PROFESSION
        defaultGarantShouldBeFound("profession.equals=" + DEFAULT_PROFESSION);

        // Get all the garantList where profession equals to UPDATED_PROFESSION
        defaultGarantShouldNotBeFound("profession.equals=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllGarantsByProfessionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where profession not equals to DEFAULT_PROFESSION
        defaultGarantShouldNotBeFound("profession.notEquals=" + DEFAULT_PROFESSION);

        // Get all the garantList where profession not equals to UPDATED_PROFESSION
        defaultGarantShouldBeFound("profession.notEquals=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllGarantsByProfessionIsInShouldWork() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where profession in DEFAULT_PROFESSION or UPDATED_PROFESSION
        defaultGarantShouldBeFound("profession.in=" + DEFAULT_PROFESSION + "," + UPDATED_PROFESSION);

        // Get all the garantList where profession equals to UPDATED_PROFESSION
        defaultGarantShouldNotBeFound("profession.in=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllGarantsByProfessionIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where profession is not null
        defaultGarantShouldBeFound("profession.specified=true");

        // Get all the garantList where profession is null
        defaultGarantShouldNotBeFound("profession.specified=false");
    }
                @Test
    @Transactional
    public void getAllGarantsByProfessionContainsSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where profession contains DEFAULT_PROFESSION
        defaultGarantShouldBeFound("profession.contains=" + DEFAULT_PROFESSION);

        // Get all the garantList where profession contains UPDATED_PROFESSION
        defaultGarantShouldNotBeFound("profession.contains=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllGarantsByProfessionNotContainsSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where profession does not contain DEFAULT_PROFESSION
        defaultGarantShouldNotBeFound("profession.doesNotContain=" + DEFAULT_PROFESSION);

        // Get all the garantList where profession does not contain UPDATED_PROFESSION
        defaultGarantShouldBeFound("profession.doesNotContain=" + UPDATED_PROFESSION);
    }


    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF equals to DEFAULT_N_ALLOCATAIRE_CAF
        defaultGarantShouldBeFound("nAllocataireCAF.equals=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the garantList where nAllocataireCAF equals to UPDATED_N_ALLOCATAIRE_CAF
        defaultGarantShouldNotBeFound("nAllocataireCAF.equals=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF not equals to DEFAULT_N_ALLOCATAIRE_CAF
        defaultGarantShouldNotBeFound("nAllocataireCAF.notEquals=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the garantList where nAllocataireCAF not equals to UPDATED_N_ALLOCATAIRE_CAF
        defaultGarantShouldBeFound("nAllocataireCAF.notEquals=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsInShouldWork() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF in DEFAULT_N_ALLOCATAIRE_CAF or UPDATED_N_ALLOCATAIRE_CAF
        defaultGarantShouldBeFound("nAllocataireCAF.in=" + DEFAULT_N_ALLOCATAIRE_CAF + "," + UPDATED_N_ALLOCATAIRE_CAF);

        // Get all the garantList where nAllocataireCAF equals to UPDATED_N_ALLOCATAIRE_CAF
        defaultGarantShouldNotBeFound("nAllocataireCAF.in=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsNullOrNotNull() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF is not null
        defaultGarantShouldBeFound("nAllocataireCAF.specified=true");

        // Get all the garantList where nAllocataireCAF is null
        defaultGarantShouldNotBeFound("nAllocataireCAF.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF is greater than or equal to DEFAULT_N_ALLOCATAIRE_CAF
        defaultGarantShouldBeFound("nAllocataireCAF.greaterThanOrEqual=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the garantList where nAllocataireCAF is greater than or equal to UPDATED_N_ALLOCATAIRE_CAF
        defaultGarantShouldNotBeFound("nAllocataireCAF.greaterThanOrEqual=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF is less than or equal to DEFAULT_N_ALLOCATAIRE_CAF
        defaultGarantShouldBeFound("nAllocataireCAF.lessThanOrEqual=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the garantList where nAllocataireCAF is less than or equal to SMALLER_N_ALLOCATAIRE_CAF
        defaultGarantShouldNotBeFound("nAllocataireCAF.lessThanOrEqual=" + SMALLER_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsLessThanSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF is less than DEFAULT_N_ALLOCATAIRE_CAF
        defaultGarantShouldNotBeFound("nAllocataireCAF.lessThan=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the garantList where nAllocataireCAF is less than UPDATED_N_ALLOCATAIRE_CAF
        defaultGarantShouldBeFound("nAllocataireCAF.lessThan=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllGarantsBynAllocataireCAFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        // Get all the garantList where nAllocataireCAF is greater than DEFAULT_N_ALLOCATAIRE_CAF
        defaultGarantShouldNotBeFound("nAllocataireCAF.greaterThan=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the garantList where nAllocataireCAF is greater than SMALLER_N_ALLOCATAIRE_CAF
        defaultGarantShouldBeFound("nAllocataireCAF.greaterThan=" + SMALLER_N_ALLOCATAIRE_CAF);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGarantShouldBeFound(String filter) throws Exception {
        restGarantMockMvc.perform(get("/api/garants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garant.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nAllocataireCAF").value(hasItem(DEFAULT_N_ALLOCATAIRE_CAF)));

        // Check, that the count call also returns 1
        restGarantMockMvc.perform(get("/api/garants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGarantShouldNotBeFound(String filter) throws Exception {
        restGarantMockMvc.perform(get("/api/garants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGarantMockMvc.perform(get("/api/garants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGarant() throws Exception {
        // Get the garant
        restGarantMockMvc.perform(get("/api/garants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarant() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        int databaseSizeBeforeUpdate = garantRepository.findAll().size();

        // Update the garant
        Garant updatedGarant = garantRepository.findById(garant.getId()).get();
        // Disconnect from session so that the updates on updatedGarant are not directly saved in db
        em.detach(updatedGarant);
        updatedGarant
            .birthDate(UPDATED_BIRTH_DATE)
            .profession(UPDATED_PROFESSION)
            .nAllocataireCAF(UPDATED_N_ALLOCATAIRE_CAF);
        GarantDTO garantDTO = garantMapper.toDto(updatedGarant);

        restGarantMockMvc.perform(put("/api/garants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(garantDTO)))
            .andExpect(status().isOk());

        // Validate the Garant in the database
        List<Garant> garantList = garantRepository.findAll();
        assertThat(garantList).hasSize(databaseSizeBeforeUpdate);
        Garant testGarant = garantList.get(garantList.size() - 1);
        assertThat(testGarant.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testGarant.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testGarant.getnAllocataireCAF()).isEqualTo(UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void updateNonExistingGarant() throws Exception {
        int databaseSizeBeforeUpdate = garantRepository.findAll().size();

        // Create the Garant
        GarantDTO garantDTO = garantMapper.toDto(garant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGarantMockMvc.perform(put("/api/garants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(garantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Garant in the database
        List<Garant> garantList = garantRepository.findAll();
        assertThat(garantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGarant() throws Exception {
        // Initialize the database
        garantRepository.saveAndFlush(garant);

        int databaseSizeBeforeDelete = garantRepository.findAll().size();

        // Delete the garant
        restGarantMockMvc.perform(delete("/api/garants/{id}", garant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Garant> garantList = garantRepository.findAll();
        assertThat(garantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
