package com.exchange.stock.market.stockMarketServices.service;

import com.exchange.stock.market.stockMarketServices.models.UserWatchList;
import org.springframework.stereotype.Service;

@Service
public interface WatchListService {

    public void saveUserWatchList(String userName, String stockName);

    public UserWatchList fetchStockWatchList(String email);
}
