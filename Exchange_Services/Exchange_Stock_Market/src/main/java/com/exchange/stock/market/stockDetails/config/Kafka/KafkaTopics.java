package com.exchange.stock.market.stockDetails.config.Kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {

    @Value("${spring.kafka.template.stockDetails-topic}")
    private String stockDetailsTopicName ;


    @Bean
    public NewTopic eventCreatedTopic(){
        return TopicBuilder.name (stockDetailsTopicName).build ();
    }
}
