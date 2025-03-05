package com.Version1._Recommandation.FirstLevelReccommadtionModel.Controller;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {


    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Product>> getRecommendations(@PathVariable Long userId) {
        List<Product> recommendedProducts = recommendationService.getRecommendedProducts(userId);
        return ResponseEntity.ok(recommendedProducts);
    }

    @GetMapping("/personalized/{userId}")
    public  ResponseEntity<List<Product>> getPersonalizedRecommendations(@PathVariable Long userId){
        List<Product> personalizedProducts= recommendationService.getPersonalizedRecommendations(userId);
        return ResponseEntity.ok(personalizedProducts);
    }
}
