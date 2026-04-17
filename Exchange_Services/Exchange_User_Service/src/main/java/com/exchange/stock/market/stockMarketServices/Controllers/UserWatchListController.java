package com.exchange.stock.market.stockMarketServices.Controllers;

import com.exchange.stock.market.stockMarketServices.models.UserWatchList;
import com.exchange.stock.market.stockMarketServices.service.WatchListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/userWatchList")
public class UserWatchListController {

    private WatchListService watchListService;

    UserWatchListController(WatchListService watchListService) {
        this.watchListService = watchListService;
    }

    @GetMapping("/fetch")
    @Operation(summary = "Fetch watchList for user")
    @Tag(name = "User Watch List Details")
    public ResponseEntity<UserWatchList> fetchUserStockWatchList(
            @RequestParam(name = "email", required = true)
            @Parameter(example = "abhijeet.mohanty001@gmail.com") String email) {
        log.info("Fetching User WatchList for all users--->" + email);
        UserWatchList userWatchList = null;
        try {
            userWatchList = watchListService.fetchStockWatchList(email);
        } catch (Exception exception) {
            log.error("Unable to fetch all the stock watch list for email -->{}", email);
            return new ResponseEntity<>(userWatchList, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userWatchList, null, HttpStatus.OK);
    }

    @GetMapping("/addStock")
    @Operation(summary = "Add stock to watchList")
    @Tag(name = "User Watch List Details")
    public ResponseEntity<String> addToCurrentWatchList(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "stockName", required = true) String stockName) {
        log.info("Adding the StockName for the user--> {}for stock->{} ", email, stockName);
        try {
            watchListService.saveUserWatchList(email, stockName);
        } catch (Exception ex) {
            log.error("Unable to persist the stock to watch List for user{} ", email);
            return new ResponseEntity<String>("Unable to add Stock ", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Stock details added successfully", null, HttpStatus.OK);
    }
}
