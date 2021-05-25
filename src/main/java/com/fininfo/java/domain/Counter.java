package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.fininfo.java.domain.enumeration.IEnumTitle;

import com.fininfo.java.domain.enumeration.IEnumUnity;

/**
 * A Counter.
 */
@Entity
@Table(name = "counter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "counter_title")
    private IEnumTitle counterTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "unity")
    private IEnumUnity unity;

    @Column(name = "decimal")
    private Integer decimal;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties(value = "counters", allowSetters = true)
    private LotBail lotBail;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEnumTitle getCounterTitle() {
        return counterTitle;
    }

    public Counter counterTitle(IEnumTitle counterTitle) {
        this.counterTitle = counterTitle;
        return this;
    }

    public void setCounterTitle(IEnumTitle counterTitle) {
        this.counterTitle = counterTitle;
    }

    public IEnumUnity getUnity() {
        return unity;
    }

    public Counter unity(IEnumUnity unity) {
        this.unity = unity;
        return this;
    }

    public void setUnity(IEnumUnity unity) {
        this.unity = unity;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public Counter decimal(Integer decimal) {
        this.decimal = decimal;
        return this;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getComment() {
        return comment;
    }

    public Counter comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LotBail getLotBail() {
        return lotBail;
    }

    public Counter lotBail(LotBail lotBail) {
        this.lotBail = lotBail;
        return this;
    }

    public void setLotBail(LotBail lotBail) {
        this.lotBail = lotBail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Counter)) {
            return false;
        }
        return id != null && id.equals(((Counter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Counter{" +
            "id=" + getId() +
            ", counterTitle='" + getCounterTitle() + "'" +
            ", unity='" + getUnity() + "'" +
            ", decimal=" + getDecimal() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
