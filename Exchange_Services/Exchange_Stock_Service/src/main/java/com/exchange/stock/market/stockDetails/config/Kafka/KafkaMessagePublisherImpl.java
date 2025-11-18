package com.exchange.stock.market.stockDetails.config.Kafka;

import com.exchange.stock.market.stockDetails.domain.StockDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaMessagePublisherImpl  implements KafkaMessagePublisher {

    @Value("${spring.kafka.template.stockDetails-topic}")
    private String stockDetailsTopicName ;

    private final KafkaTemplate<String, StockDetails> kafkaTemplate;

    KafkaMessagePublisherImpl(KafkaTemplate<String, StockDetails> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendStockDetailsMessage(StockDetails stockDetails) {
        log.info("Sending Stock Details Message to Kafka Topic: {}", stockDetails.getName());
        kafkaTemplate.send(stockDetailsTopicName,"StockDetails" ,stockDetails);
    }
}
