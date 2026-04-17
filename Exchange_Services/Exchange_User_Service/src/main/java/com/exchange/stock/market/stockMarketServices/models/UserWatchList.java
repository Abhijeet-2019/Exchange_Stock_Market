package com.exchange.stock.market.stockMarketServices.models;


import com.exchange.stock.market.stockMarketServices.domains.WatchListStockDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserWatchList {

    private String userEmail;
    private List<WatchListStockDetails> userWatchList;
}
