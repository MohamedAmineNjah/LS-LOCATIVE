package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fininfo.java.domain.enumeration.IEnumperiodicityType;

/**
 * A DTO for the {@link com.fininfo.java.domain.Periodicity} entity.
 */
public class PeriodicityDTO implements Serializable {
    
    private Long id;

    private IEnumperiodicityType periodicityType;

    private BigDecimal value;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEnumperiodicityType getPeriodicityType() {
        return periodicityType;
    }

    public void setPeriodicityType(IEnumperiodicityType periodicityType) {
        this.periodicityType = periodicityType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodicityDTO)) {
            return false;
        }

        return id != null && id.equals(((PeriodicityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodicityDTO{" +
            "id=" + getId() +
            ", periodicityType='" + getPeriodicityType() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
