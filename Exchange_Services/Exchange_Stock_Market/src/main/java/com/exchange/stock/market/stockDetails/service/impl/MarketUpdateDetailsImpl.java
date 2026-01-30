package com.exchange.stock.market.stockDetails.service.impl;


import com.exchange.stock.market.stockDetails.domain.MarketStockInfo;
import com.exchange.stock.market.stockDetails.service.MarketUpdateDetails;
import com.exchange.stock.market.stockDetails.service.StockPublisher;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;

import java.net.HttpURLConnection;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Slf4j
@Service
public class MarketUpdateDetailsImpl implements MarketUpdateDetails {

    private final WebClient webClient;

    @Value("${common.bsePopulateStockURL}")
    private String baseUrl;

    private final Path BASE_DIR = Paths.get("downloadedCSV");
    private final Path ZIP_PATH = BASE_DIR.resolve("bhavcopy.zip");
    private final Path CSV_PATH = BASE_DIR.resolve("bhavcopy.csv");
    private final StockPublisher stockPublisher;


    public MarketUpdateDetailsImpl(WebClient upstoxWebClient,
                                   StockPublisher stockPublisher) {
        this.webClient = upstoxWebClient;
        this.stockPublisher = stockPublisher;
    }

    public void populateStockDetailsFromCSV(LocalDate date) {
        try {
            String formattedDate = date.format(java.time.format.DateTimeFormatter.ofPattern("ddMMyy"));
            log.info(" The input date for fetching  the stock details {} ", formattedDate);
            if (!Files.exists(BASE_DIR)) {
                Files.createDirectories(BASE_DIR);
            }
            String DOWNLOAD_URL1 =
                    "https://www.bseindia.com/bsedata/newbhavcopy/bhavcopy" + formattedDate + "_CSV.ZIP";
            downloadZip(DOWNLOAD_URL1, ZIP_PATH);
            unzip(ZIP_PATH, CSV_PATH);
             parseCSVandPopulateStocks(CSV_PATH, date);
        } catch (Exception ex) {
            log.error("Unable to parse the Provided URL for the date --- {}", date);
        }
    }

    private void parseCSVandPopulateStocks(Path csvPath, LocalDate date) {
        try {
            CSVReader reader = new CSVReader(new FileReader(csvPath.toFile()));
            String[] header = reader.readNext(); // skip header
            String[] row;
            int count = 0;
            while ((row = reader.readNext()) != null) {
                // Defensive check (sometimes blank rows exist)
                if (row.length < 10) continue;
                MarketStockInfo stockInfo = new MarketStockInfo();
                stockInfo.setTckrSymb(row[0]);        // SC_CODE
                stockInfo.setName(row[1]);        // SC_NAME
                stockInfo.setIsin(row[2]);
                stockInfo.setOpen(parseDouble(row[4]));
                stockInfo.setClose(parseDouble(row[7]));
                stockInfo.setLow(parseDouble(row[6]));
                stockInfo.setHigh(parseDouble(row[5]));
                stockInfo.setPrevClosePrice(parseDouble(row[9]));
                stockInfo.setLastTradedDate(row[10]);
                stockInfo.setTotalTradedQty(parseDouble(row[15]));
                stockInfo.setYearWeekHigh(parseDouble(row[20]));
                stockInfo.setYearWeekLow(parseDouble(row[21]));
                stockInfo.setMarket("BSE");
                stockInfo.setBizDate(date.toString());
                stockInfo.setLastTradedDate(date.toString());
                stockInfo.setIndustry(row[25]);
                stockPublisher.publishStockDetails(stockInfo);
                count++;
            }
            log.info("The Total number of records fetched " +
                    "and published  {} in the date {}", count, date);
        } catch (Exception exception) {
            log.error("Unable to read and parse data for date P{");
            exception.printStackTrace();
        }
    }

    // ---------------- DOWNLOAD ----------------
    private void downloadZip(String urlStr, Path zipPath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        try (InputStream in = conn.getInputStream()) {
            Files.copy(in, zipPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // ---------------- UNZIP ----------------
    private static void unzip(Path zipFile, Path csvFile) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile.toFile()))) {
            ZipEntry entry = zis.getNextEntry();
            if (entry != null) {
                Files.copy(zis, csvFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private double parseDouble(String val) {
        return val == null || val.isBlank() ? 0.0 : Double.parseDouble(val.trim());
    }

    private long parseLong(String val) {
        return val == null || val.isBlank() ? 0L : Long.parseLong(val.trim());
    }
}
