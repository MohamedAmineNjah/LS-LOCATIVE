package com.fininfo.java.service;

import com.fininfo.java.domain.LotBail;
import com.fininfo.java.repository.LotBailRepository;
import com.fininfo.java.service.dto.LotBailDTO;
import com.fininfo.java.service.mapper.LotBailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LotBail}.
 */
@Service
@Transactional
public class LotBailService {

    private final Logger log = LoggerFactory.getLogger(LotBailService.class);

    private final LotBailRepository lotBailRepository;

    private final LotBailMapper lotBailMapper;

    public LotBailService(LotBailRepository lotBailRepository, LotBailMapper lotBailMapper) {
        this.lotBailRepository = lotBailRepository;
        this.lotBailMapper = lotBailMapper;
    }

    /**
     * Save a lotBail.
     *
     * @param lotBailDTO the entity to save.
     * @return the persisted entity.
     */
    public LotBailDTO save(LotBailDTO lotBailDTO) {
        log.debug("Request to save LotBail : {}", lotBailDTO);
        LotBail lotBail = lotBailMapper.toEntity(lotBailDTO);
        lotBail = lotBailRepository.save(lotBail);
        return lotBailMapper.toDto(lotBail);
    }

    /**
     * Get all the lotBails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LotBailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LotBails");
        return lotBailRepository.findAll(pageable)
            .map(lotBailMapper::toDto);
    }


    /**
     * Get one lotBail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LotBailDTO> findOne(Long id) {
        log.debug("Request to get LotBail : {}", id);
        return lotBailRepository.findById(id)
            .map(lotBailMapper::toDto);
    }

    /**
     * Delete the lotBail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LotBail : {}", id);
        lotBailRepository.deleteById(id);
    }
}
