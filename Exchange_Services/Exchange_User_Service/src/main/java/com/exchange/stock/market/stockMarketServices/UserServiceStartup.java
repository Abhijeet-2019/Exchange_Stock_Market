package com.exchange.stock.market.stockMarketServices;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The UserServiceStartup class
 * serves as the entry point for the User Service application.
 *
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceStartup {
    public static void main(String[] args) {
      log.info("Started the User service app");
      SpringApplication.run(UserServiceStartup.class, args);
      log.info("Started the user service app");
    }
}