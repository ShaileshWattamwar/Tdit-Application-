package com.Version1._Recommandation.FirstLevelReccommadtionModel.Controller;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Service.ServiceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;



    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productServiceImpl.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/get")
    public List<Product> getAllProducts() {

        return productServiceImpl.getAllProducts();
    }

}
