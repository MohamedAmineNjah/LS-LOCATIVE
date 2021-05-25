package com.fininfo.java.service;

import com.fininfo.java.domain.RefundMode;
import com.fininfo.java.repository.RefundModeRepository;
import com.fininfo.java.service.dto.RefundModeDTO;
import com.fininfo.java.service.mapper.RefundModeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RefundMode}.
 */
@Service
@Transactional
public class RefundModeService {

    private final Logger log = LoggerFactory.getLogger(RefundModeService.class);

    private final RefundModeRepository refundModeRepository;

    private final RefundModeMapper refundModeMapper;

    public RefundModeService(RefundModeRepository refundModeRepository, RefundModeMapper refundModeMapper) {
        this.refundModeRepository = refundModeRepository;
        this.refundModeMapper = refundModeMapper;
    }

    /**
     * Save a refundMode.
     *
     * @param refundModeDTO the entity to save.
     * @return the persisted entity.
     */
    public RefundModeDTO save(RefundModeDTO refundModeDTO) {
        log.debug("Request to save RefundMode : {}", refundModeDTO);
        RefundMode refundMode = refundModeMapper.toEntity(refundModeDTO);
        refundMode = refundModeRepository.save(refundMode);
        return refundModeMapper.toDto(refundMode);
    }

    /**
     * Get all the refundModes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RefundModeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefundModes");
        return refundModeRepository.findAll(pageable)
            .map(refundModeMapper::toDto);
    }


    /**
     * Get one refundMode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RefundModeDTO> findOne(Long id) {
        log.debug("Request to get RefundMode : {}", id);
        return refundModeRepository.findById(id)
            .map(refundModeMapper::toDto);
    }

    /**
     * Delete the refundMode by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RefundMode : {}", id);
        refundModeRepository.deleteById(id);
    }
}
