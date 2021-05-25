package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.fininfo.java.domain.enumeration.IEnumTitle;
import com.fininfo.java.domain.enumeration.IEnumUnity;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.fininfo.java.domain.Counter} entity. This class is used
 * in {@link com.fininfo.java.web.rest.CounterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /counters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CounterCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IEnumTitle
     */
    public static class IEnumTitleFilter extends Filter<IEnumTitle> {

        public IEnumTitleFilter() {
        }

        public IEnumTitleFilter(IEnumTitleFilter filter) {
            super(filter);
        }

        @Override
        public IEnumTitleFilter copy() {
            return new IEnumTitleFilter(this);
        }

    }
    /**
     * Class for filtering IEnumUnity
     */
    public static class IEnumUnityFilter extends Filter<IEnumUnity> {

        public IEnumUnityFilter() {
        }

        public IEnumUnityFilter(IEnumUnityFilter filter) {
            super(filter);
        }

        @Override
        public IEnumUnityFilter copy() {
            return new IEnumUnityFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IEnumTitleFilter counterTitle;

    private IEnumUnityFilter unity;

    private IntegerFilter decimal;

    private StringFilter comment;

    private LongFilter lotBailId;

    public CounterCriteria() {
    }

    public CounterCriteria(CounterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.counterTitle = other.counterTitle == null ? null : other.counterTitle.copy();
        this.unity = other.unity == null ? null : other.unity.copy();
        this.decimal = other.decimal == null ? null : other.decimal.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.lotBailId = other.lotBailId == null ? null : other.lotBailId.copy();
    }

    @Override
    public CounterCriteria copy() {
        return new CounterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IEnumTitleFilter getCounterTitle() {
        return counterTitle;
    }

    public void setCounterTitle(IEnumTitleFilter counterTitle) {
        this.counterTitle = counterTitle;
    }

    public IEnumUnityFilter getUnity() {
        return unity;
    }

    public void setUnity(IEnumUnityFilter unity) {
        this.unity = unity;
    }

    public IntegerFilter getDecimal() {
        return decimal;
    }

    public void setDecimal(IntegerFilter decimal) {
        this.decimal = decimal;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
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
        final CounterCriteria that = (CounterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(counterTitle, that.counterTitle) &&
            Objects.equals(unity, that.unity) &&
            Objects.equals(decimal, that.decimal) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(lotBailId, that.lotBailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        counterTitle,
        unity,
        decimal,
        comment,
        lotBailId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CounterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (counterTitle != null ? "counterTitle=" + counterTitle + ", " : "") +
                (unity != null ? "unity=" + unity + ", " : "") +
                (decimal != null ? "decimal=" + decimal + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (lotBailId != null ? "lotBailId=" + lotBailId + ", " : "") +
            "}";
    }

}
