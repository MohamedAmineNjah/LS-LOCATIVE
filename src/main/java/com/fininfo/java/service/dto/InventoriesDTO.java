package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import com.fininfo.java.domain.enumeration.IEnumInventory;

/**
 * A DTO for the {@link com.fininfo.java.domain.Inventories} entity.
 */
public class InventoriesDTO implements Serializable {
    
    private Long id;

    private IEnumInventory inventoriesType;

    private LocalDate inventoriesDate;

    private String inventoriesStatus;

    private String generalObservation;


    private Long lotBailId;

    private String lotBailName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEnumInventory getInventoriesType() {
        return inventoriesType;
    }

    public void setInventoriesType(IEnumInventory inventoriesType) {
        this.inventoriesType = inventoriesType;
    }

    public LocalDate getInventoriesDate() {
        return inventoriesDate;
    }

    public void setInventoriesDate(LocalDate inventoriesDate) {
        this.inventoriesDate = inventoriesDate;
    }

    public String getInventoriesStatus() {
        return inventoriesStatus;
    }

    public void setInventoriesStatus(String inventoriesStatus) {
        this.inventoriesStatus = inventoriesStatus;
    }

    public String getGeneralObservation() {
        return generalObservation;
    }

    public void setGeneralObservation(String generalObservation) {
        this.generalObservation = generalObservation;
    }

    public Long getLotBailId() {
        return lotBailId;
    }

    public void setLotBailId(Long lotBailId) {
        this.lotBailId = lotBailId;
    }

    public String getLotBailName() {
        return lotBailName;
    }

    public void setLotBailName(String lotBailName) {
        this.lotBailName = lotBailName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoriesDTO)) {
            return false;
        }

        return id != null && id.equals(((InventoriesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoriesDTO{" +
            "id=" + getId() +
            ", inventoriesType='" + getInventoriesType() + "'" +
            ", inventoriesDate='" + getInventoriesDate() + "'" +
            ", inventoriesStatus='" + getInventoriesStatus() + "'" +
            ", generalObservation='" + getGeneralObservation() + "'" +
            ", lotBailId=" + getLotBailId() +
            ", lotBailName='" + getLotBailName() + "'" +
            "}";
    }
}
