package com.milkcollection.dto;

import com.milkcollection.entity.RouteStop;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStopResponse {
    private Long id;
    private Long collectionPointId;
    private String collectionPointName;
    private Integer sequenceOrder;

    public static RouteStopResponse from(RouteStop rs) {
        return RouteStopResponse.builder()
                .id(rs.getId())
                .collectionPointId(rs.getCollectionPoint().getId())
                .collectionPointName(rs.getCollectionPoint().getName())
                .sequenceOrder(rs.getSequenceOrder())
                .build();
    }
}