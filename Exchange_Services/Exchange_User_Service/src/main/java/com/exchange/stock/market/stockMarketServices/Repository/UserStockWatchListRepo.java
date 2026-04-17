package com.exchange.stock.market.stockMarketServices.Repository;

import com.exchange.stock.market.stockMarketServices.models.UserStockWatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockWatchListRepo extends JpaRepository<UserStockWatchList,Integer> {

}
