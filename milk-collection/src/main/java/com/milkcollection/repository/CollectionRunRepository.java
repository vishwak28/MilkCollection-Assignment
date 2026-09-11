package com.milkcollection.repository;

import com.milkcollection.entity.CollectionRun;
import com.milkcollection.enums.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CollectionRunRepository extends JpaRepository<CollectionRun, Long> {
    // Prevents duplicate run generation for the same route/date/shift
    Optional<CollectionRun> findByRouteIdAndRunDateAndShift(Long routeId, LocalDate runDate, Shift shift);

    List<CollectionRun> findByRunDateAndShift(LocalDate runDate, Shift shift);
}