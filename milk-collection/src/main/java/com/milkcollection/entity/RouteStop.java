package com.milkcollection.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "route_stops",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"route_id", "sequence_order"}),
                @UniqueConstraint(columnNames = {"route_id", "collection_point_id"})
        }
)
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collection_point_id", nullable = false)
    private CollectionPoint collectionPoint;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;
}