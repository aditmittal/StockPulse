package com.stockpulse.stock_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "watchlist",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "stock_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "alert_threshold", nullable = false)
    private Double alertThreshold;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
