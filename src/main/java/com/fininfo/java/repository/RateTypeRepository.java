package com.fininfo.java.repository;

import com.fininfo.java.domain.RateType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateTypeRepository extends JpaRepository<RateType, Long>, JpaSpecificationExecutor<RateType> {
}
