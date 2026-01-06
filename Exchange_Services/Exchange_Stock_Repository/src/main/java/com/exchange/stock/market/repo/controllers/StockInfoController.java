package com.exchange.stock.market.repo.controllers;


import com.exchange.stock.market.repo.domain.LatestStockDetailsResponse;
import com.exchange.stock.market.repo.domain.StockDetails;

import com.exchange.stock.market.repo.service.StockInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/stockDetails")
@AllArgsConstructor
@Tag(name = "Stock Information")
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from port 8080
public class StockInfoController {

    private final StockInfoService stockInfoService;

    /**
     *
     * @param stockName :
     * @return :
     */
    @GetMapping("/stockName")
    @Operation(summary = "Fetch all Stock Details Information")
    public ResponseEntity<List<StockDetails>> fetchAllHistoricalData(@RequestParam(name = "stockName", required = true) @Parameter(example = "TCS") String stockName) {
        List<StockDetails> stockDetails = null;
        try {
            stockDetails = stockInfoService.fetchStockHistoricalDataByName(stockName);
        } catch (Exception e) {
            log.error("Error while populating stock details: {}", e.getMessage());
            return new ResponseEntity<List<StockDetails>>(null, null, 500);
        }
        return new ResponseEntity<List<StockDetails>>(stockDetails, null, 200);
    }

    /**
     *
     * @param stockName : Name of the stock
     * @return :
     */
    @GetMapping("/fetchLatestData")
    @Operation(summary = "Fetch the latest stock price")
    public ResponseEntity<StockDetails> fetchLatestData(@RequestParam(name = "stockName", required = true) @Parameter(example = "TCS") String stockName) {
        StockDetails stockDetails = null;
        try {
            stockDetails = stockInfoService.fetchLatestStockPrice(stockName);
        } catch (Exception e) {
            log.error("Error while populating stock details: {}", e.getMessage());
            return new ResponseEntity<StockDetails>(null, null, 500);
        }
        return new ResponseEntity<StockDetails>(stockDetails, null, 200);
    }

    /**
     * This method is used when we need to fetch the latest market data for 200 Stocks.
     * @param size
     * @param searchAfter
     * @return
     */
    @GetMapping("/fetchMarketWatch")
    @Operation(summary = "Fetch the list of top 200 stocks")
    public ResponseEntity<LatestStockDetailsResponse> fetchLatestMarketWatch
            (@RequestParam(defaultValue = "200") int size,
             @RequestParam(required = false) String searchAfter) {
        LatestStockDetailsResponse response = null;
        try {
            response = stockInfoService.fetchMarketWatchData(size,searchAfter);
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch Market Depth");
        }
        return new ResponseEntity<LatestStockDetailsResponse>(response, null,200);
    }
}



