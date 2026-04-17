package com.exchange.stock.market.repo.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetails {
    private String tckrSymb;
    private String market;
    private String isin;
    private String name;
    private double open;
    private double high;
    private double low;
    private double prevClosePrice;
    private double close;
    private String lastTradedDate;
    private double totalTradedQty;
    private double yearWeekHigh;
    private double yearWeekLow;
    private String industry;
    private boolean addedToWatchList;
}

