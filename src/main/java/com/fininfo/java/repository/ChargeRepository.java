package com.fininfo.java.repository;

import com.fininfo.java.domain.Charge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Charge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long>, JpaSpecificationExecutor<Charge> {
}
