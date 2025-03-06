package com.ElasticSearch.SearchService.service;

import com.ElasticSearch.SearchService.entity.Product;
import com.ElasticSearch.SearchService.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepo productRepo;

    public Iterable<Product> getProducts() {
        logger.info("Fetching all products from Elasticsearch");

        return productRepo.findAll();
    }

    public Product insertProduct(Product product) {
        logger.info("Inserting product: {}", product);
        Product savedProduct = productRepo.save(product);
        logger.info("Product inserted successfully with ID: {}", savedProduct.getId());
        return savedProduct;


    }

    public Product updateProduct(Product product, int id) {
        logger.info("Updating product with ID: {}", id);

        Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            productToUpdate.setPrice(product.getPrice());
            productRepo.save(productToUpdate);
            logger.info("Product with ID: {} updated successfully", id);
            return productToUpdate;
        } else {
            logger.error("Product with ID: {} not found, update failed", id);
            throw new RuntimeException("Product not found");
        }
    }

    public void deleteProduct(int id ) {
        logger.info("Deleting product with ID: {}", id);

        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            logger.info("Product with ID: {} deleted successfully", id);
        } else {
            logger.warn("Product with ID: {} not found, delete operation skipped", id);
        }
    }

}
