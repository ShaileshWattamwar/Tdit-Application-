package com.ElasticSearch.SearchService.repo;

import com.ElasticSearch.SearchService.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepo extends ElasticsearchRepository<Product,Integer> {
}
