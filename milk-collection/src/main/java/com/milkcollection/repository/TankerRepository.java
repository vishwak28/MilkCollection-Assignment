package com.milkcollection.repository;

import com.milkcollection.entity.Tanker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TankerRepository extends JpaRepository<Tanker, Long> {
    boolean existsByRegistrationNumber(String registrationNumber);
}