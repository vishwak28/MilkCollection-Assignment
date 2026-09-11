package com.milkcollection.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VillageRequest {
    private String name;
    private double latitude;
    private double longitude;
}