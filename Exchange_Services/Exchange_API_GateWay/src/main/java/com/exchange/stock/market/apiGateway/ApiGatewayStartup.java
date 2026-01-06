package com.exchange.stock.market.apiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;



@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayStartup {
    public static void main(String[] args) {
        System.out.println("Starting Api Gateway Service...");
        SpringApplication.run(ApiGatewayStartup.class);
        System.out.println("Api Gateway Service Started Successfully.");
    }
}