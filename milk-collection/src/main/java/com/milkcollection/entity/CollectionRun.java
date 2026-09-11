package com.milkcollection.entity;

import com.milkcollection.enums.RunStatus;
import com.milkcollection.enums.Shift;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "collection_runs",
        uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "run_date", "shift"})
)
public class CollectionRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "run_date", nullable = false)
    private LocalDate runDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Shift shift;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private RunStatus status = RunStatus.PLANNED;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @Builder.Default
    private boolean rejected = false;

    private String rejectionReason;
}