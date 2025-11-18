package com.exchange.stock.market.repo.controllers;


import com.exchange.stock.market.repo.domain.StockDetails;
import com.exchange.stock.market.repo.service.StockInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/fetchStockInfo")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from port 8080
public class StockInfoController {

    private final StockInfoService stockInfoService;


    @GetMapping("/stockName")
    @Tag(name = "Stock Information")
    @Operation(summary = "Fetch all Stock Details Information")
    public ResponseEntity<StockDetails> fetchStockInfo(@RequestParam(name = "stockName", required = true) @Parameter(example = "TCS") String stockName) {
        StockDetails stockDetails =    null;
        try {
            stockDetails =     stockInfoService.fetchStockInformation(stockName);
        } catch (Exception e) {
            log.error("Error while populating stock details: {}", e.getMessage());
            return new ResponseEntity("Error while populating stock details", null, 500);
        }
        return new ResponseEntity(stockDetails, null, 200);
    }
}



