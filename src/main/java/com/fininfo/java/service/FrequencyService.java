package com.fininfo.java.service;

import com.fininfo.java.domain.Frequency;
import com.fininfo.java.repository.FrequencyRepository;
import com.fininfo.java.service.dto.FrequencyDTO;
import com.fininfo.java.service.mapper.FrequencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Frequency}.
 */
@Service
@Transactional
public class FrequencyService {

    private final Logger log = LoggerFactory.getLogger(FrequencyService.class);

    private final FrequencyRepository frequencyRepository;

    private final FrequencyMapper frequencyMapper;

    public FrequencyService(FrequencyRepository frequencyRepository, FrequencyMapper frequencyMapper) {
        this.frequencyRepository = frequencyRepository;
        this.frequencyMapper = frequencyMapper;
    }

    /**
     * Save a frequency.
     *
     * @param frequencyDTO the entity to save.
     * @return the persisted entity.
     */
    public FrequencyDTO save(FrequencyDTO frequencyDTO) {
        log.debug("Request to save Frequency : {}", frequencyDTO);
        Frequency frequency = frequencyMapper.toEntity(frequencyDTO);
        frequency = frequencyRepository.save(frequency);
        return frequencyMapper.toDto(frequency);
    }

    /**
     * Get all the frequencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FrequencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Frequencies");
        return frequencyRepository.findAll(pageable)
            .map(frequencyMapper::toDto);
    }


    /**
     * Get one frequency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FrequencyDTO> findOne(Long id) {
        log.debug("Request to get Frequency : {}", id);
        return frequencyRepository.findById(id)
            .map(frequencyMapper::toDto);
    }

    /**
     * Delete the frequency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Frequency : {}", id);
        frequencyRepository.deleteById(id);
    }
}
