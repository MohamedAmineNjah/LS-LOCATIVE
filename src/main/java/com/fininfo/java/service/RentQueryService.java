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

import com.fininfo.java.domain.Rent;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.RentRepository;
import com.fininfo.java.service.dto.RentCriteria;
import com.fininfo.java.service.dto.RentDTO;
import com.fininfo.java.service.mapper.RentMapper;

/**
 * Service for executing complex queries for {@link Rent} entities in the database.
 * The main input is a {@link RentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RentDTO} or a {@link Page} of {@link RentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RentQueryService extends QueryService<Rent> {

    private final Logger log = LoggerFactory.getLogger(RentQueryService.class);

    private final RentRepository rentRepository;

    private final RentMapper rentMapper;

    public RentQueryService(RentRepository rentRepository, RentMapper rentMapper) {
        this.rentRepository = rentRepository;
        this.rentMapper = rentMapper;
    }

    /**
     * Return a {@link List} of {@link RentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RentDTO> findByCriteria(RentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rent> specification = createSpecification(criteria);
        return rentMapper.toDto(rentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RentDTO> findByCriteria(RentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rent> specification = createSpecification(criteria);
        return rentRepository.findAll(specification, page)
            .map(rentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rent> specification = createSpecification(criteria);
        return rentRepository.count(specification);
    }

    /**
     * Function to convert {@link RentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rent> createSpecification(RentCriteria criteria) {
        Specification<Rent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rent_.id));
            }
            if (criteria.getNominalValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNominalValue(), Rent_.nominalValue));
            }
            if (criteria.getTaux() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaux(), Rent_.taux));
            }
            if (criteria.getMode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMode(), Rent_.mode));
            }
            if (criteria.getCapital() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapital(), Rent_.capital));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Rent_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Rent_.endDate));
            }
            if (criteria.getRefundAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefundAmount(), Rent_.refundAmount));
            }
            if (criteria.getStartExcluded() != null) {
                specification = specification.and(buildSpecification(criteria.getStartExcluded(), Rent_.startExcluded));
            }
            if (criteria.getMonthEnd() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthEnd(), Rent_.monthEnd));
            }
            if (criteria.getEndExcluded() != null) {
                specification = specification.and(buildSpecification(criteria.getEndExcluded(), Rent_.endExcluded));
            }
            if (criteria.getRateValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRateValue(), Rent_.rateValue));
            }
            if (criteria.getCouponDecimalNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCouponDecimalNumber(), Rent_.couponDecimalNumber));
            }
            if (criteria.getCouponFirstDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCouponFirstDate(), Rent_.couponFirstDate));
            }
            if (criteria.getCouponLastDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCouponLastDate(), Rent_.couponLastDate));
            }
            if (criteria.getRefundFirstDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefundFirstDate(), Rent_.refundFirstDate));
            }
            if (criteria.getRefundDecimalNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefundDecimalNumber(), Rent_.refundDecimalNumber));
            }
            if (criteria.getRefundLastDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefundLastDate(), Rent_.refundLastDate));
            }
            if (criteria.getPeriodicityId() != null) {
                specification = specification.and(buildSpecification(criteria.getPeriodicityId(),
                    root -> root.join(Rent_.periodicity, JoinType.LEFT).get(Periodicity_.id)));
            }
            if (criteria.getScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getScheduleId(),
                    root -> root.join(Rent_.schedules, JoinType.LEFT).get(Schedule_.id)));
            }
        }
        return specification;
    }
}
