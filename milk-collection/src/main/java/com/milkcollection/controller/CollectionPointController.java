package com.milkcollection.controller;

import com.milkcollection.dto.CollectionPointRequest;
import com.milkcollection.dto.CollectionPointResponse;
import com.milkcollection.service.CollectionPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection-points")
@RequiredArgsConstructor
public class CollectionPointController {

    private final CollectionPointService collectionPointService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionPointResponse create(@RequestBody CollectionPointRequest request) {
        return collectionPointService.create(request);
    }

    @GetMapping
    public List<CollectionPointResponse> listAll(@RequestParam(required = false) Long villageId) {
        return villageId != null ? collectionPointService.listByVillage(villageId) : collectionPointService.listAll();
    }

    @GetMapping("/{id}")
    public CollectionPointResponse getById(@PathVariable Long id) {
        return collectionPointService.getById(id);
    }
}