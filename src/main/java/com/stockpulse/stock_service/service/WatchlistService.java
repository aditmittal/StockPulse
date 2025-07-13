package com.stockpulse.stock_service.service;

import com.stockpulse.stock_service.dto.WatchlistRequest;
import com.stockpulse.stock_service.dto.WatchlistResponse;
import com.stockpulse.stock_service.exception.ResourceNotFoundException;
import com.stockpulse.stock_service.model.Stock;
import com.stockpulse.stock_service.model.User;
import com.stockpulse.stock_service.model.Watchlist;
import com.stockpulse.stock_service.repository.StockRepository;
import com.stockpulse.stock_service.repository.UserRepository;
import com.stockpulse.stock_service.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    public void addToWatchlist(WatchlistRequest request, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Stock stock = stockRepository.findById(request.getStockId())
            .orElseThrow(() -> new RuntimeException("Stock not found with ID: " + request.getStockId()));

        if (watchlistRepository.findByUserAndStock(user, stock).isPresent()) {
            throw new RuntimeException("Stock already in watchlist");
        }

        Watchlist entry = Watchlist.builder()
            .user(user)
            .stock(stock)
            .alertThreshold(request.getAlertThreshold())
            .build();

        watchlistRepository.save(entry);
    }

    public List<WatchlistResponse> getUserWatchlist(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        List<Watchlist> watchlist = watchlistRepository.findByUser(user);

        return watchlist.stream()
            .map(w -> new WatchlistResponse(
                w.getId(),
                w.getStock().getSymbol(),
                w.getStock().getName(),
                w.getStock().getPrice(),
                w.getAlertThreshold(),
                w.getCreatedAt() != null ? w.getCreatedAt().toString() : null
            ))
            .collect(Collectors.toList());
    }

    public void removeFromWatchlist(Long stockId, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        Stock stock = stockRepository.findById(stockId)
            .orElseThrow(() -> new RuntimeException("Stock not found with ID: " + stockId));

        Watchlist entry = watchlistRepository.findByUserAndStock(user, stock)
            .orElseThrow(() -> new RuntimeException("Stock not found in user's watchlist"));

        watchlistRepository.delete(entry);
    }
}
