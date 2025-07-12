package com.stockpulse.stock_service.dto;

import lombok.Data;

@Data
public class WatchlistRequest {
    private Long stockId;
    private Double alertThreshold;
}
