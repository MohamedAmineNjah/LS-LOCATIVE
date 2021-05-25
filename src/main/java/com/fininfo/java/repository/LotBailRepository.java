package com.fininfo.java.repository;

import com.fininfo.java.domain.LotBail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LotBail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LotBailRepository extends JpaRepository<LotBail, Long>, JpaSpecificationExecutor<LotBail> {
}
