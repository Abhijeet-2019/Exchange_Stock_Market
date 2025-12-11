package com.exchange.stock.market.repo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
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
}
