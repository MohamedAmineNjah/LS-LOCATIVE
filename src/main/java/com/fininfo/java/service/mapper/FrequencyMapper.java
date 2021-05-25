package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.FrequencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Frequency} and its DTO {@link FrequencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {RentMapper.class})
public interface FrequencyMapper extends EntityMapper<FrequencyDTO, Frequency> {

    @Mapping(source = "rent.id", target = "rentId")
    FrequencyDTO toDto(Frequency frequency);

    @Mapping(source = "rentId", target = "rent")
    Frequency toEntity(FrequencyDTO frequencyDTO);

    default Frequency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Frequency frequency = new Frequency();
        frequency.setId(id);
        return frequency;
    }
}
