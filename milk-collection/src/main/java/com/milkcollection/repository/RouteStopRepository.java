package com.milkcollection.repository;

import com.milkcollection.entity.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {
    List<RouteStop> findByRouteIdOrderBySequenceOrder(Long routeId);

    // Used to answer "which route does this collection point belong to" for the tracking lookup
    List<RouteStop> findByCollectionPointId(Long collectionPointId);
}