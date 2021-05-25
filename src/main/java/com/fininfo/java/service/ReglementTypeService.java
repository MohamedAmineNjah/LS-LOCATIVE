package com.fininfo.java.service;

import com.fininfo.java.domain.ReglementType;
import com.fininfo.java.repository.ReglementTypeRepository;
import com.fininfo.java.service.dto.ReglementTypeDTO;
import com.fininfo.java.service.mapper.ReglementTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReglementType}.
 */
@Service
@Transactional
public class ReglementTypeService {

    private final Logger log = LoggerFactory.getLogger(ReglementTypeService.class);

    private final ReglementTypeRepository reglementTypeRepository;

    private final ReglementTypeMapper reglementTypeMapper;

    public ReglementTypeService(ReglementTypeRepository reglementTypeRepository, ReglementTypeMapper reglementTypeMapper) {
        this.reglementTypeRepository = reglementTypeRepository;
        this.reglementTypeMapper = reglementTypeMapper;
    }

    /**
     * Save a reglementType.
     *
     * @param reglementTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ReglementTypeDTO save(ReglementTypeDTO reglementTypeDTO) {
        log.debug("Request to save ReglementType : {}", reglementTypeDTO);
        ReglementType reglementType = reglementTypeMapper.toEntity(reglementTypeDTO);
        reglementType = reglementTypeRepository.save(reglementType);
        return reglementTypeMapper.toDto(reglementType);
    }

    /**
     * Get all the reglementTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReglementTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReglementTypes");
        return reglementTypeRepository.findAll(pageable)
            .map(reglementTypeMapper::toDto);
    }


    /**
     * Get one reglementType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReglementTypeDTO> findOne(Long id) {
        log.debug("Request to get ReglementType : {}", id);
        return reglementTypeRepository.findById(id)
            .map(reglementTypeMapper::toDto);
    }

    /**
     * Delete the reglementType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReglementType : {}", id);
        reglementTypeRepository.deleteById(id);
    }
}
