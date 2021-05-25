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

import com.fininfo.java.domain.Bail;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.BailRepository;
import com.fininfo.java.service.dto.BailCriteria;
import com.fininfo.java.service.dto.BailDTO;
import com.fininfo.java.service.mapper.BailMapper;

/**
 * Service for executing complex queries for {@link Bail} entities in the database.
 * The main input is a {@link BailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BailDTO} or a {@link Page} of {@link BailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BailQueryService extends QueryService<Bail> {

    private final Logger log = LoggerFactory.getLogger(BailQueryService.class);

    private final BailRepository bailRepository;

    private final BailMapper bailMapper;

    public BailQueryService(BailRepository bailRepository, BailMapper bailMapper) {
        this.bailRepository = bailRepository;
        this.bailMapper = bailMapper;
    }

    /**
     * Return a {@link List} of {@link BailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BailDTO> findByCriteria(BailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bail> specification = createSpecification(criteria);
        return bailMapper.toDto(bailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BailDTO> findByCriteria(BailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bail> specification = createSpecification(criteria);
        return bailRepository.findAll(specification, page)
            .map(bailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bail> specification = createSpecification(criteria);
        return bailRepository.count(specification);
    }

    /**
     * Function to convert {@link BailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bail> createSpecification(BailCriteria criteria) {
        Specification<Bail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bail_.id));
            }
            if (criteria.getCodeBail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeBail(), Bail_.codeBail));
            }
            if (criteria.getDurationBail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDurationBail(), Bail_.durationBail));
            }
            if (criteria.getTypeBail() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeBail(), Bail_.typeBail));
            }
            if (criteria.getSignatureDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSignatureDate(), Bail_.signatureDate));
            }
            if (criteria.getFirstRentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstRentDate(), Bail_.firstRentDate));
            }
            if (criteria.getDestinationLocal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinationLocal(), Bail_.destinationLocal));
            }
            if (criteria.getIdOPCI() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdOPCI(), Bail_.idOPCI));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Bail_.status));
            }
            if (criteria.getLocataireId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocataireId(),
                    root -> root.join(Bail_.locataire, JoinType.LEFT).get(Locataire_.id)));
            }
            if (criteria.getGarantId() != null) {
                specification = specification.and(buildSpecification(criteria.getGarantId(),
                    root -> root.join(Bail_.garant, JoinType.LEFT).get(Garant_.id)));
            }
        }
        return specification;
    }
}
