package com.Version1._Recommandation.FirstLevelReccommadtionModel.Controller;


import com.Version1._Recommandation.FirstLevelReccommadtionModel.DTO.InteractionRequest;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Service.UserProductInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class UserProductInteractionController {
// here get recommendation based on another user also included  intraction type
    @Autowired
    private UserProductInteractionService userProductInteractionService;

    @PostMapping("/save")
    public ResponseEntity<String> saveInteraction(@RequestBody InteractionRequest request) {
        userProductInteractionService.saveUserInteraction(request.getUserId(), request.getProductId(), request.getInteractionType());
        return ResponseEntity.ok("Interaction saved successfully");
    }

    // API to get recommended products for a user
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<List<Long>> getRecommendations(@PathVariable Long userId) {
        List<Long> recommendations = userProductInteractionService.getRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }
}
