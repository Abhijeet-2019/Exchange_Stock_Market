package com.exchange.stock.market.stockMarketServices.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WatchListStockDetails {
    private String tckrSymb;
    private String isin;
    private String name;
    private double open;
    private double high;
    private double low;
    private double prevClosePrice;
    private double close;
    private double yearWeekHigh;
    private double yearWeekLow;
    private String industry;
    private boolean addedToWatchList;
}
