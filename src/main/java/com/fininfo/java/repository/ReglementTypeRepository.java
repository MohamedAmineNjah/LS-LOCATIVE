package com.fininfo.java.repository;

import com.fininfo.java.domain.ReglementType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReglementType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReglementTypeRepository extends JpaRepository<ReglementType, Long>, JpaSpecificationExecutor<ReglementType> {
}
