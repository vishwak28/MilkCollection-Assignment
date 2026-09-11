package com.milkcollection.repository;

import com.milkcollection.entity.Village;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VillageRepository extends JpaRepository<Village, Long> {
}