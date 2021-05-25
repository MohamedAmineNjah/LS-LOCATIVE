package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.fininfo.java.domain.enumeration.IEnumInventory;

/**
 * A Inventories.
 */
@Entity
@Table(name = "inventories")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventories_type")
    private IEnumInventory inventoriesType;

    @Column(name = "inventories_date")
    private LocalDate inventoriesDate;

    @Column(name = "inventories_status")
    private String inventoriesStatus;

    @Column(name = "general_observation")
    private String generalObservation;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventories", allowSetters = true)
    private LotBail lotBail;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEnumInventory getInventoriesType() {
        return inventoriesType;
    }

    public Inventories inventoriesType(IEnumInventory inventoriesType) {
        this.inventoriesType = inventoriesType;
        return this;
    }

    public void setInventoriesType(IEnumInventory inventoriesType) {
        this.inventoriesType = inventoriesType;
    }

    public LocalDate getInventoriesDate() {
        return inventoriesDate;
    }

    public Inventories inventoriesDate(LocalDate inventoriesDate) {
        this.inventoriesDate = inventoriesDate;
        return this;
    }

    public void setInventoriesDate(LocalDate inventoriesDate) {
        this.inventoriesDate = inventoriesDate;
    }

    public String getInventoriesStatus() {
        return inventoriesStatus;
    }

    public Inventories inventoriesStatus(String inventoriesStatus) {
        this.inventoriesStatus = inventoriesStatus;
        return this;
    }

    public void setInventoriesStatus(String inventoriesStatus) {
        this.inventoriesStatus = inventoriesStatus;
    }

    public String getGeneralObservation() {
        return generalObservation;
    }

    public Inventories generalObservation(String generalObservation) {
        this.generalObservation = generalObservation;
        return this;
    }

    public void setGeneralObservation(String generalObservation) {
        this.generalObservation = generalObservation;
    }

    public LotBail getLotBail() {
        return lotBail;
    }

    public Inventories lotBail(LotBail lotBail) {
        this.lotBail = lotBail;
        return this;
    }

    public void setLotBail(LotBail lotBail) {
        this.lotBail = lotBail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventories)) {
            return false;
        }
        return id != null && id.equals(((Inventories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventories{" +
            "id=" + getId() +
            ", inventoriesType='" + getInventoriesType() + "'" +
            ", inventoriesDate='" + getInventoriesDate() + "'" +
            ", inventoriesStatus='" + getInventoriesStatus() + "'" +
            ", generalObservation='" + getGeneralObservation() + "'" +
            "}";
    }
}
