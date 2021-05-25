package com.fininfo.java.service;

import com.fininfo.java.domain.Base;
import com.fininfo.java.repository.BaseRepository;
import com.fininfo.java.service.dto.BaseDTO;
import com.fininfo.java.service.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Base}.
 */
@Service
@Transactional
public class BaseService {

    private final Logger log = LoggerFactory.getLogger(BaseService.class);

    private final BaseRepository baseRepository;

    private final BaseMapper baseMapper;

    public BaseService(BaseRepository baseRepository, BaseMapper baseMapper) {
        this.baseRepository = baseRepository;
        this.baseMapper = baseMapper;
    }

    /**
     * Save a base.
     *
     * @param baseDTO the entity to save.
     * @return the persisted entity.
     */
    public BaseDTO save(BaseDTO baseDTO) {
        log.debug("Request to save Base : {}", baseDTO);
        Base base = baseMapper.toEntity(baseDTO);
        base = baseRepository.save(base);
        return baseMapper.toDto(base);
    }

    /**
     * Get all the bases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bases");
        return baseRepository.findAll(pageable)
            .map(baseMapper::toDto);
    }


    /**
     * Get one base by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BaseDTO> findOne(Long id) {
        log.debug("Request to get Base : {}", id);
        return baseRepository.findById(id)
            .map(baseMapper::toDto);
    }

    /**
     * Delete the base by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Base : {}", id);
        baseRepository.deleteById(id);
    }
}
