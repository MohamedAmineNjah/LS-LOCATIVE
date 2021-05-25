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

import com.fininfo.java.domain.Frequency;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.FrequencyRepository;
import com.fininfo.java.service.dto.FrequencyCriteria;
import com.fininfo.java.service.dto.FrequencyDTO;
import com.fininfo.java.service.mapper.FrequencyMapper;

/**
 * Service for executing complex queries for {@link Frequency} entities in the database.
 * The main input is a {@link FrequencyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FrequencyDTO} or a {@link Page} of {@link FrequencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FrequencyQueryService extends QueryService<Frequency> {

    private final Logger log = LoggerFactory.getLogger(FrequencyQueryService.class);

    private final FrequencyRepository frequencyRepository;

    private final FrequencyMapper frequencyMapper;

    public FrequencyQueryService(FrequencyRepository frequencyRepository, FrequencyMapper frequencyMapper) {
        this.frequencyRepository = frequencyRepository;
        this.frequencyMapper = frequencyMapper;
    }

    /**
     * Return a {@link List} of {@link FrequencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FrequencyDTO> findByCriteria(FrequencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Frequency> specification = createSpecification(criteria);
        return frequencyMapper.toDto(frequencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FrequencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FrequencyDTO> findByCriteria(FrequencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Frequency> specification = createSpecification(criteria);
        return frequencyRepository.findAll(specification, page)
            .map(frequencyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FrequencyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Frequency> specification = createSpecification(criteria);
        return frequencyRepository.count(specification);
    }

    /**
     * Function to convert {@link FrequencyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Frequency> createSpecification(FrequencyCriteria criteria) {
        Specification<Frequency> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Frequency_.id));
            }
            if (criteria.getRentId() != null) {
                specification = specification.and(buildSpecification(criteria.getRentId(),
                    root -> root.join(Frequency_.rent, JoinType.LEFT).get(Rent_.id)));
            }
        }
        return specification;
    }
}
