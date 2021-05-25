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

import com.fininfo.java.domain.Base;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.BaseRepository;
import com.fininfo.java.service.dto.BaseCriteria;
import com.fininfo.java.service.dto.BaseDTO;
import com.fininfo.java.service.mapper.BaseMapper;

/**
 * Service for executing complex queries for {@link Base} entities in the database.
 * The main input is a {@link BaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BaseDTO} or a {@link Page} of {@link BaseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BaseQueryService extends QueryService<Base> {

    private final Logger log = LoggerFactory.getLogger(BaseQueryService.class);

    private final BaseRepository baseRepository;

    private final BaseMapper baseMapper;

    public BaseQueryService(BaseRepository baseRepository, BaseMapper baseMapper) {
        this.baseRepository = baseRepository;
        this.baseMapper = baseMapper;
    }

    /**
     * Return a {@link List} of {@link BaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BaseDTO> findByCriteria(BaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Base> specification = createSpecification(criteria);
        return baseMapper.toDto(baseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BaseDTO> findByCriteria(BaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Base> specification = createSpecification(criteria);
        return baseRepository.findAll(specification, page)
            .map(baseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Base> specification = createSpecification(criteria);
        return baseRepository.count(specification);
    }

    /**
     * Function to convert {@link BaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Base> createSpecification(BaseCriteria criteria) {
        Specification<Base> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Base_.id));
            }
            if (criteria.getRentId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentId(),
                    root -> root.join(Base_.rent, JoinType.LEFT).get(Rent_.id)));
            }
        }
        return specification;
    }
}
