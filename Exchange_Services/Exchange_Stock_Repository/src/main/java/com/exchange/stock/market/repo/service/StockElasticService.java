package com.exchange.stock.market.repo.service;

import com.exchange.stock.market.repo.domain.StockDetails;
import com.exchange.stock.market.repo.domain.StockDocument;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockElasticService {

    private static final String INDEX = "stock_details";

    private final StockElasticRepository repository;
    private final ElasticsearchOperations elasticOps;

    /**
     * Create index at startup if not present.
     */
    @PostConstruct
    public void createIndexIfNotExists() {

        boolean exists = elasticOps.indexOps(StockDocument.class).exists();

        if (!exists) {
            elasticOps.indexOps(StockDocument.class).create();
            elasticOps.indexOps(StockDocument.class).putMapping();

            log.info("✅ Created Elasticsearch index: {}", INDEX);
        } else {
            log.info("Index {} already exists", INDEX);
        }
    }

    /**
     * Persist a single stock record.
     */
    public void saveSingleStockRecord(StockDetails stockDetails) {

        String stockTicker = stockDetails.getTckrSymb();
        // Fetch existing document
        StockDocument stockDocument = repository.findById(stockTicker).orElse(null);

        if (stockDocument == null) {
            // Create new document
            stockDocument = new StockDocument();
            stockDocument.setId(stockTicker);
            stockDocument.setStockName(stockDetails.getName());

            List<StockDetails> list = new ArrayList<>();
            list.add(stockDetails);
            stockDocument.setStockDetails(list);

            log.info(" Creating new stock document for: {}", stockTicker);

        } else {
            // Append to existing list
            stockDocument.getStockDetails().add(stockDetails);

            log.info(" Appending stock details for: {}", stockTicker);
        }

        repository.save(stockDocument);
    }
}
