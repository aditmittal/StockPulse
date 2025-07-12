package com.stockpulse.stock_service.controller;

import com.stockpulse.stock_service.dto.WatchlistRequest;
import com.stockpulse.stock_service.model.Watchlist;
import com.stockpulse.stock_service.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody WatchlistRequest request, Authentication auth) {
        watchlistService.addToWatchlist(request, auth.getName());
        return ResponseEntity.ok("Added to watchlist");
    }

    @GetMapping
    public ResponseEntity<List<Watchlist>> get(Authentication auth) {
        return ResponseEntity.ok(watchlistService.getUserWatchlist(auth.getName()));
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<String> delete(@PathVariable Long stockId, Authentication auth) {
        watchlistService.removeFromWatchlist(stockId, auth.getName());
        return ResponseEntity.ok("Removed from watchlist");
    }

}
