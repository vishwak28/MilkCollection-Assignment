package com.milkcollection.dto;

import com.milkcollection.entity.CollectionRun;
import com.milkcollection.enums.RunStatus;
import com.milkcollection.enums.Shift;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionRunResponse {
    private Long id;
    private Long routeId;
    private String routeName;
    private LocalDate runDate;
    private Shift shift;
    private RunStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private boolean rejected;
    private String rejectionReason;

    public static CollectionRunResponse from(CollectionRun run) {
        return CollectionRunResponse.builder()
                .id(run.getId())
                .routeId(run.getRoute().getId())
                .routeName(run.getRoute().getName())
                .runDate(run.getRunDate())
                .shift(run.getShift())
                .status(run.getStatus())
                .startedAt(run.getStartedAt())
                .completedAt(run.getCompletedAt())
                .rejected(run.isRejected())
                .rejectionReason(run.getRejectionReason())
                .build();
    }
}