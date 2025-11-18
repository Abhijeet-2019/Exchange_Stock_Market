package com.exchange.stock.market.stockDetails.config.Kafka;

import com.exchange.stock.market.stockDetails.domain.StockDetails;

import java.util.Map;

public interface KafkaMessagePublisher {
    void sendStockDetailsMessage(StockDetails stockDetailsMap);
}
