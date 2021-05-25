package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import com.fininfo.java.domain.enumeration.IEnumMethode;
import com.fininfo.java.domain.enumeration.IEnumStatus;

/**
 * A DTO for the {@link com.fininfo.java.domain.Bill} entity.
 */
public class BillDTO implements Serializable {
    
    private Long id;

    private String reference;

    private BigDecimal amountExludingTax;

    private BigDecimal tva;

    private BigDecimal ttc;

    private LocalDate billDate;

    private IEnumMethode reglementMethod;

    private IEnumStatus billStatus;


    private Long scheduleId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmountExludingTax() {
        return amountExludingTax;
    }

    public void setAmountExludingTax(BigDecimal amountExludingTax) {
        this.amountExludingTax = amountExludingTax;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getTtc() {
        return ttc;
    }

    public void setTtc(BigDecimal ttc) {
        this.ttc = ttc;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public IEnumMethode getReglementMethod() {
        return reglementMethod;
    }

    public void setReglementMethod(IEnumMethode reglementMethod) {
        this.reglementMethod = reglementMethod;
    }

    public IEnumStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(IEnumStatus billStatus) {
        this.billStatus = billStatus;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillDTO)) {
            return false;
        }

        return id != null && id.equals(((BillDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", amountExludingTax=" + getAmountExludingTax() +
            ", tva=" + getTva() +
            ", ttc=" + getTtc() +
            ", billDate='" + getBillDate() + "'" +
            ", reglementMethod='" + getReglementMethod() + "'" +
            ", billStatus='" + getBillStatus() + "'" +
            ", scheduleId=" + getScheduleId() +
            "}";
    }
}
