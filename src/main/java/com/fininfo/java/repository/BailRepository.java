package com.fininfo.java.repository;

import com.fininfo.java.domain.Bail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BailRepository extends JpaRepository<Bail, Long>, JpaSpecificationExecutor<Bail> {
}
