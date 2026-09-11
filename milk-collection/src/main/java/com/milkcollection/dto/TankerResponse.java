package com.milkcollection.dto;

import com.milkcollection.entity.Tanker;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TankerResponse {
    private Long id;
    private String registrationNumber;
    private Integer capacityLitres;
    private boolean active;

    public static TankerResponse from(Tanker t) {
        return TankerResponse.builder()
                .id(t.getId())
                .registrationNumber(t.getRegistrationNumber())
                .capacityLitres(t.getCapacityLitres())
                .active(t.isActive())
                .build();
    }
}