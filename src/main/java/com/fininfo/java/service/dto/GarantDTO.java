package com.fininfo.java.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fininfo.java.domain.Garant} entity.
 */
public class GarantDTO implements Serializable {
    
    private Long id;

    private LocalDate birthDate;

    private String profession;

    private Integer nAllocataireCAF;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getnAllocataireCAF() {
        return nAllocataireCAF;
    }

    public void setnAllocataireCAF(Integer nAllocataireCAF) {
        this.nAllocataireCAF = nAllocataireCAF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GarantDTO)) {
            return false;
        }

        return id != null && id.equals(((GarantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GarantDTO{" +
            "id=" + getId() +
            ", birthDate='" + getBirthDate() + "'" +
            ", profession='" + getProfession() + "'" +
            ", nAllocataireCAF=" + getnAllocataireCAF() +
            "}";
    }
}
