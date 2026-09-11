package com.milkcollection.dto;

import com.milkcollection.entity.CollectionPoint;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionPointResponse {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private boolean active;
    private Long villageId;
    private String villageName;

    public static CollectionPointResponse from(CollectionPoint cp) {
        return CollectionPointResponse.builder()
                .id(cp.getId())
                .name(cp.getName())
                .latitude(cp.getLatitude())
                .longitude(cp.getLongitude())
                .active(cp.isActive())
                .villageId(cp.getVillage().getId())
                .villageName(cp.getVillage().getName())
                .build();
    }
}