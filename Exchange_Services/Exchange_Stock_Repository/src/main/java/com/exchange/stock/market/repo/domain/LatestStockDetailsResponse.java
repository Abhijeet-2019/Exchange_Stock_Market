package com.exchange.stock.market.repo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class LatestStockDetailsResponse {

 private List<StockDetails>  stockDetails;
 private String nextSearchStockName;
 private int pageSize;
 private boolean hasNextElement;


}
