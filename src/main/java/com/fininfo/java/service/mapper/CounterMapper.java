package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.CounterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Counter} and its DTO {@link CounterDTO}.
 */
@Mapper(componentModel = "spring", uses = {LotBailMapper.class})
public interface CounterMapper extends EntityMapper<CounterDTO, Counter> {

    @Mapping(source = "lotBail.id", target = "lotBailId")
    CounterDTO toDto(Counter counter);

    @Mapping(source = "lotBailId", target = "lotBail")
    Counter toEntity(CounterDTO counterDTO);

    default Counter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Counter counter = new Counter();
        counter.setId(id);
        return counter;
    }
}
