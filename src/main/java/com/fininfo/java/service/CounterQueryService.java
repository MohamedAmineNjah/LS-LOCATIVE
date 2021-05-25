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

import com.fininfo.java.domain.Counter;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.CounterRepository;
import com.fininfo.java.service.dto.CounterCriteria;
import com.fininfo.java.service.dto.CounterDTO;
import com.fininfo.java.service.mapper.CounterMapper;

/**
 * Service for executing complex queries for {@link Counter} entities in the database.
 * The main input is a {@link CounterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CounterDTO} or a {@link Page} of {@link CounterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CounterQueryService extends QueryService<Counter> {

    private final Logger log = LoggerFactory.getLogger(CounterQueryService.class);

    private final CounterRepository counterRepository;

    private final CounterMapper counterMapper;

    public CounterQueryService(CounterRepository counterRepository, CounterMapper counterMapper) {
        this.counterRepository = counterRepository;
        this.counterMapper = counterMapper;
    }

    /**
     * Return a {@link List} of {@link CounterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CounterDTO> findByCriteria(CounterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Counter> specification = createSpecification(criteria);
        return counterMapper.toDto(counterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CounterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CounterDTO> findByCriteria(CounterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Counter> specification = createSpecification(criteria);
        return counterRepository.findAll(specification, page)
            .map(counterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CounterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Counter> specification = createSpecification(criteria);
        return counterRepository.count(specification);
    }

    /**
     * Function to convert {@link CounterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Counter> createSpecification(CounterCriteria criteria) {
        Specification<Counter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Counter_.id));
            }
            if (criteria.getCounterTitle() != null) {
                specification = specification.and(buildSpecification(criteria.getCounterTitle(), Counter_.counterTitle));
            }
            if (criteria.getUnity() != null) {
                specification = specification.and(buildSpecification(criteria.getUnity(), Counter_.unity));
            }
            if (criteria.getDecimal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDecimal(), Counter_.decimal));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Counter_.comment));
            }
            if (criteria.getLotBailId() != null) {
                specification = specification.and(buildSpecification(criteria.getLotBailId(),
                    root -> root.join(Counter_.lotBail, JoinType.LEFT).get(LotBail_.id)));
            }
        }
        return specification;
    }
}
