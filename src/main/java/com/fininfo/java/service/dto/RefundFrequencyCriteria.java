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

/**
 * Criteria class for the {@link com.fininfo.java.domain.RefundFrequency} entity. This class is used
 * in {@link com.fininfo.java.web.rest.RefundFrequencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /refund-frequencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RefundFrequencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter rentId;

    public RefundFrequencyCriteria() {
    }

    public RefundFrequencyCriteria(RefundFrequencyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rentId = other.rentId == null ? null : other.rentId.copy();
    }

    @Override
    public RefundFrequencyCriteria copy() {
        return new RefundFrequencyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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
        final RefundFrequencyCriteria that = (RefundFrequencyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rentId, that.rentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefundFrequencyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rentId != null ? "rentId=" + rentId + ", " : "") +
            "}";
    }

}
