package com.fininfo.java.service;

import com.fininfo.java.domain.Rent;
import com.fininfo.java.repository.RentRepository;
import com.fininfo.java.service.dto.RentDTO;
import com.fininfo.java.service.mapper.RentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Rent}.
 */
@Service
@Transactional
public class RentService {

    private final Logger log = LoggerFactory.getLogger(RentService.class);

    private final RentRepository rentRepository;

    private final RentMapper rentMapper;

    public RentService(RentRepository rentRepository, RentMapper rentMapper) {
        this.rentRepository = rentRepository;
        this.rentMapper = rentMapper;
    }

    /**
     * Save a rent.
     *
     * @param rentDTO the entity to save.
     * @return the persisted entity.
     */
    public RentDTO save(RentDTO rentDTO) {
        log.debug("Request to save Rent : {}", rentDTO);
        Rent rent = rentMapper.toEntity(rentDTO);
        rent = rentRepository.save(rent);
        return rentMapper.toDto(rent);
    }

    /**
     * Get all the rents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rents");
        return rentRepository.findAll(pageable)
            .map(rentMapper::toDto);
    }


    /**
     * Get one rent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RentDTO> findOne(Long id) {
        log.debug("Request to get Rent : {}", id);
        return rentRepository.findById(id)
            .map(rentMapper::toDto);
    }

    /**
     * Delete the rent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rent : {}", id);
        rentRepository.deleteById(id);
    }
}
