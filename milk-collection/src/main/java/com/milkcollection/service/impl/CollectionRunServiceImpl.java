package com.milkcollection.service.impl;

import com.milkcollection.dto.*;
import com.milkcollection.entity.*;
import com.milkcollection.enums.RunStatus;
import com.milkcollection.enums.Shift;
import com.milkcollection.enums.StopStatus;
import com.milkcollection.exception.ResourceNotFoundException;
import com.milkcollection.repository.CollectionRunRepository;
import com.milkcollection.repository.RouteStopRepository;
import com.milkcollection.repository.RunStopRepository;
import com.milkcollection.service.CollectionPointService;
import com.milkcollection.service.CollectionRunService;
import com.milkcollection.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionRunServiceImpl implements CollectionRunService {

    private final CollectionRunRepository collectionRunRepository;
    private final RunStopRepository runStopRepository;
    private final RouteStopRepository routeStopRepository;
    private final RouteService routeService;
    private final CollectionPointService collectionPointService;

    @Override
    public CollectionRunResponse generateRun(GenerateRunRequest request) {
        Route route = routeService.getEntityById(request.getRouteId());

        collectionRunRepository.findByRouteIdAndRunDateAndShift(route.getId(), request.getRunDate(), route.getShift())
                .ifPresent(existing -> {
                    throw new IllegalStateException("A run already exists for route " + route.getId()
                            + " on " + request.getRunDate() + " (" + route.getShift() + ")");
                });

        List<RouteStop> routeStops = routeStopRepository.findByRouteIdOrderBySequenceOrder(route.getId());
        if (routeStops.isEmpty()) {
            throw new IllegalStateException("Route has no stops defined: " + route.getId());
        }

        CollectionRun run = CollectionRun.builder()
                .route(route)
                .runDate(request.getRunDate())
                .shift(route.getShift())
                .status(RunStatus.PLANNED)
                .rejected(false)
                .build();
        run = collectionRunRepository.save(run);

        CollectionRun savedRun = run;
        List<RunStop> runStops = routeStops.stream()
                .map(rs -> RunStop.builder()
                        .collectionRun(savedRun)
                        .collectionPoint(rs.getCollectionPoint())
                        .sequenceOrder(rs.getSequenceOrder())
                        .status(StopStatus.PENDING)
                        .build())
                .toList();
        runStopRepository.saveAll(runStops);

        return CollectionRunResponse.from(run);
    }

    @Override
    public CollectionRunResponse getById(Long runId) {
        return CollectionRunResponse.from(getRunEntity(runId));
    }

    @Override
    public List<CollectionRunResponse> listByDateAndShift(LocalDate runDate, Shift shift) {
        return collectionRunRepository.findByRunDateAndShift(runDate, shift)
                .stream().map(CollectionRunResponse::from).toList();
    }

    @Override
    public List<RunStopResponse> getStops(Long runId) {
        return runStopRepository.findByCollectionRunIdOrderBySequenceOrder(runId)
                .stream().map(RunStopResponse::from).toList();
    }

    @Override
    public RunStopResponse markArrived(Long runId, Long collectionPointId, MarkArrivedRequest request) {
        CollectionRun run = getRunEntity(runId);
        validateRunIsActive(run);
        RunStop stop = getRunStop(runId, collectionPointId);

        stop.setStatus(StopStatus.ARRIVED);
        stop.setArrivedAt(LocalDateTime.now());
        stop.setQuantityLitres(request.getQuantityLitres());
        runStopRepository.save(stop);

        startRunIfNeeded(run);
        return RunStopResponse.from(stop);
    }

    @Override
    public RunStopResponse markSkipped(Long runId, Long collectionPointId, MarkSkippedRequest request) {
        CollectionRun run = getRunEntity(runId);
        validateRunIsActive(run);
        RunStop stop = getRunStop(runId, collectionPointId);

        stop.setStatus(StopStatus.SKIPPED);
        stop.setArrivedAt(LocalDateTime.now()); // doubles as "processed at" for skipped stops
        stop.setSkippedReason(request.getReason());
        runStopRepository.save(stop);

        startRunIfNeeded(run);
        return RunStopResponse.from(stop);
    }

    @Override
    public CollectionRunResponse completeRun(Long runId) {
        CollectionRun run = getRunEntity(runId);
        if (run.getStatus() == RunStatus.COMPLETED) {
            throw new IllegalStateException("Run is already completed: " + runId);
        }
        if (run.getStatus() == RunStatus.CANCELLED) {
            throw new IllegalStateException("Run is cancelled: " + runId);
        }

        List<RunStop> stops = runStopRepository.findByCollectionRunIdOrderBySequenceOrder(runId);
        long pending = stops.stream().filter(s -> s.getStatus() == StopStatus.PENDING).count();
        if (pending > 0) {
            throw new IllegalStateException("Cannot complete run: " + pending + " stop(s) still pending");
        }

        LocalDateTime completedAt = LocalDateTime.now();
        run.setCompletedAt(completedAt);
        run.setStatus(RunStatus.COMPLETED);

        if (run.getStartedAt() != null) {
            long elapsedMinutes = Duration.between(run.getStartedAt(), completedAt).toMinutes();
            int limit = run.getRoute().getMaxTransitMinutes();
            if (elapsedMinutes > limit) {
                run.setRejected(true);
                run.setRejectionReason("Transit time " + elapsedMinutes + " min exceeded limit of " + limit + " min");
            }
        }

        collectionRunRepository.save(run);
        return CollectionRunResponse.from(run);
    }

    @Override
    public TankerLocationResponse trackByCollectionPoint(Long collectionPointId) {
        CollectionPoint point = collectionPointService.getEntityById(collectionPointId);
        List<RouteStop> routeStops = routeStopRepository.findByCollectionPointId(collectionPointId);
        LocalDate today = LocalDate.now();

        CollectionRun bestRun = null;
        for (RouteStop rs : routeStops) {
            Route route = rs.getRoute();
            Optional<CollectionRun> maybeRun = collectionRunRepository
                    .findByRouteIdAndRunDateAndShift(route.getId(), today, route.getShift());
            if (maybeRun.isPresent()) {
                CollectionRun run = maybeRun.get();
                if (run.getStatus() == RunStatus.IN_PROGRESS) {
                    bestRun = run;
                    break; // an in-progress run is always the most relevant answer
                }
                if (bestRun == null) {
                    bestRun = run;
                }
            }
        }

        if (bestRun == null) {
            return TankerLocationResponse.builder()
                    .collectionPointId(point.getId())
                    .collectionPointName(point.getName())
                    .runStatus("NOT_SCHEDULED")
                    .message("No collection run has been generated for this point today yet")
                    .build();
        }

        List<RunStop> stops = runStopRepository.findByCollectionRunIdOrderBySequenceOrder(bestRun.getId());
        RunStop lastVisited = null;
        RunStop nextStop = null;
        for (RunStop s : stops) {
            if (s.getStatus() != StopStatus.PENDING) {
                lastVisited = s;
            } else if (nextStop == null) {
                nextStop = s;
            }
        }

        return TankerLocationResponse.builder()
                .collectionPointId(point.getId())
                .collectionPointName(point.getName())
                .runId(bestRun.getId())
                .runStatus(bestRun.getStatus().name())
                .lastStopName(lastVisited != null ? lastVisited.getCollectionPoint().getName() : null)
                .lastStopTime(lastVisited != null ? lastVisited.getArrivedAt() : null)
                .nextStopName(nextStop != null ? nextStop.getCollectionPoint().getName() : null)
                .message(buildTrackingMessage(bestRun, lastVisited, nextStop))
                .build();
    }

    private String buildTrackingMessage(CollectionRun run, RunStop lastVisited, RunStop nextStop) {
        if (run.getStatus() == RunStatus.PLANNED) {
            return "Tanker hasn't started this run yet";
        }
        if (run.getStatus() == RunStatus.COMPLETED) {
            return "This run is complete" + (run.isRejected() ? " (milk was rejected: " + run.getRejectionReason() + ")" : "");
        }
        if (nextStop != null) {
            return "Tanker last at " + (lastVisited != null ? lastVisited.getCollectionPoint().getName() : "the start")
                    + ", heading to " + nextStop.getCollectionPoint().getName();
        }
        return "Tanker is en route";
    }

    private CollectionRun getRunEntity(Long runId) {
        return collectionRunRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection run not found: " + runId));
    }

    private RunStop getRunStop(Long runId, Long collectionPointId) {
        return runStopRepository.findByCollectionRunIdAndCollectionPointId(runId, collectionPointId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No stop for collection point " + collectionPointId + " on run " + runId));
    }

    private void validateRunIsActive(CollectionRun run) {
        if (run.getStatus() == RunStatus.COMPLETED || run.getStatus() == RunStatus.CANCELLED) {
            throw new IllegalStateException("Run is no longer active: " + run.getId());
        }
    }

    private void startRunIfNeeded(CollectionRun run) {
        if (run.getStatus() == RunStatus.PLANNED) {
            run.setStatus(RunStatus.IN_PROGRESS);
            run.setStartedAt(LocalDateTime.now());
            collectionRunRepository.save(run);
        }
    }
}