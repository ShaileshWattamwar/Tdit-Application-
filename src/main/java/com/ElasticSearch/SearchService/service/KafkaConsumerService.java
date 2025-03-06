package com.ElasticSearch.SearchService.service;

import com.ElasticSearch.SearchService.entity.Product;

import com.ElasticSearch.SearchService.repo.ProductRepo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final ProductRepo productRepo;

    public KafkaConsumerService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @KafkaListener(
            topics = "product",
            groupId = "search_group"
    )
    public void consume(Product product) {
        logger.info("Received Product from Kafka: {}", product);
        productRepo.save(product); // Save to Elasticsearch
        logger.info("Product saved to Elasticsearch: {}", product);
    }


}
