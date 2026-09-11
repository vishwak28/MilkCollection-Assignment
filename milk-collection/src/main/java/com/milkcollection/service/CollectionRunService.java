package com.milkcollection.service;

import com.milkcollection.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface CollectionRunService {
    CollectionRunResponse generateRun(GenerateRunRequest request);
    CollectionRunResponse getById(Long runId);
    List<CollectionRunResponse> listByDateAndShift(LocalDate runDate, com.milkcollection.enums.Shift shift);
    List<RunStopResponse> getStops(Long runId);
    RunStopResponse markArrived(Long runId, Long collectionPointId, MarkArrivedRequest request);
    RunStopResponse markSkipped(Long runId, Long collectionPointId, MarkSkippedRequest request);
    CollectionRunResponse completeRun(Long runId);
    TankerLocationResponse trackByCollectionPoint(Long collectionPointId);
}