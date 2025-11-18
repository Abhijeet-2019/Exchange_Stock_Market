package com.exchange.stock.market.stockDetails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
@EnableConfigServer
public class ConfigAppStartup {
    public static void main(String[] args) {
        LocalDateTime t1 = LocalDateTime.now();
        log.info("Starting the Stock Server");
        SpringApplication.run(ConfigAppStartup.class);
        Duration startupTime = Duration.between(t1, LocalDateTime.now());
        log.info("Started the Stock Config Server in {} ",startupTime);
    }
}