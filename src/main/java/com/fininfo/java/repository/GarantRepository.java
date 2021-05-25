package com.fininfo.java.repository;

import com.fininfo.java.domain.Garant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Garant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GarantRepository extends JpaRepository<Garant, Long>, JpaSpecificationExecutor<Garant> {
}
