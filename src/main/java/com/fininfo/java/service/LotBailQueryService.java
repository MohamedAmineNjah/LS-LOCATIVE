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

import com.fininfo.java.domain.LotBail;
import com.fininfo.java.domain.*; // for static metamodels
import com.fininfo.java.repository.LotBailRepository;
import com.fininfo.java.service.dto.LotBailCriteria;
import com.fininfo.java.service.dto.LotBailDTO;
import com.fininfo.java.service.mapper.LotBailMapper;

/**
 * Service for executing complex queries for {@link LotBail} entities in the database.
 * The main input is a {@link LotBailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LotBailDTO} or a {@link Page} of {@link LotBailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LotBailQueryService extends QueryService<LotBail> {

    private final Logger log = LoggerFactory.getLogger(LotBailQueryService.class);

    private final LotBailRepository lotBailRepository;

    private final LotBailMapper lotBailMapper;

    public LotBailQueryService(LotBailRepository lotBailRepository, LotBailMapper lotBailMapper) {
        this.lotBailRepository = lotBailRepository;
        this.lotBailMapper = lotBailMapper;
    }

    /**
     * Return a {@link List} of {@link LotBailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LotBailDTO> findByCriteria(LotBailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LotBail> specification = createSpecification(criteria);
        return lotBailMapper.toDto(lotBailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LotBailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LotBailDTO> findByCriteria(LotBailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LotBail> specification = createSpecification(criteria);
        return lotBailRepository.findAll(specification, page)
            .map(lotBailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LotBailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LotBail> specification = createSpecification(criteria);
        return lotBailRepository.count(specification);
    }

    /**
     * Function to convert {@link LotBailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LotBail> createSpecification(LotBailCriteria criteria) {
        Specification<LotBail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LotBail_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LotBail_.name));
            }
            if (criteria.getCodeLot() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeLot(), LotBail_.codeLot));
            }
            if (criteria.getBuilding() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBuilding(), LotBail_.building));
            }
            if (criteria.getStairs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStairs(), LotBail_.stairs));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), LotBail_.comments));
            }
            if (criteria.getTechnicalInformation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnicalInformation(), LotBail_.technicalInformation));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), LotBail_.creationDate));
            }
            if (criteria.getAcquisationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcquisationDate(), LotBail_.acquisationDate));
            }
            if (criteria.getSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurface(), LotBail_.surface));
            }
            if (criteria.getParkingsNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParkingsNumber(), LotBail_.parkingsNumber));
            }
            if (criteria.getFloorsNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsNumber(), LotBail_.floorsNumber));
            }
            if (criteria.getRealNumberOfLot() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRealNumberOfLot(), LotBail_.realNumberOfLot));
            }
            if (criteria.getNumberOfSecondaryUnits() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfSecondaryUnits(), LotBail_.numberOfSecondaryUnits));
            }
            if (criteria.getOutDoorParking() != null) {
                specification = specification.and(buildSpecification(criteria.getOutDoorParking(), LotBail_.outDoorParking));
            }
            if (criteria.getLotForMultipleOccupation() != null) {
                specification = specification.and(buildSpecification(criteria.getLotForMultipleOccupation(), LotBail_.lotForMultipleOccupation));
            }
            if (criteria.getPriceOfSquareMeter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceOfSquareMeter(), LotBail_.priceOfSquareMeter));
            }
            if (criteria.getRentalValueForSquareMeter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRentalValueForSquareMeter(), LotBail_.rentalValueForSquareMeter));
            }
            if (criteria.getTransferAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransferAmount(), LotBail_.transferAmount));
            }
            if (criteria.getAcquisationAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcquisationAmount(), LotBail_.acquisationAmount));
            }
            if (criteria.getRentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRentAmount(), LotBail_.rentAmount));
            }
            if (criteria.getPoolFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoolFactor(), LotBail_.poolFactor));
            }
            if (criteria.getMaturityDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaturityDate(), LotBail_.maturityDate));
            }
            if (criteria.getConvertibilityIndicator() != null) {
                specification = specification.and(buildSpecification(criteria.getConvertibilityIndicator(), LotBail_.convertibilityIndicator));
            }
            if (criteria.getSubordinationIndicator() != null) {
                specification = specification.and(buildSpecification(criteria.getSubordinationIndicator(), LotBail_.subordinationIndicator));
            }
            if (criteria.getIndexed() != null) {
                specification = specification.and(buildSpecification(criteria.getIndexed(), LotBail_.indexed));
            }
            if (criteria.getEligibilityIndicator() != null) {
                specification = specification.and(buildSpecification(criteria.getEligibilityIndicator(), LotBail_.eligibilityIndicator));
            }
            if (criteria.getRiskPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRiskPremium(), LotBail_.riskPremium));
            }
            if (criteria.getGotouarantorCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGotouarantorCode(), LotBail_.gotouarantorCode));
            }
            if (criteria.getGuarantorDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuarantorDescription(), LotBail_.guarantorDescription));
            }
            if (criteria.getAmortizationTableManagement() != null) {
                specification = specification.and(buildSpecification(criteria.getAmortizationTableManagement(), LotBail_.amortizationTableManagement));
            }
            if (criteria.getVariableRate() != null) {
                specification = specification.and(buildSpecification(criteria.getVariableRate(), LotBail_.variableRate));
            }
            if (criteria.getChargeId() != null) {
                specification = specification.and(buildSpecification(criteria.getChargeId(),
                    root -> root.join(LotBail_.charges, JoinType.LEFT).get(Charge_.id)));
            }
            if (criteria.getCounterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCounterId(),
                    root -> root.join(LotBail_.counters, JoinType.LEFT).get(Counter_.id)));
            }
            if (criteria.getScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getScheduleId(),
                    root -> root.join(LotBail_.schedules, JoinType.LEFT).get(Schedule_.id)));
            }
            if (criteria.getBailId() != null) {
                specification = specification.and(buildSpecification(criteria.getBailId(),
                    root -> root.join(LotBail_.bail, JoinType.LEFT).get(Bail_.id)));
            }
        }
        return specification;
    }
}
