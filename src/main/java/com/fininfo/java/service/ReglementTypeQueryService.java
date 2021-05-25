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

import com.fininfo.java.domain.ReglementType;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.ReglementTypeRepository;
import com.fininfo.java.service.dto.ReglementTypeCriteria;
import com.fininfo.java.service.dto.ReglementTypeDTO;
import com.fininfo.java.service.mapper.ReglementTypeMapper;

/**
 * Service for executing complex queries for {@link ReglementType} entities in the database.
 * The main input is a {@link ReglementTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReglementTypeDTO} or a {@link Page} of {@link ReglementTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReglementTypeQueryService extends QueryService<ReglementType> {

    private final Logger log = LoggerFactory.getLogger(ReglementTypeQueryService.class);

    private final ReglementTypeRepository reglementTypeRepository;

    private final ReglementTypeMapper reglementTypeMapper;

    public ReglementTypeQueryService(ReglementTypeRepository reglementTypeRepository, ReglementTypeMapper reglementTypeMapper) {
        this.reglementTypeRepository = reglementTypeRepository;
        this.reglementTypeMapper = reglementTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ReglementTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReglementTypeDTO> findByCriteria(ReglementTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReglementType> specification = createSpecification(criteria);
        return reglementTypeMapper.toDto(reglementTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReglementTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReglementTypeDTO> findByCriteria(ReglementTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReglementType> specification = createSpecification(criteria);
        return reglementTypeRepository.findAll(specification, page)
            .map(reglementTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReglementTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReglementType> specification = createSpecification(criteria);
        return reglementTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ReglementTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReglementType> createSpecification(ReglementTypeCriteria criteria) {
        Specification<ReglementType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReglementType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ReglementType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ReglementType_.description));
            }
            if (criteria.getLocationRegulationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationRegulationId(),
                    root -> root.join(ReglementType_.locationRegulation, JoinType.LEFT).get(LocationRegulation_.id)));
            }
        }
        return specification;
    }
}
