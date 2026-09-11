package com.milkcollection.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerRequest {
    private String name;
    private String phone;
    private Long collectionPointId;
}