package com.fininfo.java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rent.
 */
@Entity
@Table(name = "rent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nominal_value", precision = 21, scale = 2)
    private BigDecimal nominalValue;

    @Column(name = "taux")
    private Double taux;

    @Column(name = "mode")
    private String mode;

    @Column(name = "capital")
    private Integer capital;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "refund_amount")
    private Integer refundAmount;

    @Column(name = "start_excluded")
    private Boolean startExcluded;

    @Column(name = "month_end")
    private Boolean monthEnd;

    @Column(name = "end_excluded")
    private Boolean endExcluded;

    @Column(name = "rate_value")
    private Integer rateValue;

    @Column(name = "coupon_decimal_number")
    private Integer couponDecimalNumber;

    @Column(name = "coupon_first_date")
    private LocalDate couponFirstDate;

    @Column(name = "coupon_last_date")
    private LocalDate couponLastDate;

    @Column(name = "refund_first_date")
    private LocalDate refundFirstDate;

    @Column(name = "refund_decimal_number")
    private Integer refundDecimalNumber;

    @Column(name = "refund_last_date")
    private LocalDate refundLastDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Periodicity periodicity;

    @OneToMany(mappedBy = "rent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Schedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNominalValue() {
        return nominalValue;
    }

    public Rent nominalValue(BigDecimal nominalValue) {
        this.nominalValue = nominalValue;
        return this;
    }

    public void setNominalValue(BigDecimal nominalValue) {
        this.nominalValue = nominalValue;
    }

    public Double getTaux() {
        return taux;
    }

    public Rent taux(Double taux) {
        this.taux = taux;
        return this;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public String getMode() {
        return mode;
    }

    public Rent mode(String mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getCapital() {
        return capital;
    }

    public Rent capital(Integer capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Rent startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Rent endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public Rent refundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Boolean isStartExcluded() {
        return startExcluded;
    }

    public Rent startExcluded(Boolean startExcluded) {
        this.startExcluded = startExcluded;
        return this;
    }

    public void setStartExcluded(Boolean startExcluded) {
        this.startExcluded = startExcluded;
    }

    public Boolean isMonthEnd() {
        return monthEnd;
    }

    public Rent monthEnd(Boolean monthEnd) {
        this.monthEnd = monthEnd;
        return this;
    }

    public void setMonthEnd(Boolean monthEnd) {
        this.monthEnd = monthEnd;
    }

    public Boolean isEndExcluded() {
        return endExcluded;
    }

    public Rent endExcluded(Boolean endExcluded) {
        this.endExcluded = endExcluded;
        return this;
    }

    public void setEndExcluded(Boolean endExcluded) {
        this.endExcluded = endExcluded;
    }

    public Integer getRateValue() {
        return rateValue;
    }

    public Rent rateValue(Integer rateValue) {
        this.rateValue = rateValue;
        return this;
    }

    public void setRateValue(Integer rateValue) {
        this.rateValue = rateValue;
    }

    public Integer getCouponDecimalNumber() {
        return couponDecimalNumber;
    }

    public Rent couponDecimalNumber(Integer couponDecimalNumber) {
        this.couponDecimalNumber = couponDecimalNumber;
        return this;
    }

    public void setCouponDecimalNumber(Integer couponDecimalNumber) {
        this.couponDecimalNumber = couponDecimalNumber;
    }

    public LocalDate getCouponFirstDate() {
        return couponFirstDate;
    }

    public Rent couponFirstDate(LocalDate couponFirstDate) {
        this.couponFirstDate = couponFirstDate;
        return this;
    }

    public void setCouponFirstDate(LocalDate couponFirstDate) {
        this.couponFirstDate = couponFirstDate;
    }

    public LocalDate getCouponLastDate() {
        return couponLastDate;
    }

    public Rent couponLastDate(LocalDate couponLastDate) {
        this.couponLastDate = couponLastDate;
        return this;
    }

    public void setCouponLastDate(LocalDate couponLastDate) {
        this.couponLastDate = couponLastDate;
    }

    public LocalDate getRefundFirstDate() {
        return refundFirstDate;
    }

    public Rent refundFirstDate(LocalDate refundFirstDate) {
        this.refundFirstDate = refundFirstDate;
        return this;
    }

    public void setRefundFirstDate(LocalDate refundFirstDate) {
        this.refundFirstDate = refundFirstDate;
    }

    public Integer getRefundDecimalNumber() {
        return refundDecimalNumber;
    }

    public Rent refundDecimalNumber(Integer refundDecimalNumber) {
        this.refundDecimalNumber = refundDecimalNumber;
        return this;
    }

    public void setRefundDecimalNumber(Integer refundDecimalNumber) {
        this.refundDecimalNumber = refundDecimalNumber;
    }

    public LocalDate getRefundLastDate() {
        return refundLastDate;
    }

    public Rent refundLastDate(LocalDate refundLastDate) {
        this.refundLastDate = refundLastDate;
        return this;
    }

    public void setRefundLastDate(LocalDate refundLastDate) {
        this.refundLastDate = refundLastDate;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public Rent periodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
        return this;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Rent schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public Rent addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setRent(this);
        return this;
    }

    public Rent removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setRent(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rent)) {
            return false;
        }
        return id != null && id.equals(((Rent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rent{" +
            "id=" + getId() +
            ", nominalValue=" + getNominalValue() +
            ", taux=" + getTaux() +
            ", mode='" + getMode() + "'" +
            ", capital=" + getCapital() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", refundAmount=" + getRefundAmount() +
            ", startExcluded='" + isStartExcluded() + "'" +
            ", monthEnd='" + isMonthEnd() + "'" +
            ", endExcluded='" + isEndExcluded() + "'" +
            ", rateValue=" + getRateValue() +
            ", couponDecimalNumber=" + getCouponDecimalNumber() +
            ", couponFirstDate='" + getCouponFirstDate() + "'" +
            ", couponLastDate='" + getCouponLastDate() + "'" +
            ", refundFirstDate='" + getRefundFirstDate() + "'" +
            ", refundDecimalNumber=" + getRefundDecimalNumber() +
            ", refundLastDate='" + getRefundLastDate() + "'" +
            "}";
    }
}
