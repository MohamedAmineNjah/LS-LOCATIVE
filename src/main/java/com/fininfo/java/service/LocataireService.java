package com.fininfo.java.service;

import com.fininfo.java.domain.Locataire;
import com.fininfo.java.repository.LocataireRepository;
import com.fininfo.java.service.dto.LocataireDTO;
import com.fininfo.java.service.mapper.LocataireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Locataire}.
 */
@Service
@Transactional
public class LocataireService {

    private final Logger log = LoggerFactory.getLogger(LocataireService.class);

    private final LocataireRepository locataireRepository;

    private final LocataireMapper locataireMapper;

    public LocataireService(LocataireRepository locataireRepository, LocataireMapper locataireMapper) {
        this.locataireRepository = locataireRepository;
        this.locataireMapper = locataireMapper;
    }

    /**
     * Save a locataire.
     *
     * @param locataireDTO the entity to save.
     * @return the persisted entity.
     */
    public LocataireDTO save(LocataireDTO locataireDTO) {
        log.debug("Request to save Locataire : {}", locataireDTO);
        Locataire locataire = locataireMapper.toEntity(locataireDTO);
        locataire = locataireRepository.save(locataire);
        return locataireMapper.toDto(locataire);
    }

    /**
     * Get all the locataires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocataireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Locataires");
        return locataireRepository.findAll(pageable)
            .map(locataireMapper::toDto);
    }


    /**
     * Get one locataire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocataireDTO> findOne(Long id) {
        log.debug("Request to get Locataire : {}", id);
        return locataireRepository.findById(id)
            .map(locataireMapper::toDto);
    }

    /**
     * Delete the locataire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Locataire : {}", id);
        locataireRepository.deleteById(id);
    }
}
