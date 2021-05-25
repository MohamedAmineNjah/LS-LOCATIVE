package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.LocataireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Locataire} and its DTO {@link LocataireDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocataireMapper extends EntityMapper<LocataireDTO, Locataire> {



    default Locataire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Locataire locataire = new Locataire();
        locataire.setId(id);
        return locataire;
    }
}
