package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.InventoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventories} and its DTO {@link InventoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {LotBailMapper.class})
public interface InventoriesMapper extends EntityMapper<InventoriesDTO, Inventories> {

    @Mapping(source = "lotBail.id", target = "lotBailId")
    @Mapping(source = "lotBail.name", target = "lotBailName")
    InventoriesDTO toDto(Inventories inventories);

    @Mapping(source = "lotBailId", target = "lotBail")
    Inventories toEntity(InventoriesDTO inventoriesDTO);

    default Inventories fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inventories inventories = new Inventories();
        inventories.setId(id);
        return inventories;
    }
}
