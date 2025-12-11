package com.exchange.stock.market.stockDetails.config.Kafka;

import com.exchange.stock.market.stockDetails.domain.MarketStockInfo;

public interface KafkaMessagePublisher {
    void sendStockDetailsMessage(MarketStockInfo stockDetails);
}
