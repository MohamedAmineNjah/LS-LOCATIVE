package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RateType.
 */
@Entity
@Table(name = "rate_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RateType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "rateTypes", allowSetters = true)
    private Rent rent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rent getRent() {
        return rent;
    }

    public RateType rent(Rent rent) {
        this.rent = rent;
        return this;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RateType)) {
            return false;
        }
        return id != null && id.equals(((RateType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RateType{" +
            "id=" + getId() +
            "}";
    }
}
