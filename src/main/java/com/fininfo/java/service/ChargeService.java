package com.fininfo.java.service;

import com.fininfo.java.domain.Charge;
import com.fininfo.java.repository.ChargeRepository;
import com.fininfo.java.service.dto.ChargeDTO;
import com.fininfo.java.service.mapper.ChargeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Charge}.
 */
@Service
@Transactional
public class ChargeService {

    private final Logger log = LoggerFactory.getLogger(ChargeService.class);

    private final ChargeRepository chargeRepository;

    private final ChargeMapper chargeMapper;

    public ChargeService(ChargeRepository chargeRepository, ChargeMapper chargeMapper) {
        this.chargeRepository = chargeRepository;
        this.chargeMapper = chargeMapper;
    }

    /**
     * Save a charge.
     *
     * @param chargeDTO the entity to save.
     * @return the persisted entity.
     */
    public ChargeDTO save(ChargeDTO chargeDTO) {
        log.debug("Request to save Charge : {}", chargeDTO);
        Charge charge = chargeMapper.toEntity(chargeDTO);
        charge = chargeRepository.save(charge);
        return chargeMapper.toDto(charge);
    }

    /**
     * Get all the charges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChargeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Charges");
        return chargeRepository.findAll(pageable)
            .map(chargeMapper::toDto);
    }


    /**
     * Get one charge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChargeDTO> findOne(Long id) {
        log.debug("Request to get Charge : {}", id);
        return chargeRepository.findById(id)
            .map(chargeMapper::toDto);
    }

    /**
     * Delete the charge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Charge : {}", id);
        chargeRepository.deleteById(id);
    }
}
