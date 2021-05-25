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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.fininfo.java.domain.LocationEvent} entity. This class is used
 * in {@link com.fininfo.java.web.rest.LocationEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /location-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocationEventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private DoubleFilter costPrice;

    private LocalDateFilter deadline;

    private IntegerFilter assetCode;

    private LongFilter locationRegulationId;

    public LocationEventCriteria() {
    }

    public LocationEventCriteria(LocationEventCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.costPrice = other.costPrice == null ? null : other.costPrice.copy();
        this.deadline = other.deadline == null ? null : other.deadline.copy();
        this.assetCode = other.assetCode == null ? null : other.assetCode.copy();
        this.locationRegulationId = other.locationRegulationId == null ? null : other.locationRegulationId.copy();
    }

    @Override
    public LocationEventCriteria copy() {
        return new LocationEventCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public DoubleFilter getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(DoubleFilter costPrice) {
        this.costPrice = costPrice;
    }

    public LocalDateFilter getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateFilter deadline) {
        this.deadline = deadline;
    }

    public IntegerFilter getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(IntegerFilter assetCode) {
        this.assetCode = assetCode;
    }

    public LongFilter getLocationRegulationId() {
        return locationRegulationId;
    }

    public void setLocationRegulationId(LongFilter locationRegulationId) {
        this.locationRegulationId = locationRegulationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationEventCriteria that = (LocationEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(costPrice, that.costPrice) &&
            Objects.equals(deadline, that.deadline) &&
            Objects.equals(assetCode, that.assetCode) &&
            Objects.equals(locationRegulationId, that.locationRegulationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        startDate,
        endDate,
        costPrice,
        deadline,
        assetCode,
        locationRegulationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (costPrice != null ? "costPrice=" + costPrice + ", " : "") +
                (deadline != null ? "deadline=" + deadline + ", " : "") +
                (assetCode != null ? "assetCode=" + assetCode + ", " : "") +
                (locationRegulationId != null ? "locationRegulationId=" + locationRegulationId + ", " : "") +
            "}";
    }

}
