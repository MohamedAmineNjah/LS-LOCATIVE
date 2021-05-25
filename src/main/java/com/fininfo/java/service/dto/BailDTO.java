package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import com.fininfo.java.domain.enumeration.IEnumTypeBail;
import com.fininfo.java.domain.enumeration.Bailstatus;

/**
 * A DTO for the {@link com.fininfo.java.domain.Bail} entity.
 */
public class BailDTO implements Serializable {
    
    private Long id;

    private String codeBail;

    private String durationBail;

    private IEnumTypeBail typeBail;

    private LocalDate signatureDate;

    private LocalDate firstRentDate;

    private String destinationLocal;

    private Long idOPCI;

    private Bailstatus status;


    private Long locataireId;

    private Long garantId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeBail() {
        return codeBail;
    }

    public void setCodeBail(String codeBail) {
        this.codeBail = codeBail;
    }

    public String getDurationBail() {
        return durationBail;
    }

    public void setDurationBail(String durationBail) {
        this.durationBail = durationBail;
    }

    public IEnumTypeBail getTypeBail() {
        return typeBail;
    }

    public void setTypeBail(IEnumTypeBail typeBail) {
        this.typeBail = typeBail;
    }

    public LocalDate getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }

    public LocalDate getFirstRentDate() {
        return firstRentDate;
    }

    public void setFirstRentDate(LocalDate firstRentDate) {
        this.firstRentDate = firstRentDate;
    }

    public String getDestinationLocal() {
        return destinationLocal;
    }

    public void setDestinationLocal(String destinationLocal) {
        this.destinationLocal = destinationLocal;
    }

    public Long getIdOPCI() {
        return idOPCI;
    }

    public void setIdOPCI(Long idOPCI) {
        this.idOPCI = idOPCI;
    }

    public Bailstatus getStatus() {
        return status;
    }

    public void setStatus(Bailstatus status) {
        this.status = status;
    }

    public Long getLocataireId() {
        return locataireId;
    }

    public void setLocataireId(Long locataireId) {
        this.locataireId = locataireId;
    }

    public Long getGarantId() {
        return garantId;
    }

    public void setGarantId(Long garantId) {
        this.garantId = garantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BailDTO)) {
            return false;
        }

        return id != null && id.equals(((BailDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BailDTO{" +
            "id=" + getId() +
            ", codeBail='" + getCodeBail() + "'" +
            ", durationBail='" + getDurationBail() + "'" +
            ", typeBail='" + getTypeBail() + "'" +
            ", signatureDate='" + getSignatureDate() + "'" +
            ", firstRentDate='" + getFirstRentDate() + "'" +
            ", destinationLocal='" + getDestinationLocal() + "'" +
            ", idOPCI=" + getIdOPCI() +
            ", status='" + getStatus() + "'" +
            ", locataireId=" + getLocataireId() +
            ", garantId=" + getGarantId() +
            "}";
    }
}
