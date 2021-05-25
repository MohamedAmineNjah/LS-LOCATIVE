package com.fininfo.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.fininfo.java.domain.enumeration.IEnumTypeBail;

import com.fininfo.java.domain.enumeration.Bailstatus;

/**
 * A Bail.
 */
@Entity
@Table(name = "bail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_bail")
    private String codeBail;

    @Column(name = "duration_bail")
    private String durationBail;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_bail")
    private IEnumTypeBail typeBail;

    @Column(name = "signature_date")
    private LocalDate signatureDate;

    @Column(name = "first_rent_date")
    private LocalDate firstRentDate;

    @Column(name = "destination_local")
    private String destinationLocal;

    @Column(name = "id_opci")
    private Long idOPCI;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Bailstatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private Locataire locataire;

    @ManyToOne
    @JsonIgnoreProperties(value = "bails", allowSetters = true)
    private Garant garant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeBail() {
        return codeBail;
    }

    public Bail codeBail(String codeBail) {
        this.codeBail = codeBail;
        return this;
    }

    public void setCodeBail(String codeBail) {
        this.codeBail = codeBail;
    }

    public String getDurationBail() {
        return durationBail;
    }

    public Bail durationBail(String durationBail) {
        this.durationBail = durationBail;
        return this;
    }

    public void setDurationBail(String durationBail) {
        this.durationBail = durationBail;
    }

    public IEnumTypeBail getTypeBail() {
        return typeBail;
    }

    public Bail typeBail(IEnumTypeBail typeBail) {
        this.typeBail = typeBail;
        return this;
    }

    public void setTypeBail(IEnumTypeBail typeBail) {
        this.typeBail = typeBail;
    }

    public LocalDate getSignatureDate() {
        return signatureDate;
    }

    public Bail signatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
        return this;
    }

    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }

    public LocalDate getFirstRentDate() {
        return firstRentDate;
    }

    public Bail firstRentDate(LocalDate firstRentDate) {
        this.firstRentDate = firstRentDate;
        return this;
    }

    public void setFirstRentDate(LocalDate firstRentDate) {
        this.firstRentDate = firstRentDate;
    }

    public String getDestinationLocal() {
        return destinationLocal;
    }

    public Bail destinationLocal(String destinationLocal) {
        this.destinationLocal = destinationLocal;
        return this;
    }

    public void setDestinationLocal(String destinationLocal) {
        this.destinationLocal = destinationLocal;
    }

    public Long getIdOPCI() {
        return idOPCI;
    }

    public Bail idOPCI(Long idOPCI) {
        this.idOPCI = idOPCI;
        return this;
    }

    public void setIdOPCI(Long idOPCI) {
        this.idOPCI = idOPCI;
    }

    public Bailstatus getStatus() {
        return status;
    }

    public Bail status(Bailstatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(Bailstatus status) {
        this.status = status;
    }

    public Locataire getLocataire() {
        return locataire;
    }

    public Bail locataire(Locataire locataire) {
        this.locataire = locataire;
        return this;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }

    public Garant getGarant() {
        return garant;
    }

    public Bail garant(Garant garant) {
        this.garant = garant;
        return this;
    }

    public void setGarant(Garant garant) {
        this.garant = garant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bail)) {
            return false;
        }
        return id != null && id.equals(((Bail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bail{" +
            "id=" + getId() +
            ", codeBail='" + getCodeBail() + "'" +
            ", durationBail='" + getDurationBail() + "'" +
            ", typeBail='" + getTypeBail() + "'" +
            ", signatureDate='" + getSignatureDate() + "'" +
            ", firstRentDate='" + getFirstRentDate() + "'" +
            ", destinationLocal='" + getDestinationLocal() + "'" +
            ", idOPCI=" + getIdOPCI() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
