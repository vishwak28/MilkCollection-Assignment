package com.milkcollection.dto;

import com.milkcollection.entity.Village;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VillageResponse {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    public static VillageResponse from(Village v) {
        return VillageResponse.builder()
                .id(v.getId())
                .name(v.getName())
                .latitude(v.getLatitude())
                .longitude(v.getLongitude())
                .build();
    }
}