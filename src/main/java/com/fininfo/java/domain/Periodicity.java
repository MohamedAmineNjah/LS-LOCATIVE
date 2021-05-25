package com.fininfo.java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fininfo.java.domain.enumeration.IEnumperiodicityType;

/**
 * A Periodicity.
 */
@Entity
@Table(name = "periodicity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Periodicity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicity_type")
    private IEnumperiodicityType periodicityType;

    @Column(name = "value", precision = 21, scale = 2)
    private BigDecimal value;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEnumperiodicityType getPeriodicityType() {
        return periodicityType;
    }

    public Periodicity periodicityType(IEnumperiodicityType periodicityType) {
        this.periodicityType = periodicityType;
        return this;
    }

    public void setPeriodicityType(IEnumperiodicityType periodicityType) {
        this.periodicityType = periodicityType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Periodicity value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Periodicity)) {
            return false;
        }
        return id != null && id.equals(((Periodicity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Periodicity{" +
            "id=" + getId() +
            ", periodicityType='" + getPeriodicityType() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
