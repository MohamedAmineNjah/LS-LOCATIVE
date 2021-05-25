package com.fininfo.java.repository;

import com.fininfo.java.domain.ClosingEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClosingEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClosingEventRepository extends JpaRepository<ClosingEvent, Long>, JpaSpecificationExecutor<ClosingEvent> {
}
