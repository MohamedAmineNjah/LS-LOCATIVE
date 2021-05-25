package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.ChargeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Charge} and its DTO {@link ChargeDTO}.
 */
@Mapper(componentModel = "spring", uses = {LotBailMapper.class})
public interface ChargeMapper extends EntityMapper<ChargeDTO, Charge> {

    @Mapping(source = "lotBail.id", target = "lotBailId")
    ChargeDTO toDto(Charge charge);

    @Mapping(source = "lotBailId", target = "lotBail")
    Charge toEntity(ChargeDTO chargeDTO);

    default Charge fromId(Long id) {
        if (id == null) {
            return null;
        }
        Charge charge = new Charge();
        charge.setId(id);
        return charge;
    }
}
