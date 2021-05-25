package com.fininfo.java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ClosingEvent.
 */
@Entity
@Table(name = "closing_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClosingEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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

    public ClosingEvent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public ClosingEvent closingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ClosingEvent endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClosingEvent)) {
            return false;
        }
        return id != null && id.equals(((ClosingEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClosingEvent{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", closingDate='" + getClosingDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
