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
 * Criteria class for the {@link com.fininfo.java.domain.ClosingEvent} entity. This class is used
 * in {@link com.fininfo.java.web.rest.ClosingEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /closing-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClosingEventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LocalDateFilter closingDate;

    private LocalDateFilter endDate;

    public ClosingEventCriteria() {
    }

    public ClosingEventCriteria(ClosingEventCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.closingDate = other.closingDate == null ? null : other.closingDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
    }

    @Override
    public ClosingEventCriteria copy() {
        return new ClosingEventCriteria(this);
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

    public LocalDateFilter getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateFilter closingDate) {
        this.closingDate = closingDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClosingEventCriteria that = (ClosingEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(closingDate, that.closingDate) &&
            Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        closingDate,
        endDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClosingEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (closingDate != null ? "closingDate=" + closingDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
            "}";
    }

}
