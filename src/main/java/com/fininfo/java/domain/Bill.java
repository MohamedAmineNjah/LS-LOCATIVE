package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fininfo.java.domain.enumeration.IEnumMethode;

import com.fininfo.java.domain.enumeration.IEnumStatus;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "amount_exluding_tax", precision = 21, scale = 2)
    private BigDecimal amountExludingTax;

    @Column(name = "tva", precision = 21, scale = 2)
    private BigDecimal tva;

    @Column(name = "ttc", precision = 21, scale = 2)
    private BigDecimal ttc;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "reglement_method")
    private IEnumMethode reglementMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "bill_status")
    private IEnumStatus billStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = "bills", allowSetters = true)
    private Schedule schedule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Bill reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmountExludingTax() {
        return amountExludingTax;
    }

    public Bill amountExludingTax(BigDecimal amountExludingTax) {
        this.amountExludingTax = amountExludingTax;
        return this;
    }

    public void setAmountExludingTax(BigDecimal amountExludingTax) {
        this.amountExludingTax = amountExludingTax;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public Bill tva(BigDecimal tva) {
        this.tva = tva;
        return this;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getTtc() {
        return ttc;
    }

    public Bill ttc(BigDecimal ttc) {
        this.ttc = ttc;
        return this;
    }

    public void setTtc(BigDecimal ttc) {
        this.ttc = ttc;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public Bill billDate(LocalDate billDate) {
        this.billDate = billDate;
        return this;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public IEnumMethode getReglementMethod() {
        return reglementMethod;
    }

    public Bill reglementMethod(IEnumMethode reglementMethod) {
        this.reglementMethod = reglementMethod;
        return this;
    }

    public void setReglementMethod(IEnumMethode reglementMethod) {
        this.reglementMethod = reglementMethod;
    }

    public IEnumStatus getBillStatus() {
        return billStatus;
    }

    public Bill billStatus(IEnumStatus billStatus) {
        this.billStatus = billStatus;
        return this;
    }

    public void setBillStatus(IEnumStatus billStatus) {
        this.billStatus = billStatus;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Bill schedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bill)) {
            return false;
        }
        return id != null && id.equals(((Bill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bill{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", amountExludingTax=" + getAmountExludingTax() +
            ", tva=" + getTva() +
            ", ttc=" + getTtc() +
            ", billDate='" + getBillDate() + "'" +
            ", reglementMethod='" + getReglementMethod() + "'" +
            ", billStatus='" + getBillStatus() + "'" +
            "}";
    }
}
