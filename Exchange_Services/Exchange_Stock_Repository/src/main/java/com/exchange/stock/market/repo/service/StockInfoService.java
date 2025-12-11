package com.exchange.stock.market.repo.service;

import com.exchange.stock.market.repo.domain.StockDetails;
import com.exchange.stock.market.repo.domain.StockDocument;
import com.exchange.stock.market.repo.domain.LatestStockDetailsResponse;
import java.util.List;

public interface StockInfoService {

    List<StockDetails>  fetchStockHistoricalDataByName(String name) throws Exception;

    List<StockDetails> populateMarketData() throws Exception;

    StockDetails fetchLatestStockPrice(String stockName) throws Exception;

    LatestStockDetailsResponse fetchMarketWatchData(int size , String fetchStockAfterName) throws Exception;


}
