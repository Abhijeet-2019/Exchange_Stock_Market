package com.exchange.stock.market.stockDetails.service;

import java.time.LocalDate;

public interface MarketUpdateDetails {

       void populateStockDetailsFromCSV(LocalDate date);
}
