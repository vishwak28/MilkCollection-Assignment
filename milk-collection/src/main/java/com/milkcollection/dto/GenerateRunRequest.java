package com.milkcollection.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateRunRequest {
    private Long routeId;
    private LocalDate runDate;
}