package com.milkcollection.repository;

import com.milkcollection.entity.CollectionPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Long> {
    List<CollectionPoint> findByVillageId(Long villageId);
}