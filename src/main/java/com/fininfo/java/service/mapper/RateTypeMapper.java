package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.RateTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RateType} and its DTO {@link RateTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {RentMapper.class})
public interface RateTypeMapper extends EntityMapper<RateTypeDTO, RateType> {

    @Mapping(source = "rent.id", target = "rentId")
    RateTypeDTO toDto(RateType rateType);

    @Mapping(source = "rentId", target = "rent")
    RateType toEntity(RateTypeDTO rateTypeDTO);

    default RateType fromId(Long id) {
        if (id == null) {
            return null;
        }
        RateType rateType = new RateType();
        rateType.setId(id);
        return rateType;
    }
}
