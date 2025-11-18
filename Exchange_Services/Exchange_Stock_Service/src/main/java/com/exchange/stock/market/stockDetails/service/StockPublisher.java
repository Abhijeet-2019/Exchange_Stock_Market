package com.exchange.stock.market.stockDetails.service;


import com.exchange.stock.market.stockDetails.config.Kafka.KafkaMessagePublisher;
import com.exchange.stock.market.stockDetails.domain.StockDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StockPublisher {


    private final KafkaMessagePublisher kafkaMessagePublisher;


    public StockPublisher(Map<String, StockDetails> stockDetailsMap,
                          KafkaMessagePublisher kafkaMessagePublisher) {
        this.kafkaMessagePublisher = kafkaMessagePublisher;
    }

     public void publishStockDetails(StockDetails stock) {
         kafkaMessagePublisher.sendStockDetailsMessage(stock);
    }
}
