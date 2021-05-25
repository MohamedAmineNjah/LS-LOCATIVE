package com.fininfo.java.service;

import com.fininfo.java.domain.Counter;
import com.fininfo.java.repository.CounterRepository;
import com.fininfo.java.service.dto.CounterDTO;
import com.fininfo.java.service.mapper.CounterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Counter}.
 */
@Service
@Transactional
public class CounterService {

    private final Logger log = LoggerFactory.getLogger(CounterService.class);

    private final CounterRepository counterRepository;

    private final CounterMapper counterMapper;

    public CounterService(CounterRepository counterRepository, CounterMapper counterMapper) {
        this.counterRepository = counterRepository;
        this.counterMapper = counterMapper;
    }

    /**
     * Save a counter.
     *
     * @param counterDTO the entity to save.
     * @return the persisted entity.
     */
    public CounterDTO save(CounterDTO counterDTO) {
        log.debug("Request to save Counter : {}", counterDTO);
        Counter counter = counterMapper.toEntity(counterDTO);
        counter = counterRepository.save(counter);
        return counterMapper.toDto(counter);
    }

    /**
     * Get all the counters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CounterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Counters");
        return counterRepository.findAll(pageable)
            .map(counterMapper::toDto);
    }


    /**
     * Get one counter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CounterDTO> findOne(Long id) {
        log.debug("Request to get Counter : {}", id);
        return counterRepository.findById(id)
            .map(counterMapper::toDto);
    }

    /**
     * Delete the counter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Counter : {}", id);
        counterRepository.deleteById(id);
    }
}
