package com.fininfo.java.service;

import com.fininfo.java.domain.Garant;
import com.fininfo.java.repository.GarantRepository;
import com.fininfo.java.service.dto.GarantDTO;
import com.fininfo.java.service.mapper.GarantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Garant}.
 */
@Service
@Transactional
public class GarantService {

    private final Logger log = LoggerFactory.getLogger(GarantService.class);

    private final GarantRepository garantRepository;

    private final GarantMapper garantMapper;

    public GarantService(GarantRepository garantRepository, GarantMapper garantMapper) {
        this.garantRepository = garantRepository;
        this.garantMapper = garantMapper;
    }

    /**
     * Save a garant.
     *
     * @param garantDTO the entity to save.
     * @return the persisted entity.
     */
    public GarantDTO save(GarantDTO garantDTO) {
        log.debug("Request to save Garant : {}", garantDTO);
        Garant garant = garantMapper.toEntity(garantDTO);
        garant = garantRepository.save(garant);
        return garantMapper.toDto(garant);
    }

    /**
     * Get all the garants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GarantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Garants");
        return garantRepository.findAll(pageable)
            .map(garantMapper::toDto);
    }


    /**
     * Get one garant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GarantDTO> findOne(Long id) {
        log.debug("Request to get Garant : {}", id);
        return garantRepository.findById(id)
            .map(garantMapper::toDto);
    }

    /**
     * Delete the garant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Garant : {}", id);
        garantRepository.deleteById(id);
    }
}
