package com.exchange.stock.market.repo.service;


import com.exchange.stock.market.repo.domain.StockDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockInfoService {


    public StockDetails fetchStockInformation(String stockName) {
        log.info("Fetching the details related to Stock Info", stockName);
        StockDetails stockDetails = new StockDetails();
        stockDetails.setName("TCS");
        stockDetails.setTckrSymb("TCS");
        stockDetails.setMarket("VSE");
        return stockDetails;
    }
}
