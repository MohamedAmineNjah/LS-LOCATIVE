package com.fininfo.java.repository;

import com.fininfo.java.domain.LeaveProcess;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LeaveProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveProcessRepository extends JpaRepository<LeaveProcess, Long>, JpaSpecificationExecutor<LeaveProcess> {
}
