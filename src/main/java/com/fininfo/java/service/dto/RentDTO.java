package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.fininfo.java.domain.Rent} entity.
 */
public class RentDTO implements Serializable {
    
    private Long id;

    private BigDecimal nominalValue;

    private Double taux;

    private String mode;

    private Integer capital;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer refundAmount;

    private Boolean startExcluded;

    private Boolean monthEnd;

    private Boolean endExcluded;

    private Integer rateValue;

    private Integer couponDecimalNumber;

    private LocalDate couponFirstDate;

    private LocalDate couponLastDate;

    private LocalDate refundFirstDate;

    private Integer refundDecimalNumber;

    private LocalDate refundLastDate;


    private Long periodicityId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(BigDecimal nominalValue) {
        this.nominalValue = nominalValue;
    }

    public Double getTaux() {
        return taux;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Boolean isStartExcluded() {
        return startExcluded;
    }

    public void setStartExcluded(Boolean startExcluded) {
        this.startExcluded = startExcluded;
    }

    public Boolean isMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(Boolean monthEnd) {
        this.monthEnd = monthEnd;
    }

    public Boolean isEndExcluded() {
        return endExcluded;
    }

    public void setEndExcluded(Boolean endExcluded) {
        this.endExcluded = endExcluded;
    }

    public Integer getRateValue() {
        return rateValue;
    }

    public void setRateValue(Integer rateValue) {
        this.rateValue = rateValue;
    }

    public Integer getCouponDecimalNumber() {
        return couponDecimalNumber;
    }

    public void setCouponDecimalNumber(Integer couponDecimalNumber) {
        this.couponDecimalNumber = couponDecimalNumber;
    }

    public LocalDate getCouponFirstDate() {
        return couponFirstDate;
    }

    public void setCouponFirstDate(LocalDate couponFirstDate) {
        this.couponFirstDate = couponFirstDate;
    }

    public LocalDate getCouponLastDate() {
        return couponLastDate;
    }

    public void setCouponLastDate(LocalDate couponLastDate) {
        this.couponLastDate = couponLastDate;
    }

    public LocalDate getRefundFirstDate() {
        return refundFirstDate;
    }

    public void setRefundFirstDate(LocalDate refundFirstDate) {
        this.refundFirstDate = refundFirstDate;
    }

    public Integer getRefundDecimalNumber() {
        return refundDecimalNumber;
    }

    public void setRefundDecimalNumber(Integer refundDecimalNumber) {
        this.refundDecimalNumber = refundDecimalNumber;
    }

    public LocalDate getRefundLastDate() {
        return refundLastDate;
    }

    public void setRefundLastDate(LocalDate refundLastDate) {
        this.refundLastDate = refundLastDate;
    }

    public Long getPeriodicityId() {
        return periodicityId;
    }

    public void setPeriodicityId(Long periodicityId) {
        this.periodicityId = periodicityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentDTO)) {
            return false;
        }

        return id != null && id.equals(((RentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentDTO{" +
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
            ", periodicityId=" + getPeriodicityId() +
            "}";
    }
}
