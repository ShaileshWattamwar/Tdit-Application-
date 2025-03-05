package com.Version1._Recommandation.FirstLevelReccommadtionModel.Service;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;

import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);
    public List<Product> getAllProducts();

}
