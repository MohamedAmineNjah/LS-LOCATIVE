package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.ReglementTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReglementType} and its DTO {@link ReglementTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationRegulationMapper.class})
public interface ReglementTypeMapper extends EntityMapper<ReglementTypeDTO, ReglementType> {

    @Mapping(source = "locationRegulation.id", target = "locationRegulationId")
    ReglementTypeDTO toDto(ReglementType reglementType);

    @Mapping(source = "locationRegulationId", target = "locationRegulation")
    ReglementType toEntity(ReglementTypeDTO reglementTypeDTO);

    default ReglementType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReglementType reglementType = new ReglementType();
        reglementType.setId(id);
        return reglementType;
    }
}
