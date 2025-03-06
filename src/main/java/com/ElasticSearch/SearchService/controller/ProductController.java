package com.ElasticSearch.SearchService.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ElasticSearch.SearchService.entity.Product;
import com.ElasticSearch.SearchService.service.ElasticSearchService;
import com.ElasticSearch.SearchService.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apis")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    private ProductService productService;

    @Autowired
    private ElasticSearchService elasticSearchService;



    @GetMapping("/findAll")
    Iterable<Product> findAll(){
        logger.info("Fetching all products from database");
        return productService.getProducts();

    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody Product product){
        logger.info("Inserting new product: {}", product);
        return productService.insertProduct(product);
    }

    @GetMapping("/matchAll")
    public String matchAll() throws IOException {
        logger.info("Executing matchAll search");
        SearchResponse<Map> searchResponse =  elasticSearchService.matchAllServices();
        logger.info("MatchAll Search Results: {}", searchResponse.hits().hits().size());


        return searchResponse.hits().hits().toString();
    }
    //matchAllProducts video content
    @GetMapping("/matchAllProducts")
    public List<Product> matchAllProducts() throws IOException {
        logger.info("Executing matchAllProducts search");

        SearchResponse<Product> searchResponse =  elasticSearchService.matchAllProductsServices();


        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts  = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        logger.info("Total Products found: {}", listOfProducts.size());

        return listOfProducts;
    }

    @GetMapping("/matchAllProducts/{fieldValue}")
    public List<Product> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
        logger.info("Executing matchAllProductsWithName search for: {}", fieldValue);

        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductsWithName(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts  = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        logger.info("Total Products found: {}", listOfProducts.size());

        return listOfProducts;
    }

    @GetMapping("/autoSuggest/{partialProductName}")
    public List<String> autoSuggestProductSearch(@PathVariable String partialProductName) throws IOException {

        logger.info("Executing autoSuggest search for: {}", partialProductName);

        // Call the service method to fetch the auto-suggested products
        SearchResponse<Product> searchResponse = elasticSearchService.autoSuggestProduct(partialProductName);

        // Extract product names from the search hits
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for (Hit<Product> hit : hitList) {
            productList.add(hit.source());
        }

        // Extract the names of the products
        List<String> listOfProductNames = new ArrayList<>();
        for (Product product : productList) {
            listOfProductNames.add(product.getName());
        }

        logger.info("Auto-Suggest Results: {}", listOfProductNames);

        return listOfProductNames;
    }

    @GetMapping("/fuzzySearch/{fieldValue}")
    public List<Product> fuzzySearchProducts(@PathVariable String fieldValue) throws IOException {
        logger.info("Executing fuzzySearch for: {}", fieldValue);

        SearchResponse<Product> searchResponse = elasticSearchService.fuzzySearchProducts(fieldValue);

        List<Product> listOfProducts = new ArrayList<>();
        searchResponse.hits().hits().forEach(hit -> listOfProducts.add(hit.source()));
        logger.info("Fuzzy Search Results: {}", listOfProducts.size());


        return listOfProducts;
    }


    @GetMapping("/customSearch")
    public List<Product> customSearch(
            @RequestParam(required = false) String nameQuery,
            @RequestParam(required = false) String descriptionQuery,
            @RequestParam(required = false) String prefix,
            @RequestParam(required = false) String category
    ) throws IOException {

        SearchResponse<Product> searchResponse = elasticSearchService.customSearch(nameQuery, descriptionQuery, prefix, category);


        return searchResponse.hits().hits().stream()
                .map(hit -> hit.source())
                .collect(Collectors.toList());
    }




}
