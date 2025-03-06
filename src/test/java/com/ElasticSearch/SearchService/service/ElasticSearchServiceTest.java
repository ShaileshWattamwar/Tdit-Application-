package com.ElasticSearch.SearchService.service;



import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.util.ObjectBuilder;
import com.ElasticSearch.SearchService.entity.Product;
import com.ElasticSearch.SearchService.util.ElasticSearchUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ElasticSearchServiceTest {

    @Mock
    private ElasticsearchClient elasticsearchClient;

    @InjectMocks
    private ElasticSearchService elasticSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void matchAllServices() throws IOException {
        SearchResponse<Map> mockResponse = mock(SearchResponse.class);

        // Create a TotalHits object
        TotalHits totalHits = TotalHits.of(t -> t
                .value(0) // Total number of hits
                .relation(TotalHitsRelation.Eq) // Exact match
        );

        // Create a HitsMetadata object
        HitsMetadata<Map> hitsMetadata = HitsMetadata.of(h -> h
                .hits(Collections.emptyList()) // No hits
                .total(totalHits) // Set the TotalHits object
        );

        // Mock the behavior of hits() to return the HitsMetadata object
        when(mockResponse.hits()).thenReturn(hitsMetadata);

        // Mock the behavior of the Elasticsearch client
        when(elasticsearchClient.search(any(Function.class), eq(Map.class))).thenReturn(mockResponse);

        SearchResponse<Map> response = elasticSearchService.matchAllServices();

        assertNotNull(response);
        verify(elasticsearchClient, times(1)).search(any(Function.class), eq(Map.class));
    }

    @Test
    void matchAllProductsServices() throws IOException {
        SearchResponse<Product> mockResponse = mock(SearchResponse.class);

        // Create a TotalHits object
        TotalHits totalHits = TotalHits.of(t -> t
                .value(0) // Total number of hits
                .relation(TotalHitsRelation.Eq) // Exact match
        );

        // Create a HitsMetadata object
        HitsMetadata<Product> hitsMetadata = HitsMetadata.of(h -> h
                .hits(Collections.emptyList()) // No hits
                .total(totalHits) // Set the TotalHits object
        );

        // Mock the behavior of hits() to return the HitsMetadata object
        when(mockResponse.hits()).thenReturn(hitsMetadata);

        // Mock the behavior of the Elasticsearch client
        when(elasticsearchClient.search(any(Function.class), eq(Product.class))).thenReturn(mockResponse);

        SearchResponse<Product> response = elasticSearchService.matchAllProductsServices();

        assertNotNull(response);
        verify(elasticsearchClient, times(1)).search(any(Function.class), eq(Product.class));
    }

    @Test
    void matchProductsWithName() throws IOException {
        String fieldValue = "testProduct";
        SearchResponse<Product> mockResponse = mock(SearchResponse.class);

        // Create a TotalHits object
        TotalHits totalHits = TotalHits.of(t -> t
                .value(0) // Total number of hits
                .relation(TotalHitsRelation.Eq) // Exact match
        );

        // Create a HitsMetadata object
        HitsMetadata<Product> hitsMetadata = HitsMetadata.of(h -> h
                .hits(Collections.emptyList()) // No hits
                .total(totalHits) // Set the TotalHits object
        );

        // Mock the behavior of hits() to return the HitsMetadata object
        when(mockResponse.hits()).thenReturn(hitsMetadata);

        // Mock the behavior of the Elasticsearch client
        when(elasticsearchClient.search(any(Function.class), eq(Product.class))).thenReturn(mockResponse);

        SearchResponse<Product> response = elasticSearchService.matchProductsWithName(fieldValue);

        assertNotNull(response);
        verify(elasticsearchClient, times(1)).search(any(Function.class), eq(Product.class));
    }

    @Test
    void autoSuggestProduct() throws IOException {
        String partialProductName = "test";
        SearchResponse<Product> mockResponse = mock(SearchResponse.class);

        // Create a TotalHits object
        TotalHits totalHits = TotalHits.of(t -> t
                .value(0) // Total number of hits
                .relation(TotalHitsRelation.Eq) // Exact match
        );

        // Create a HitsMetadata object
        HitsMetadata<Product> hitsMetadata = HitsMetadata.of(h -> h
                .hits(Collections.emptyList()) // No hits
                .total(totalHits) // Set the TotalHits object
        );

        // Mock the behavior of hits() to return the HitsMetadata object
        when(mockResponse.hits()).thenReturn(hitsMetadata);

        // Mock the behavior of the Elasticsearch client
        when(elasticsearchClient.search(any(Function.class), eq(Product.class))).thenReturn(mockResponse);

        SearchResponse<Product> response = elasticSearchService.autoSuggestProduct(partialProductName);

        assertNotNull(response);
        verify(elasticsearchClient, times(1)).search(any(Function.class), eq(Product.class));
    }

    @Test
    void fuzzySearchProducts() throws IOException {
        String fieldValue = "test";
        SearchResponse<Product> mockResponse = mock(SearchResponse.class);

        // Create a TotalHits object
        TotalHits totalHits = TotalHits.of(t -> t
                .value(0) // Total number of hits
                .relation(TotalHitsRelation.Eq) // Exact match
        );

        // Create a HitsMetadata object
        HitsMetadata<Product> hitsMetadata = HitsMetadata.of(h -> h
                .hits(Collections.emptyList()) // No hits
                .total(totalHits) // Set the TotalHits object
        );

        // Mock the behavior of hits() to return the HitsMetadata object
        when(mockResponse.hits()).thenReturn(hitsMetadata);

        // Mock the behavior of the Elasticsearch client
        when(elasticsearchClient.search(any(Function.class), eq(Product.class))).thenReturn(mockResponse);

        SearchResponse<Product> response = elasticSearchService.fuzzySearchProducts(fieldValue);

        assertNotNull(response);
        verify(elasticsearchClient, times(1)).search(any(Function.class), eq(Product.class));
    }

    @Test
    void customSearch() throws IOException {
        // Mock a product object
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setCategory("Electronics");
        product.setPrice(BigDecimal.valueOf(99.99));

        // Mock the Hit object with the required index and source (product)
        Hit<Product> hit = Hit.of(h -> h
                .index("product-index")  // Mock the index to avoid MissingRequiredPropertyException
                .source(product));

        // Mock total hits metadata (1 hit)
        TotalHits totalHits = TotalHits.of(t -> t
                .value(1) // Total number of hits
                .relation(TotalHitsRelation.Eq));

        // Mock HitsMetadata with our hit and total hits
        HitsMetadata<Product> hitsMetadata = HitsMetadata.of(h -> h
                .hits(Collections.singletonList(hit))  // Add our mock hit here
                .total(totalHits));

        // Mock the SearchResponse to return the HitsMetadata
        SearchResponse<Product> mockResponse = mock(SearchResponse.class);

        // Mock the behavior of hits() to return the HitsMetadata
        when(mockResponse.hits()).thenReturn(hitsMetadata);

        // Mock ElasticsearchClient's search method to return our mockResponse
        when(elasticsearchClient.search(any(SearchRequest.class), eq(Product.class)))
                .thenReturn(mockResponse);

        // Call the method under test
        SearchResponse<Product> response = elasticSearchService.customSearch("testName", "testDescription", "testPrefix", "testCategory");

        // Assertions
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.hits(), "Response hits should not be null");
        assertFalse(response.hits().hits().isEmpty(), "Hits list should not be empty");

        // Verify Elasticsearch client was called once
        verify(elasticsearchClient, times(1)).search(any(SearchRequest.class), eq(Product.class));
    }







}
