package com.fininfo.java.repository;

import com.fininfo.java.domain.RefundFrequency;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RefundFrequency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefundFrequencyRepository extends JpaRepository<RefundFrequency, Long>, JpaSpecificationExecutor<RefundFrequency> {
}
