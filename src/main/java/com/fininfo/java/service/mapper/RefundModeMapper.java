package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.RefundModeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefundMode} and its DTO {@link RefundModeDTO}.
 */
@Mapper(componentModel = "spring", uses = {RentMapper.class})
public interface RefundModeMapper extends EntityMapper<RefundModeDTO, RefundMode> {

    @Mapping(source = "rent.id", target = "rentId")
    RefundModeDTO toDto(RefundMode refundMode);

    @Mapping(source = "rentId", target = "rent")
    RefundMode toEntity(RefundModeDTO refundModeDTO);

    default RefundMode fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefundMode refundMode = new RefundMode();
        refundMode.setId(id);
        return refundMode;
    }
}
