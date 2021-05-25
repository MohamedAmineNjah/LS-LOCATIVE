package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.ScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Schedule} and its DTO {@link ScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {LotBailMapper.class, RentMapper.class})
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {

    @Mapping(source = "lotBail.id", target = "lotBailId")
    @Mapping(source = "rent.id", target = "rentId")
    ScheduleDTO toDto(Schedule schedule);

    @Mapping(target = "bills", ignore = true)
    @Mapping(target = "removeBill", ignore = true)
    @Mapping(source = "lotBailId", target = "lotBail")
    @Mapping(source = "rentId", target = "rent")
    Schedule toEntity(ScheduleDTO scheduleDTO);

    default Schedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setId(id);
        return schedule;
    }
}
