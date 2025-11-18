package com.exchange.stock.market.stockDetails.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${stockExchange.base-url}")
    private String baseUrl;


    @Bean
    public WebClient indianApiWebClient() {
        System.out.println(" The base Url" + baseUrl);
        return WebClient.builder()
                .baseUrl("https://nse-stock-market-india.p.rapidapi.com")
                .defaultHeader("x-rapidapi-host", "nse-stock-market-india.p.rapidapi.com")
                .defaultHeader("x-rapidapi-key", "88f84500a0msh997e3a6060a0eaep1ac807jsnc4bf7a811821")
                .build();
    }
}