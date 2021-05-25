package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.BailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bail} and its DTO {@link BailDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocataireMapper.class, GarantMapper.class})
public interface BailMapper extends EntityMapper<BailDTO, Bail> {

    @Mapping(source = "locataire.id", target = "locataireId")
    @Mapping(source = "garant.id", target = "garantId")
    BailDTO toDto(Bail bail);

    @Mapping(source = "locataireId", target = "locataire")
    @Mapping(source = "garantId", target = "garant")
    Bail toEntity(BailDTO bailDTO);

    default Bail fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bail bail = new Bail();
        bail.setId(id);
        return bail;
    }
}
