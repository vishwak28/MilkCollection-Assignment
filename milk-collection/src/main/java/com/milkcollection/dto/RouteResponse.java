package com.milkcollection.dto;

import com.milkcollection.entity.Route;
import com.milkcollection.enums.Shift;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponse {
    private Long id;
    private String name;
    private Shift shift;
    private Long tankerId;
    private String tankerRegistration;
    private Integer maxTransitMinutes;
    private boolean active;

    public static RouteResponse from(Route r) {
        return RouteResponse.builder()
                .id(r.getId())
                .name(r.getName())
                .shift(r.getShift())
                .tankerId(r.getTanker().getId())
                .tankerRegistration(r.getTanker().getRegistrationNumber())
                .maxTransitMinutes(r.getMaxTransitMinutes())
                .active(r.isActive())
                .build();
    }
}