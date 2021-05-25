package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fininfo.java.domain.ClosingEvent} entity.
 */
public class ClosingEventDTO implements Serializable {
    
    private Long id;

    private String description;

    private LocalDate closingDate;

    private LocalDate endDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClosingEventDTO)) {
            return false;
        }

        return id != null && id.equals(((ClosingEventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClosingEventDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", closingDate='" + getClosingDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
