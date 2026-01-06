package com.exchange.stock.market.portfolio.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/userPortfolio")
@CrossOrigin(origins = "http://localhost:8080")
public class UserPortfolioDetails {

    @GetMapping("/fetch")
    public ResponseEntity fetchAll(String userName){
        log.info("Fetching the user portfolioDetails for {} --",userName);
        return ResponseEntity.ok().body("hi  --"+userName);
    }

    @PostMapping("/addToPortfolio")
    public ResponseEntity add(Object obj){
        log.info("Fetching the user portfolioDetails for {} --",obj);
        return ResponseEntity.ok().body("hi");
    }
}
