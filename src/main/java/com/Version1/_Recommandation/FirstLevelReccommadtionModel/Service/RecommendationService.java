package com.Version1._Recommandation.FirstLevelReccommadtionModel.Service;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.UserInteraction;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.ProductRepository;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.UserInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    @Autowired
    UserInteractionRepository userInteractionRepository;

    @Autowired
    ProductRepository productRepository;

 // here we get recommendation as per asending order which user added
    public List<Product> getRecommendedProducts(Long userId) {
        List<UserInteraction> interactions = userInteractionRepository.findByUser_Id(userId);

        Set<String> categories = new HashSet<>();
        Set<String> tags = new HashSet<>();

        for (UserInteraction interaction : interactions) {
            Product product = interaction.getProduct();
            categories.add(product.getCategory());
            tags.addAll(Arrays.asList(product.getTags().split(",")));
        }

        return productRepository.findAll().stream()
                .filter(p -> categories.contains(p.getCategory()) ||
                        Arrays.stream(p.getTags().split(",")).anyMatch(tags::contains))
                .collect(Collectors.toList());
    }


    // updated
    // here we get recommendation as per getting count like order is based on highest count of product
    public List<Product> getPersonalizedRecommendations(Long userId) {
        List<UserInteraction> interactions = userInteractionRepository.findByUser_Id(userId);
        // Count the frequency of categories and tags

        Map<String, Integer> categoryFrequency = new HashMap<>();
        Map<String, Integer> tagFrequency = new HashMap<>();

        for (UserInteraction interaction : interactions) {
            Product product = interaction.getProduct();

            // Count category frequency
            categoryFrequency.put(product.getCategory(), categoryFrequency.getOrDefault(product.getCategory(), 0) + 1);

            // Count tag frequency
            for (String tag : product.getTags().split(",")) {
                tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) + 1);
            }


        }
        // Get all products and sort based on frequency of categories & tags
        return productRepository.findAll().stream()
                .sorted((p1,p2)->{
                    int p1Score=categoryFrequency.getOrDefault(p1.getCategory(), 0) +
                            Arrays.stream(p1.getTags().split(","))
                                    .mapToInt(tag -> tagFrequency.getOrDefault(tag, 0))
                                    .sum();

                    int p2Score = categoryFrequency.getOrDefault(p2.getCategory(), 0) +
                            Arrays.stream(p2.getTags().split(","))
                                    .mapToInt(tag -> tagFrequency.getOrDefault(tag, 0))
                                    .sum();
                    return Integer.compare(p2Score, p1Score); // Sort in descending order

                })

                .collect(Collectors.toList());

    }

}
