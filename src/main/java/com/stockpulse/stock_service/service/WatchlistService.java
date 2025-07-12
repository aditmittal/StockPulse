package com.stockpulse.stock_service.service;

import com.stockpulse.stock_service.dto.WatchlistRequest;
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

    public List<Watchlist> getUserWatchlist(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return watchlistRepository.findByUser(user);
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
