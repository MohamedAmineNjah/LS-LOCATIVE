package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.BaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Base} and its DTO {@link BaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {RentMapper.class})
public interface BaseMapper extends EntityMapper<BaseDTO, Base> {

    @Mapping(source = "rent.id", target = "rentId")
    BaseDTO toDto(Base base);

    @Mapping(source = "rentId", target = "rent")
    Base toEntity(BaseDTO baseDTO);

    default Base fromId(Long id) {
        if (id == null) {
            return null;
        }
        Base base = new Base();
        base.setId(id);
        return base;
    }
}
