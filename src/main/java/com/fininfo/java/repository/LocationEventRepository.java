package com.fininfo.java.repository;

import com.fininfo.java.domain.LocationEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LocationEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationEventRepository extends JpaRepository<LocationEvent, Long>, JpaSpecificationExecutor<LocationEvent> {
}
