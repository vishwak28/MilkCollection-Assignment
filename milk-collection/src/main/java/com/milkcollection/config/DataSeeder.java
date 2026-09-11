package com.milkcollection.config;

import com.milkcollection.dto.*;
import com.milkcollection.enums.Shift;
import com.milkcollection.repository.VillageRepository;
import com.milkcollection.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final VillageRepository villageRepository;
    private final VillageService villageService;
    private final CollectionPointService collectionPointService;
    private final FarmerService farmerService;
    private final TankerService tankerService;
    private final RouteService routeService;
    private final CollectionRunService collectionRunService;

    @Override
    public void run(String... args) {
        if (villageRepository.count() > 0) {
            log.info("Seed data already present, skipping.");
            return;
        }
        log.info("Seeding demo data...");

        VillageResponse village = villageService.create(new VillageRequest("Rampur", 22.71, 75.85));

        CollectionPointResponse point1 = collectionPointService.create(
                new CollectionPointRequest("Rampur Chowk", 22.711, 75.851, village.getId()));
        CollectionPointResponse point2 = collectionPointService.create(
                new CollectionPointRequest("Rampur School Gate", 22.715, 75.858, village.getId()));

        farmerService.create(new FarmerRequest("Ramesh", "9876543210", point2.getId()));
        farmerService.create(new FarmerRequest("Suresh", "9876543211", point2.getId()));

        TankerResponse tanker = tankerService.create(new TankerRequest("MP09AB1234", 5000));

        RouteResponse route = routeService.create(
                new RouteRequest("Rampur Morning Route", Shift.MORNING, tanker.getId(), 120));

        routeService.addStop(route.getId(), new RouteStopRequest(point1.getId(), 1));
        routeService.addStop(route.getId(), new RouteStopRequest(point2.getId(), 2));

        collectionRunService.generateRun(new GenerateRunRequest(route.getId(), LocalDate.now()));

        log.info("Seed data ready: village={}, points=[{},{}], tanker={}, route={}",
                village.getId(), point1.getId(), point2.getId(), tanker.getId(), route.getId());
    }
}