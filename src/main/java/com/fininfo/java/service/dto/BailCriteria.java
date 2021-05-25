package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.fininfo.java.domain.enumeration.IEnumTypeBail;
import com.fininfo.java.domain.enumeration.Bailstatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.fininfo.java.domain.Bail} entity. This class is used
 * in {@link com.fininfo.java.web.rest.BailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BailCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IEnumTypeBail
     */
    public static class IEnumTypeBailFilter extends Filter<IEnumTypeBail> {

        public IEnumTypeBailFilter() {
        }

        public IEnumTypeBailFilter(IEnumTypeBailFilter filter) {
            super(filter);
        }

        @Override
        public IEnumTypeBailFilter copy() {
            return new IEnumTypeBailFilter(this);
        }

    }
    /**
     * Class for filtering Bailstatus
     */
    public static class BailstatusFilter extends Filter<Bailstatus> {

        public BailstatusFilter() {
        }

        public BailstatusFilter(BailstatusFilter filter) {
            super(filter);
        }

        @Override
        public BailstatusFilter copy() {
            return new BailstatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codeBail;

    private StringFilter durationBail;

    private IEnumTypeBailFilter typeBail;

    private LocalDateFilter signatureDate;

    private LocalDateFilter firstRentDate;

    private StringFilter destinationLocal;

    private LongFilter idOPCI;

    private BailstatusFilter status;

    private LongFilter locataireId;

    private LongFilter garantId;

    public BailCriteria() {
    }

    public BailCriteria(BailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codeBail = other.codeBail == null ? null : other.codeBail.copy();
        this.durationBail = other.durationBail == null ? null : other.durationBail.copy();
        this.typeBail = other.typeBail == null ? null : other.typeBail.copy();
        this.signatureDate = other.signatureDate == null ? null : other.signatureDate.copy();
        this.firstRentDate = other.firstRentDate == null ? null : other.firstRentDate.copy();
        this.destinationLocal = other.destinationLocal == null ? null : other.destinationLocal.copy();
        this.idOPCI = other.idOPCI == null ? null : other.idOPCI.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.locataireId = other.locataireId == null ? null : other.locataireId.copy();
        this.garantId = other.garantId == null ? null : other.garantId.copy();
    }

    @Override
    public BailCriteria copy() {
        return new BailCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodeBail() {
        return codeBail;
    }

    public void setCodeBail(StringFilter codeBail) {
        this.codeBail = codeBail;
    }

    public StringFilter getDurationBail() {
        return durationBail;
    }

    public void setDurationBail(StringFilter durationBail) {
        this.durationBail = durationBail;
    }

    public IEnumTypeBailFilter getTypeBail() {
        return typeBail;
    }

    public void setTypeBail(IEnumTypeBailFilter typeBail) {
        this.typeBail = typeBail;
    }

    public LocalDateFilter getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(LocalDateFilter signatureDate) {
        this.signatureDate = signatureDate;
    }

    public LocalDateFilter getFirstRentDate() {
        return firstRentDate;
    }

    public void setFirstRentDate(LocalDateFilter firstRentDate) {
        this.firstRentDate = firstRentDate;
    }

    public StringFilter getDestinationLocal() {
        return destinationLocal;
    }

    public void setDestinationLocal(StringFilter destinationLocal) {
        this.destinationLocal = destinationLocal;
    }

    public LongFilter getIdOPCI() {
        return idOPCI;
    }

    public void setIdOPCI(LongFilter idOPCI) {
        this.idOPCI = idOPCI;
    }

    public BailstatusFilter getStatus() {
        return status;
    }

    public void setStatus(BailstatusFilter status) {
        this.status = status;
    }

    public LongFilter getLocataireId() {
        return locataireId;
    }

    public void setLocataireId(LongFilter locataireId) {
        this.locataireId = locataireId;
    }

    public LongFilter getGarantId() {
        return garantId;
    }

    public void setGarantId(LongFilter garantId) {
        this.garantId = garantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BailCriteria that = (BailCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codeBail, that.codeBail) &&
            Objects.equals(durationBail, that.durationBail) &&
            Objects.equals(typeBail, that.typeBail) &&
            Objects.equals(signatureDate, that.signatureDate) &&
            Objects.equals(firstRentDate, that.firstRentDate) &&
            Objects.equals(destinationLocal, that.destinationLocal) &&
            Objects.equals(idOPCI, that.idOPCI) &&
            Objects.equals(status, that.status) &&
            Objects.equals(locataireId, that.locataireId) &&
            Objects.equals(garantId, that.garantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codeBail,
        durationBail,
        typeBail,
        signatureDate,
        firstRentDate,
        destinationLocal,
        idOPCI,
        status,
        locataireId,
        garantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BailCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codeBail != null ? "codeBail=" + codeBail + ", " : "") +
                (durationBail != null ? "durationBail=" + durationBail + ", " : "") +
                (typeBail != null ? "typeBail=" + typeBail + ", " : "") +
                (signatureDate != null ? "signatureDate=" + signatureDate + ", " : "") +
                (firstRentDate != null ? "firstRentDate=" + firstRentDate + ", " : "") +
                (destinationLocal != null ? "destinationLocal=" + destinationLocal + ", " : "") +
                (idOPCI != null ? "idOPCI=" + idOPCI + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (locataireId != null ? "locataireId=" + locataireId + ", " : "") +
                (garantId != null ? "garantId=" + garantId + ", " : "") +
            "}";
    }

}
