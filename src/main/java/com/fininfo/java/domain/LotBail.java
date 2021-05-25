package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A LotBail.
 */
@Entity
@Table(name = "lot_bail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LotBail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code_lot")
    private String codeLot;

    @Column(name = "building")
    private String building;

    @Column(name = "stairs")
    private String stairs;

    @Column(name = "comments")
    private String comments;

    @Column(name = "technical_information")
    private String technicalInformation;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "acquisation_date")
    private LocalDate acquisationDate;

    @Column(name = "surface", precision = 21, scale = 2)
    private BigDecimal surface;

    @Column(name = "parkings_number")
    private Integer parkingsNumber;

    @Column(name = "floors_number")
    private Integer floorsNumber;

    @Column(name = "real_number_of_lot")
    private Integer realNumberOfLot;

    @Column(name = "number_of_secondary_units")
    private Integer numberOfSecondaryUnits;

    @Column(name = "out_door_parking")
    private Boolean outDoorParking;

    @Column(name = "lot_for_multiple_occupation")
    private Boolean lotForMultipleOccupation;

    @Column(name = "price_of_square_meter", precision = 21, scale = 2)
    private BigDecimal priceOfSquareMeter;

    @Column(name = "rental_value_for_square_meter", precision = 21, scale = 2)
    private BigDecimal rentalValueForSquareMeter;

    @Column(name = "transfer_amount", precision = 21, scale = 2)
    private BigDecimal transferAmount;

    @Column(name = "acquisation_amount", precision = 21, scale = 2)
    private BigDecimal acquisationAmount;

    @Column(name = "rent_amount", precision = 21, scale = 2)
    private BigDecimal rentAmount;

    @Column(name = "pool_factor")
    private Integer poolFactor;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "convertibility_indicator")
    private Boolean convertibilityIndicator;

    @Column(name = "subordination_indicator")
    private Boolean subordinationIndicator;

    @Column(name = "indexed")
    private Boolean indexed;

    @Column(name = "eligibility_indicator")
    private Boolean eligibilityIndicator;

    @Column(name = "risk_premium")
    private Integer riskPremium;

    @Column(name = "gotouarantor_code")
    private Integer gotouarantorCode;

    @Column(name = "guarantor_description")
    private String guarantorDescription;

    @Column(name = "amortization_table_management")
    private Boolean amortizationTableManagement;

    @Column(name = "variable_rate")
    private Boolean variableRate;

    @OneToMany(mappedBy = "lotBail")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Charge> charges = new HashSet<>();

    @OneToMany(mappedBy = "lotBail")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Counter> counters = new HashSet<>();

    @OneToMany(mappedBy = "lotBail")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "lotBails", allowSetters = true)
    private Bail bail;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LotBail name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeLot() {
        return codeLot;
    }

    public LotBail codeLot(String codeLot) {
        this.codeLot = codeLot;
        return this;
    }

    public void setCodeLot(String codeLot) {
        this.codeLot = codeLot;
    }

    public String getBuilding() {
        return building;
    }

    public LotBail building(String building) {
        this.building = building;
        return this;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStairs() {
        return stairs;
    }

    public LotBail stairs(String stairs) {
        this.stairs = stairs;
        return this;
    }

    public void setStairs(String stairs) {
        this.stairs = stairs;
    }

    public String getComments() {
        return comments;
    }

    public LotBail comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTechnicalInformation() {
        return technicalInformation;
    }

    public LotBail technicalInformation(String technicalInformation) {
        this.technicalInformation = technicalInformation;
        return this;
    }

    public void setTechnicalInformation(String technicalInformation) {
        this.technicalInformation = technicalInformation;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LotBail creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getAcquisationDate() {
        return acquisationDate;
    }

    public LotBail acquisationDate(LocalDate acquisationDate) {
        this.acquisationDate = acquisationDate;
        return this;
    }

    public void setAcquisationDate(LocalDate acquisationDate) {
        this.acquisationDate = acquisationDate;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public LotBail surface(BigDecimal surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public Integer getParkingsNumber() {
        return parkingsNumber;
    }

    public LotBail parkingsNumber(Integer parkingsNumber) {
        this.parkingsNumber = parkingsNumber;
        return this;
    }

    public void setParkingsNumber(Integer parkingsNumber) {
        this.parkingsNumber = parkingsNumber;
    }

    public Integer getFloorsNumber() {
        return floorsNumber;
    }

    public LotBail floorsNumber(Integer floorsNumber) {
        this.floorsNumber = floorsNumber;
        return this;
    }

    public void setFloorsNumber(Integer floorsNumber) {
        this.floorsNumber = floorsNumber;
    }

    public Integer getRealNumberOfLot() {
        return realNumberOfLot;
    }

    public LotBail realNumberOfLot(Integer realNumberOfLot) {
        this.realNumberOfLot = realNumberOfLot;
        return this;
    }

    public void setRealNumberOfLot(Integer realNumberOfLot) {
        this.realNumberOfLot = realNumberOfLot;
    }

    public Integer getNumberOfSecondaryUnits() {
        return numberOfSecondaryUnits;
    }

    public LotBail numberOfSecondaryUnits(Integer numberOfSecondaryUnits) {
        this.numberOfSecondaryUnits = numberOfSecondaryUnits;
        return this;
    }

    public void setNumberOfSecondaryUnits(Integer numberOfSecondaryUnits) {
        this.numberOfSecondaryUnits = numberOfSecondaryUnits;
    }

    public Boolean isOutDoorParking() {
        return outDoorParking;
    }

    public LotBail outDoorParking(Boolean outDoorParking) {
        this.outDoorParking = outDoorParking;
        return this;
    }

    public void setOutDoorParking(Boolean outDoorParking) {
        this.outDoorParking = outDoorParking;
    }

    public Boolean isLotForMultipleOccupation() {
        return lotForMultipleOccupation;
    }

    public LotBail lotForMultipleOccupation(Boolean lotForMultipleOccupation) {
        this.lotForMultipleOccupation = lotForMultipleOccupation;
        return this;
    }

    public void setLotForMultipleOccupation(Boolean lotForMultipleOccupation) {
        this.lotForMultipleOccupation = lotForMultipleOccupation;
    }

    public BigDecimal getPriceOfSquareMeter() {
        return priceOfSquareMeter;
    }

    public LotBail priceOfSquareMeter(BigDecimal priceOfSquareMeter) {
        this.priceOfSquareMeter = priceOfSquareMeter;
        return this;
    }

    public void setPriceOfSquareMeter(BigDecimal priceOfSquareMeter) {
        this.priceOfSquareMeter = priceOfSquareMeter;
    }

    public BigDecimal getRentalValueForSquareMeter() {
        return rentalValueForSquareMeter;
    }

    public LotBail rentalValueForSquareMeter(BigDecimal rentalValueForSquareMeter) {
        this.rentalValueForSquareMeter = rentalValueForSquareMeter;
        return this;
    }

    public void setRentalValueForSquareMeter(BigDecimal rentalValueForSquareMeter) {
        this.rentalValueForSquareMeter = rentalValueForSquareMeter;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public LotBail transferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
        return this;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getAcquisationAmount() {
        return acquisationAmount;
    }

    public LotBail acquisationAmount(BigDecimal acquisationAmount) {
        this.acquisationAmount = acquisationAmount;
        return this;
    }

    public void setAcquisationAmount(BigDecimal acquisationAmount) {
        this.acquisationAmount = acquisationAmount;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public LotBail rentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
        return this;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Integer getPoolFactor() {
        return poolFactor;
    }

    public LotBail poolFactor(Integer poolFactor) {
        this.poolFactor = poolFactor;
        return this;
    }

    public void setPoolFactor(Integer poolFactor) {
        this.poolFactor = poolFactor;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public LotBail maturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Boolean isConvertibilityIndicator() {
        return convertibilityIndicator;
    }

    public LotBail convertibilityIndicator(Boolean convertibilityIndicator) {
        this.convertibilityIndicator = convertibilityIndicator;
        return this;
    }

    public void setConvertibilityIndicator(Boolean convertibilityIndicator) {
        this.convertibilityIndicator = convertibilityIndicator;
    }

    public Boolean isSubordinationIndicator() {
        return subordinationIndicator;
    }

    public LotBail subordinationIndicator(Boolean subordinationIndicator) {
        this.subordinationIndicator = subordinationIndicator;
        return this;
    }

    public void setSubordinationIndicator(Boolean subordinationIndicator) {
        this.subordinationIndicator = subordinationIndicator;
    }

    public Boolean isIndexed() {
        return indexed;
    }

    public LotBail indexed(Boolean indexed) {
        this.indexed = indexed;
        return this;
    }

    public void setIndexed(Boolean indexed) {
        this.indexed = indexed;
    }

    public Boolean isEligibilityIndicator() {
        return eligibilityIndicator;
    }

    public LotBail eligibilityIndicator(Boolean eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
        return this;
    }

    public void setEligibilityIndicator(Boolean eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
    }

    public Integer getRiskPremium() {
        return riskPremium;
    }

    public LotBail riskPremium(Integer riskPremium) {
        this.riskPremium = riskPremium;
        return this;
    }

    public void setRiskPremium(Integer riskPremium) {
        this.riskPremium = riskPremium;
    }

    public Integer getGotouarantorCode() {
        return gotouarantorCode;
    }

    public LotBail gotouarantorCode(Integer gotouarantorCode) {
        this.gotouarantorCode = gotouarantorCode;
        return this;
    }

    public void setGotouarantorCode(Integer gotouarantorCode) {
        this.gotouarantorCode = gotouarantorCode;
    }

    public String getGuarantorDescription() {
        return guarantorDescription;
    }

    public LotBail guarantorDescription(String guarantorDescription) {
        this.guarantorDescription = guarantorDescription;
        return this;
    }

    public void setGuarantorDescription(String guarantorDescription) {
        this.guarantorDescription = guarantorDescription;
    }

    public Boolean isAmortizationTableManagement() {
        return amortizationTableManagement;
    }

    public LotBail amortizationTableManagement(Boolean amortizationTableManagement) {
        this.amortizationTableManagement = amortizationTableManagement;
        return this;
    }

    public void setAmortizationTableManagement(Boolean amortizationTableManagement) {
        this.amortizationTableManagement = amortizationTableManagement;
    }

    public Boolean isVariableRate() {
        return variableRate;
    }

    public LotBail variableRate(Boolean variableRate) {
        this.variableRate = variableRate;
        return this;
    }

    public void setVariableRate(Boolean variableRate) {
        this.variableRate = variableRate;
    }

    public Set<Charge> getCharges() {
        return charges;
    }

    public LotBail charges(Set<Charge> charges) {
        this.charges = charges;
        return this;
    }

    public LotBail addCharge(Charge charge) {
        this.charges.add(charge);
        charge.setLotBail(this);
        return this;
    }

    public LotBail removeCharge(Charge charge) {
        this.charges.remove(charge);
        charge.setLotBail(null);
        return this;
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    public Set<Counter> getCounters() {
        return counters;
    }

    public LotBail counters(Set<Counter> counters) {
        this.counters = counters;
        return this;
    }

    public LotBail addCounter(Counter counter) {
        this.counters.add(counter);
        counter.setLotBail(this);
        return this;
    }

    public LotBail removeCounter(Counter counter) {
        this.counters.remove(counter);
        counter.setLotBail(null);
        return this;
    }

    public void setCounters(Set<Counter> counters) {
        this.counters = counters;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public LotBail schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public LotBail addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setLotBail(this);
        return this;
    }

    public LotBail removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setLotBail(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Bail getBail() {
        return bail;
    }

    public LotBail bail(Bail bail) {
        this.bail = bail;
        return this;
    }

    public void setBail(Bail bail) {
        this.bail = bail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LotBail)) {
            return false;
        }
        return id != null && id.equals(((LotBail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LotBail{" +
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
            "}";
    }
}
