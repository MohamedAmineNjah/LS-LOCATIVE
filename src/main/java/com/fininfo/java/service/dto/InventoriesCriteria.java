package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.fininfo.java.domain.enumeration.IEnumInventory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.fininfo.java.domain.Inventories} entity. This class is used
 * in {@link com.fininfo.java.web.rest.InventoriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoriesCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IEnumInventory
     */
    public static class IEnumInventoryFilter extends Filter<IEnumInventory> {

        public IEnumInventoryFilter() {
        }

        public IEnumInventoryFilter(IEnumInventoryFilter filter) {
            super(filter);
        }

        @Override
        public IEnumInventoryFilter copy() {
            return new IEnumInventoryFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IEnumInventoryFilter inventoriesType;

    private LocalDateFilter inventoriesDate;

    private StringFilter inventoriesStatus;

    private StringFilter generalObservation;

    private LongFilter lotBailId;

    public InventoriesCriteria() {
    }

    public InventoriesCriteria(InventoriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.inventoriesType = other.inventoriesType == null ? null : other.inventoriesType.copy();
        this.inventoriesDate = other.inventoriesDate == null ? null : other.inventoriesDate.copy();
        this.inventoriesStatus = other.inventoriesStatus == null ? null : other.inventoriesStatus.copy();
        this.generalObservation = other.generalObservation == null ? null : other.generalObservation.copy();
        this.lotBailId = other.lotBailId == null ? null : other.lotBailId.copy();
    }

    @Override
    public InventoriesCriteria copy() {
        return new InventoriesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IEnumInventoryFilter getInventoriesType() {
        return inventoriesType;
    }

    public void setInventoriesType(IEnumInventoryFilter inventoriesType) {
        this.inventoriesType = inventoriesType;
    }

    public LocalDateFilter getInventoriesDate() {
        return inventoriesDate;
    }

    public void setInventoriesDate(LocalDateFilter inventoriesDate) {
        this.inventoriesDate = inventoriesDate;
    }

    public StringFilter getInventoriesStatus() {
        return inventoriesStatus;
    }

    public void setInventoriesStatus(StringFilter inventoriesStatus) {
        this.inventoriesStatus = inventoriesStatus;
    }

    public StringFilter getGeneralObservation() {
        return generalObservation;
    }

    public void setGeneralObservation(StringFilter generalObservation) {
        this.generalObservation = generalObservation;
    }

    public LongFilter getLotBailId() {
        return lotBailId;
    }

    public void setLotBailId(LongFilter lotBailId) {
        this.lotBailId = lotBailId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoriesCriteria that = (InventoriesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(inventoriesType, that.inventoriesType) &&
            Objects.equals(inventoriesDate, that.inventoriesDate) &&
            Objects.equals(inventoriesStatus, that.inventoriesStatus) &&
            Objects.equals(generalObservation, that.generalObservation) &&
            Objects.equals(lotBailId, that.lotBailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        inventoriesType,
        inventoriesDate,
        inventoriesStatus,
        generalObservation,
        lotBailId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoriesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (inventoriesType != null ? "inventoriesType=" + inventoriesType + ", " : "") +
                (inventoriesDate != null ? "inventoriesDate=" + inventoriesDate + ", " : "") +
                (inventoriesStatus != null ? "inventoriesStatus=" + inventoriesStatus + ", " : "") +
                (generalObservation != null ? "generalObservation=" + generalObservation + ", " : "") +
                (lotBailId != null ? "lotBailId=" + lotBailId + ", " : "") +
            "}";
    }

}
