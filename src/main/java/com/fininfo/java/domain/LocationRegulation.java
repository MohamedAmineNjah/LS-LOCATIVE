package com.fininfo.java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A LocationRegulation.
 */
@Entity
@Table(name = "location_regulation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LocationRegulation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "regulation_date")
    private LocalDate regulationDate;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @OneToMany(mappedBy = "locationRegulation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ReglementType> reglementTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegulationDate() {
        return regulationDate;
    }

    public LocationRegulation regulationDate(LocalDate regulationDate) {
        this.regulationDate = regulationDate;
        return this;
    }

    public void setRegulationDate(LocalDate regulationDate) {
        this.regulationDate = regulationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocationRegulation amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Set<ReglementType> getReglementTypes() {
        return reglementTypes;
    }

    public LocationRegulation reglementTypes(Set<ReglementType> reglementTypes) {
        this.reglementTypes = reglementTypes;
        return this;
    }

    public LocationRegulation addReglementType(ReglementType reglementType) {
        this.reglementTypes.add(reglementType);
        reglementType.setLocationRegulation(this);
        return this;
    }

    public LocationRegulation removeReglementType(ReglementType reglementType) {
        this.reglementTypes.remove(reglementType);
        reglementType.setLocationRegulation(null);
        return this;
    }

    public void setReglementTypes(Set<ReglementType> reglementTypes) {
        this.reglementTypes = reglementTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationRegulation)) {
            return false;
        }
        return id != null && id.equals(((LocationRegulation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationRegulation{" +
            "id=" + getId() +
            ", regulationDate='" + getRegulationDate() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
