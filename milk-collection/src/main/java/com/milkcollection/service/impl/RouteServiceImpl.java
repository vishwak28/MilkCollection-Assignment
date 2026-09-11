package com.milkcollection.service.impl;

import com.milkcollection.dto.RouteRequest;
import com.milkcollection.dto.RouteResponse;
import com.milkcollection.dto.RouteStopRequest;
import com.milkcollection.dto.RouteStopResponse;
import com.milkcollection.entity.CollectionPoint;
import com.milkcollection.entity.Route;
import com.milkcollection.entity.RouteStop;
import com.milkcollection.entity.Tanker;
import com.milkcollection.exception.ResourceNotFoundException;
import com.milkcollection.repository.RouteRepository;
import com.milkcollection.repository.RouteStopRepository;
import com.milkcollection.service.CollectionPointService;
import com.milkcollection.service.RouteService;
import com.milkcollection.service.TankerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final TankerService tankerService;
    private final CollectionPointService collectionPointService;

    public RouteResponse create(RouteRequest request) {
        Tanker tanker = tankerService.getEntityById(request.getTankerId());
        Route route = Route.builder()
                .name(request.getName())
                .shift(request.getShift())
                .tanker(tanker)
                .maxTransitMinutes(request.getMaxTransitMinutes())
                .active(true)
                .build();
        return RouteResponse.from(routeRepository.save(route));
    }

    public List<RouteResponse> listAll() {
        return routeRepository.findAll().stream().map(RouteResponse::from).toList();
    }

    public Route getEntityById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found: " + id));
    }

    public RouteResponse getById(Long id) {
        return RouteResponse.from(getEntityById(id));
    }

    public RouteStopResponse addStop(Long routeId, RouteStopRequest request) {
        Route route = getEntityById(routeId);
        CollectionPoint point = collectionPointService.getEntityById(request.getCollectionPointId());
        RouteStop stop = RouteStop.builder()
                .route(route)
                .collectionPoint(point)
                .sequenceOrder(request.getSequenceOrder())
                .build();
        return RouteStopResponse.from(routeStopRepository.save(stop));
    }

    public List<RouteStopResponse> getStops(Long routeId) {
        return routeStopRepository.findByRouteIdOrderBySequenceOrder(routeId)
                .stream().map(RouteStopResponse::from).toList();
    }
}