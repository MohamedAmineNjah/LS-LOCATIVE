package com.fininfo.java.service;

import com.fininfo.java.domain.RefundFrequency;
import com.fininfo.java.repository.RefundFrequencyRepository;
import com.fininfo.java.service.dto.RefundFrequencyDTO;
import com.fininfo.java.service.mapper.RefundFrequencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RefundFrequency}.
 */
@Service
@Transactional
public class RefundFrequencyService {

    private final Logger log = LoggerFactory.getLogger(RefundFrequencyService.class);

    private final RefundFrequencyRepository refundFrequencyRepository;

    private final RefundFrequencyMapper refundFrequencyMapper;

    public RefundFrequencyService(RefundFrequencyRepository refundFrequencyRepository, RefundFrequencyMapper refundFrequencyMapper) {
        this.refundFrequencyRepository = refundFrequencyRepository;
        this.refundFrequencyMapper = refundFrequencyMapper;
    }

    /**
     * Save a refundFrequency.
     *
     * @param refundFrequencyDTO the entity to save.
     * @return the persisted entity.
     */
    public RefundFrequencyDTO save(RefundFrequencyDTO refundFrequencyDTO) {
        log.debug("Request to save RefundFrequency : {}", refundFrequencyDTO);
        RefundFrequency refundFrequency = refundFrequencyMapper.toEntity(refundFrequencyDTO);
        refundFrequency = refundFrequencyRepository.save(refundFrequency);
        return refundFrequencyMapper.toDto(refundFrequency);
    }

    /**
     * Get all the refundFrequencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RefundFrequencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefundFrequencies");
        return refundFrequencyRepository.findAll(pageable)
            .map(refundFrequencyMapper::toDto);
    }


    /**
     * Get one refundFrequency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RefundFrequencyDTO> findOne(Long id) {
        log.debug("Request to get RefundFrequency : {}", id);
        return refundFrequencyRepository.findById(id)
            .map(refundFrequencyMapper::toDto);
    }

    /**
     * Delete the refundFrequency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RefundFrequency : {}", id);
        refundFrequencyRepository.deleteById(id);
    }
}
