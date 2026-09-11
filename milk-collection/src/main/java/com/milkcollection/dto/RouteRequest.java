package com.milkcollection.dto;

import com.milkcollection.enums.Shift;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
    private String name;
    private Shift shift;
    private Long tankerId;
    private Integer maxTransitMinutes;
}