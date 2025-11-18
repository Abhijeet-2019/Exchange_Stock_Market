package com.exchange.stock.market.repo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The StockRepoAppStartUp class serves as the entry point for the Stock Repository application.
 * It is annotated with @SpringBootApplication to enable Spring Boot's auto-configuration
 * and component scanning features.
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class StockRepoAppStartUp {

    public static void main(String[] args) {
        log.info("Starting Stock Repository Application...");
        SpringApplication.run(StockRepoAppStartUp.class, args);
        log.info("Stock Repository Application started successfully.");
    }
}