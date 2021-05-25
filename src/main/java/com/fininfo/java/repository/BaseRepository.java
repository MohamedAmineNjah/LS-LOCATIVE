package com.fininfo.java.repository;

import com.fininfo.java.domain.Base;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Base entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseRepository extends JpaRepository<Base, Long>, JpaSpecificationExecutor<Base> {
}
