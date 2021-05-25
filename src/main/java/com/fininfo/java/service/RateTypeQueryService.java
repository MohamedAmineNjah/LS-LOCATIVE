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

import com.fininfo.java.domain.RateType;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.RateTypeRepository;
import com.fininfo.java.service.dto.RateTypeCriteria;
import com.fininfo.java.service.dto.RateTypeDTO;
import com.fininfo.java.service.mapper.RateTypeMapper;

/**
 * Service for executing complex queries for {@link RateType} entities in the database.
 * The main input is a {@link RateTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RateTypeDTO} or a {@link Page} of {@link RateTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RateTypeQueryService extends QueryService<RateType> {

    private final Logger log = LoggerFactory.getLogger(RateTypeQueryService.class);

    private final RateTypeRepository rateTypeRepository;

    private final RateTypeMapper rateTypeMapper;

    public RateTypeQueryService(RateTypeRepository rateTypeRepository, RateTypeMapper rateTypeMapper) {
        this.rateTypeRepository = rateTypeRepository;
        this.rateTypeMapper = rateTypeMapper;
    }

    /**
     * Return a {@link List} of {@link RateTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RateTypeDTO> findByCriteria(RateTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RateType> specification = createSpecification(criteria);
        return rateTypeMapper.toDto(rateTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RateTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RateTypeDTO> findByCriteria(RateTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RateType> specification = createSpecification(criteria);
        return rateTypeRepository.findAll(specification, page)
            .map(rateTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RateTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RateType> specification = createSpecification(criteria);
        return rateTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link RateTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RateType> createSpecification(RateTypeCriteria criteria) {
        Specification<RateType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RateType_.id));
            }
            if (criteria.getRentId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentId(),
                    root -> root.join(RateType_.rent, JoinType.LEFT).get(Rent_.id)));
            }
        }
        return specification;
    }
}
