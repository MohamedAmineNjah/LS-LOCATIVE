package com.fininfo.java.service;

import com.fininfo.java.domain.Periodicity;
import com.fininfo.java.repository.PeriodicityRepository;
import com.fininfo.java.service.dto.PeriodicityDTO;
import com.fininfo.java.service.mapper.PeriodicityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Periodicity}.
 */
@Service
@Transactional
public class PeriodicityService {

    private final Logger log = LoggerFactory.getLogger(PeriodicityService.class);

    private final PeriodicityRepository periodicityRepository;

    private final PeriodicityMapper periodicityMapper;

    public PeriodicityService(PeriodicityRepository periodicityRepository, PeriodicityMapper periodicityMapper) {
        this.periodicityRepository = periodicityRepository;
        this.periodicityMapper = periodicityMapper;
    }

    /**
     * Save a periodicity.
     *
     * @param periodicityDTO the entity to save.
     * @return the persisted entity.
     */
    public PeriodicityDTO save(PeriodicityDTO periodicityDTO) {
        log.debug("Request to save Periodicity : {}", periodicityDTO);
        Periodicity periodicity = periodicityMapper.toEntity(periodicityDTO);
        periodicity = periodicityRepository.save(periodicity);
        return periodicityMapper.toDto(periodicity);
    }

    /**
     * Get all the periodicities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PeriodicityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Periodicities");
        return periodicityRepository.findAll(pageable)
            .map(periodicityMapper::toDto);
    }


    /**
     * Get one periodicity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PeriodicityDTO> findOne(Long id) {
        log.debug("Request to get Periodicity : {}", id);
        return periodicityRepository.findById(id)
            .map(periodicityMapper::toDto);
    }

    /**
     * Delete the periodicity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Periodicity : {}", id);
        periodicityRepository.deleteById(id);
    }
}
