package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Charge.
 */
@Entity
@Table(name = "charge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Charge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status_charge")
    private String statusCharge;

    @Column(name = "charge_date")
    private LocalDate chargeDate;

    @Column(name = "designation")
    private String designation;

    @Column(name = "reference")
    private String reference;

    @Column(name = "amount_charge", precision = 21, scale = 2)
    private BigDecimal amountCharge;

    @ManyToOne
    @JsonIgnoreProperties(value = "charges", allowSetters = true)
    private LotBail lotBail;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusCharge() {
        return statusCharge;
    }

    public Charge statusCharge(String statusCharge) {
        this.statusCharge = statusCharge;
        return this;
    }

    public void setStatusCharge(String statusCharge) {
        this.statusCharge = statusCharge;
    }

    public LocalDate getChargeDate() {
        return chargeDate;
    }

    public Charge chargeDate(LocalDate chargeDate) {
        this.chargeDate = chargeDate;
        return this;
    }

    public void setChargeDate(LocalDate chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getDesignation() {
        return designation;
    }

    public Charge designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getReference() {
        return reference;
    }

    public Charge reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmountCharge() {
        return amountCharge;
    }

    public Charge amountCharge(BigDecimal amountCharge) {
        this.amountCharge = amountCharge;
        return this;
    }

    public void setAmountCharge(BigDecimal amountCharge) {
        this.amountCharge = amountCharge;
    }

    public LotBail getLotBail() {
        return lotBail;
    }

    public Charge lotBail(LotBail lotBail) {
        this.lotBail = lotBail;
        return this;
    }

    public void setLotBail(LotBail lotBail) {
        this.lotBail = lotBail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Charge)) {
            return false;
        }
        return id != null && id.equals(((Charge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Charge{" +
            "id=" + getId() +
            ", statusCharge='" + getStatusCharge() + "'" +
            ", chargeDate='" + getChargeDate() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", reference='" + getReference() + "'" +
            ", amountCharge=" + getAmountCharge() +
            "}";
    }
}
