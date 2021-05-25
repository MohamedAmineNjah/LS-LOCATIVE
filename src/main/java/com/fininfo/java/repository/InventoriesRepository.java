package com.fininfo.java.repository;

import com.fininfo.java.domain.Inventories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inventories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoriesRepository extends JpaRepository<Inventories, Long>, JpaSpecificationExecutor<Inventories> {
}
