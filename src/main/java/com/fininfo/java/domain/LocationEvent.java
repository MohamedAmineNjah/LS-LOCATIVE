package com.fininfo.java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A LocationEvent.
 */
@Entity
@Table(name = "location_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LocationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "cost_price")
    private Double costPrice;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "asset_code")
    private Integer assetCode;

    @OneToOne
    @JoinColumn(unique = true)
    private LocationRegulation locationRegulation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public LocationEvent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocationEvent startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocationEvent endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public LocationEvent costPrice(Double costPrice) {
        this.costPrice = costPrice;
        return this;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocationEvent deadline(LocalDate deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Integer getAssetCode() {
        return assetCode;
    }

    public LocationEvent assetCode(Integer assetCode) {
        this.assetCode = assetCode;
        return this;
    }

    public void setAssetCode(Integer assetCode) {
        this.assetCode = assetCode;
    }

    public LocationRegulation getLocationRegulation() {
        return locationRegulation;
    }

    public LocationEvent locationRegulation(LocationRegulation locationRegulation) {
        this.locationRegulation = locationRegulation;
        return this;
    }

    public void setLocationRegulation(LocationRegulation locationRegulation) {
        this.locationRegulation = locationRegulation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationEvent)) {
            return false;
        }
        return id != null && id.equals(((LocationEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationEvent{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", costPrice=" + getCostPrice() +
            ", deadline='" + getDeadline() + "'" +
            ", assetCode=" + getAssetCode() +
            "}";
    }
}
