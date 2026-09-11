package com.milkcollection.entity;

import com.milkcollection.enums.Shift;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tanker_id", nullable = false)
    private Tanker tanker;

    /** Max allowed minutes from first pickup to chilling-plant arrival before milk is treated as spoiled. */
    @Column(nullable = false)
    private Integer maxTransitMinutes;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}