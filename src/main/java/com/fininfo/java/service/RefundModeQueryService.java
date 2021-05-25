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

import com.fininfo.java.domain.RefundMode;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.RefundModeRepository;
import com.fininfo.java.service.dto.RefundModeCriteria;
import com.fininfo.java.service.dto.RefundModeDTO;
import com.fininfo.java.service.mapper.RefundModeMapper;

/**
 * Service for executing complex queries for {@link RefundMode} entities in the database.
 * The main input is a {@link RefundModeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RefundModeDTO} or a {@link Page} of {@link RefundModeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RefundModeQueryService extends QueryService<RefundMode> {

    private final Logger log = LoggerFactory.getLogger(RefundModeQueryService.class);

    private final RefundModeRepository refundModeRepository;

    private final RefundModeMapper refundModeMapper;

    public RefundModeQueryService(RefundModeRepository refundModeRepository, RefundModeMapper refundModeMapper) {
        this.refundModeRepository = refundModeRepository;
        this.refundModeMapper = refundModeMapper;
    }

    /**
     * Return a {@link List} of {@link RefundModeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RefundModeDTO> findByCriteria(RefundModeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RefundMode> specification = createSpecification(criteria);
        return refundModeMapper.toDto(refundModeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RefundModeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RefundModeDTO> findByCriteria(RefundModeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RefundMode> specification = createSpecification(criteria);
        return refundModeRepository.findAll(specification, page)
            .map(refundModeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RefundModeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RefundMode> specification = createSpecification(criteria);
        return refundModeRepository.count(specification);
    }

    /**
     * Function to convert {@link RefundModeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RefundMode> createSpecification(RefundModeCriteria criteria) {
        Specification<RefundMode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RefundMode_.id));
            }
            if (criteria.getRentId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentId(),
                    root -> root.join(RefundMode_.rent, JoinType.LEFT).get(Rent_.id)));
            }
        }
        return specification;
    }
}
