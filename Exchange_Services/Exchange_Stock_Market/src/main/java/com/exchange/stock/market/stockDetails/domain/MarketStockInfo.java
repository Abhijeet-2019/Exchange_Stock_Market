package com.exchange.stock.market.stockDetails.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketStockInfo {
    private String tckrSymb;
    private String isin;
    private String market;
    private String name;
    private double open;
    private double high;
    private double low;
    private double prevClosePrice;
    private double close;
    private String lastTradedDate;
    private double totalTradedQty;
    private String bizDate;
}
