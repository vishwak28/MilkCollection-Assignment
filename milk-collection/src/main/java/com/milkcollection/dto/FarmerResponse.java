package com.milkcollection.dto;

import com.milkcollection.entity.Farmer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmerResponse {
    private Long id;
    private String name;
    private String phone;
    private boolean active;
    private Long collectionPointId;

    public static FarmerResponse from(Farmer f) {
        return FarmerResponse.builder()
                .id(f.getId())
                .name(f.getName())
                .phone(f.getPhone())
                .active(f.isActive())
                .collectionPointId(f.getCollectionPoint().getId())
                .build();
    }
}