package com.exchange.stock.market.repo.config.Elastic;

import com.exchange.stock.market.repo.domain.LatestStockDetailsResponse;
import com.exchange.stock.market.repo.domain.StockDetails;
import com.exchange.stock.market.repo.domain.StockDocument;

import java.util.List;

public interface ElasticDao {

    void saveSingleStockRecord(StockDetails stockDocument);

    List<StockDocument> fetchStockHistoricalDataByName(String name);

    List<StockDocument> fetchMarketData(int start, int end);

    StockDetails fetchLatestStockPrice(String stockName) throws Exception;

    LatestStockDetailsResponse fetchMarketWatchData(int size, String fetchStockAfterName);
}
