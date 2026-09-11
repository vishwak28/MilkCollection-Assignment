package com.milkcollection.controller;

import com.milkcollection.dto.TankerLocationResponse;
import com.milkcollection.service.CollectionRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/track")
@RequiredArgsConstructor
public class TrackingController {

    private final CollectionRunService collectionRunService;

    @GetMapping("/collection-points/{collectionPointId}")
    public TankerLocationResponse track(@PathVariable Long collectionPointId) {
        return collectionRunService.trackByCollectionPoint(collectionPointId);
    }
}