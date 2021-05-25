package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.BillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bill} and its DTO {@link BillDTO}.
 */
@Mapper(componentModel = "spring", uses = {ScheduleMapper.class})
public interface BillMapper extends EntityMapper<BillDTO, Bill> {

    @Mapping(source = "schedule.id", target = "scheduleId")
    BillDTO toDto(Bill bill);

    @Mapping(source = "scheduleId", target = "schedule")
    Bill toEntity(BillDTO billDTO);

    default Bill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bill bill = new Bill();
        bill.setId(id);
        return bill;
    }
}
