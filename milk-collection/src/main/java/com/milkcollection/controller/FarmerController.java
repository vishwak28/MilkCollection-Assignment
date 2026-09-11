package com.milkcollection.controller;

import com.milkcollection.dto.FarmerRequest;
import com.milkcollection.dto.FarmerResponse;
import com.milkcollection.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FarmerResponse create(@RequestBody FarmerRequest request) {
        return farmerService.create(request);
    }

    @GetMapping
    public List<FarmerResponse> listAll(@RequestParam(required = false) Long collectionPointId) {
        return collectionPointId != null
                ? farmerService.listByCollectionPoint(collectionPointId)
                : farmerService.listAll();
    }

    @GetMapping("/{id}")
    public FarmerResponse getById(@PathVariable Long id) {
        return farmerService.getById(id);
    }
}