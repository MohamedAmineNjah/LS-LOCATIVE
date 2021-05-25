package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Locataire;
import com.fininfo.java.repository.LocataireRepository;
import com.fininfo.java.service.LocataireService;
import com.fininfo.java.service.dto.LocataireDTO;
import com.fininfo.java.service.mapper.LocataireMapper;
import com.fininfo.java.service.dto.LocataireCriteria;
import com.fininfo.java.service.LocataireQueryService;

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
 * Integration tests for the {@link LocataireResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocataireResourceIT {

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_N_ALLOCATAIRE_CAF = 1;
    private static final Integer UPDATED_N_ALLOCATAIRE_CAF = 2;
    private static final Integer SMALLER_N_ALLOCATAIRE_CAF = 1 - 1;

    @Autowired
    private LocataireRepository locataireRepository;

    @Autowired
    private LocataireMapper locataireMapper;

    @Autowired
    private LocataireService locataireService;

    @Autowired
    private LocataireQueryService locataireQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocataireMockMvc;

    private Locataire locataire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locataire createEntity(EntityManager em) {
        Locataire locataire = new Locataire()
            .birthDate(DEFAULT_BIRTH_DATE)
            .profession(DEFAULT_PROFESSION)
            .nAllocataireCAF(DEFAULT_N_ALLOCATAIRE_CAF);
        return locataire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locataire createUpdatedEntity(EntityManager em) {
        Locataire locataire = new Locataire()
            .birthDate(UPDATED_BIRTH_DATE)
            .profession(UPDATED_PROFESSION)
            .nAllocataireCAF(UPDATED_N_ALLOCATAIRE_CAF);
        return locataire;
    }

    @BeforeEach
    public void initTest() {
        locataire = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocataire() throws Exception {
        int databaseSizeBeforeCreate = locataireRepository.findAll().size();
        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);
        restLocataireMockMvc.perform(post("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isCreated());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeCreate + 1);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testLocataire.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testLocataire.getnAllocataireCAF()).isEqualTo(DEFAULT_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void createLocataireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locataireRepository.findAll().size();

        // Create the Locataire with an existing ID
        locataire.setId(1L);
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocataireMockMvc.perform(post("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocataires() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList
        restLocataireMockMvc.perform(get("/api/locataires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nAllocataireCAF").value(hasItem(DEFAULT_N_ALLOCATAIRE_CAF)));
    }
    
    @Test
    @Transactional
    public void getLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get the locataire
        restLocataireMockMvc.perform(get("/api/locataires/{id}", locataire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locataire.getId().intValue()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.nAllocataireCAF").value(DEFAULT_N_ALLOCATAIRE_CAF));
    }


    @Test
    @Transactional
    public void getLocatairesByIdFiltering() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        Long id = locataire.getId();

        defaultLocataireShouldBeFound("id.equals=" + id);
        defaultLocataireShouldNotBeFound("id.notEquals=" + id);

        defaultLocataireShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLocataireShouldNotBeFound("id.greaterThan=" + id);

        defaultLocataireShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLocataireShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultLocataireShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the locataireList where birthDate equals to UPDATED_BIRTH_DATE
        defaultLocataireShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultLocataireShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the locataireList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultLocataireShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultLocataireShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the locataireList where birthDate equals to UPDATED_BIRTH_DATE
        defaultLocataireShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate is not null
        defaultLocataireShouldBeFound("birthDate.specified=true");

        // Get all the locataireList where birthDate is null
        defaultLocataireShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultLocataireShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the locataireList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultLocataireShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultLocataireShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the locataireList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultLocataireShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultLocataireShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the locataireList where birthDate is less than UPDATED_BIRTH_DATE
        defaultLocataireShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllLocatairesByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultLocataireShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the locataireList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultLocataireShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllLocatairesByProfessionIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where profession equals to DEFAULT_PROFESSION
        defaultLocataireShouldBeFound("profession.equals=" + DEFAULT_PROFESSION);

        // Get all the locataireList where profession equals to UPDATED_PROFESSION
        defaultLocataireShouldNotBeFound("profession.equals=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllLocatairesByProfessionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where profession not equals to DEFAULT_PROFESSION
        defaultLocataireShouldNotBeFound("profession.notEquals=" + DEFAULT_PROFESSION);

        // Get all the locataireList where profession not equals to UPDATED_PROFESSION
        defaultLocataireShouldBeFound("profession.notEquals=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllLocatairesByProfessionIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where profession in DEFAULT_PROFESSION or UPDATED_PROFESSION
        defaultLocataireShouldBeFound("profession.in=" + DEFAULT_PROFESSION + "," + UPDATED_PROFESSION);

        // Get all the locataireList where profession equals to UPDATED_PROFESSION
        defaultLocataireShouldNotBeFound("profession.in=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllLocatairesByProfessionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where profession is not null
        defaultLocataireShouldBeFound("profession.specified=true");

        // Get all the locataireList where profession is null
        defaultLocataireShouldNotBeFound("profession.specified=false");
    }
                @Test
    @Transactional
    public void getAllLocatairesByProfessionContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where profession contains DEFAULT_PROFESSION
        defaultLocataireShouldBeFound("profession.contains=" + DEFAULT_PROFESSION);

        // Get all the locataireList where profession contains UPDATED_PROFESSION
        defaultLocataireShouldNotBeFound("profession.contains=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllLocatairesByProfessionNotContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where profession does not contain DEFAULT_PROFESSION
        defaultLocataireShouldNotBeFound("profession.doesNotContain=" + DEFAULT_PROFESSION);

        // Get all the locataireList where profession does not contain UPDATED_PROFESSION
        defaultLocataireShouldBeFound("profession.doesNotContain=" + UPDATED_PROFESSION);
    }


    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF equals to DEFAULT_N_ALLOCATAIRE_CAF
        defaultLocataireShouldBeFound("nAllocataireCAF.equals=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the locataireList where nAllocataireCAF equals to UPDATED_N_ALLOCATAIRE_CAF
        defaultLocataireShouldNotBeFound("nAllocataireCAF.equals=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF not equals to DEFAULT_N_ALLOCATAIRE_CAF
        defaultLocataireShouldNotBeFound("nAllocataireCAF.notEquals=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the locataireList where nAllocataireCAF not equals to UPDATED_N_ALLOCATAIRE_CAF
        defaultLocataireShouldBeFound("nAllocataireCAF.notEquals=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF in DEFAULT_N_ALLOCATAIRE_CAF or UPDATED_N_ALLOCATAIRE_CAF
        defaultLocataireShouldBeFound("nAllocataireCAF.in=" + DEFAULT_N_ALLOCATAIRE_CAF + "," + UPDATED_N_ALLOCATAIRE_CAF);

        // Get all the locataireList where nAllocataireCAF equals to UPDATED_N_ALLOCATAIRE_CAF
        defaultLocataireShouldNotBeFound("nAllocataireCAF.in=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF is not null
        defaultLocataireShouldBeFound("nAllocataireCAF.specified=true");

        // Get all the locataireList where nAllocataireCAF is null
        defaultLocataireShouldNotBeFound("nAllocataireCAF.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF is greater than or equal to DEFAULT_N_ALLOCATAIRE_CAF
        defaultLocataireShouldBeFound("nAllocataireCAF.greaterThanOrEqual=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the locataireList where nAllocataireCAF is greater than or equal to UPDATED_N_ALLOCATAIRE_CAF
        defaultLocataireShouldNotBeFound("nAllocataireCAF.greaterThanOrEqual=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF is less than or equal to DEFAULT_N_ALLOCATAIRE_CAF
        defaultLocataireShouldBeFound("nAllocataireCAF.lessThanOrEqual=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the locataireList where nAllocataireCAF is less than or equal to SMALLER_N_ALLOCATAIRE_CAF
        defaultLocataireShouldNotBeFound("nAllocataireCAF.lessThanOrEqual=" + SMALLER_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsLessThanSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF is less than DEFAULT_N_ALLOCATAIRE_CAF
        defaultLocataireShouldNotBeFound("nAllocataireCAF.lessThan=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the locataireList where nAllocataireCAF is less than UPDATED_N_ALLOCATAIRE_CAF
        defaultLocataireShouldBeFound("nAllocataireCAF.lessThan=" + UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void getAllLocatairesBynAllocataireCAFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nAllocataireCAF is greater than DEFAULT_N_ALLOCATAIRE_CAF
        defaultLocataireShouldNotBeFound("nAllocataireCAF.greaterThan=" + DEFAULT_N_ALLOCATAIRE_CAF);

        // Get all the locataireList where nAllocataireCAF is greater than SMALLER_N_ALLOCATAIRE_CAF
        defaultLocataireShouldBeFound("nAllocataireCAF.greaterThan=" + SMALLER_N_ALLOCATAIRE_CAF);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocataireShouldBeFound(String filter) throws Exception {
        restLocataireMockMvc.perform(get("/api/locataires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nAllocataireCAF").value(hasItem(DEFAULT_N_ALLOCATAIRE_CAF)));

        // Check, that the count call also returns 1
        restLocataireMockMvc.perform(get("/api/locataires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocataireShouldNotBeFound(String filter) throws Exception {
        restLocataireMockMvc.perform(get("/api/locataires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocataireMockMvc.perform(get("/api/locataires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLocataire() throws Exception {
        // Get the locataire
        restLocataireMockMvc.perform(get("/api/locataires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();

        // Update the locataire
        Locataire updatedLocataire = locataireRepository.findById(locataire.getId()).get();
        // Disconnect from session so that the updates on updatedLocataire are not directly saved in db
        em.detach(updatedLocataire);
        updatedLocataire
            .birthDate(UPDATED_BIRTH_DATE)
            .profession(UPDATED_PROFESSION)
            .nAllocataireCAF(UPDATED_N_ALLOCATAIRE_CAF);
        LocataireDTO locataireDTO = locataireMapper.toDto(updatedLocataire);

        restLocataireMockMvc.perform(put("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isOk());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testLocataire.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testLocataire.getnAllocataireCAF()).isEqualTo(UPDATED_N_ALLOCATAIRE_CAF);
    }

    @Test
    @Transactional
    public void updateNonExistingLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocataireMockMvc.perform(put("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeDelete = locataireRepository.findAll().size();

        // Delete the locataire
        restLocataireMockMvc.perform(delete("/api/locataires/{id}", locataire.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
