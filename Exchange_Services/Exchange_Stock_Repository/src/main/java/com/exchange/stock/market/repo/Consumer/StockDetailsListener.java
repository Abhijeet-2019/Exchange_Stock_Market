package com.exchange.stock.market.repo.Consumer;

import com.exchange.stock.market.repo.config.Elastic.ElasticDao;
import com.exchange.stock.market.repo.domain.StockDetails;
import com.exchange.stock.market.repo.service.StockElasticService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * The StockDetailsListener class is a Spring component that listens for stock details updates.
 * It is annotated with @Component to enable Spring's component scanning and dependency injection features.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StockDetailsListener {

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Autowired
    private ElasticDao elasticDao;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Kafa Listener method for Persistance of messages.
     * @param record
     */
    @KafkaListener(topics = "${spring.kafka.template.stockDetails-topic}",
            groupId = "stockDetail-group")
    public void kafkaListener(
            ConsumerRecord<String, Object> record) {
            try{
                log.info("The received stock details message: " + record.key());
                StockDetails stockDetails = objectMapper.convertValue(
                        record.value(),
                        StockDetails.class);
                elasticDao.saveSingleStockRecord(stockDetails);
                log.info("Saved stock: {}", stockDetails.getName());
            }catch (Exception ex){
                log.error("Unable to read and persist Stock details Messages");
                ex.printStackTrace();
            }
    }

    /**
     * Removed this call as it does not use Object Mapper and
     * Code is redundant,
     */
//    @KafkaListener(topics = "${spring.kafka.template.stockDetails-topic}", groupId = "stockDetail-group") public void kafkaListener (
//            ConsumerRecord<String, Object> record) {
//        try {
//            log.info("The received stock details message: " + record.key());
//            StockDetails stockDetails = new StockDetails();
//            LinkedHashMap<String, Object> valueMap= (LinkedHashMap<String, Object>) record.value();
//            stockDetails.setName(valueMap.get("name").toString());
//            stockDetails.setTckrSymb(valueMap.get("tckrSymb").toString());
//            stockDetails.setMarket(valueMap.get("market").toString());
//            stockDetails.setOpen(Double.parseDouble(valueMap.get("open").toString()));
//            stockDetails.setHigh(Double.parseDouble(valueMap.get("high").toString()));
//            stockDetails.setLow(Double.parseDouble(valueMap.get("low").toString()));
//            stockDetails.setPrevClosePrice(Double.parseDouble(valueMap.get("prevClosePrice").toString()));
//            stockDetails.setClose(Double.parseDouble(valueMap.get("close").toString()));
//            stockDetails.setTotalTradedQty(Double.parseDouble(valueMap.get("totalTradedQty").toString()));
//            stockDetails.setLastTradedDate(valueMap.get("lastTradedDate").toString());
//            stockDetails.setIsin(valueMap.get("isin").toString());
//            log.info("Stock Name: {}",stockDetails.getName());
////
//            elasticDao.saveSingleStockRecord(stockDetails);
//            log.info("Reco " + stockDetails.getName());
//        }catch (Exception e){
//            log.error("Error processing the kafka message", e);
//        }
//    }
}
