package com.fininfo.java.service;

import com.fininfo.java.domain.Inventories;
import com.fininfo.java.repository.InventoriesRepository;
import com.fininfo.java.service.dto.InventoriesDTO;
import com.fininfo.java.service.mapper.InventoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Inventories}.
 */
@Service
@Transactional
public class InventoriesService {

    private final Logger log = LoggerFactory.getLogger(InventoriesService.class);

    private final InventoriesRepository inventoriesRepository;

    private final InventoriesMapper inventoriesMapper;

    public InventoriesService(InventoriesRepository inventoriesRepository, InventoriesMapper inventoriesMapper) {
        this.inventoriesRepository = inventoriesRepository;
        this.inventoriesMapper = inventoriesMapper;
    }

    /**
     * Save a inventories.
     *
     * @param inventoriesDTO the entity to save.
     * @return the persisted entity.
     */
    public InventoriesDTO save(InventoriesDTO inventoriesDTO) {
        log.debug("Request to save Inventories : {}", inventoriesDTO);
        Inventories inventories = inventoriesMapper.toEntity(inventoriesDTO);
        inventories = inventoriesRepository.save(inventories);
        return inventoriesMapper.toDto(inventories);
    }

    /**
     * Get all the inventories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inventories");
        return inventoriesRepository.findAll(pageable)
            .map(inventoriesMapper::toDto);
    }


    /**
     * Get one inventories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoriesDTO> findOne(Long id) {
        log.debug("Request to get Inventories : {}", id);
        return inventoriesRepository.findById(id)
            .map(inventoriesMapper::toDto);
    }

    /**
     * Delete the inventories by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Inventories : {}", id);
        inventoriesRepository.deleteById(id);
    }
}
