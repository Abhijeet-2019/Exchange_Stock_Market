package com.exchange.stock.market.repo.config.Elastic;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.SortOrder;


import com.exchange.stock.market.repo.domain.LatestStockDetailsResponse;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;


import com.exchange.stock.market.repo.domain.StockDetails;
import com.exchange.stock.market.repo.domain.StockDocument;
import com.exchange.stock.market.repo.service.StockElasticRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class ElasticDaoImpl implements ElasticDao {

    private static final String INDEX = "stock_details";

    private final StockElasticRepository repository;
    private final ElasticsearchOperations elasticOps;

    /**
     * Create index at startup if not present.
     */
    @PostConstruct
    public void createIndexIfNotExists() {

        boolean exists = elasticOps.indexOps(StockDocument.class).exists();

        if (!exists) {
            elasticOps.indexOps(StockDocument.class).create();
            elasticOps.indexOps(StockDocument.class).putMapping();

            log.info("✅ Created Elasticsearch index: {}", INDEX);
        } else {
            log.info("Index {} already exists", INDEX);
        }
    }

    @Override
    public void saveSingleStockRecord(StockDetails stockDetails) {
        String stockTicker = stockDetails.getTckrSymb();
        // Fetch existing document
        StockDocument stockDocument = repository.findById(stockTicker).orElse(null);

        if (stockDocument == null) {
            // Create new document
            stockDocument = new StockDocument();
            stockDocument.setId(stockTicker);
            stockDocument.setStockName(stockDetails.getName());
            List<StockDetails> stockDetailsList = new ArrayList<>();
            stockDetailsList.add(stockDetails);
            stockDocument.setStockDetails(stockDetailsList);
            log.info(" Creating new stock document for: {}", stockTicker);
        } else {
            // Append to existing list
            stockDocument.getStockDetails().add(stockDetails);
            log.info(" Appending stock document for: {}", stockTicker);
        }
        repository.save(stockDocument);
    }

    @Override
    public List<StockDocument> fetchStockHistoricalDataByName(String name) {
        Criteria stockNameCriteria = new Criteria("stock_name").is(name);

        Criteria dateCriteria = new Criteria("stock_details.lastTradedDate").matches("2025-12-08");

        Criteria nestedCriteria = new Criteria("stock_details").subCriteria(dateCriteria);

        Criteria finalCriteria = stockNameCriteria.and(nestedCriteria);
        CriteriaQuery query = new CriteriaQuery(finalCriteria);

        SearchHits<StockDocument> hits = elasticOps.search(query, StockDocument.class);

        List<StockDocument> list = hits.getSearchHits().stream().map(SearchHit::getContent).toList();
        System.out.println("The size of List" + list.size());
        return list;
    }

    @Override
    public List<StockDocument> fetchMarketData(int start, int end) {
        return List.of();
    }

    @Override
    public StockDetails fetchLatestStockPrice(String stockName) throws Exception {
        StockDetails stockDetails = null;
        try {
            NativeQuery query = NativeQuery.builder()
                    .withQuery(q -> q
                            .bool(b -> b
                                    .must(m -> m
                                            .term(t -> t
                                                    .field("stock_name")
                                                    .value(stockName)
                                            )
                                    )
                                    .must(m -> m
                                            .nested(n -> n
                                                    .path("stock_details")
                                                    .query(nq -> nq.matchAll(ma -> ma))
                                                    .innerHits(ih -> ih
                                                            .size(1)
                                                            .sort(s -> s
                                                                    .field(f -> f
                                                                            .field("stock_details.lastTradedDate")
                                                                            .order(SortOrder.Desc)
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
                    .withPageable(PageRequest.of(0, 1))
                    .build();
            SearchHits<StockDocument> hits =
                    elasticOps.search(query, StockDocument.class);
            if (hits.hasSearchHits()) {
                SearchHit<StockDocument> hit = hits.getSearchHit(0);
                SearchHits<Object> innerHits =
                        (SearchHits<Object>) hit.getInnerHits().get("stock_details");
                if (innerHits != null && !innerHits.isEmpty()) {
                    stockDetails = (StockDetails) innerHits.getSearchHit(0).getContent();
                    log.info("The Latest Stock details are {}", stockDetails.getHigh());
                }
            }
        } catch (ElasticsearchException exception) {
            throw new Exception("Unable to process the fetch request {},", exception);
        }
        if (stockDetails == null) {
            throw new Exception("Unable to find the Stock Details {},for stock ");
        }
        return stockDetails;
    }

    @Override
    public LatestStockDetailsResponse fetchMarketWatchData(int size, String fetchStockAfterName) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .nested(n -> n
                                .path("stock_details")
                                .query(nq -> nq.matchAll(ma -> ma))
                                .innerHits(ih -> ih
                                        .size(1)
                                        .sort(s -> s
                                                .field(f -> f
                                                        .field("stock_details.lastTradedDate")
                                                        .order(SortOrder.Desc)
                                                )
                                        )
                                )
                        )
                )
                .withSort(s -> s
                        .field(f -> f
                                .field("stock_name")
                                .order(SortOrder.Asc)
                        )
                )
                .withPageable(PageRequest.of(0, size))
                .build();

        if (fetchStockAfterName != null) {
            query.setSearchAfter(List.of(fetchStockAfterName));
        }

        SearchHits<StockDocument> hits =
                elasticOps.search(query, StockDocument.class);

        List<StockDetails> results = new ArrayList<>();

        for (SearchHit<StockDocument> hit : hits) {

            @SuppressWarnings("unchecked")
            SearchHits<StockDetails> innerHits =
                    (SearchHits<StockDetails>) hit.getInnerHits().get("stock_details");

            if (innerHits != null && !innerHits.isEmpty()) {
                StockDetails latest = innerHits.getSearchHit(0).getContent();
                results.add(latest);
            }
        }

        String nextCursor = hits.hasSearchHits()
                ? (String) hits.getSearchHit(hits.getSearchHits().size() - 1)
                .getContent().getStockName()
                : null;


        return new LatestStockDetailsResponse(
                results,
                nextCursor,
                size,
                nextCursor != null
        );
    }
}
