package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.LeaveProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveProcess} and its DTO {@link LeaveProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {RentMapper.class})
public interface LeaveProcessMapper extends EntityMapper<LeaveProcessDTO, LeaveProcess> {

    @Mapping(source = "rent.id", target = "rentId")
    LeaveProcessDTO toDto(LeaveProcess leaveProcess);

    @Mapping(source = "rentId", target = "rent")
    LeaveProcess toEntity(LeaveProcessDTO leaveProcessDTO);

    default LeaveProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeaveProcess leaveProcess = new LeaveProcess();
        leaveProcess.setId(id);
        return leaveProcess;
    }
}
