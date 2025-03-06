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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(
                1L,
                "Laptop",
                "Electronics",
                new BigDecimal("999.99"),
                "Gaming laptop with RTX 3070",
                10,
                new BigDecimal("4.5"),
                "Dell"
        );
    }

    @Test
    void testGetProducts() {
        when(productRepo.findAll()).thenReturn(java.util.List.of(product));

        Iterable<Product> products = productService.getProducts();
        assertNotNull(products);
        assertTrue(products.iterator().hasNext());
    }

    @Test
    void testInsertProduct() {
        when(productRepo.save(product)).thenReturn(product);

        Product savedProduct = productService.insertProduct(product);
        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());
    }

    @Test
    void testUpdateProduct() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));

        Product updatedProduct = productService.updateProduct(product, 1);

        assertNotNull(updatedProduct);
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        verify(productRepo, times(1)).findById(1);  // Verify method call
    }

    @Test
    void testDeleteProduct() {
        int productId = 1;

        // Mocking existsById to return true, so deleteById gets executed
        when(productRepo.existsById(productId)).thenReturn(true);

        // Call the service method
        productService.deleteProduct(productId);

        // Verify that deleteById was called
        verify(productRepo, times(1)).deleteById(productId);
    }
}
