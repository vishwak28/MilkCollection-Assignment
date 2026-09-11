package com.milkcollection.repository;

import com.milkcollection.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    List<Farmer> findByCollectionPointId(Long collectionPointId);
}