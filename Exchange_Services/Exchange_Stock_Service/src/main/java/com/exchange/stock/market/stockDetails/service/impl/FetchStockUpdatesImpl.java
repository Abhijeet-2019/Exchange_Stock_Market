package com.exchange.stock.market.stockDetails.service.impl;


import com.exchange.stock.market.stockDetails.domain.StockDetails;
import com.exchange.stock.market.stockDetails.service.FetchStockUpdates;
import com.exchange.stock.market.stockDetails.service.StockPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class FetchStockUpdatesImpl implements FetchStockUpdates {

    private final WebClient webClient;


    @Value("${common.bsePopulateStockURL}")
    private String baseUrl;

    private final StockPublisher stockPublisher;

    public FetchStockUpdatesImpl(WebClient upstoxWebClient,
                                   StockPublisher stockPublisher) {
        this.webClient = upstoxWebClient;
        this.stockPublisher = stockPublisher;
    }

    /**
     * Used to populate stock details from CSV file.
     */
    @Override
    public void populateStockDetailsDailyFromCSV() {
        try {

        } catch (Exception exception) {
        }
    }

    @Override
    public void fetchStockCurrentPrice(String stockName) {

    }

    /**
     * Used to populate stock details from CSV file for a specific date.
     *
     * @param date
     */
    @Override
    public void populateStockDetailsFromCSV(LocalDate date) {
        try {
            String formattedDate = date.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
            String urlWithDate = baseUrl + formattedDate + "_F_0000.CSV";
            log.info("The connected URL: " + urlWithDate);
            populateDataFromURL(urlWithDate);
        } catch (IOException exception) {
            exception.printStackTrace();
                throw new RuntimeException("Unable to parse the file from the URL", exception);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new RuntimeException("Unable to fetch stock details from the URL", exception);
        }
    }

    private void populateDataFromURL(String urlWithDate) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithDate))
                .GET()
                .build();
        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()));
        String header = reader.readLine();
        log.info("CSV Header: " + header);
        String line;
        List<StockDetails> stockList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            // Some lines may be empty
            if (line.trim().isEmpty()) continue;
            String[] cols = line.split(",");
            // Ensure enough columns exist
            if (cols.length < 7) continue;
            StockDetails stockDetails = new StockDetails();
            stockDetails.setIsin(cols[6].trim());
            stockDetails.setTckrSymb(cols[7].trim());
            stockDetails.setName(cols[13].trim());
            stockDetails.setMarket("BSE");
            stockDetails.setOpen(Double.parseDouble(cols[14].trim()));
            stockDetails.setHigh(Double.parseDouble(cols[15].trim()));
            stockDetails.setLow(Double.parseDouble(cols[16].trim()));
            stockDetails.setPrevClosePrice(Double.parseDouble(cols[19].trim()));
            stockDetails.setClose(Double.parseDouble(cols[17].trim()));
            stockDetails.setLastTradedDate(cols[18].trim());
            stockDetails.setTotalTradedQty(Double.parseDouble(cols[24].trim()));
            log.info("The Ticker Symbol: " + stockDetails.getTckrSymb()) ;
            stockList.add(stockDetails);
            // Publish each stock detail
            stockPublisher.publishStockDetails(stockDetails);
        }
        log.info("Total stocks fetched: " + stockList.size());
    }
}
