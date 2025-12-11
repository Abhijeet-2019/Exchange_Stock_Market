package com.exchange.stock.market.repo.service;

import com.exchange.stock.market.repo.domain.StockDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockElasticRepository
        extends ElasticsearchRepository<StockDocument, String> {

    @Query("""
    {
      "term": {
        "stock_name": {
          "value": "?0"
        }
      }
    }
    """)
    List<StockDocument> findByStockName(String name);
}
