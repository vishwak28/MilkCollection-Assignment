package com.milkcollection.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPointRequest {
    private String name;
    private Double latitude;
    private Double longitude;
    private Long villageId;
}