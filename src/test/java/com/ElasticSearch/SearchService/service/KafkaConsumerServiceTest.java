package com.ElasticSearch.SearchService.service;

import com.ElasticSearch.SearchService.entity.Product;
import com.ElasticSearch.SearchService.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Laptop", "Electronics", BigDecimal.valueOf(1200.00), "Gaming laptop", 10, BigDecimal.valueOf(4.5), "Dell");
    }

    @Test
    void testConsume() {
        kafkaConsumerService.consume(product);

        verify(productRepo, times(1)).save(product);
    }
}
