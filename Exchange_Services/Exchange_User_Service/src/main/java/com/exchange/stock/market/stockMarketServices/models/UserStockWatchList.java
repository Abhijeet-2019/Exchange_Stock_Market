package com.exchange.stock.market.stockMarketServices.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.* ;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "user_stock_watchlist")
public class UserStockWatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watch_list_id")
    private Integer watchListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "stock_Name", nullable = false, length = 50)
    private String stockName;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    // Constructors
    public UserStockWatchList() {
    }

    public UserStockWatchList(Users users, String stockName, LocalDate transactionDate) {
        this.users = users;
        this.stockName = stockName;
        this.transactionDate = transactionDate;
    }
}
