package com.fininfo.java.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fininfo.java.domain.ReglementType} entity.
 */
public class ReglementTypeDTO implements Serializable {
    
    private Long id;

    private String name;

    private String description;


    private Long locationRegulationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof ReglementTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((ReglementTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReglementTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", locationRegulationId=" + getLocationRegulationId() +
            "}";
    }
}
