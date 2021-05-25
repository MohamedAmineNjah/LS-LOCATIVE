package com.fininfo.java.service;

import com.fininfo.java.domain.ClosingEvent;
import com.fininfo.java.repository.ClosingEventRepository;
import com.fininfo.java.service.dto.ClosingEventDTO;
import com.fininfo.java.service.mapper.ClosingEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClosingEvent}.
 */
@Service
@Transactional
public class ClosingEventService {

    private final Logger log = LoggerFactory.getLogger(ClosingEventService.class);

    private final ClosingEventRepository closingEventRepository;

    private final ClosingEventMapper closingEventMapper;

    public ClosingEventService(ClosingEventRepository closingEventRepository, ClosingEventMapper closingEventMapper) {
        this.closingEventRepository = closingEventRepository;
        this.closingEventMapper = closingEventMapper;
    }

    /**
     * Save a closingEvent.
     *
     * @param closingEventDTO the entity to save.
     * @return the persisted entity.
     */
    public ClosingEventDTO save(ClosingEventDTO closingEventDTO) {
        log.debug("Request to save ClosingEvent : {}", closingEventDTO);
        ClosingEvent closingEvent = closingEventMapper.toEntity(closingEventDTO);
        closingEvent = closingEventRepository.save(closingEvent);
        return closingEventMapper.toDto(closingEvent);
    }

    /**
     * Get all the closingEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClosingEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClosingEvents");
        return closingEventRepository.findAll(pageable)
            .map(closingEventMapper::toDto);
    }


    /**
     * Get one closingEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClosingEventDTO> findOne(Long id) {
        log.debug("Request to get ClosingEvent : {}", id);
        return closingEventRepository.findById(id)
            .map(closingEventMapper::toDto);
    }

    /**
     * Delete the closingEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClosingEvent : {}", id);
        closingEventRepository.deleteById(id);
    }
}
