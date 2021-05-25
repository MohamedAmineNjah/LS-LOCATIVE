package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.RentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rent} and its DTO {@link RentDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeriodicityMapper.class})
public interface RentMapper extends EntityMapper<RentDTO, Rent> {

    @Mapping(source = "periodicity.id", target = "periodicityId")
    RentDTO toDto(Rent rent);

    @Mapping(source = "periodicityId", target = "periodicity")
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedule", ignore = true)
    Rent toEntity(RentDTO rentDTO);

    default Rent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rent rent = new Rent();
        rent.setId(id);
        return rent;
    }
}
