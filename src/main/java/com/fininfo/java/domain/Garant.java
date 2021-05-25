package com.fininfo.java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Garant.
 */
@Entity
@Table(name = "garant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Garant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profession")
    private String profession;

    @Column(name = "n_allocataire_caf")
    private Integer nAllocataireCAF;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Garant birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfession() {
        return profession;
    }

    public Garant profession(String profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getnAllocataireCAF() {
        return nAllocataireCAF;
    }

    public Garant nAllocataireCAF(Integer nAllocataireCAF) {
        this.nAllocataireCAF = nAllocataireCAF;
        return this;
    }

    public void setnAllocataireCAF(Integer nAllocataireCAF) {
        this.nAllocataireCAF = nAllocataireCAF;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Garant)) {
            return false;
        }
        return id != null && id.equals(((Garant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Garant{" +
            "id=" + getId() +
            ", birthDate='" + getBirthDate() + "'" +
            ", profession='" + getProfession() + "'" +
            ", nAllocataireCAF=" + getnAllocataireCAF() +
            "}";
    }
}
