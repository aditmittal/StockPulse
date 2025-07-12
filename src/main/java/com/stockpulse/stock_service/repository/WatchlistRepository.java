package com.stockpulse.stock_service.repository;

import com.stockpulse.stock_service.model.Watchlist;
import com.stockpulse.stock_service.model.User;
import com.stockpulse.stock_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    Optional<Watchlist> findByUserAndStock(User user, Stock stock);
    List<Watchlist> findByUser(User user);
}
