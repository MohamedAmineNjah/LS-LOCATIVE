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

import com.fininfo.java.domain.Bill;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.BillRepository;
import com.fininfo.java.service.dto.BillCriteria;
import com.fininfo.java.service.dto.BillDTO;
import com.fininfo.java.service.mapper.BillMapper;

/**
 * Service for executing complex queries for {@link Bill} entities in the database.
 * The main input is a {@link BillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BillDTO} or a {@link Page} of {@link BillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BillQueryService extends QueryService<Bill> {

    private final Logger log = LoggerFactory.getLogger(BillQueryService.class);

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    public BillQueryService(BillRepository billRepository, BillMapper billMapper) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
    }

    /**
     * Return a {@link List} of {@link BillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BillDTO> findByCriteria(BillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bill> specification = createSpecification(criteria);
        return billMapper.toDto(billRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BillDTO> findByCriteria(BillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bill> specification = createSpecification(criteria);
        return billRepository.findAll(specification, page)
            .map(billMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bill> specification = createSpecification(criteria);
        return billRepository.count(specification);
    }

    /**
     * Function to convert {@link BillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bill> createSpecification(BillCriteria criteria) {
        Specification<Bill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bill_.id));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Bill_.reference));
            }
            if (criteria.getAmountExludingTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountExludingTax(), Bill_.amountExludingTax));
            }
            if (criteria.getTva() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTva(), Bill_.tva));
            }
            if (criteria.getTtc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTtc(), Bill_.ttc));
            }
            if (criteria.getBillDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillDate(), Bill_.billDate));
            }
            if (criteria.getReglementMethod() != null) {
                specification = specification.and(buildSpecification(criteria.getReglementMethod(), Bill_.reglementMethod));
            }
            if (criteria.getBillStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getBillStatus(), Bill_.billStatus));
            }
            if (criteria.getScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getScheduleId(),
                    root -> root.join(Bill_.schedule, JoinType.LEFT).get(Schedule_.id)));
            }
        }
        return specification;
    }
}
