package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.fininfo.java.domain.LotBail} entity.
 */
public class LotBailDTO implements Serializable {
    
    private Long id;

    private String name;

    private String codeLot;

    private String building;

    private String stairs;

    private String comments;

    private String technicalInformation;

    private LocalDate creationDate;

    private LocalDate acquisationDate;

    private BigDecimal surface;

    private Integer parkingsNumber;

    private Integer floorsNumber;

    private Integer realNumberOfLot;

    private Integer numberOfSecondaryUnits;

    private Boolean outDoorParking;

    private Boolean lotForMultipleOccupation;

    private BigDecimal priceOfSquareMeter;

    private BigDecimal rentalValueForSquareMeter;

    private BigDecimal transferAmount;

    private BigDecimal acquisationAmount;

    private BigDecimal rentAmount;

    private Integer poolFactor;

    private LocalDate maturityDate;

    private Boolean convertibilityIndicator;

    private Boolean subordinationIndicator;

    private Boolean indexed;

    private Boolean eligibilityIndicator;

    private Integer riskPremium;

    private Integer gotouarantorCode;

    private String guarantorDescription;

    private Boolean amortizationTableManagement;

    private Boolean variableRate;


    private Long bailId;
    
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

    public String getCodeLot() {
        return codeLot;
    }

    public void setCodeLot(String codeLot) {
        this.codeLot = codeLot;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStairs() {
        return stairs;
    }

    public void setStairs(String stairs) {
        this.stairs = stairs;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTechnicalInformation() {
        return technicalInformation;
    }

    public void setTechnicalInformation(String technicalInformation) {
        this.technicalInformation = technicalInformation;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getAcquisationDate() {
        return acquisationDate;
    }

    public void setAcquisationDate(LocalDate acquisationDate) {
        this.acquisationDate = acquisationDate;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public Integer getParkingsNumber() {
        return parkingsNumber;
    }

    public void setParkingsNumber(Integer parkingsNumber) {
        this.parkingsNumber = parkingsNumber;
    }

    public Integer getFloorsNumber() {
        return floorsNumber;
    }

    public void setFloorsNumber(Integer floorsNumber) {
        this.floorsNumber = floorsNumber;
    }

    public Integer getRealNumberOfLot() {
        return realNumberOfLot;
    }

    public void setRealNumberOfLot(Integer realNumberOfLot) {
        this.realNumberOfLot = realNumberOfLot;
    }

    public Integer getNumberOfSecondaryUnits() {
        return numberOfSecondaryUnits;
    }

    public void setNumberOfSecondaryUnits(Integer numberOfSecondaryUnits) {
        this.numberOfSecondaryUnits = numberOfSecondaryUnits;
    }

    public Boolean isOutDoorParking() {
        return outDoorParking;
    }

    public void setOutDoorParking(Boolean outDoorParking) {
        this.outDoorParking = outDoorParking;
    }

    public Boolean isLotForMultipleOccupation() {
        return lotForMultipleOccupation;
    }

    public void setLotForMultipleOccupation(Boolean lotForMultipleOccupation) {
        this.lotForMultipleOccupation = lotForMultipleOccupation;
    }

    public BigDecimal getPriceOfSquareMeter() {
        return priceOfSquareMeter;
    }

    public void setPriceOfSquareMeter(BigDecimal priceOfSquareMeter) {
        this.priceOfSquareMeter = priceOfSquareMeter;
    }

    public BigDecimal getRentalValueForSquareMeter() {
        return rentalValueForSquareMeter;
    }

    public void setRentalValueForSquareMeter(BigDecimal rentalValueForSquareMeter) {
        this.rentalValueForSquareMeter = rentalValueForSquareMeter;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getAcquisationAmount() {
        return acquisationAmount;
    }

    public void setAcquisationAmount(BigDecimal acquisationAmount) {
        this.acquisationAmount = acquisationAmount;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Integer getPoolFactor() {
        return poolFactor;
    }

    public void setPoolFactor(Integer poolFactor) {
        this.poolFactor = poolFactor;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Boolean isConvertibilityIndicator() {
        return convertibilityIndicator;
    }

    public void setConvertibilityIndicator(Boolean convertibilityIndicator) {
        this.convertibilityIndicator = convertibilityIndicator;
    }

    public Boolean isSubordinationIndicator() {
        return subordinationIndicator;
    }

    public void setSubordinationIndicator(Boolean subordinationIndicator) {
        this.subordinationIndicator = subordinationIndicator;
    }

    public Boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(Boolean indexed) {
        this.indexed = indexed;
    }

    public Boolean isEligibilityIndicator() {
        return eligibilityIndicator;
    }

    public void setEligibilityIndicator(Boolean eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
    }

    public Integer getRiskPremium() {
        return riskPremium;
    }

    public void setRiskPremium(Integer riskPremium) {
        this.riskPremium = riskPremium;
    }

    public Integer getGotouarantorCode() {
        return gotouarantorCode;
    }

    public void setGotouarantorCode(Integer gotouarantorCode) {
        this.gotouarantorCode = gotouarantorCode;
    }

    public String getGuarantorDescription() {
        return guarantorDescription;
    }

    public void setGuarantorDescription(String guarantorDescription) {
        this.guarantorDescription = guarantorDescription;
    }

    public Boolean isAmortizationTableManagement() {
        return amortizationTableManagement;
    }

    public void setAmortizationTableManagement(Boolean amortizationTableManagement) {
        this.amortizationTableManagement = amortizationTableManagement;
    }

    public Boolean isVariableRate() {
        return variableRate;
    }

    public void setVariableRate(Boolean variableRate) {
        this.variableRate = variableRate;
    }

    public Long getBailId() {
        return bailId;
    }

    public void setBailId(Long bailId) {
        this.bailId = bailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LotBailDTO)) {
            return false;
        }

        return id != null && id.equals(((LotBailDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LotBailDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", codeLot='" + getCodeLot() + "'" +
            ", building='" + getBuilding() + "'" +
            ", stairs='" + getStairs() + "'" +
            ", comments='" + getComments() + "'" +
            ", technicalInformation='" + getTechnicalInformation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", acquisationDate='" + getAcquisationDate() + "'" +
            ", surface=" + getSurface() +
            ", parkingsNumber=" + getParkingsNumber() +
            ", floorsNumber=" + getFloorsNumber() +
            ", realNumberOfLot=" + getRealNumberOfLot() +
            ", numberOfSecondaryUnits=" + getNumberOfSecondaryUnits() +
            ", outDoorParking='" + isOutDoorParking() + "'" +
            ", lotForMultipleOccupation='" + isLotForMultipleOccupation() + "'" +
            ", priceOfSquareMeter=" + getPriceOfSquareMeter() +
            ", rentalValueForSquareMeter=" + getRentalValueForSquareMeter() +
            ", transferAmount=" + getTransferAmount() +
            ", acquisationAmount=" + getAcquisationAmount() +
            ", rentAmount=" + getRentAmount() +
            ", poolFactor=" + getPoolFactor() +
            ", maturityDate='" + getMaturityDate() + "'" +
            ", convertibilityIndicator='" + isConvertibilityIndicator() + "'" +
            ", subordinationIndicator='" + isSubordinationIndicator() + "'" +
            ", indexed='" + isIndexed() + "'" +
            ", eligibilityIndicator='" + isEligibilityIndicator() + "'" +
            ", riskPremium=" + getRiskPremium() +
            ", gotouarantorCode=" + getGotouarantorCode() +
            ", guarantorDescription='" + getGuarantorDescription() + "'" +
            ", amortizationTableManagement='" + isAmortizationTableManagement() + "'" +
            ", variableRate='" + isVariableRate() + "'" +
            ", bailId=" + getBailId() +
            "}";
    }
}
