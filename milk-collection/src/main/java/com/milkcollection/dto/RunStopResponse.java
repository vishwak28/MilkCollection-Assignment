package com.milkcollection.dto;

import com.milkcollection.entity.RunStop;
import com.milkcollection.enums.StopStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunStopResponse {
    private Long id;
    private Long collectionRunId;
    private Long collectionPointId;
    private String collectionPointName;
    private Integer sequenceOrder;
    private StopStatus status;
    private LocalDateTime arrivedAt;
    private Double quantityLitres;
    private String skippedReason;

    public static RunStopResponse from(RunStop stop) {
        return RunStopResponse.builder()
                .id(stop.getId())
                .collectionRunId(stop.getCollectionRun().getId())
                .collectionPointId(stop.getCollectionPoint().getId())
                .collectionPointName(stop.getCollectionPoint().getName())
                .sequenceOrder(stop.getSequenceOrder())
                .status(stop.getStatus())
                .arrivedAt(stop.getArrivedAt())
                .quantityLitres(stop.getQuantityLitres())
                .skippedReason(stop.getSkippedReason())
                .build();
    }
}