package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.PeriodicityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Periodicity} and its DTO {@link PeriodicityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeriodicityMapper extends EntityMapper<PeriodicityDTO, Periodicity> {



    default Periodicity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Periodicity periodicity = new Periodicity();
        periodicity.setId(id);
        return periodicity;
    }
}
