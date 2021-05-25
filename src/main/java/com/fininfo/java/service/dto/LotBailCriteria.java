package com.fininfo.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.fininfo.java.domain.LotBail} entity. This class is used
 * in {@link com.fininfo.java.web.rest.LotBailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lot-bails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LotBailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter codeLot;

    private StringFilter building;

    private StringFilter stairs;

    private StringFilter comments;

    private StringFilter technicalInformation;

    private LocalDateFilter creationDate;

    private LocalDateFilter acquisationDate;

    private BigDecimalFilter surface;

    private IntegerFilter parkingsNumber;

    private IntegerFilter floorsNumber;

    private IntegerFilter realNumberOfLot;

    private IntegerFilter numberOfSecondaryUnits;

    private BooleanFilter outDoorParking;

    private BooleanFilter lotForMultipleOccupation;

    private BigDecimalFilter priceOfSquareMeter;

    private BigDecimalFilter rentalValueForSquareMeter;

    private BigDecimalFilter transferAmount;

    private BigDecimalFilter acquisationAmount;

    private BigDecimalFilter rentAmount;

    private IntegerFilter poolFactor;

    private LocalDateFilter maturityDate;

    private BooleanFilter convertibilityIndicator;

    private BooleanFilter subordinationIndicator;

    private BooleanFilter indexed;

    private BooleanFilter eligibilityIndicator;

    private IntegerFilter riskPremium;

    private IntegerFilter gotouarantorCode;

    private StringFilter guarantorDescription;

    private BooleanFilter amortizationTableManagement;

    private BooleanFilter variableRate;

    private LongFilter chargeId;

    private LongFilter counterId;

    private LongFilter scheduleId;

    private LongFilter bailId;

    public LotBailCriteria() {
    }

    public LotBailCriteria(LotBailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.codeLot = other.codeLot == null ? null : other.codeLot.copy();
        this.building = other.building == null ? null : other.building.copy();
        this.stairs = other.stairs == null ? null : other.stairs.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.technicalInformation = other.technicalInformation == null ? null : other.technicalInformation.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.acquisationDate = other.acquisationDate == null ? null : other.acquisationDate.copy();
        this.surface = other.surface == null ? null : other.surface.copy();
        this.parkingsNumber = other.parkingsNumber == null ? null : other.parkingsNumber.copy();
        this.floorsNumber = other.floorsNumber == null ? null : other.floorsNumber.copy();
        this.realNumberOfLot = other.realNumberOfLot == null ? null : other.realNumberOfLot.copy();
        this.numberOfSecondaryUnits = other.numberOfSecondaryUnits == null ? null : other.numberOfSecondaryUnits.copy();
        this.outDoorParking = other.outDoorParking == null ? null : other.outDoorParking.copy();
        this.lotForMultipleOccupation = other.lotForMultipleOccupation == null ? null : other.lotForMultipleOccupation.copy();
        this.priceOfSquareMeter = other.priceOfSquareMeter == null ? null : other.priceOfSquareMeter.copy();
        this.rentalValueForSquareMeter = other.rentalValueForSquareMeter == null ? null : other.rentalValueForSquareMeter.copy();
        this.transferAmount = other.transferAmount == null ? null : other.transferAmount.copy();
        this.acquisationAmount = other.acquisationAmount == null ? null : other.acquisationAmount.copy();
        this.rentAmount = other.rentAmount == null ? null : other.rentAmount.copy();
        this.poolFactor = other.poolFactor == null ? null : other.poolFactor.copy();
        this.maturityDate = other.maturityDate == null ? null : other.maturityDate.copy();
        this.convertibilityIndicator = other.convertibilityIndicator == null ? null : other.convertibilityIndicator.copy();
        this.subordinationIndicator = other.subordinationIndicator == null ? null : other.subordinationIndicator.copy();
        this.indexed = other.indexed == null ? null : other.indexed.copy();
        this.eligibilityIndicator = other.eligibilityIndicator == null ? null : other.eligibilityIndicator.copy();
        this.riskPremium = other.riskPremium == null ? null : other.riskPremium.copy();
        this.gotouarantorCode = other.gotouarantorCode == null ? null : other.gotouarantorCode.copy();
        this.guarantorDescription = other.guarantorDescription == null ? null : other.guarantorDescription.copy();
        this.amortizationTableManagement = other.amortizationTableManagement == null ? null : other.amortizationTableManagement.copy();
        this.variableRate = other.variableRate == null ? null : other.variableRate.copy();
        this.chargeId = other.chargeId == null ? null : other.chargeId.copy();
        this.counterId = other.counterId == null ? null : other.counterId.copy();
        this.scheduleId = other.scheduleId == null ? null : other.scheduleId.copy();
        this.bailId = other.bailId == null ? null : other.bailId.copy();
    }

    @Override
    public LotBailCriteria copy() {
        return new LotBailCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCodeLot() {
        return codeLot;
    }

    public void setCodeLot(StringFilter codeLot) {
        this.codeLot = codeLot;
    }

    public StringFilter getBuilding() {
        return building;
    }

    public void setBuilding(StringFilter building) {
        this.building = building;
    }

    public StringFilter getStairs() {
        return stairs;
    }

    public void setStairs(StringFilter stairs) {
        this.stairs = stairs;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getTechnicalInformation() {
        return technicalInformation;
    }

    public void setTechnicalInformation(StringFilter technicalInformation) {
        this.technicalInformation = technicalInformation;
    }

    public LocalDateFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateFilter creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateFilter getAcquisationDate() {
        return acquisationDate;
    }

    public void setAcquisationDate(LocalDateFilter acquisationDate) {
        this.acquisationDate = acquisationDate;
    }

    public BigDecimalFilter getSurface() {
        return surface;
    }

    public void setSurface(BigDecimalFilter surface) {
        this.surface = surface;
    }

    public IntegerFilter getParkingsNumber() {
        return parkingsNumber;
    }

    public void setParkingsNumber(IntegerFilter parkingsNumber) {
        this.parkingsNumber = parkingsNumber;
    }

    public IntegerFilter getFloorsNumber() {
        return floorsNumber;
    }

    public void setFloorsNumber(IntegerFilter floorsNumber) {
        this.floorsNumber = floorsNumber;
    }

    public IntegerFilter getRealNumberOfLot() {
        return realNumberOfLot;
    }

    public void setRealNumberOfLot(IntegerFilter realNumberOfLot) {
        this.realNumberOfLot = realNumberOfLot;
    }

    public IntegerFilter getNumberOfSecondaryUnits() {
        return numberOfSecondaryUnits;
    }

    public void setNumberOfSecondaryUnits(IntegerFilter numberOfSecondaryUnits) {
        this.numberOfSecondaryUnits = numberOfSecondaryUnits;
    }

    public BooleanFilter getOutDoorParking() {
        return outDoorParking;
    }

    public void setOutDoorParking(BooleanFilter outDoorParking) {
        this.outDoorParking = outDoorParking;
    }

    public BooleanFilter getLotForMultipleOccupation() {
        return lotForMultipleOccupation;
    }

    public void setLotForMultipleOccupation(BooleanFilter lotForMultipleOccupation) {
        this.lotForMultipleOccupation = lotForMultipleOccupation;
    }

    public BigDecimalFilter getPriceOfSquareMeter() {
        return priceOfSquareMeter;
    }

    public void setPriceOfSquareMeter(BigDecimalFilter priceOfSquareMeter) {
        this.priceOfSquareMeter = priceOfSquareMeter;
    }

    public BigDecimalFilter getRentalValueForSquareMeter() {
        return rentalValueForSquareMeter;
    }

    public void setRentalValueForSquareMeter(BigDecimalFilter rentalValueForSquareMeter) {
        this.rentalValueForSquareMeter = rentalValueForSquareMeter;
    }

    public BigDecimalFilter getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimalFilter transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimalFilter getAcquisationAmount() {
        return acquisationAmount;
    }

    public void setAcquisationAmount(BigDecimalFilter acquisationAmount) {
        this.acquisationAmount = acquisationAmount;
    }

    public BigDecimalFilter getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimalFilter rentAmount) {
        this.rentAmount = rentAmount;
    }

    public IntegerFilter getPoolFactor() {
        return poolFactor;
    }

    public void setPoolFactor(IntegerFilter poolFactor) {
        this.poolFactor = poolFactor;
    }

    public LocalDateFilter getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateFilter maturityDate) {
        this.maturityDate = maturityDate;
    }

    public BooleanFilter getConvertibilityIndicator() {
        return convertibilityIndicator;
    }

    public void setConvertibilityIndicator(BooleanFilter convertibilityIndicator) {
        this.convertibilityIndicator = convertibilityIndicator;
    }

    public BooleanFilter getSubordinationIndicator() {
        return subordinationIndicator;
    }

    public void setSubordinationIndicator(BooleanFilter subordinationIndicator) {
        this.subordinationIndicator = subordinationIndicator;
    }

    public BooleanFilter getIndexed() {
        return indexed;
    }

    public void setIndexed(BooleanFilter indexed) {
        this.indexed = indexed;
    }

    public BooleanFilter getEligibilityIndicator() {
        return eligibilityIndicator;
    }

    public void setEligibilityIndicator(BooleanFilter eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
    }

    public IntegerFilter getRiskPremium() {
        return riskPremium;
    }

    public void setRiskPremium(IntegerFilter riskPremium) {
        this.riskPremium = riskPremium;
    }

    public IntegerFilter getGotouarantorCode() {
        return gotouarantorCode;
    }

    public void setGotouarantorCode(IntegerFilter gotouarantorCode) {
        this.gotouarantorCode = gotouarantorCode;
    }

    public StringFilter getGuarantorDescription() {
        return guarantorDescription;
    }

    public void setGuarantorDescription(StringFilter guarantorDescription) {
        this.guarantorDescription = guarantorDescription;
    }

    public BooleanFilter getAmortizationTableManagement() {
        return amortizationTableManagement;
    }

    public void setAmortizationTableManagement(BooleanFilter amortizationTableManagement) {
        this.amortizationTableManagement = amortizationTableManagement;
    }

    public BooleanFilter getVariableRate() {
        return variableRate;
    }

    public void setVariableRate(BooleanFilter variableRate) {
        this.variableRate = variableRate;
    }

    public LongFilter getChargeId() {
        return chargeId;
    }

    public void setChargeId(LongFilter chargeId) {
        this.chargeId = chargeId;
    }

    public LongFilter getCounterId() {
        return counterId;
    }

    public void setCounterId(LongFilter counterId) {
        this.counterId = counterId;
    }

    public LongFilter getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(LongFilter scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LongFilter getBailId() {
        return bailId;
    }

    public void setBailId(LongFilter bailId) {
        this.bailId = bailId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LotBailCriteria that = (LotBailCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(codeLot, that.codeLot) &&
            Objects.equals(building, that.building) &&
            Objects.equals(stairs, that.stairs) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(technicalInformation, that.technicalInformation) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(acquisationDate, that.acquisationDate) &&
            Objects.equals(surface, that.surface) &&
            Objects.equals(parkingsNumber, that.parkingsNumber) &&
            Objects.equals(floorsNumber, that.floorsNumber) &&
            Objects.equals(realNumberOfLot, that.realNumberOfLot) &&
            Objects.equals(numberOfSecondaryUnits, that.numberOfSecondaryUnits) &&
            Objects.equals(outDoorParking, that.outDoorParking) &&
            Objects.equals(lotForMultipleOccupation, that.lotForMultipleOccupation) &&
            Objects.equals(priceOfSquareMeter, that.priceOfSquareMeter) &&
            Objects.equals(rentalValueForSquareMeter, that.rentalValueForSquareMeter) &&
            Objects.equals(transferAmount, that.transferAmount) &&
            Objects.equals(acquisationAmount, that.acquisationAmount) &&
            Objects.equals(rentAmount, that.rentAmount) &&
            Objects.equals(poolFactor, that.poolFactor) &&
            Objects.equals(maturityDate, that.maturityDate) &&
            Objects.equals(convertibilityIndicator, that.convertibilityIndicator) &&
            Objects.equals(subordinationIndicator, that.subordinationIndicator) &&
            Objects.equals(indexed, that.indexed) &&
            Objects.equals(eligibilityIndicator, that.eligibilityIndicator) &&
            Objects.equals(riskPremium, that.riskPremium) &&
            Objects.equals(gotouarantorCode, that.gotouarantorCode) &&
            Objects.equals(guarantorDescription, that.guarantorDescription) &&
            Objects.equals(amortizationTableManagement, that.amortizationTableManagement) &&
            Objects.equals(variableRate, that.variableRate) &&
            Objects.equals(chargeId, that.chargeId) &&
            Objects.equals(counterId, that.counterId) &&
            Objects.equals(scheduleId, that.scheduleId) &&
            Objects.equals(bailId, that.bailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        codeLot,
        building,
        stairs,
        comments,
        technicalInformation,
        creationDate,
        acquisationDate,
        surface,
        parkingsNumber,
        floorsNumber,
        realNumberOfLot,
        numberOfSecondaryUnits,
        outDoorParking,
        lotForMultipleOccupation,
        priceOfSquareMeter,
        rentalValueForSquareMeter,
        transferAmount,
        acquisationAmount,
        rentAmount,
        poolFactor,
        maturityDate,
        convertibilityIndicator,
        subordinationIndicator,
        indexed,
        eligibilityIndicator,
        riskPremium,
        gotouarantorCode,
        guarantorDescription,
        amortizationTableManagement,
        variableRate,
        chargeId,
        counterId,
        scheduleId,
        bailId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LotBailCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (codeLot != null ? "codeLot=" + codeLot + ", " : "") +
                (building != null ? "building=" + building + ", " : "") +
                (stairs != null ? "stairs=" + stairs + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (technicalInformation != null ? "technicalInformation=" + technicalInformation + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (acquisationDate != null ? "acquisationDate=" + acquisationDate + ", " : "") +
                (surface != null ? "surface=" + surface + ", " : "") +
                (parkingsNumber != null ? "parkingsNumber=" + parkingsNumber + ", " : "") +
                (floorsNumber != null ? "floorsNumber=" + floorsNumber + ", " : "") +
                (realNumberOfLot != null ? "realNumberOfLot=" + realNumberOfLot + ", " : "") +
                (numberOfSecondaryUnits != null ? "numberOfSecondaryUnits=" + numberOfSecondaryUnits + ", " : "") +
                (outDoorParking != null ? "outDoorParking=" + outDoorParking + ", " : "") +
                (lotForMultipleOccupation != null ? "lotForMultipleOccupation=" + lotForMultipleOccupation + ", " : "") +
                (priceOfSquareMeter != null ? "priceOfSquareMeter=" + priceOfSquareMeter + ", " : "") +
                (rentalValueForSquareMeter != null ? "rentalValueForSquareMeter=" + rentalValueForSquareMeter + ", " : "") +
                (transferAmount != null ? "transferAmount=" + transferAmount + ", " : "") +
                (acquisationAmount != null ? "acquisationAmount=" + acquisationAmount + ", " : "") +
                (rentAmount != null ? "rentAmount=" + rentAmount + ", " : "") +
                (poolFactor != null ? "poolFactor=" + poolFactor + ", " : "") +
                (maturityDate != null ? "maturityDate=" + maturityDate + ", " : "") +
                (convertibilityIndicator != null ? "convertibilityIndicator=" + convertibilityIndicator + ", " : "") +
                (subordinationIndicator != null ? "subordinationIndicator=" + subordinationIndicator + ", " : "") +
                (indexed != null ? "indexed=" + indexed + ", " : "") +
                (eligibilityIndicator != null ? "eligibilityIndicator=" + eligibilityIndicator + ", " : "") +
                (riskPremium != null ? "riskPremium=" + riskPremium + ", " : "") +
                (gotouarantorCode != null ? "gotouarantorCode=" + gotouarantorCode + ", " : "") +
                (guarantorDescription != null ? "guarantorDescription=" + guarantorDescription + ", " : "") +
                (amortizationTableManagement != null ? "amortizationTableManagement=" + amortizationTableManagement + ", " : "") +
                (variableRate != null ? "variableRate=" + variableRate + ", " : "") +
                (chargeId != null ? "chargeId=" + chargeId + ", " : "") +
                (counterId != null ? "counterId=" + counterId + ", " : "") +
                (scheduleId != null ? "scheduleId=" + scheduleId + ", " : "") +
                (bailId != null ? "bailId=" + bailId + ", " : "") +
            "}";
    }

}
