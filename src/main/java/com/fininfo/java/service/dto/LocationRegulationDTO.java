package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.fininfo.java.domain.LocationRegulation} entity.
 */
public class LocationRegulationDTO implements Serializable {
    
    private Long id;

    private LocalDate regulationDate;

    private BigDecimal amount;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegulationDate() {
        return regulationDate;
    }

    public void setRegulationDate(LocalDate regulationDate) {
        this.regulationDate = regulationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationRegulationDTO)) {
            return false;
        }

        return id != null && id.equals(((LocationRegulationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationRegulationDTO{" +
            "id=" + getId() +
            ", regulationDate='" + getRegulationDate() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
