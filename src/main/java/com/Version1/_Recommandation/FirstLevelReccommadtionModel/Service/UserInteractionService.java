package com.Version1._Recommandation.FirstLevelReccommadtionModel.Service;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.User;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.UserInteraction;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.ProductRepository;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.UserInteractionRepository;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInteractionService {

    @Autowired
    private UserInteractionRepository userInteractionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

//    public UserInteraction saveInteraction(UserInteraction interaction) {
//        return userInteractionRepository.save(interaction);
//    }

    public UserInteraction saveUserInteraction(Long userId, Long productId) {
        // Fetch user and product from DB
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Create and save UserInteraction
        UserInteraction interaction = new UserInteraction();
        interaction.setUser(user);
        interaction.setProduct(product);

        return userInteractionRepository.save(interaction);
    }

    public List<UserInteraction> getInteractionsByUserId(Long userId) {
        return userInteractionRepository.findByUser_Id(userId);
    }
}
