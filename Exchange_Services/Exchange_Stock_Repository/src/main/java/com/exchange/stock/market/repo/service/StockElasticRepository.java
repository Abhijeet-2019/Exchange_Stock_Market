package com.exchange.stock.market.repo.service;

import com.exchange.stock.market.repo.domain.StockDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


import org.springframework.stereotype.Repository;
@Repository
public interface StockElasticRepository
        extends ElasticsearchRepository<StockDocument, String> {
}
