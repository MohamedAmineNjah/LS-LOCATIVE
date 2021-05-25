package com.fininfo.java.service.dto;

import java.io.Serializable;
import com.fininfo.java.domain.enumeration.IEnumTitle;
import com.fininfo.java.domain.enumeration.IEnumUnity;

/**
 * A DTO for the {@link com.fininfo.java.domain.Counter} entity.
 */
public class CounterDTO implements Serializable {
    
    private Long id;

    private IEnumTitle counterTitle;

    private IEnumUnity unity;

    private Integer decimal;

    private String comment;


    private Long lotBailId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IEnumTitle getCounterTitle() {
        return counterTitle;
    }

    public void setCounterTitle(IEnumTitle counterTitle) {
        this.counterTitle = counterTitle;
    }

    public IEnumUnity getUnity() {
        return unity;
    }

    public void setUnity(IEnumUnity unity) {
        this.unity = unity;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getLotBailId() {
        return lotBailId;
    }

    public void setLotBailId(Long lotBailId) {
        this.lotBailId = lotBailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CounterDTO)) {
            return false;
        }

        return id != null && id.equals(((CounterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CounterDTO{" +
            "id=" + getId() +
            ", counterTitle='" + getCounterTitle() + "'" +
            ", unity='" + getUnity() + "'" +
            ", decimal=" + getDecimal() +
            ", comment='" + getComment() + "'" +
            ", lotBailId=" + getLotBailId() +
            "}";
    }
}
