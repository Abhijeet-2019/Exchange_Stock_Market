package com.exchange.stock.market.repo.service;

import com.exchange.stock.market.repo.config.Elastic.ElasticDao;
import com.exchange.stock.market.repo.domain.LatestStockDetailsResponse;
import com.exchange.stock.market.repo.domain.StockDetails;
import com.exchange.stock.market.repo.domain.StockDocument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class StockInfoServiceImpl implements StockInfoService {

    private ElasticDao elasticDao;

    public List<StockDetails> fetchStockHistoricalDataByName(String name) throws Exception {
        List<StockDetails> stockDetailsList = new ArrayList<>();
        List<StockDocument> stockDocumentList = elasticDao.fetchStockHistoricalDataByName(name);
        if(stockDocumentList.isEmpty()){
            throw  new Exception("Unable To find Stock Detauls {}");
        }
        log.info("the resultant details {}", stockDocumentList);
        stockDocumentList.forEach(stockDocument -> {
            stockDetailsList.addAll(stockDocument.getStockDetails());
        });
        return stockDetailsList;
    }

    @Override
    public List<StockDetails> populateMarketData() throws Exception {
        return List.of();
    }

    @Override
    public StockDetails fetchLatestStockPrice(String stockName) throws Exception {
           return elasticDao.fetchLatestStockPrice(stockName);
    }

    @Override
    public LatestStockDetailsResponse fetchMarketWatchData(int size, String fetchStockAfterName) throws Exception {
        return elasticDao.fetchMarketWatchData( size, fetchStockAfterName);
    }
}
