package com.fininfo.java.repository;

import com.fininfo.java.domain.Schedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Schedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {
}
