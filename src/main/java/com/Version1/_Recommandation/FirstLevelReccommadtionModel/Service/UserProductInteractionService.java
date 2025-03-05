package com.Version1._Recommandation.FirstLevelReccommadtionModel.Service;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.InteractionType;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.UserProductInteraction;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserProductInteractionService {
// In that user get notigication based on another users notifications
    @Autowired
    private UserProductRepository userProductRepository;

    public void saveUserInteraction(Long userId, Long productId, InteractionType interactionType) {
        // Check if an interaction already exists
        Optional<UserProductInteraction> existingInteraction = userProductRepository
                .findByUserIdAndProductId(userId, productId);

        if (existingInteraction.isPresent()) {
            // If exists, update interaction type
            UserProductInteraction interaction = existingInteraction.get();
            interaction.setInteractionType(interactionType);
            interaction.setInteractionTime(LocalDateTime.now());
            userProductRepository.save(interaction);
        } else {
            // If not exists, create new entry
            UserProductInteraction interaction = new UserProductInteraction(userId, productId, interactionType);
            userProductRepository.save(interaction);
        }
    }

    public List<Long> getRecommendations(Long userId) {
        // Get the user's interacted products
        List<Long> userProducts = userProductRepository.findUserProducts(userId);
        if (userProducts.isEmpty()) return Collections.emptyList();

        // Find similar users
        List<Long> similarUsers = userProductRepository.findSimilarUsers(userProducts, userId);
        if (similarUsers.isEmpty()) return Collections.emptyList();

        // Get recommended products
        return userProductRepository.findRecommendedProducts(similarUsers, userProducts);
    }
}
