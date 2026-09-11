package com.milkcollection.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteStopRequest {
    private Long collectionPointId;
    private Integer sequenceOrder;
}