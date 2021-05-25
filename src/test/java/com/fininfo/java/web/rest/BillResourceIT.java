package com.fininfo.java.web.rest;

import com.fininfo.java.LocativeApp;
import com.fininfo.java.domain.Bill;
import com.fininfo.java.domain.Schedule;
import com.fininfo.java.repository.BillRepository;
import com.fininfo.java.service.BillService;
import com.fininfo.java.service.dto.BillDTO;
import com.fininfo.java.service.mapper.BillMapper;
import com.fininfo.java.service.dto.BillCriteria;
import com.fininfo.java.service.BillQueryService;

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

import com.fininfo.java.domain.enumeration.IEnumMethode;
import com.fininfo.java.domain.enumeration.IEnumStatus;
/**
 * Integration tests for the {@link BillResource} REST controller.
 */
@SpringBootTest(classes = LocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BillResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT_EXLUDING_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_EXLUDING_TAX = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_EXLUDING_TAX = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TVA = new BigDecimal(2);
    private static final BigDecimal SMALLER_TVA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TTC = new BigDecimal(1);
    private static final BigDecimal UPDATED_TTC = new BigDecimal(2);
    private static final BigDecimal SMALLER_TTC = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_BILL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BILL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BILL_DATE = LocalDate.ofEpochDay(-1L);

    private static final IEnumMethode DEFAULT_REGLEMENT_METHOD = IEnumMethode.CHEQUE;
    private static final IEnumMethode UPDATED_REGLEMENT_METHOD = IEnumMethode.ESPECE;

    private static final IEnumStatus DEFAULT_BILL_STATUS = IEnumStatus.ENCOURSDEVALIDATION;
    private static final IEnumStatus UPDATED_BILL_STATUS = IEnumStatus.VALIDE;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillService billService;

    @Autowired
    private BillQueryService billQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillMockMvc;

    private Bill bill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bill createEntity(EntityManager em) {
        Bill bill = new Bill()
            .reference(DEFAULT_REFERENCE)
            .amountExludingTax(DEFAULT_AMOUNT_EXLUDING_TAX)
            .tva(DEFAULT_TVA)
            .ttc(DEFAULT_TTC)
            .billDate(DEFAULT_BILL_DATE)
            .reglementMethod(DEFAULT_REGLEMENT_METHOD)
            .billStatus(DEFAULT_BILL_STATUS);
        return bill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bill createUpdatedEntity(EntityManager em) {
        Bill bill = new Bill()
            .reference(UPDATED_REFERENCE)
            .amountExludingTax(UPDATED_AMOUNT_EXLUDING_TAX)
            .tva(UPDATED_TVA)
            .ttc(UPDATED_TTC)
            .billDate(UPDATED_BILL_DATE)
            .reglementMethod(UPDATED_REGLEMENT_METHOD)
            .billStatus(UPDATED_BILL_STATUS);
        return bill;
    }

    @BeforeEach
    public void initTest() {
        bill = createEntity(em);
    }

    @Test
    @Transactional
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();
        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);
        restBillMockMvc.perform(post("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testBill.getAmountExludingTax()).isEqualTo(DEFAULT_AMOUNT_EXLUDING_TAX);
        assertThat(testBill.getTva()).isEqualTo(DEFAULT_TVA);
        assertThat(testBill.getTtc()).isEqualTo(DEFAULT_TTC);
        assertThat(testBill.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testBill.getReglementMethod()).isEqualTo(DEFAULT_REGLEMENT_METHOD);
        assertThat(testBill.getBillStatus()).isEqualTo(DEFAULT_BILL_STATUS);
    }

    @Test
    @Transactional
    public void createBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill with an existing ID
        bill.setId(1L);
        BillDTO billDTO = billMapper.toDto(bill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillMockMvc.perform(post("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList
        restBillMockMvc.perform(get("/api/bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].amountExludingTax").value(hasItem(DEFAULT_AMOUNT_EXLUDING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.intValue())))
            .andExpect(jsonPath("$.[*].ttc").value(hasItem(DEFAULT_TTC.intValue())))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].reglementMethod").value(hasItem(DEFAULT_REGLEMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].billStatus").value(hasItem(DEFAULT_BILL_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.amountExludingTax").value(DEFAULT_AMOUNT_EXLUDING_TAX.intValue()))
            .andExpect(jsonPath("$.tva").value(DEFAULT_TVA.intValue()))
            .andExpect(jsonPath("$.ttc").value(DEFAULT_TTC.intValue()))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.reglementMethod").value(DEFAULT_REGLEMENT_METHOD.toString()))
            .andExpect(jsonPath("$.billStatus").value(DEFAULT_BILL_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getBillsByIdFiltering() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        Long id = bill.getId();

        defaultBillShouldBeFound("id.equals=" + id);
        defaultBillShouldNotBeFound("id.notEquals=" + id);

        defaultBillShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBillShouldNotBeFound("id.greaterThan=" + id);

        defaultBillShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBillShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBillsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reference equals to DEFAULT_REFERENCE
        defaultBillShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the billList where reference equals to UPDATED_REFERENCE
        defaultBillShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllBillsByReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reference not equals to DEFAULT_REFERENCE
        defaultBillShouldNotBeFound("reference.notEquals=" + DEFAULT_REFERENCE);

        // Get all the billList where reference not equals to UPDATED_REFERENCE
        defaultBillShouldBeFound("reference.notEquals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllBillsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultBillShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the billList where reference equals to UPDATED_REFERENCE
        defaultBillShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllBillsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reference is not null
        defaultBillShouldBeFound("reference.specified=true");

        // Get all the billList where reference is null
        defaultBillShouldNotBeFound("reference.specified=false");
    }
                @Test
    @Transactional
    public void getAllBillsByReferenceContainsSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reference contains DEFAULT_REFERENCE
        defaultBillShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the billList where reference contains UPDATED_REFERENCE
        defaultBillShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllBillsByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reference does not contain DEFAULT_REFERENCE
        defaultBillShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the billList where reference does not contain UPDATED_REFERENCE
        defaultBillShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax equals to DEFAULT_AMOUNT_EXLUDING_TAX
        defaultBillShouldBeFound("amountExludingTax.equals=" + DEFAULT_AMOUNT_EXLUDING_TAX);

        // Get all the billList where amountExludingTax equals to UPDATED_AMOUNT_EXLUDING_TAX
        defaultBillShouldNotBeFound("amountExludingTax.equals=" + UPDATED_AMOUNT_EXLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax not equals to DEFAULT_AMOUNT_EXLUDING_TAX
        defaultBillShouldNotBeFound("amountExludingTax.notEquals=" + DEFAULT_AMOUNT_EXLUDING_TAX);

        // Get all the billList where amountExludingTax not equals to UPDATED_AMOUNT_EXLUDING_TAX
        defaultBillShouldBeFound("amountExludingTax.notEquals=" + UPDATED_AMOUNT_EXLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax in DEFAULT_AMOUNT_EXLUDING_TAX or UPDATED_AMOUNT_EXLUDING_TAX
        defaultBillShouldBeFound("amountExludingTax.in=" + DEFAULT_AMOUNT_EXLUDING_TAX + "," + UPDATED_AMOUNT_EXLUDING_TAX);

        // Get all the billList where amountExludingTax equals to UPDATED_AMOUNT_EXLUDING_TAX
        defaultBillShouldNotBeFound("amountExludingTax.in=" + UPDATED_AMOUNT_EXLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax is not null
        defaultBillShouldBeFound("amountExludingTax.specified=true");

        // Get all the billList where amountExludingTax is null
        defaultBillShouldNotBeFound("amountExludingTax.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax is greater than or equal to DEFAULT_AMOUNT_EXLUDING_TAX
        defaultBillShouldBeFound("amountExludingTax.greaterThanOrEqual=" + DEFAULT_AMOUNT_EXLUDING_TAX);

        // Get all the billList where amountExludingTax is greater than or equal to UPDATED_AMOUNT_EXLUDING_TAX
        defaultBillShouldNotBeFound("amountExludingTax.greaterThanOrEqual=" + UPDATED_AMOUNT_EXLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax is less than or equal to DEFAULT_AMOUNT_EXLUDING_TAX
        defaultBillShouldBeFound("amountExludingTax.lessThanOrEqual=" + DEFAULT_AMOUNT_EXLUDING_TAX);

        // Get all the billList where amountExludingTax is less than or equal to SMALLER_AMOUNT_EXLUDING_TAX
        defaultBillShouldNotBeFound("amountExludingTax.lessThanOrEqual=" + SMALLER_AMOUNT_EXLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax is less than DEFAULT_AMOUNT_EXLUDING_TAX
        defaultBillShouldNotBeFound("amountExludingTax.lessThan=" + DEFAULT_AMOUNT_EXLUDING_TAX);

        // Get all the billList where amountExludingTax is less than UPDATED_AMOUNT_EXLUDING_TAX
        defaultBillShouldBeFound("amountExludingTax.lessThan=" + UPDATED_AMOUNT_EXLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountExludingTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amountExludingTax is greater than DEFAULT_AMOUNT_EXLUDING_TAX
        defaultBillShouldNotBeFound("amountExludingTax.greaterThan=" + DEFAULT_AMOUNT_EXLUDING_TAX);

        // Get all the billList where amountExludingTax is greater than SMALLER_AMOUNT_EXLUDING_TAX
        defaultBillShouldBeFound("amountExludingTax.greaterThan=" + SMALLER_AMOUNT_EXLUDING_TAX);
    }


    @Test
    @Transactional
    public void getAllBillsByTvaIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva equals to DEFAULT_TVA
        defaultBillShouldBeFound("tva.equals=" + DEFAULT_TVA);

        // Get all the billList where tva equals to UPDATED_TVA
        defaultBillShouldNotBeFound("tva.equals=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllBillsByTvaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva not equals to DEFAULT_TVA
        defaultBillShouldNotBeFound("tva.notEquals=" + DEFAULT_TVA);

        // Get all the billList where tva not equals to UPDATED_TVA
        defaultBillShouldBeFound("tva.notEquals=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllBillsByTvaIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva in DEFAULT_TVA or UPDATED_TVA
        defaultBillShouldBeFound("tva.in=" + DEFAULT_TVA + "," + UPDATED_TVA);

        // Get all the billList where tva equals to UPDATED_TVA
        defaultBillShouldNotBeFound("tva.in=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllBillsByTvaIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva is not null
        defaultBillShouldBeFound("tva.specified=true");

        // Get all the billList where tva is null
        defaultBillShouldNotBeFound("tva.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByTvaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva is greater than or equal to DEFAULT_TVA
        defaultBillShouldBeFound("tva.greaterThanOrEqual=" + DEFAULT_TVA);

        // Get all the billList where tva is greater than or equal to UPDATED_TVA
        defaultBillShouldNotBeFound("tva.greaterThanOrEqual=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllBillsByTvaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva is less than or equal to DEFAULT_TVA
        defaultBillShouldBeFound("tva.lessThanOrEqual=" + DEFAULT_TVA);

        // Get all the billList where tva is less than or equal to SMALLER_TVA
        defaultBillShouldNotBeFound("tva.lessThanOrEqual=" + SMALLER_TVA);
    }

    @Test
    @Transactional
    public void getAllBillsByTvaIsLessThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva is less than DEFAULT_TVA
        defaultBillShouldNotBeFound("tva.lessThan=" + DEFAULT_TVA);

        // Get all the billList where tva is less than UPDATED_TVA
        defaultBillShouldBeFound("tva.lessThan=" + UPDATED_TVA);
    }

    @Test
    @Transactional
    public void getAllBillsByTvaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where tva is greater than DEFAULT_TVA
        defaultBillShouldNotBeFound("tva.greaterThan=" + DEFAULT_TVA);

        // Get all the billList where tva is greater than SMALLER_TVA
        defaultBillShouldBeFound("tva.greaterThan=" + SMALLER_TVA);
    }


    @Test
    @Transactional
    public void getAllBillsByTtcIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc equals to DEFAULT_TTC
        defaultBillShouldBeFound("ttc.equals=" + DEFAULT_TTC);

        // Get all the billList where ttc equals to UPDATED_TTC
        defaultBillShouldNotBeFound("ttc.equals=" + UPDATED_TTC);
    }

    @Test
    @Transactional
    public void getAllBillsByTtcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc not equals to DEFAULT_TTC
        defaultBillShouldNotBeFound("ttc.notEquals=" + DEFAULT_TTC);

        // Get all the billList where ttc not equals to UPDATED_TTC
        defaultBillShouldBeFound("ttc.notEquals=" + UPDATED_TTC);
    }

    @Test
    @Transactional
    public void getAllBillsByTtcIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc in DEFAULT_TTC or UPDATED_TTC
        defaultBillShouldBeFound("ttc.in=" + DEFAULT_TTC + "," + UPDATED_TTC);

        // Get all the billList where ttc equals to UPDATED_TTC
        defaultBillShouldNotBeFound("ttc.in=" + UPDATED_TTC);
    }

    @Test
    @Transactional
    public void getAllBillsByTtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc is not null
        defaultBillShouldBeFound("ttc.specified=true");

        // Get all the billList where ttc is null
        defaultBillShouldNotBeFound("ttc.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByTtcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc is greater than or equal to DEFAULT_TTC
        defaultBillShouldBeFound("ttc.greaterThanOrEqual=" + DEFAULT_TTC);

        // Get all the billList where ttc is greater than or equal to UPDATED_TTC
        defaultBillShouldNotBeFound("ttc.greaterThanOrEqual=" + UPDATED_TTC);
    }

    @Test
    @Transactional
    public void getAllBillsByTtcIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc is less than or equal to DEFAULT_TTC
        defaultBillShouldBeFound("ttc.lessThanOrEqual=" + DEFAULT_TTC);

        // Get all the billList where ttc is less than or equal to SMALLER_TTC
        defaultBillShouldNotBeFound("ttc.lessThanOrEqual=" + SMALLER_TTC);
    }

    @Test
    @Transactional
    public void getAllBillsByTtcIsLessThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc is less than DEFAULT_TTC
        defaultBillShouldNotBeFound("ttc.lessThan=" + DEFAULT_TTC);

        // Get all the billList where ttc is less than UPDATED_TTC
        defaultBillShouldBeFound("ttc.lessThan=" + UPDATED_TTC);
    }

    @Test
    @Transactional
    public void getAllBillsByTtcIsGreaterThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where ttc is greater than DEFAULT_TTC
        defaultBillShouldNotBeFound("ttc.greaterThan=" + DEFAULT_TTC);

        // Get all the billList where ttc is greater than SMALLER_TTC
        defaultBillShouldBeFound("ttc.greaterThan=" + SMALLER_TTC);
    }


    @Test
    @Transactional
    public void getAllBillsByBillDateIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate equals to DEFAULT_BILL_DATE
        defaultBillShouldBeFound("billDate.equals=" + DEFAULT_BILL_DATE);

        // Get all the billList where billDate equals to UPDATED_BILL_DATE
        defaultBillShouldNotBeFound("billDate.equals=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByBillDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate not equals to DEFAULT_BILL_DATE
        defaultBillShouldNotBeFound("billDate.notEquals=" + DEFAULT_BILL_DATE);

        // Get all the billList where billDate not equals to UPDATED_BILL_DATE
        defaultBillShouldBeFound("billDate.notEquals=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByBillDateIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate in DEFAULT_BILL_DATE or UPDATED_BILL_DATE
        defaultBillShouldBeFound("billDate.in=" + DEFAULT_BILL_DATE + "," + UPDATED_BILL_DATE);

        // Get all the billList where billDate equals to UPDATED_BILL_DATE
        defaultBillShouldNotBeFound("billDate.in=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByBillDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate is not null
        defaultBillShouldBeFound("billDate.specified=true");

        // Get all the billList where billDate is null
        defaultBillShouldNotBeFound("billDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByBillDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate is greater than or equal to DEFAULT_BILL_DATE
        defaultBillShouldBeFound("billDate.greaterThanOrEqual=" + DEFAULT_BILL_DATE);

        // Get all the billList where billDate is greater than or equal to UPDATED_BILL_DATE
        defaultBillShouldNotBeFound("billDate.greaterThanOrEqual=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByBillDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate is less than or equal to DEFAULT_BILL_DATE
        defaultBillShouldBeFound("billDate.lessThanOrEqual=" + DEFAULT_BILL_DATE);

        // Get all the billList where billDate is less than or equal to SMALLER_BILL_DATE
        defaultBillShouldNotBeFound("billDate.lessThanOrEqual=" + SMALLER_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByBillDateIsLessThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate is less than DEFAULT_BILL_DATE
        defaultBillShouldNotBeFound("billDate.lessThan=" + DEFAULT_BILL_DATE);

        // Get all the billList where billDate is less than UPDATED_BILL_DATE
        defaultBillShouldBeFound("billDate.lessThan=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByBillDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billDate is greater than DEFAULT_BILL_DATE
        defaultBillShouldNotBeFound("billDate.greaterThan=" + DEFAULT_BILL_DATE);

        // Get all the billList where billDate is greater than SMALLER_BILL_DATE
        defaultBillShouldBeFound("billDate.greaterThan=" + SMALLER_BILL_DATE);
    }


    @Test
    @Transactional
    public void getAllBillsByReglementMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reglementMethod equals to DEFAULT_REGLEMENT_METHOD
        defaultBillShouldBeFound("reglementMethod.equals=" + DEFAULT_REGLEMENT_METHOD);

        // Get all the billList where reglementMethod equals to UPDATED_REGLEMENT_METHOD
        defaultBillShouldNotBeFound("reglementMethod.equals=" + UPDATED_REGLEMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllBillsByReglementMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reglementMethod not equals to DEFAULT_REGLEMENT_METHOD
        defaultBillShouldNotBeFound("reglementMethod.notEquals=" + DEFAULT_REGLEMENT_METHOD);

        // Get all the billList where reglementMethod not equals to UPDATED_REGLEMENT_METHOD
        defaultBillShouldBeFound("reglementMethod.notEquals=" + UPDATED_REGLEMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllBillsByReglementMethodIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reglementMethod in DEFAULT_REGLEMENT_METHOD or UPDATED_REGLEMENT_METHOD
        defaultBillShouldBeFound("reglementMethod.in=" + DEFAULT_REGLEMENT_METHOD + "," + UPDATED_REGLEMENT_METHOD);

        // Get all the billList where reglementMethod equals to UPDATED_REGLEMENT_METHOD
        defaultBillShouldNotBeFound("reglementMethod.in=" + UPDATED_REGLEMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllBillsByReglementMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where reglementMethod is not null
        defaultBillShouldBeFound("reglementMethod.specified=true");

        // Get all the billList where reglementMethod is null
        defaultBillShouldNotBeFound("reglementMethod.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByBillStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billStatus equals to DEFAULT_BILL_STATUS
        defaultBillShouldBeFound("billStatus.equals=" + DEFAULT_BILL_STATUS);

        // Get all the billList where billStatus equals to UPDATED_BILL_STATUS
        defaultBillShouldNotBeFound("billStatus.equals=" + UPDATED_BILL_STATUS);
    }

    @Test
    @Transactional
    public void getAllBillsByBillStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billStatus not equals to DEFAULT_BILL_STATUS
        defaultBillShouldNotBeFound("billStatus.notEquals=" + DEFAULT_BILL_STATUS);

        // Get all the billList where billStatus not equals to UPDATED_BILL_STATUS
        defaultBillShouldBeFound("billStatus.notEquals=" + UPDATED_BILL_STATUS);
    }

    @Test
    @Transactional
    public void getAllBillsByBillStatusIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billStatus in DEFAULT_BILL_STATUS or UPDATED_BILL_STATUS
        defaultBillShouldBeFound("billStatus.in=" + DEFAULT_BILL_STATUS + "," + UPDATED_BILL_STATUS);

        // Get all the billList where billStatus equals to UPDATED_BILL_STATUS
        defaultBillShouldNotBeFound("billStatus.in=" + UPDATED_BILL_STATUS);
    }

    @Test
    @Transactional
    public void getAllBillsByBillStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billStatus is not null
        defaultBillShouldBeFound("billStatus.specified=true");

        // Get all the billList where billStatus is null
        defaultBillShouldNotBeFound("billStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByScheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);
        Schedule schedule = ScheduleResourceIT.createEntity(em);
        em.persist(schedule);
        em.flush();
        bill.setSchedule(schedule);
        billRepository.saveAndFlush(bill);
        Long scheduleId = schedule.getId();

        // Get all the billList where schedule equals to scheduleId
        defaultBillShouldBeFound("scheduleId.equals=" + scheduleId);

        // Get all the billList where schedule equals to scheduleId + 1
        defaultBillShouldNotBeFound("scheduleId.equals=" + (scheduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBillShouldBeFound(String filter) throws Exception {
        restBillMockMvc.perform(get("/api/bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].amountExludingTax").value(hasItem(DEFAULT_AMOUNT_EXLUDING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.intValue())))
            .andExpect(jsonPath("$.[*].ttc").value(hasItem(DEFAULT_TTC.intValue())))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].reglementMethod").value(hasItem(DEFAULT_REGLEMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].billStatus").value(hasItem(DEFAULT_BILL_STATUS.toString())));

        // Check, that the count call also returns 1
        restBillMockMvc.perform(get("/api/bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBillShouldNotBeFound(String filter) throws Exception {
        restBillMockMvc.perform(get("/api/bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBillMockMvc.perform(get("/api/bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        Bill updatedBill = billRepository.findById(bill.getId()).get();
        // Disconnect from session so that the updates on updatedBill are not directly saved in db
        em.detach(updatedBill);
        updatedBill
            .reference(UPDATED_REFERENCE)
            .amountExludingTax(UPDATED_AMOUNT_EXLUDING_TAX)
            .tva(UPDATED_TVA)
            .ttc(UPDATED_TTC)
            .billDate(UPDATED_BILL_DATE)
            .reglementMethod(UPDATED_REGLEMENT_METHOD)
            .billStatus(UPDATED_BILL_STATUS);
        BillDTO billDTO = billMapper.toDto(updatedBill);

        restBillMockMvc.perform(put("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testBill.getAmountExludingTax()).isEqualTo(UPDATED_AMOUNT_EXLUDING_TAX);
        assertThat(testBill.getTva()).isEqualTo(UPDATED_TVA);
        assertThat(testBill.getTtc()).isEqualTo(UPDATED_TTC);
        assertThat(testBill.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testBill.getReglementMethod()).isEqualTo(UPDATED_REGLEMENT_METHOD);
        assertThat(testBill.getBillStatus()).isEqualTo(UPDATED_BILL_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillMockMvc.perform(put("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Delete the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
