package com.milkcollection.repository;

import com.milkcollection.entity.RunStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunStopRepository extends JpaRepository<RunStop, Long> {
    List<RunStop> findByCollectionRunIdOrderBySequenceOrder(Long collectionRunId);

    Optional<RunStop> findByCollectionRunIdAndCollectionPointId(Long collectionRunId, Long collectionPointId);
}