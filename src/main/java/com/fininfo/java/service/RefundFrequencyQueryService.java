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

import com.fininfo.java.domain.RefundFrequency;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.RefundFrequencyRepository;
import com.fininfo.java.service.dto.RefundFrequencyCriteria;
import com.fininfo.java.service.dto.RefundFrequencyDTO;
import com.fininfo.java.service.mapper.RefundFrequencyMapper;

/**
 * Service for executing complex queries for {@link RefundFrequency} entities in the database.
 * The main input is a {@link RefundFrequencyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RefundFrequencyDTO} or a {@link Page} of {@link RefundFrequencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RefundFrequencyQueryService extends QueryService<RefundFrequency> {

    private final Logger log = LoggerFactory.getLogger(RefundFrequencyQueryService.class);

    private final RefundFrequencyRepository refundFrequencyRepository;

    private final RefundFrequencyMapper refundFrequencyMapper;

    public RefundFrequencyQueryService(RefundFrequencyRepository refundFrequencyRepository, RefundFrequencyMapper refundFrequencyMapper) {
        this.refundFrequencyRepository = refundFrequencyRepository;
        this.refundFrequencyMapper = refundFrequencyMapper;
    }

    /**
     * Return a {@link List} of {@link RefundFrequencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RefundFrequencyDTO> findByCriteria(RefundFrequencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RefundFrequency> specification = createSpecification(criteria);
        return refundFrequencyMapper.toDto(refundFrequencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RefundFrequencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RefundFrequencyDTO> findByCriteria(RefundFrequencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RefundFrequency> specification = createSpecification(criteria);
        return refundFrequencyRepository.findAll(specification, page)
            .map(refundFrequencyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RefundFrequencyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RefundFrequency> specification = createSpecification(criteria);
        return refundFrequencyRepository.count(specification);
    }

    /**
     * Function to convert {@link RefundFrequencyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RefundFrequency> createSpecification(RefundFrequencyCriteria criteria) {
        Specification<RefundFrequency> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RefundFrequency_.id));
            }
            if (criteria.getRentId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentId(),
                    root -> root.join(RefundFrequency_.rent, JoinType.LEFT).get(Rent_.id)));
            }
        }
        return specification;
    }
}
