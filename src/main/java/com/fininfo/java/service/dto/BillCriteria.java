package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.fininfo.java.domain.enumeration.IEnumMethode;
import com.fininfo.java.domain.enumeration.IEnumStatus;
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
 * Criteria class for the {@link com.fininfo.java.domain.Bill} entity. This class is used
 * in {@link com.fininfo.java.web.rest.BillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BillCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IEnumMethode
     */
    public static class IEnumMethodeFilter extends Filter<IEnumMethode> {

        public IEnumMethodeFilter() {
        }

        public IEnumMethodeFilter(IEnumMethodeFilter filter) {
            super(filter);
        }

        @Override
        public IEnumMethodeFilter copy() {
            return new IEnumMethodeFilter(this);
        }

    }
    /**
     * Class for filtering IEnumStatus
     */
    public static class IEnumStatusFilter extends Filter<IEnumStatus> {

        public IEnumStatusFilter() {
        }

        public IEnumStatusFilter(IEnumStatusFilter filter) {
            super(filter);
        }

        @Override
        public IEnumStatusFilter copy() {
            return new IEnumStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reference;

    private BigDecimalFilter amountExludingTax;

    private BigDecimalFilter tva;

    private BigDecimalFilter ttc;

    private LocalDateFilter billDate;

    private IEnumMethodeFilter reglementMethod;

    private IEnumStatusFilter billStatus;

    private LongFilter scheduleId;

    public BillCriteria() {
    }

    public BillCriteria(BillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.amountExludingTax = other.amountExludingTax == null ? null : other.amountExludingTax.copy();
        this.tva = other.tva == null ? null : other.tva.copy();
        this.ttc = other.ttc == null ? null : other.ttc.copy();
        this.billDate = other.billDate == null ? null : other.billDate.copy();
        this.reglementMethod = other.reglementMethod == null ? null : other.reglementMethod.copy();
        this.billStatus = other.billStatus == null ? null : other.billStatus.copy();
        this.scheduleId = other.scheduleId == null ? null : other.scheduleId.copy();
    }

    @Override
    public BillCriteria copy() {
        return new BillCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public BigDecimalFilter getAmountExludingTax() {
        return amountExludingTax;
    }

    public void setAmountExludingTax(BigDecimalFilter amountExludingTax) {
        this.amountExludingTax = amountExludingTax;
    }

    public BigDecimalFilter getTva() {
        return tva;
    }

    public void setTva(BigDecimalFilter tva) {
        this.tva = tva;
    }

    public BigDecimalFilter getTtc() {
        return ttc;
    }

    public void setTtc(BigDecimalFilter ttc) {
        this.ttc = ttc;
    }

    public LocalDateFilter getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateFilter billDate) {
        this.billDate = billDate;
    }

    public IEnumMethodeFilter getReglementMethod() {
        return reglementMethod;
    }

    public void setReglementMethod(IEnumMethodeFilter reglementMethod) {
        this.reglementMethod = reglementMethod;
    }

    public IEnumStatusFilter getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(IEnumStatusFilter billStatus) {
        this.billStatus = billStatus;
    }

    public LongFilter getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(LongFilter scheduleId) {
        this.scheduleId = scheduleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BillCriteria that = (BillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(amountExludingTax, that.amountExludingTax) &&
            Objects.equals(tva, that.tva) &&
            Objects.equals(ttc, that.ttc) &&
            Objects.equals(billDate, that.billDate) &&
            Objects.equals(reglementMethod, that.reglementMethod) &&
            Objects.equals(billStatus, that.billStatus) &&
            Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        reference,
        amountExludingTax,
        tva,
        ttc,
        billDate,
        reglementMethod,
        billStatus,
        scheduleId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (amountExludingTax != null ? "amountExludingTax=" + amountExludingTax + ", " : "") +
                (tva != null ? "tva=" + tva + ", " : "") +
                (ttc != null ? "ttc=" + ttc + ", " : "") +
                (billDate != null ? "billDate=" + billDate + ", " : "") +
                (reglementMethod != null ? "reglementMethod=" + reglementMethod + ", " : "") +
                (billStatus != null ? "billStatus=" + billStatus + ", " : "") +
                (scheduleId != null ? "scheduleId=" + scheduleId + ", " : "") +
            "}";
    }

}
