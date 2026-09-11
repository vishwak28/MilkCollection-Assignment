package com.milkcollection.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TankerRequest {
    private String registrationNumber;
    private Integer capacityLitres;
}