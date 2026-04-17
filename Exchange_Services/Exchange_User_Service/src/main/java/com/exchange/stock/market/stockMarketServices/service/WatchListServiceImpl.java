package com.exchange.stock.market.stockMarketServices.service;

import com.exchange.stock.market.stockMarketServices.Repository.UserRepository;
import com.exchange.stock.market.stockMarketServices.Repository.UserStockWatchListRepo;
import com.exchange.stock.market.stockMarketServices.domains.WatchListStockDetails;
import com.exchange.stock.market.stockMarketServices.models.UserStockWatchList;
import com.exchange.stock.market.stockMarketServices.models.UserWatchList;
import com.exchange.stock.market.stockMarketServices.models.Users;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Service
public class WatchListServiceImpl implements WatchListService {

    private final UserStockWatchListRepo userStockWatchListRepo;
    private final UserRepository userRepository;
    private final ThreadPoolTaskExecutor fetchStockDetailsExecutor;
    private final WebClient webClient;

    WatchListServiceImpl(UserStockWatchListRepo userStockWatchListRepo,
                         UserRepository userRepository,
                         @Qualifier("fetchStockDetailsExecutor") ThreadPoolTaskExecutor fetchStockDetailsExecutor
            , WebClient webClient) {
        this.userStockWatchListRepo = userStockWatchListRepo;
        this.userRepository = userRepository;
        this.fetchStockDetailsExecutor = fetchStockDetailsExecutor;
        this.webClient = webClient;
    }

    /**
     * Code to persist the user watch List
     *
     * @param userName
     * @param stockName
     * @return
     */
    @Override
    @Transactional(propagation = REQUIRES_NEW)
    public void saveUserWatchList(String userName, String stockName) {
        Optional<Users> users = userRepository.findByEmail(userName);
        if (users.isEmpty()) {
            throw new RuntimeException("Unable to find userDetails for adding WatchList");
        }
        if (((List) (Users) users.get().getUserStockWatchList()).contains(stockName)) {
            throw new RuntimeException("Stock already available in user Watch List");
        }
        UserStockWatchList userStockWatchList = new UserStockWatchList();
        userStockWatchList.setStockName(stockName);
        userStockWatchList.setTransactionDate(LocalDate.now());
        userStockWatchList.setUsers((Users) users.get());
        userStockWatchListRepo.save(userStockWatchList);
        log.info("The save stock details {}", stockName);
    }

    /**
     * Code to fetch the user watch List
     *
     * @param email
     * @return
     */
    @Override
    @Transactional(propagation = REQUIRES_NEW)
    public UserWatchList fetchStockWatchList(String email) {
        long startTime = System.currentTimeMillis();
        log.info("Fetching the userDetails for user {}-->>", email);

        Optional<Users> userDetails = userRepository.findByEmail(email);
        if (userDetails.isEmpty()) {
            throw new RuntimeException("Unable to find userDetails for adding WatchList for user {}-->>" + email);
        }
        Set<UserStockWatchList> userStockList = (userDetails.get()).getUserStockWatchList();
        JwtAuthenticationToken auth =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String token = auth.getToken().getTokenValue();

        List<WatchListStockDetails> stockList = new ArrayList<WatchListStockDetails>();
        userStockList.forEach(userStock -> {
            // Use the Stock details name to fetch all details..
            stockList.add(fetchAllMarketDetails(userStock.getStockName(), token));
        });
        long timeConsumed = System.currentTimeMillis() - startTime;
        log.info("The total time consumed to fetch all user watchList is{}", timeConsumed);
        return new UserWatchList(email, stockList);
    }

    /**
     * Code to fetch the stock details
     * for the given stock name from Market data service..
     *
     * @param stockName
     */
    private WatchListStockDetails fetchAllMarketDetails(String stockName, String token) {
        WatchListStockDetails returnWatchListInfo = new WatchListStockDetails();
        try {
            ExecutorService executorService = fetchStockDetailsExecutor.getThreadPoolExecutor();
            // Submit a blocking task that will block on the Mono and return the List<WatchListStockDetails>
            Future<List<WatchListStockDetails>> stockDetailsFuture = executorService.submit(() -> {
                log.info("Fetching the stock details for stock name {}-->>", stockName);
                // block with a timeout to convert Mono<List<WatchListStockDetails>> to List<WatchListStockDetails>
                return callStockMatketApi(stockName, token).block(Duration.ofSeconds(5));
            });
            List<WatchListStockDetails> stockList = stockDetailsFuture.get();
            if (stockList == null || stockList.isEmpty()) {
                throw new RuntimeException("Unable to fetch the stock details for stock name {}-->>" + stockName);
            }
            returnWatchListInfo = stockList.get(0);
            if (returnWatchListInfo == null) {
                throw new RuntimeException("Unable to fetch the stock details for stock name {}-->>" + stockName);
            }
            log.info("The latest stock price for stock name {}-->>", returnWatchListInfo);
        } catch (Exception e) {
            log.error("Unable to fetch the stock details for stock name {}-->>", stockName, e);
        }
        if (returnWatchListInfo != null) {
            returnWatchListInfo.setAddedToWatchList(true);
        }
        return returnWatchListInfo;
    }

    @CircuitBreaker(name = "serviceBBreaker", fallbackMethod = "fallbackForServiceStcokRepo")
    private Mono<List<WatchListStockDetails>> callStockMatketApi(String stockName, String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stock-repo-service/stockDetails/fetchLatestData")
                        .queryParam("stockName", stockName)
                        .build())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<WatchListStockDetails>>() {
                })
                .retry(3) // Re-try three times
                .doOnError(error -> System.out.println("Error calling stock service: " + error.getMessage()));
    }


    private Mono<List<WatchListStockDetails>> fallbackForServiceStcokRepo(String stockName, String token, Throwable t) {
        // Log the error for observability
        System.err.println("Circuit Breaker triggered for " + stockName + ". Reason: " + t.getMessage());

        // Return an empty list or cached data wrapped in a Mono
        return Mono.just(Collections.emptyList());
    }

}
