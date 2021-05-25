package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.RefundFrequencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefundFrequency} and its DTO {@link RefundFrequencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {RentMapper.class})
public interface RefundFrequencyMapper extends EntityMapper<RefundFrequencyDTO, RefundFrequency> {

    @Mapping(source = "rent.id", target = "rentId")
    RefundFrequencyDTO toDto(RefundFrequency refundFrequency);

    @Mapping(source = "rentId", target = "rent")
    RefundFrequency toEntity(RefundFrequencyDTO refundFrequencyDTO);

    default RefundFrequency fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefundFrequency refundFrequency = new RefundFrequency();
        refundFrequency.setId(id);
        return refundFrequency;
    }
}
