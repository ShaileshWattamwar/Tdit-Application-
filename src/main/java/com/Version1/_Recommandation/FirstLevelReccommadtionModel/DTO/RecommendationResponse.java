package com.Version1._Recommandation.FirstLevelReccommadtionModel.DTO;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;

import java.util.List;

public class RecommendationResponse {

    private int count;
    private List<Product> recommendedProducts;

    public RecommendationResponse(int count, List<Product> recommendedProducts) {
        this.count = count;
        this.recommendedProducts = recommendedProducts;
    }

    public int getCount() {
        return count;
    }

    public List<Product> getRecommendedProducts() {
        return recommendedProducts;
    }
}
