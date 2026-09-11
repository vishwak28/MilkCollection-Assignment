package com.milkcollection.service;

import com.milkcollection.dto.RouteRequest;
import com.milkcollection.dto.RouteResponse;
import com.milkcollection.dto.RouteStopRequest;
import com.milkcollection.dto.RouteStopResponse;
import com.milkcollection.entity.Route;

import java.util.List;

public interface RouteService {
    public RouteResponse create(RouteRequest request);
    public List<RouteResponse> listAll();
    public Route getEntityById(Long id);
    public RouteResponse getById(Long id);
    public RouteStopResponse addStop(Long routeId, RouteStopRequest request);
    public List<RouteStopResponse> getStops(Long routeId);

}
