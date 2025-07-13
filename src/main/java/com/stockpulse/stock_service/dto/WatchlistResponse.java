package com.stockpulse.stock_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WatchlistResponse {
    private Long id;
    private String stockSymbol;
    private String stockName;
    private Double price;
    private Double alertThreshold;
    private String createdAt;
}
