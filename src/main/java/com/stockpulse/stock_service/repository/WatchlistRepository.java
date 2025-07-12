package com.stockpulse.stock_service.repository;

import com.stockpulse.stock_service.model.Watchlist;
import com.stockpulse.stock_service.model.User;
import com.stockpulse.stock_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUser(User user);
    Optional<Watchlist> findByUserAndStock(User user, Stock stock);
}
