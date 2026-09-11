package com.milkcollection.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarkSkippedRequest {
    private String reason;
}