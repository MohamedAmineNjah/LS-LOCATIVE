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

import com.fininfo.java.domain.Inventories;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.InventoriesRepository;
import com.fininfo.java.service.dto.InventoriesCriteria;
import com.fininfo.java.service.dto.InventoriesDTO;
import com.fininfo.java.service.mapper.InventoriesMapper;

/**
 * Service for executing complex queries for {@link Inventories} entities in the database.
 * The main input is a {@link InventoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoriesDTO} or a {@link Page} of {@link InventoriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoriesQueryService extends QueryService<Inventories> {

    private final Logger log = LoggerFactory.getLogger(InventoriesQueryService.class);

    private final InventoriesRepository inventoriesRepository;

    private final InventoriesMapper inventoriesMapper;

    public InventoriesQueryService(InventoriesRepository inventoriesRepository, InventoriesMapper inventoriesMapper) {
        this.inventoriesRepository = inventoriesRepository;
        this.inventoriesMapper = inventoriesMapper;
    }

    /**
     * Return a {@link List} of {@link InventoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoriesDTO> findByCriteria(InventoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Inventories> specification = createSpecification(criteria);
        return inventoriesMapper.toDto(inventoriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InventoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoriesDTO> findByCriteria(InventoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inventories> specification = createSpecification(criteria);
        return inventoriesRepository.findAll(specification, page)
            .map(inventoriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Inventories> specification = createSpecification(criteria);
        return inventoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inventories> createSpecification(InventoriesCriteria criteria) {
        Specification<Inventories> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Inventories_.id));
            }
            if (criteria.getInventoriesType() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoriesType(), Inventories_.inventoriesType));
            }
            if (criteria.getInventoriesDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInventoriesDate(), Inventories_.inventoriesDate));
            }
            if (criteria.getInventoriesStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInventoriesStatus(), Inventories_.inventoriesStatus));
            }
            if (criteria.getGeneralObservation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGeneralObservation(), Inventories_.generalObservation));
            }
            if (criteria.getLotBailId() != null) {
                specification = specification.and(buildSpecification(criteria.getLotBailId(),
                    root -> root.join(Inventories_.lotBail, JoinType.LEFT).get(LotBail_.id)));
            }
        }
        return specification;
    }
}
