package com.fininfo.java.repository;

import com.fininfo.java.domain.RefundMode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RefundMode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefundModeRepository extends JpaRepository<RefundMode, Long>, JpaSpecificationExecutor<RefundMode> {
}
