package com.ElasticSearch.SearchService.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;


@Document(indexName = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Long id;

    private String name;

    private String category;

    private BigDecimal price;

    private String description;

    private int stock;

    private BigDecimal rating;

    private String brand;

    public Product(Long id, String name, String category, BigDecimal price, String description, int stock, BigDecimal rating, String brand) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.rating = rating;
        this.brand = brand;
    }

    public Product(long id, String testProduct, String testCategory, double v, String testDescription, int stock, double v1, String testBrand) {

    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", rating=" + rating +
                ", brand='" + brand + '\'' +
                '}';
    }
}
