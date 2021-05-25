package com.fininfo.java.service;

import com.fininfo.java.domain.Bail;
import com.fininfo.java.repository.BailRepository;
import com.fininfo.java.service.dto.BailDTO;
import com.fininfo.java.service.mapper.BailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bail}.
 */
@Service
@Transactional
public class BailService {

    private final Logger log = LoggerFactory.getLogger(BailService.class);

    private final BailRepository bailRepository;

    private final BailMapper bailMapper;

    public BailService(BailRepository bailRepository, BailMapper bailMapper) {
        this.bailRepository = bailRepository;
        this.bailMapper = bailMapper;
    }

    /**
     * Save a bail.
     *
     * @param bailDTO the entity to save.
     * @return the persisted entity.
     */
    public BailDTO save(BailDTO bailDTO) {
        log.debug("Request to save Bail : {}", bailDTO);
        Bail bail = bailMapper.toEntity(bailDTO);
        bail = bailRepository.save(bail);
        return bailMapper.toDto(bail);
    }

    /**
     * Get all the bails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bails");
        return bailRepository.findAll(pageable)
            .map(bailMapper::toDto);
    }


    /**
     * Get one bail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BailDTO> findOne(Long id) {
        log.debug("Request to get Bail : {}", id);
        return bailRepository.findById(id)
            .map(bailMapper::toDto);
    }

    /**
     * Delete the bail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bail : {}", id);
        bailRepository.deleteById(id);
    }
}
