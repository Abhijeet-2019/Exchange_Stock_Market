package com.exchange.stock.market.stockDetails.domain;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class StockDetails {
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
}
