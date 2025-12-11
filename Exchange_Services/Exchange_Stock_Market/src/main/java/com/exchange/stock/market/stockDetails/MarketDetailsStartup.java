package com.exchange.stock.market.stockDetails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.LocalDateTime;
import java.time.Duration;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class MarketDetailsStartup {
    public static void main(String[] args) {
        LocalDateTime t1 = LocalDateTime.now();
        log.info("Starting the Stock server");
        SpringApplication.run(MarketDetailsStartup.class);
        Duration startupTime = Duration.between(t1, LocalDateTime.now());
        log.info("Started the Stock Server in {} ",startupTime);
    }
}