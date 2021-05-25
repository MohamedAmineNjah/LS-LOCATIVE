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

import com.fininfo.java.domain.Garant;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.GarantRepository;
import com.fininfo.java.service.dto.GarantCriteria;
import com.fininfo.java.service.dto.GarantDTO;
import com.fininfo.java.service.mapper.GarantMapper;

/**
 * Service for executing complex queries for {@link Garant} entities in the database.
 * The main input is a {@link GarantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GarantDTO} or a {@link Page} of {@link GarantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GarantQueryService extends QueryService<Garant> {

    private final Logger log = LoggerFactory.getLogger(GarantQueryService.class);

    private final GarantRepository garantRepository;

    private final GarantMapper garantMapper;

    public GarantQueryService(GarantRepository garantRepository, GarantMapper garantMapper) {
        this.garantRepository = garantRepository;
        this.garantMapper = garantMapper;
    }

    /**
     * Return a {@link List} of {@link GarantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GarantDTO> findByCriteria(GarantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Garant> specification = createSpecification(criteria);
        return garantMapper.toDto(garantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GarantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GarantDTO> findByCriteria(GarantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Garant> specification = createSpecification(criteria);
        return garantRepository.findAll(specification, page)
            .map(garantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GarantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Garant> specification = createSpecification(criteria);
        return garantRepository.count(specification);
    }

    /**
     * Function to convert {@link GarantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Garant> createSpecification(GarantCriteria criteria) {
        Specification<Garant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Garant_.id));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Garant_.birthDate));
            }
            if (criteria.getProfession() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfession(), Garant_.profession));
            }
            if (criteria.getnAllocataireCAF() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getnAllocataireCAF(), Garant_.nAllocataireCAF));
            }
        }
        return specification;
    }
}
