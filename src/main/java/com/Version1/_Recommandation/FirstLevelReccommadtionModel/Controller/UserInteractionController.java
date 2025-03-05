package com.Version1._Recommandation.FirstLevelReccommadtionModel.Controller;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.UserInteraction;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.ProductRepository;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.UserInteractionRepository;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interactions")
public class UserInteractionController {


    @Autowired
    private UserInteractionRepository userInteractionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserInteractionService userInteractionService;


//    @PostMapping
//    public ResponseEntity<UserInteraction> saveInteraction(@RequestBody UserInteraction interaction) {
//        Product product = productRepository.findById(interaction.getProduct().getId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        interaction.setProduct(product);
//        UserInteraction savedInteraction = userInteractionRepository.save(interaction);
//        return ResponseEntity.ok(savedInteraction);
//    }

    @PostMapping
    public ResponseEntity<UserInteraction> saveInteraction(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long productId = request.get("productId");

        UserInteraction interaction = userInteractionService.saveUserInteraction(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(interaction);
    }
    @GetMapping
    public List<UserInteraction> getAllInteractions() {
        return userInteractionRepository.findAll();
    }
}
