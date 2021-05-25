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
 * Criteria class for the {@link com.fininfo.java.domain.Garant} entity. This class is used
 * in {@link com.fininfo.java.web.rest.GarantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /garants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GarantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter birthDate;

    private StringFilter profession;

    private IntegerFilter nAllocataireCAF;

    public GarantCriteria() {
    }

    public GarantCriteria(GarantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.profession = other.profession == null ? null : other.profession.copy();
        this.nAllocataireCAF = other.nAllocataireCAF == null ? null : other.nAllocataireCAF.copy();
    }

    @Override
    public GarantCriteria copy() {
        return new GarantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateFilter birthDate) {
        this.birthDate = birthDate;
    }

    public StringFilter getProfession() {
        return profession;
    }

    public void setProfession(StringFilter profession) {
        this.profession = profession;
    }

    public IntegerFilter getnAllocataireCAF() {
        return nAllocataireCAF;
    }

    public void setnAllocataireCAF(IntegerFilter nAllocataireCAF) {
        this.nAllocataireCAF = nAllocataireCAF;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GarantCriteria that = (GarantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(profession, that.profession) &&
            Objects.equals(nAllocataireCAF, that.nAllocataireCAF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        birthDate,
        profession,
        nAllocataireCAF
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GarantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
                (profession != null ? "profession=" + profession + ", " : "") +
                (nAllocataireCAF != null ? "nAllocataireCAF=" + nAllocataireCAF + ", " : "") +
            "}";
    }

}
