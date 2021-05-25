package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount_schedule", precision = 21, scale = 2)
    private BigDecimal amountSchedule;

    @OneToMany(mappedBy = "schedule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Bill> bills = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "schedules", allowSetters = true)
    private LotBail lotBail;

    @ManyToOne
    @JsonIgnoreProperties(value = "schedules", allowSetters = true)
    private Rent rent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountSchedule() {
        return amountSchedule;
    }

    public Schedule amountSchedule(BigDecimal amountSchedule) {
        this.amountSchedule = amountSchedule;
        return this;
    }

    public void setAmountSchedule(BigDecimal amountSchedule) {
        this.amountSchedule = amountSchedule;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public Schedule bills(Set<Bill> bills) {
        this.bills = bills;
        return this;
    }

    public Schedule addBill(Bill bill) {
        this.bills.add(bill);
        bill.setSchedule(this);
        return this;
    }

    public Schedule removeBill(Bill bill) {
        this.bills.remove(bill);
        bill.setSchedule(null);
        return this;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public LotBail getLotBail() {
        return lotBail;
    }

    public Schedule lotBail(LotBail lotBail) {
        this.lotBail = lotBail;
        return this;
    }

    public void setLotBail(LotBail lotBail) {
        this.lotBail = lotBail;
    }

    public Rent getRent() {
        return rent;
    }

    public Schedule rent(Rent rent) {
        this.rent = rent;
        return this;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        return id != null && id.equals(((Schedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + getId() +
            ", amountSchedule=" + getAmountSchedule() +
            "}";
    }
}
