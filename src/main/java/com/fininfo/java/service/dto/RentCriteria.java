package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.fininfo.java.domain.Rent} entity. This class is used
 * in {@link com.fininfo.java.web.rest.RentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter nominalValue;

    private DoubleFilter taux;

    private StringFilter mode;

    private IntegerFilter capital;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private IntegerFilter refundAmount;

    private BooleanFilter startExcluded;

    private BooleanFilter monthEnd;

    private BooleanFilter endExcluded;

    private IntegerFilter rateValue;

    private IntegerFilter couponDecimalNumber;

    private LocalDateFilter couponFirstDate;

    private LocalDateFilter couponLastDate;

    private LocalDateFilter refundFirstDate;

    private IntegerFilter refundDecimalNumber;

    private LocalDateFilter refundLastDate;

    private LongFilter periodicityId;

    private LongFilter scheduleId;

    public RentCriteria() {
    }

    public RentCriteria(RentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nominalValue = other.nominalValue == null ? null : other.nominalValue.copy();
        this.taux = other.taux == null ? null : other.taux.copy();
        this.mode = other.mode == null ? null : other.mode.copy();
        this.capital = other.capital == null ? null : other.capital.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.refundAmount = other.refundAmount == null ? null : other.refundAmount.copy();
        this.startExcluded = other.startExcluded == null ? null : other.startExcluded.copy();
        this.monthEnd = other.monthEnd == null ? null : other.monthEnd.copy();
        this.endExcluded = other.endExcluded == null ? null : other.endExcluded.copy();
        this.rateValue = other.rateValue == null ? null : other.rateValue.copy();
        this.couponDecimalNumber = other.couponDecimalNumber == null ? null : other.couponDecimalNumber.copy();
        this.couponFirstDate = other.couponFirstDate == null ? null : other.couponFirstDate.copy();
        this.couponLastDate = other.couponLastDate == null ? null : other.couponLastDate.copy();
        this.refundFirstDate = other.refundFirstDate == null ? null : other.refundFirstDate.copy();
        this.refundDecimalNumber = other.refundDecimalNumber == null ? null : other.refundDecimalNumber.copy();
        this.refundLastDate = other.refundLastDate == null ? null : other.refundLastDate.copy();
        this.periodicityId = other.periodicityId == null ? null : other.periodicityId.copy();
        this.scheduleId = other.scheduleId == null ? null : other.scheduleId.copy();
    }

    @Override
    public RentCriteria copy() {
        return new RentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(BigDecimalFilter nominalValue) {
        this.nominalValue = nominalValue;
    }

    public DoubleFilter getTaux() {
        return taux;
    }

    public void setTaux(DoubleFilter taux) {
        this.taux = taux;
    }

    public StringFilter getMode() {
        return mode;
    }

    public void setMode(StringFilter mode) {
        this.mode = mode;
    }

    public IntegerFilter getCapital() {
        return capital;
    }

    public void setCapital(IntegerFilter capital) {
        this.capital = capital;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public IntegerFilter getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(IntegerFilter refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BooleanFilter getStartExcluded() {
        return startExcluded;
    }

    public void setStartExcluded(BooleanFilter startExcluded) {
        this.startExcluded = startExcluded;
    }

    public BooleanFilter getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(BooleanFilter monthEnd) {
        this.monthEnd = monthEnd;
    }

    public BooleanFilter getEndExcluded() {
        return endExcluded;
    }

    public void setEndExcluded(BooleanFilter endExcluded) {
        this.endExcluded = endExcluded;
    }

    public IntegerFilter getRateValue() {
        return rateValue;
    }

    public void setRateValue(IntegerFilter rateValue) {
        this.rateValue = rateValue;
    }

    public IntegerFilter getCouponDecimalNumber() {
        return couponDecimalNumber;
    }

    public void setCouponDecimalNumber(IntegerFilter couponDecimalNumber) {
        this.couponDecimalNumber = couponDecimalNumber;
    }

    public LocalDateFilter getCouponFirstDate() {
        return couponFirstDate;
    }

    public void setCouponFirstDate(LocalDateFilter couponFirstDate) {
        this.couponFirstDate = couponFirstDate;
    }

    public LocalDateFilter getCouponLastDate() {
        return couponLastDate;
    }

    public void setCouponLastDate(LocalDateFilter couponLastDate) {
        this.couponLastDate = couponLastDate;
    }

    public LocalDateFilter getRefundFirstDate() {
        return refundFirstDate;
    }

    public void setRefundFirstDate(LocalDateFilter refundFirstDate) {
        this.refundFirstDate = refundFirstDate;
    }

    public IntegerFilter getRefundDecimalNumber() {
        return refundDecimalNumber;
    }

    public void setRefundDecimalNumber(IntegerFilter refundDecimalNumber) {
        this.refundDecimalNumber = refundDecimalNumber;
    }

    public LocalDateFilter getRefundLastDate() {
        return refundLastDate;
    }

    public void setRefundLastDate(LocalDateFilter refundLastDate) {
        this.refundLastDate = refundLastDate;
    }

    public LongFilter getPeriodicityId() {
        return periodicityId;
    }

    public void setPeriodicityId(LongFilter periodicityId) {
        this.periodicityId = periodicityId;
    }

    public LongFilter getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(LongFilter scheduleId) {
        this.scheduleId = scheduleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RentCriteria that = (RentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nominalValue, that.nominalValue) &&
            Objects.equals(taux, that.taux) &&
            Objects.equals(mode, that.mode) &&
            Objects.equals(capital, that.capital) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(refundAmount, that.refundAmount) &&
            Objects.equals(startExcluded, that.startExcluded) &&
            Objects.equals(monthEnd, that.monthEnd) &&
            Objects.equals(endExcluded, that.endExcluded) &&
            Objects.equals(rateValue, that.rateValue) &&
            Objects.equals(couponDecimalNumber, that.couponDecimalNumber) &&
            Objects.equals(couponFirstDate, that.couponFirstDate) &&
            Objects.equals(couponLastDate, that.couponLastDate) &&
            Objects.equals(refundFirstDate, that.refundFirstDate) &&
            Objects.equals(refundDecimalNumber, that.refundDecimalNumber) &&
            Objects.equals(refundLastDate, that.refundLastDate) &&
            Objects.equals(periodicityId, that.periodicityId) &&
            Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nominalValue,
        taux,
        mode,
        capital,
        startDate,
        endDate,
        refundAmount,
        startExcluded,
        monthEnd,
        endExcluded,
        rateValue,
        couponDecimalNumber,
        couponFirstDate,
        couponLastDate,
        refundFirstDate,
        refundDecimalNumber,
        refundLastDate,
        periodicityId,
        scheduleId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nominalValue != null ? "nominalValue=" + nominalValue + ", " : "") +
                (taux != null ? "taux=" + taux + ", " : "") +
                (mode != null ? "mode=" + mode + ", " : "") +
                (capital != null ? "capital=" + capital + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (refundAmount != null ? "refundAmount=" + refundAmount + ", " : "") +
                (startExcluded != null ? "startExcluded=" + startExcluded + ", " : "") +
                (monthEnd != null ? "monthEnd=" + monthEnd + ", " : "") +
                (endExcluded != null ? "endExcluded=" + endExcluded + ", " : "") +
                (rateValue != null ? "rateValue=" + rateValue + ", " : "") +
                (couponDecimalNumber != null ? "couponDecimalNumber=" + couponDecimalNumber + ", " : "") +
                (couponFirstDate != null ? "couponFirstDate=" + couponFirstDate + ", " : "") +
                (couponLastDate != null ? "couponLastDate=" + couponLastDate + ", " : "") +
                (refundFirstDate != null ? "refundFirstDate=" + refundFirstDate + ", " : "") +
                (refundDecimalNumber != null ? "refundDecimalNumber=" + refundDecimalNumber + ", " : "") +
                (refundLastDate != null ? "refundLastDate=" + refundLastDate + ", " : "") +
                (periodicityId != null ? "periodicityId=" + periodicityId + ", " : "") +
                (scheduleId != null ? "scheduleId=" + scheduleId + ", " : "") +
            "}";
    }

}
