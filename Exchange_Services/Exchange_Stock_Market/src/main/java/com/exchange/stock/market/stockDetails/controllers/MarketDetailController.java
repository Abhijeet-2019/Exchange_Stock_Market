package com.exchange.stock.market.stockDetails.controllers;

import com.exchange.stock.market.stockDetails.aop.LogExecutionTime;
import com.exchange.stock.market.stockDetails.service.MarketUpdateDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/fetch")
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from port 8080
public class MarketDetailController {

    private final MarketUpdateDetails fetchStockUpdates;

    public MarketDetailController(MarketUpdateDetails fetchStockUpdates) {
        this.fetchStockUpdates = fetchStockUpdates;
    }

    /**
     *
     * @param date :
     *             The date to fetch the date
     * @return :
     *          Response Entity Object
     */
    @GetMapping("/populateBSEStockDetails")
    @Operation(summary = "Populate  Stock Details")
    @Tag(name = "Stock Details")
    @LogExecutionTime
    public ResponseEntity<String> populateBSEStockDetails
            (@RequestParam(name = "date", required = true)
             @Parameter(example = "2026-02-10") LocalDate date) {
        try{
            fetchStockUpdates.populateStockDetailsFromCSV(date);
        }catch (Exception e){
            log.error("Error while populating stock details: {}", e.getMessage());
            return new ResponseEntity<String>("Error while populating stock details",null, 500);
        }
       return new ResponseEntity<String>("Stock Details Population Initiated",null, 200);
    }

    /**
     *
     * @param name :
     * @return : Response Entity
     */
    @GetMapping("testConnection")
    @Tag(name = "Test Connection")
    @LogExecutionTime
    public ResponseEntity<String>
    checkHeartBeat(@RequestParam(name = "testuser", required = true)String name){
        log.info("The connection is working as expeted {}",name);
        String result = "The connection is open for-->"+name;
        return new ResponseEntity<String>(result,null,200);
    }
}