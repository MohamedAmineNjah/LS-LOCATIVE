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
 * Criteria class for the {@link com.fininfo.java.domain.LocationRegulation} entity. This class is used
 * in {@link com.fininfo.java.web.rest.LocationRegulationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /location-regulations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocationRegulationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter regulationDate;

    private BigDecimalFilter amount;

    private LongFilter reglementTypeId;

    public LocationRegulationCriteria() {
    }

    public LocationRegulationCriteria(LocationRegulationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.regulationDate = other.regulationDate == null ? null : other.regulationDate.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.reglementTypeId = other.reglementTypeId == null ? null : other.reglementTypeId.copy();
    }

    @Override
    public LocationRegulationCriteria copy() {
        return new LocationRegulationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getRegulationDate() {
        return regulationDate;
    }

    public void setRegulationDate(LocalDateFilter regulationDate) {
        this.regulationDate = regulationDate;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public LongFilter getReglementTypeId() {
        return reglementTypeId;
    }

    public void setReglementTypeId(LongFilter reglementTypeId) {
        this.reglementTypeId = reglementTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationRegulationCriteria that = (LocationRegulationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(regulationDate, that.regulationDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(reglementTypeId, that.reglementTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        regulationDate,
        amount,
        reglementTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationRegulationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (regulationDate != null ? "regulationDate=" + regulationDate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (reglementTypeId != null ? "reglementTypeId=" + reglementTypeId + ", " : "") +
            "}";
    }

}
