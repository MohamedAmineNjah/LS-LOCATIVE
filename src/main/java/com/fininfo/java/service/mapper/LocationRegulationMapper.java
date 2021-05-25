package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.LocationRegulationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationRegulation} and its DTO {@link LocationRegulationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationRegulationMapper extends EntityMapper<LocationRegulationDTO, LocationRegulation> {


    @Mapping(target = "reglementTypes", ignore = true)
    @Mapping(target = "removeReglementType", ignore = true)
    LocationRegulation toEntity(LocationRegulationDTO locationRegulationDTO);

    default LocationRegulation fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationRegulation locationRegulation = new LocationRegulation();
        locationRegulation.setId(id);
        return locationRegulation;
    }
}
