package com.milkcollection.controller;

import com.milkcollection.dto.RouteRequest;
import com.milkcollection.dto.RouteResponse;
import com.milkcollection.dto.RouteStopRequest;
import com.milkcollection.dto.RouteStopResponse;
import com.milkcollection.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse create(@RequestBody RouteRequest request) {
        return routeService.create(request);
    }

    @GetMapping
    public List<RouteResponse> listAll() {
        return routeService.listAll();
    }

    @GetMapping("/{id}")
    public RouteResponse getById(@PathVariable Long id) {
        return routeService.getById(id);
    }

    @PostMapping("/{routeId}/stops")
    @ResponseStatus(HttpStatus.CREATED)
    public RouteStopResponse addStop(@PathVariable Long routeId, @RequestBody RouteStopRequest request) {
        return routeService.addStop(routeId, request);
    }

    @GetMapping("/{routeId}/stops")
    public List<RouteStopResponse> getStops(@PathVariable Long routeId) {
        return routeService.getStops(routeId);
    }
}