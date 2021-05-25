package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.fininfo.java.domain.Charge} entity.
 */
public class ChargeDTO implements Serializable {
    
    private Long id;

    private String statusCharge;

    private LocalDate chargeDate;

    private String designation;

    private String reference;

    private BigDecimal amountCharge;


    private Long lotBailId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusCharge() {
        return statusCharge;
    }

    public void setStatusCharge(String statusCharge) {
        this.statusCharge = statusCharge;
    }

    public LocalDate getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(LocalDate chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmountCharge() {
        return amountCharge;
    }

    public void setAmountCharge(BigDecimal amountCharge) {
        this.amountCharge = amountCharge;
    }

    public Long getLotBailId() {
        return lotBailId;
    }

    public void setLotBailId(Long lotBailId) {
        this.lotBailId = lotBailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargeDTO)) {
            return false;
        }

        return id != null && id.equals(((ChargeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargeDTO{" +
            "id=" + getId() +
            ", statusCharge='" + getStatusCharge() + "'" +
            ", chargeDate='" + getChargeDate() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", reference='" + getReference() + "'" +
            ", amountCharge=" + getAmountCharge() +
            ", lotBailId=" + getLotBailId() +
            "}";
    }
}
