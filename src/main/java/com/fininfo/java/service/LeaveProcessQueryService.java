package com.fininfo.java.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.fininfo.java.domain.LeaveProcess;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.LeaveProcessRepository;
import com.fininfo.java.service.dto.LeaveProcessCriteria;
import com.fininfo.java.service.dto.LeaveProcessDTO;
import com.fininfo.java.service.mapper.LeaveProcessMapper;

/**
 * Service for executing complex queries for {@link LeaveProcess} entities in the database.
 * The main input is a {@link LeaveProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveProcessDTO} or a {@link Page} of {@link LeaveProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveProcessQueryService extends QueryService<LeaveProcess> {

    private final Logger log = LoggerFactory.getLogger(LeaveProcessQueryService.class);

    private final LeaveProcessRepository leaveProcessRepository;

    private final LeaveProcessMapper leaveProcessMapper;

    public LeaveProcessQueryService(LeaveProcessRepository leaveProcessRepository, LeaveProcessMapper leaveProcessMapper) {
        this.leaveProcessRepository = leaveProcessRepository;
        this.leaveProcessMapper = leaveProcessMapper;
    }

    /**
     * Return a {@link List} of {@link LeaveProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveProcessDTO> findByCriteria(LeaveProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveProcess> specification = createSpecification(criteria);
        return leaveProcessMapper.toDto(leaveProcessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeaveProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveProcessDTO> findByCriteria(LeaveProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveProcess> specification = createSpecification(criteria);
        return leaveProcessRepository.findAll(specification, page)
            .map(leaveProcessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveProcess> specification = createSpecification(criteria);
        return leaveProcessRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveProcessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveProcess> createSpecification(LeaveProcessCriteria criteria) {
        Specification<LeaveProcess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveProcess_.id));
            }
            if (criteria.getRentId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentId(),
                    root -> root.join(LeaveProcess_.rent, JoinType.LEFT).get(Rent_.id)));
            }
        }
        return specification;
    }
}
