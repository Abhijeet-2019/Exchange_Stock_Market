package com.exchange.stock.market.stockDetails.service;


import com.exchange.stock.market.stockDetails.config.Kafka.KafkaMessagePublisher;
import com.exchange.stock.market.stockDetails.domain.MarketStockInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StockPublisher {

    private final KafkaMessagePublisher kafkaMessagePublisher;


    public StockPublisher(Map<String, MarketStockInfo> stockDetailsMap,
                          KafkaMessagePublisher kafkaMessagePublisher) {
        this.kafkaMessagePublisher = kafkaMessagePublisher;
    }

     public void publishStockDetails(MarketStockInfo stock) {
         kafkaMessagePublisher.sendStockDetailsMessage(stock);
    }
}
