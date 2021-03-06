package com.fininfo.java.repository;

import com.fininfo.java.domain.Rent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Rent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentRepository extends JpaRepository<Rent, Long>, JpaSpecificationExecutor<Rent> {
}
