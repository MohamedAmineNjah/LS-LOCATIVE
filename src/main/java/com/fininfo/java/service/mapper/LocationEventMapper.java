package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.LocationEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationEvent} and its DTO {@link LocationEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationRegulationMapper.class})
public interface LocationEventMapper extends EntityMapper<LocationEventDTO, LocationEvent> {

    @Mapping(source = "locationRegulation.id", target = "locationRegulationId")
    LocationEventDTO toDto(LocationEvent locationEvent);

    @Mapping(source = "locationRegulationId", target = "locationRegulation")
    LocationEvent toEntity(LocationEventDTO locationEventDTO);

    default LocationEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationEvent locationEvent = new LocationEvent();
        locationEvent.setId(id);
        return locationEvent;
    }
}
