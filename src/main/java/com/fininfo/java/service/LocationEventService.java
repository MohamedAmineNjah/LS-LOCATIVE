package com.fininfo.java.service;

import com.fininfo.java.domain.LocationEvent;
import com.fininfo.java.repository.LocationEventRepository;
import com.fininfo.java.service.dto.LocationEventDTO;
import com.fininfo.java.service.mapper.LocationEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocationEvent}.
 */
@Service
@Transactional
public class LocationEventService {

    private final Logger log = LoggerFactory.getLogger(LocationEventService.class);

    private final LocationEventRepository locationEventRepository;

    private final LocationEventMapper locationEventMapper;

    public LocationEventService(LocationEventRepository locationEventRepository, LocationEventMapper locationEventMapper) {
        this.locationEventRepository = locationEventRepository;
        this.locationEventMapper = locationEventMapper;
    }

    /**
     * Save a locationEvent.
     *
     * @param locationEventDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationEventDTO save(LocationEventDTO locationEventDTO) {
        log.debug("Request to save LocationEvent : {}", locationEventDTO);
        LocationEvent locationEvent = locationEventMapper.toEntity(locationEventDTO);
        locationEvent = locationEventRepository.save(locationEvent);
        return locationEventMapper.toDto(locationEvent);
    }

    /**
     * Get all the locationEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationEvents");
        return locationEventRepository.findAll(pageable)
            .map(locationEventMapper::toDto);
    }


    /**
     * Get one locationEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationEventDTO> findOne(Long id) {
        log.debug("Request to get LocationEvent : {}", id);
        return locationEventRepository.findById(id)
            .map(locationEventMapper::toDto);
    }

    /**
     * Delete the locationEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationEvent : {}", id);
        locationEventRepository.deleteById(id);
    }
}
