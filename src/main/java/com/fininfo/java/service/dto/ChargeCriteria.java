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
 * Criteria class for the {@link com.fininfo.java.domain.Charge} entity. This class is used
 * in {@link com.fininfo.java.web.rest.ChargeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /charges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChargeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter statusCharge;

    private LocalDateFilter chargeDate;

    private StringFilter designation;

    private StringFilter reference;

    private BigDecimalFilter amountCharge;

    private LongFilter lotBailId;

    public ChargeCriteria() {
    }

    public ChargeCriteria(ChargeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.statusCharge = other.statusCharge == null ? null : other.statusCharge.copy();
        this.chargeDate = other.chargeDate == null ? null : other.chargeDate.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.amountCharge = other.amountCharge == null ? null : other.amountCharge.copy();
        this.lotBailId = other.lotBailId == null ? null : other.lotBailId.copy();
    }

    @Override
    public ChargeCriteria copy() {
        return new ChargeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStatusCharge() {
        return statusCharge;
    }

    public void setStatusCharge(StringFilter statusCharge) {
        this.statusCharge = statusCharge;
    }

    public LocalDateFilter getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(LocalDateFilter chargeDate) {
        this.chargeDate = chargeDate;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public BigDecimalFilter getAmountCharge() {
        return amountCharge;
    }

    public void setAmountCharge(BigDecimalFilter amountCharge) {
        this.amountCharge = amountCharge;
    }

    public LongFilter getLotBailId() {
        return lotBailId;
    }

    public void setLotBailId(LongFilter lotBailId) {
        this.lotBailId = lotBailId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChargeCriteria that = (ChargeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(statusCharge, that.statusCharge) &&
            Objects.equals(chargeDate, that.chargeDate) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(amountCharge, that.amountCharge) &&
            Objects.equals(lotBailId, that.lotBailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        statusCharge,
        chargeDate,
        designation,
        reference,
        amountCharge,
        lotBailId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (statusCharge != null ? "statusCharge=" + statusCharge + ", " : "") +
                (chargeDate != null ? "chargeDate=" + chargeDate + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (amountCharge != null ? "amountCharge=" + amountCharge + ", " : "") +
                (lotBailId != null ? "lotBailId=" + lotBailId + ", " : "") +
            "}";
    }

}
