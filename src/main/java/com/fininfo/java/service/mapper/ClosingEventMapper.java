package com.fininfo.java.service.mapper;


import com.fininfo.java.domain.*;
import com.fininfo.java.service.dto.ClosingEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClosingEvent} and its DTO {@link ClosingEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClosingEventMapper extends EntityMapper<ClosingEventDTO, ClosingEvent> {



    default ClosingEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClosingEvent closingEvent = new ClosingEvent();
        closingEvent.setId(id);
        return closingEvent;
    }
}
