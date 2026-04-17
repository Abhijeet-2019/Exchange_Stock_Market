package com.exchange.stock.market.stockMarketServices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class is used to configure the WebClient for making HTTP requests to external services.
 * It can be used to define base URLs, timeouts, and other related configurations for the WebClient.
 */
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080")   // Base URL of API Gateway target service
                .build();
    }
}
