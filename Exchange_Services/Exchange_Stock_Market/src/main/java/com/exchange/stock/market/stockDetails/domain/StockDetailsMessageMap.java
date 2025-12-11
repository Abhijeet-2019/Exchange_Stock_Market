package com.exchange.stock.market.stockDetails.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
public class StockDetailsMessageMap {

    private ConcurrentMap<String , MarketStockInfo> stockDetailsMessageMap;

}
