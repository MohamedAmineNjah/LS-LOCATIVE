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

/**
 * Criteria class for the {@link com.fininfo.java.domain.Schedule} entity. This class is used
 * in {@link com.fininfo.java.web.rest.ScheduleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /schedules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScheduleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter amountSchedule;

    private LongFilter billId;

    private LongFilter lotBailId;

    private LongFilter rentId;

    public ScheduleCriteria() {
    }

    public ScheduleCriteria(ScheduleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amountSchedule = other.amountSchedule == null ? null : other.amountSchedule.copy();
        this.billId = other.billId == null ? null : other.billId.copy();
        this.lotBailId = other.lotBailId == null ? null : other.lotBailId.copy();
        this.rentId = other.rentId == null ? null : other.rentId.copy();
    }

    @Override
    public ScheduleCriteria copy() {
        return new ScheduleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getAmountSchedule() {
        return amountSchedule;
    }

    public void setAmountSchedule(BigDecimalFilter amountSchedule) {
        this.amountSchedule = amountSchedule;
    }

    public LongFilter getBillId() {
        return billId;
    }

    public void setBillId(LongFilter billId) {
        this.billId = billId;
    }

    public LongFilter getLotBailId() {
        return lotBailId;
    }

    public void setLotBailId(LongFilter lotBailId) {
        this.lotBailId = lotBailId;
    }

    public LongFilter getRentId() {
        return rentId;
    }

    public void setRentId(LongFilter rentId) {
        this.rentId = rentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScheduleCriteria that = (ScheduleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amountSchedule, that.amountSchedule) &&
            Objects.equals(billId, that.billId) &&
            Objects.equals(lotBailId, that.lotBailId) &&
            Objects.equals(rentId, that.rentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amountSchedule,
        billId,
        lotBailId,
        rentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amountSchedule != null ? "amountSchedule=" + amountSchedule + ", " : "") +
                (billId != null ? "billId=" + billId + ", " : "") +
                (lotBailId != null ? "lotBailId=" + lotBailId + ", " : "") +
                (rentId != null ? "rentId=" + rentId + ", " : "") +
            "}";
    }

}
