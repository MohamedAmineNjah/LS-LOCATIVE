package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.LotBailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LotBail} and its DTO {@link LotBailDTO}.
 */
@Mapper(componentModel = "spring", uses = {BailMapper.class})
public interface LotBailMapper extends EntityMapper<LotBailDTO, LotBail> {

    @Mapping(source = "bail.id", target = "bailId")
    LotBailDTO toDto(LotBail lotBail);

    @Mapping(target = "charges", ignore = true)
    @Mapping(target = "removeCharge", ignore = true)
    @Mapping(target = "counters", ignore = true)
    @Mapping(target = "removeCounter", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedule", ignore = true)
    @Mapping(source = "bailId", target = "bail")
    LotBail toEntity(LotBailDTO lotBailDTO);

    default LotBail fromId(Long id) {
        if (id == null) {
            return null;
        }
        LotBail lotBail = new LotBail();
        lotBail.setId(id);
        return lotBail;
    }
}
