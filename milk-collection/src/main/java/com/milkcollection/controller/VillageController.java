package com.milkcollection.controller;

import com.milkcollection.dto.VillageRequest;
import com.milkcollection.dto.VillageResponse;
import com.milkcollection.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/villages")
@RequiredArgsConstructor
public class VillageController {

    private final VillageService villageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VillageResponse create(@RequestBody VillageRequest request) {
        return villageService.create(request);
    }

    @GetMapping
    public List<VillageResponse> listAll() {
        return villageService.listAll();
    }

    @GetMapping("/{id}")
    public VillageResponse getById(@PathVariable Long id) {
        return villageService.getById(id);
    }
}