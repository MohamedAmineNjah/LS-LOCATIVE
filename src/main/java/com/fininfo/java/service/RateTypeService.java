package com.fininfo.java.service;

import com.fininfo.java.domain.RateType;
import com.fininfo.java.repository.RateTypeRepository;
import com.fininfo.java.service.dto.RateTypeDTO;
import com.fininfo.java.service.mapper.RateTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RateType}.
 */
@Service
@Transactional
public class RateTypeService {

    private final Logger log = LoggerFactory.getLogger(RateTypeService.class);

    private final RateTypeRepository rateTypeRepository;

    private final RateTypeMapper rateTypeMapper;

    public RateTypeService(RateTypeRepository rateTypeRepository, RateTypeMapper rateTypeMapper) {
        this.rateTypeRepository = rateTypeRepository;
        this.rateTypeMapper = rateTypeMapper;
    }

    /**
     * Save a rateType.
     *
     * @param rateTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public RateTypeDTO save(RateTypeDTO rateTypeDTO) {
        log.debug("Request to save RateType : {}", rateTypeDTO);
        RateType rateType = rateTypeMapper.toEntity(rateTypeDTO);
        rateType = rateTypeRepository.save(rateType);
        return rateTypeMapper.toDto(rateType);
    }

    /**
     * Get all the rateTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RateTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RateTypes");
        return rateTypeRepository.findAll(pageable)
            .map(rateTypeMapper::toDto);
    }


    /**
     * Get one rateType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RateTypeDTO> findOne(Long id) {
        log.debug("Request to get RateType : {}", id);
        return rateTypeRepository.findById(id)
            .map(rateTypeMapper::toDto);
    }

    /**
     * Delete the rateType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RateType : {}", id);
        rateTypeRepository.deleteById(id);
    }
}
