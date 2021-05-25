package com.fininfo.java.repository;

import com.fininfo.java.domain.LocationRegulation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LocationRegulation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRegulationRepository extends JpaRepository<LocationRegulation, Long>, JpaSpecificationExecutor<LocationRegulation> {
}
