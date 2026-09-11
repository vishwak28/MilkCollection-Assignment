package com.milkcollection.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TankerLocationResponse {
    private Long collectionPointId;
    private String collectionPointName;
    private Long runId;
    private String runStatus;
    private String lastStopName;
    private LocalDateTime lastStopTime;
    private String nextStopName;
    private String message;
}