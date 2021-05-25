package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.fininfo.java.domain.Schedule} entity.
 */
public class ScheduleDTO implements Serializable {
    
    private Long id;

    private BigDecimal amountSchedule;


    private Long lotBailId;

    private Long rentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountSchedule() {
        return amountSchedule;
    }

    public void setAmountSchedule(BigDecimal amountSchedule) {
        this.amountSchedule = amountSchedule;
    }

    public Long getLotBailId() {
        return lotBailId;
    }

    public void setLotBailId(Long lotBailId) {
        this.lotBailId = lotBailId;
    }

    public Long getRentId() {
        return rentId;
    }

    public void setRentId(Long rentId) {
        this.rentId = rentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleDTO)) {
            return false;
        }

        return id != null && id.equals(((ScheduleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleDTO{" +
            "id=" + getId() +
            ", amountSchedule=" + getAmountSchedule() +
            ", lotBailId=" + getLotBailId() +
            ", rentId=" + getRentId() +
            "}";
    }
}
