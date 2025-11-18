package com.exchange.stock.market.repo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "stock_details")   // <--- REQUIRED

public class StockDocument {
    @Id
    private String id;   // <-- REQUIRED for Spring ES

    @Field(name = "stock_name", type = FieldType.Keyword)
    private String stockName;

    @Field(name = "stock_details", type = FieldType.Nested)
    private List<StockDetails> stockDetails;
}
