package com.exchange.stock.market.stockDetails.controllers;


import com.exchange.stock.market.stockDetails.service.FetchStockUpdates;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/fetch")
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from port 8080
public class FetchStockTickerController {

    private final FetchStockUpdates fetchStockUpdates;

    public FetchStockTickerController(FetchStockUpdates fetchStockUpdates) {
        this.fetchStockUpdates = fetchStockUpdates;
    }

    @GetMapping("/fetchStockTicker")
    @Operation(summary = "Fetch  Stock Ticker Details")
    @Tag(name = "Stock Details")
    public Object fetchStockTickerDetails
            (@RequestParam(name = "stockName", required = true)
             @Parameter(example = "Reliance") String stockName) {
        log.info("The name of the Stock, {}",stockName);
        fetchStockUpdates.fetchStockCurrentPrice(stockName.toUpperCase());
        return new Object();
    }

    @GetMapping("/populateBSEStockDetails")
    @Operation(summary = "Populate  Stock Details")
    @Tag(name = "Stock Details")
    public ResponseEntity populateBSEStockDetails
            (@RequestParam(name = "date", required = true)
             @Parameter(example = "2025-11-10") LocalDate date) {
        try{
            fetchStockUpdates.populateStockDetailsFromCSV(date);
        }catch (Exception e){
            log.error("Error while populating stock details: {}", e.getMessage());
            return new ResponseEntity("Error while populating stock details",null, 500);
        }
       return new ResponseEntity("Stock Details Population Initiated",null, 200);
    }
}
