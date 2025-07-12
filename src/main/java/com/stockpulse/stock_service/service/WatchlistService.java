package com.stockpulse.stock_service.service;


import com.stockpulse.stock_service.dto.WatchlistRequest;
import com.stockpulse.stock_service.model.Stock;
import com.stockpulse.stock_service.model.User;
import com.stockpulse.stock_service.model.Watchlist;
import com.stockpulse.stock_service.repository.StockRepository;
import com.stockpulse.stock_service.repository.UserRepository;
import com.stockpulse.stock_service.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    public void addToWatchlist(WatchlistRequest request, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        Stock stock = stockRepository.findById(request.getStockId()).orElseThrow();

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

    public List<Watchlist> getUserWatchlist(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        return watchlistRepository.findByUser(user);
    }

    public void removeFromWatchlist(Long stockId, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        Stock stock = stockRepository.findById(stockId).orElseThrow();

        Watchlist entry = watchlistRepository.findByUserAndStock(user, stock)
                .orElseThrow(() -> new RuntimeException("Not found in watchlist"));

        watchlistRepository.delete(entry);
    }
}
