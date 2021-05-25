package com.fininfo.java.repository;

import com.fininfo.java.domain.Locataire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Locataire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocataireRepository extends JpaRepository<Locataire, Long>, JpaSpecificationExecutor<Locataire> {
}
