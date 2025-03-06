package com.ElasticSearch.SearchService.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.ElasticSearch.SearchService.entity.Product;
import com.ElasticSearch.SearchService.util.ElasticSearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public SearchResponse<Map> matchAllServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        logger.info("Executing matchAll query for all services...");
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class);
        logger.info("MatchAll query executed. Total results found: {}", searchResponse.hits().hits().size());

        return searchResponse;
    }
    //matchAllProducts video content

    public SearchResponse<Product> matchAllProductsServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        logger.info("Executing matchAll query for products...");
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()),Product.class);
        logger.info("MatchAll query executed. Total products found: {}", searchResponse.hits().hits().size());

        return searchResponse;
    }

    //matchProductWithName

    public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
        logger.info("Executing matchProductWithName query for fieldValue: {}", fieldValue);

        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()),Product.class);
        logger.info("MatchProductWithName query executed. Total products found: {}", searchResponse.hits().hits().size());

        return searchResponse;
    }



    public SearchResponse<Product> autoSuggestProduct(String partialProductName) throws IOException {
        logger.info("Executing auto-suggest query for input: {}", partialProductName);
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierAutoSuggest(partialProductName);
        System.out.println("Elasticsearch auto-suggest query: " + supplier.get().toString());


        return elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
    }

    public SearchResponse<Product> fuzzySearchProducts(String fieldValue) throws IOException {
        logger.info("Executing fuzzy search for: {}", fieldValue);
        Supplier<Query> fuzzySearchSupplier = ElasticSearchUtil.fuzzySearchSupplier(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient
                .search(s -> s.index("products").query(fuzzySearchSupplier.get()), Product.class);
        logger.info("Fuzzy search executed. Total products found: {}", searchResponse.hits().hits().size());


        return searchResponse;
    }

    public SearchResponse<Product> customSearch(String nameQuery, String descriptionQuery, String prefix, String category) throws IOException {
        Supplier<Query> querySupplier = ElasticSearchUtil.buildCustomSearchQuery(nameQuery, descriptionQuery, prefix, category);

        logger.info("Executing custom search with parameters - Name: {}, Description: {}, Prefix: {}, Category: {}", nameQuery, descriptionQuery, prefix, category);

        SearchRequest request = SearchRequest.of(s -> s
                .index("products")
                .query(querySupplier.get())
                .size(20) // Limit results to 10
                .sort(ElasticSearchUtil.sortByRatingDesc())); // Sort by rating in descending order


        SearchResponse<Product> searchResponse = elasticsearchClient.search(request, Product.class);
//        logger.info("Custom search executed. Total results found: {}", searchResponse.hits().hits().size());
//        if (searchResponse != null && searchResponse.hits() != null) {
//            logger.info("Custom search executed. Total results found: {}", searchResponse.hits().hits().size());
//        } else {
//            logger.warn("No results found for custom search.");
//        }


        return searchResponse;
    }





}
