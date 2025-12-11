package com.exchange.stock.market.portfolio;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PortfolioApp {
    public static void main(String[] args) {
        log.info("Starting the Portfolio Service application...");
        SpringApplication.run(PortfolioApp.class, args);
        log.info("Portfolio Service application started successfully.");
    }
}