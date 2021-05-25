package com.fininfo.java.service;

import com.fininfo.java.domain.LeaveProcess;
import com.fininfo.java.repository.LeaveProcessRepository;
import com.fininfo.java.service.dto.LeaveProcessDTO;
import com.fininfo.java.service.mapper.LeaveProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LeaveProcess}.
 */
@Service
@Transactional
public class LeaveProcessService {

    private final Logger log = LoggerFactory.getLogger(LeaveProcessService.class);

    private final LeaveProcessRepository leaveProcessRepository;

    private final LeaveProcessMapper leaveProcessMapper;

    public LeaveProcessService(LeaveProcessRepository leaveProcessRepository, LeaveProcessMapper leaveProcessMapper) {
        this.leaveProcessRepository = leaveProcessRepository;
        this.leaveProcessMapper = leaveProcessMapper;
    }

    /**
     * Save a leaveProcess.
     *
     * @param leaveProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public LeaveProcessDTO save(LeaveProcessDTO leaveProcessDTO) {
        log.debug("Request to save LeaveProcess : {}", leaveProcessDTO);
        LeaveProcess leaveProcess = leaveProcessMapper.toEntity(leaveProcessDTO);
        leaveProcess = leaveProcessRepository.save(leaveProcess);
        return leaveProcessMapper.toDto(leaveProcess);
    }

    /**
     * Get all the leaveProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveProcesses");
        return leaveProcessRepository.findAll(pageable)
            .map(leaveProcessMapper::toDto);
    }


    /**
     * Get one leaveProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveProcessDTO> findOne(Long id) {
        log.debug("Request to get LeaveProcess : {}", id);
        return leaveProcessRepository.findById(id)
            .map(leaveProcessMapper::toDto);
    }

    /**
     * Delete the leaveProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveProcess : {}", id);
        leaveProcessRepository.deleteById(id);
    }
}
