package com.fininfo.java.repository;

import com.fininfo.java.domain.Counter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Counter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CounterRepository extends JpaRepository<Counter, Long>, JpaSpecificationExecutor<Counter> {
}
