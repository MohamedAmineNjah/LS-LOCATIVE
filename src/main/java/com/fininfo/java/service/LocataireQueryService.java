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

import com.fininfo.java.domain.Locataire;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.LocataireRepository;
import com.fininfo.java.service.dto.LocataireCriteria;
import com.fininfo.java.service.dto.LocataireDTO;
import com.fininfo.java.service.mapper.LocataireMapper;

/**
 * Service for executing complex queries for {@link Locataire} entities in the database.
 * The main input is a {@link LocataireCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocataireDTO} or a {@link Page} of {@link LocataireDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocataireQueryService extends QueryService<Locataire> {

    private final Logger log = LoggerFactory.getLogger(LocataireQueryService.class);

    private final LocataireRepository locataireRepository;

    private final LocataireMapper locataireMapper;

    public LocataireQueryService(LocataireRepository locataireRepository, LocataireMapper locataireMapper) {
        this.locataireRepository = locataireRepository;
        this.locataireMapper = locataireMapper;
    }

    /**
     * Return a {@link List} of {@link LocataireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocataireDTO> findByCriteria(LocataireCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Locataire> specification = createSpecification(criteria);
        return locataireMapper.toDto(locataireRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocataireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocataireDTO> findByCriteria(LocataireCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Locataire> specification = createSpecification(criteria);
        return locataireRepository.findAll(specification, page)
            .map(locataireMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocataireCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Locataire> specification = createSpecification(criteria);
        return locataireRepository.count(specification);
    }

    /**
     * Function to convert {@link LocataireCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Locataire> createSpecification(LocataireCriteria criteria) {
        Specification<Locataire> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Locataire_.id));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Locataire_.birthDate));
            }
            if (criteria.getProfession() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfession(), Locataire_.profession));
            }
            if (criteria.getnAllocataireCAF() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getnAllocataireCAF(), Locataire_.nAllocataireCAF));
            }
        }
        return specification;
    }
}
