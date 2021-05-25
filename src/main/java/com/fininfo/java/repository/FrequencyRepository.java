package com.fininfo.java.repository;

import com.fininfo.java.domain.Frequency;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Frequency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrequencyRepository extends JpaRepository<Frequency, Long>, JpaSpecificationExecutor<Frequency> {
}
