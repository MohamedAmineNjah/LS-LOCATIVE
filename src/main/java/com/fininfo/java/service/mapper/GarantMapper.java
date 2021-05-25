package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.GarantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Garant} and its DTO {@link GarantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GarantMapper extends EntityMapper<GarantDTO, Garant> {



    default Garant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Garant garant = new Garant();
        garant.setId(id);
        return garant;
    }
}
