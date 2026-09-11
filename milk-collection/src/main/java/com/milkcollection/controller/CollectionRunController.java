package com.milkcollection.controller;

import com.milkcollection.dto.*;
import com.milkcollection.enums.Shift;
import com.milkcollection.service.CollectionRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/runs")
@RequiredArgsConstructor
public class CollectionRunController {

    private final CollectionRunService collectionRunService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionRunResponse generate(@RequestBody GenerateRunRequest request) {
        return collectionRunService.generateRun(request);
    }

    @GetMapping("/{id}")
    public CollectionRunResponse getById(@PathVariable Long id) {
        return collectionRunService.getById(id);
    }

    @GetMapping
    public List<CollectionRunResponse> listByDateAndShift(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate runDate,
            @RequestParam Shift shift) {
        return collectionRunService.listByDateAndShift(runDate, shift);
    }

    @GetMapping("/{id}/stops")
    public List<RunStopResponse> getStops(@PathVariable Long id) {
        return collectionRunService.getStops(id);
    }

    @PostMapping("/{runId}/stops/{collectionPointId}/arrive")
    public RunStopResponse markArrived(
            @PathVariable Long runId,
            @PathVariable Long collectionPointId,
            @RequestBody MarkArrivedRequest request) {
        return collectionRunService.markArrived(runId, collectionPointId, request);
    }

    @PostMapping("/{runId}/stops/{collectionPointId}/skip")
    public RunStopResponse markSkipped(
            @PathVariable Long runId,
            @PathVariable Long collectionPointId,
            @RequestBody MarkSkippedRequest request) {
        return collectionRunService.markSkipped(runId, collectionPointId, request);
    }

    @PostMapping("/{id}/complete")
    public CollectionRunResponse complete(@PathVariable Long id) {
        return collectionRunService.completeRun(id);
    }
}