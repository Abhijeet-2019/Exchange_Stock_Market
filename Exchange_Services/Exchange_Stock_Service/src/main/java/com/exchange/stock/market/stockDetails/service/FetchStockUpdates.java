package com.exchange.stock.market.stockDetails.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface FetchStockUpdates {

        void populateStockDetailsDailyFromCSV();

        void fetchStockCurrentPrice(String stockName);

        void populateStockDetailsFromCSV(LocalDate date);
}
