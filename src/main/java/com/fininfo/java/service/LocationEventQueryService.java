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

import com.fininfo.java.domain.LocationEvent;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.LocationEventRepository;
import com.fininfo.java.service.dto.LocationEventCriteria;
import com.fininfo.java.service.dto.LocationEventDTO;
import com.fininfo.java.service.mapper.LocationEventMapper;

/**
 * Service for executing complex queries for {@link LocationEvent} entities in the database.
 * The main input is a {@link LocationEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocationEventDTO} or a {@link Page} of {@link LocationEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationEventQueryService extends QueryService<LocationEvent> {

    private final Logger log = LoggerFactory.getLogger(LocationEventQueryService.class);

    private final LocationEventRepository locationEventRepository;

    private final LocationEventMapper locationEventMapper;

    public LocationEventQueryService(LocationEventRepository locationEventRepository, LocationEventMapper locationEventMapper) {
        this.locationEventRepository = locationEventRepository;
        this.locationEventMapper = locationEventMapper;
    }

    /**
     * Return a {@link List} of {@link LocationEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocationEventDTO> findByCriteria(LocationEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LocationEvent> specification = createSpecification(criteria);
        return locationEventMapper.toDto(locationEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocationEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationEventDTO> findByCriteria(LocationEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationEvent> specification = createSpecification(criteria);
        return locationEventRepository.findAll(specification, page)
            .map(locationEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LocationEvent> specification = createSpecification(criteria);
        return locationEventRepository.count(specification);
    }

    /**
     * Function to convert {@link LocationEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LocationEvent> createSpecification(LocationEventCriteria criteria) {
        Specification<LocationEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LocationEvent_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), LocationEvent_.description));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), LocationEvent_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LocationEvent_.endDate));
            }
            if (criteria.getCostPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostPrice(), LocationEvent_.costPrice));
            }
            if (criteria.getDeadline() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeadline(), LocationEvent_.deadline));
            }
            if (criteria.getAssetCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetCode(), LocationEvent_.assetCode));
            }
            if (criteria.getLocationRegulationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationRegulationId(),
                    root -> root.join(LocationEvent_.locationRegulation, JoinType.LEFT).get(LocationRegulation_.id)));
            }
        }
        return specification;
    }
}
