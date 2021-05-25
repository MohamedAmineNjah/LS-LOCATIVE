package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RefundFrequency.
 */
@Entity
@Table(name = "refund_frequency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RefundFrequency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "refundFrequencies", allowSetters = true)
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

    public RefundFrequency rent(Rent rent) {
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
        if (!(o instanceof RefundFrequency)) {
            return false;
        }
        return id != null && id.equals(((RefundFrequency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefundFrequency{" +
            "id=" + getId() +
            "}";
    }
}
