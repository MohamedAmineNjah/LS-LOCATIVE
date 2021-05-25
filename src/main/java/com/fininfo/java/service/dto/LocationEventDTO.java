package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fininfo.java.domain.LocationEvent} entity.
 */
public class LocationEventDTO implements Serializable {
    
    private Long id;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double costPrice;

    private LocalDate deadline;

    private Integer assetCode;


    private Long locationRegulationId;
    
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Integer getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(Integer assetCode) {
        this.assetCode = assetCode;
    }

    public Long getLocationRegulationId() {
        return locationRegulationId;
    }

    public void setLocationRegulationId(Long locationRegulationId) {
        this.locationRegulationId = locationRegulationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationEventDTO)) {
            return false;
        }

        return id != null && id.equals(((LocationEventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationEventDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", costPrice=" + getCostPrice() +
            ", deadline='" + getDeadline() + "'" +
            ", assetCode=" + getAssetCode() +
            ", locationRegulationId=" + getLocationRegulationId() +
            "}";
    }
}
