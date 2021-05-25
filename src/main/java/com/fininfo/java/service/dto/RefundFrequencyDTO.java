package com.fininfo.java.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fininfo.java.domain.RefundFrequency} entity.
 */
public class RefundFrequencyDTO implements Serializable {
    
    private Long id;


    private Long rentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof RefundFrequencyDTO)) {
            return false;
        }

        return id != null && id.equals(((RefundFrequencyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefundFrequencyDTO{" +
            "id=" + getId() +
            ", rentId=" + getRentId() +
            "}";
    }
}
