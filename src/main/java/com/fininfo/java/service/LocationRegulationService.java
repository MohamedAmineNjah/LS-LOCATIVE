package com.fininfo.java.service;

import com.fininfo.java.domain.LocationRegulation;
import com.fininfo.java.repository.LocationRegulationRepository;
import com.fininfo.java.service.dto.LocationRegulationDTO;
import com.fininfo.java.service.mapper.LocationRegulationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocationRegulation}.
 */
@Service
@Transactional
public class LocationRegulationService {

    private final Logger log = LoggerFactory.getLogger(LocationRegulationService.class);

    private final LocationRegulationRepository locationRegulationRepository;

    private final LocationRegulationMapper locationRegulationMapper;

    public LocationRegulationService(LocationRegulationRepository locationRegulationRepository, LocationRegulationMapper locationRegulationMapper) {
        this.locationRegulationRepository = locationRegulationRepository;
        this.locationRegulationMapper = locationRegulationMapper;
    }

    /**
     * Save a locationRegulation.
     *
     * @param locationRegulationDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationRegulationDTO save(LocationRegulationDTO locationRegulationDTO) {
        log.debug("Request to save LocationRegulation : {}", locationRegulationDTO);
        LocationRegulation locationRegulation = locationRegulationMapper.toEntity(locationRegulationDTO);
        locationRegulation = locationRegulationRepository.save(locationRegulation);
        return locationRegulationMapper.toDto(locationRegulation);
    }

    /**
     * Get all the locationRegulations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationRegulationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationRegulations");
        return locationRegulationRepository.findAll(pageable)
            .map(locationRegulationMapper::toDto);
    }


    /**
     * Get one locationRegulation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationRegulationDTO> findOne(Long id) {
        log.debug("Request to get LocationRegulation : {}", id);
        return locationRegulationRepository.findById(id)
            .map(locationRegulationMapper::toDto);
    }

    /**
     * Delete the locationRegulation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationRegulation : {}", id);
        locationRegulationRepository.deleteById(id);
    }
}
