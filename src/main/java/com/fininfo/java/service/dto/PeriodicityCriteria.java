package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.fininfo.java.domain.enumeration.IEnumperiodicityType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.fininfo.java.domain.Periodicity} entity. This class is used
 * in {@link com.fininfo.java.web.rest.PeriodicityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /periodicities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PeriodicityCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IEnumperiodicityType
     */
    public static class IEnumperiodicityTypeFilter extends Filter<IEnumperiodicityType> {

        public IEnumperiodicityTypeFilter() {
        }

        public IEnumperiodicityTypeFilter(IEnumperiodicityTypeFilter filter) {
            super(filter);
        }

        @Override
        public IEnumperiodicityTypeFilter copy() {
            return new IEnumperiodicityTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IEnumperiodicityTypeFilter periodicityType;

    private BigDecimalFilter value;

    public PeriodicityCriteria() {
    }

    public PeriodicityCriteria(PeriodicityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.periodicityType = other.periodicityType == null ? null : other.periodicityType.copy();
        this.value = other.value == null ? null : other.value.copy();
    }

    @Override
    public PeriodicityCriteria copy() {
        return new PeriodicityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IEnumperiodicityTypeFilter getPeriodicityType() {
        return periodicityType;
    }

    public void setPeriodicityType(IEnumperiodicityTypeFilter periodicityType) {
        this.periodicityType = periodicityType;
    }

    public BigDecimalFilter getValue() {
        return value;
    }

    public void setValue(BigDecimalFilter value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PeriodicityCriteria that = (PeriodicityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(periodicityType, that.periodicityType) &&
            Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        periodicityType,
        value
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodicityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (periodicityType != null ? "periodicityType=" + periodicityType + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
            "}";
    }

}
