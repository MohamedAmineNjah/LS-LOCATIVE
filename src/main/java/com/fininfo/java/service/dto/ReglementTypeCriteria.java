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
 * Criteria class for the {@link com.fininfo.java.domain.ReglementType} entity. This class is used
 * in {@link com.fininfo.java.web.rest.ReglementTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reglement-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReglementTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private LongFilter locationRegulationId;

    public ReglementTypeCriteria() {
    }

    public ReglementTypeCriteria(ReglementTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.locationRegulationId = other.locationRegulationId == null ? null : other.locationRegulationId.copy();
    }

    @Override
    public ReglementTypeCriteria copy() {
        return new ReglementTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final ReglementTypeCriteria that = (ReglementTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(locationRegulationId, that.locationRegulationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        locationRegulationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReglementTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (locationRegulationId != null ? "locationRegulationId=" + locationRegulationId + ", " : "") +
            "}";
    }

}
