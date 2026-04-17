package com.exchange.stock.market.stockMarketServices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * This class is used to configure the ExecutorService for asynchronous tasks in the application.
 * It can be used to define thread pools, task scheduling, and other related configurations.
 */
@Configuration
public class ExecutorServiceConfig {
    int corePoolSize = 5;
    int maxPoolSize = 7;

    @Bean(name = "fetchStockDetailsExecutor")
    ThreadPoolTaskExecutor fetchStockDetailsExecutor() {
        try {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(corePoolSize);
            executor.setMaxPoolSize(maxPoolSize);
            executor.initialize();
            return executor;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create ExecutorService for fetching stock details", e);
        }
    }
}
