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

import com.fininfo.java.domain.ClosingEvent;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.ClosingEventRepository;
import com.fininfo.java.service.dto.ClosingEventCriteria;
import com.fininfo.java.service.dto.ClosingEventDTO;
import com.fininfo.java.service.mapper.ClosingEventMapper;

/**
 * Service for executing complex queries for {@link ClosingEvent} entities in the database.
 * The main input is a {@link ClosingEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClosingEventDTO} or a {@link Page} of {@link ClosingEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClosingEventQueryService extends QueryService<ClosingEvent> {

    private final Logger log = LoggerFactory.getLogger(ClosingEventQueryService.class);

    private final ClosingEventRepository closingEventRepository;

    private final ClosingEventMapper closingEventMapper;

    public ClosingEventQueryService(ClosingEventRepository closingEventRepository, ClosingEventMapper closingEventMapper) {
        this.closingEventRepository = closingEventRepository;
        this.closingEventMapper = closingEventMapper;
    }

    /**
     * Return a {@link List} of {@link ClosingEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClosingEventDTO> findByCriteria(ClosingEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClosingEvent> specification = createSpecification(criteria);
        return closingEventMapper.toDto(closingEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClosingEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClosingEventDTO> findByCriteria(ClosingEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClosingEvent> specification = createSpecification(criteria);
        return closingEventRepository.findAll(specification, page)
            .map(closingEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClosingEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClosingEvent> specification = createSpecification(criteria);
        return closingEventRepository.count(specification);
    }

    /**
     * Function to convert {@link ClosingEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClosingEvent> createSpecification(ClosingEventCriteria criteria) {
        Specification<ClosingEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClosingEvent_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ClosingEvent_.description));
            }
            if (criteria.getClosingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClosingDate(), ClosingEvent_.closingDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ClosingEvent_.endDate));
            }
        }
        return specification;
    }
}
