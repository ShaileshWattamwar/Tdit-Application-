package com.ecommerce.WishList.service;

import com.ecommerce.WishList.Exception.*;
import com.ecommerce.WishList.entity.Product;
import com.ecommerce.WishList.entity.User;
import com.ecommerce.WishList.entity.WishList;
import com.ecommerce.WishList.repository.ProductRepository;
import com.ecommerce.WishList.repository.UserRepository;
import com.ecommerce.WishList.repository.WishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // get wishlist items for  user with pagination
    @Cacheable(value = "wishlistCache", key = "#userId")
    public Page<WishList> getWishlistItems(Integer userId, int page, int size) {
        logger.info("Fetching wishlist items for userId: {}", userId);

        // get wishlist items from the repository
        Page<WishList> wishlist = wishlistRepository.findByUserId(userId, PageRequest.of(page, size));

        // If wishlist is Empty then throw an exception
        if (wishlist.isEmpty()) {
            logger.warn("wishlist is empty for userId {}", userId);
            throw new WishlistNotFoundException("wishlist not found for userId " + userId);
        }

        logger.info("successfully fetched wishlist items for userId {}", userId);
        return wishlist;
    }

    // Add  product to wishlist
    @CacheEvict(value = "wishlistCache", key = "#userId + '-*'")
    public WishList addToWishlist(Integer userId, Integer productId) {
        logger.info(" add productId {} to wishlist for userId {}", productId, userId);

        // get user by id or throw exception if user not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("user not found with id {}", userId);
                    return new UserNotFoundException("user not found with id " + userId);
                });

        // get product by id or throw exception if  user not found
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("product not found with id {}", productId);
                    return new ProductNotFoundException("product not found with id " + productId);
                });

        // Check if the product is in stock
        if (product.getStock() <= 0) {
            logger.warn("product id {} is out of stock", productId);
            throw new ProductOutOfStockException("product id " + productId + " is out of stock");
        }

        // Check if the product is already in wishlist
        if (wishlistRepository.existsByUserAndProduct(user, product)) {
            logger.warn("product id {} already exists in wishlist for userId {}", productId, userId);
            throw new DuplicateWishlistItemException("product already exists in wishlist");}

        // create new wishlist
        WishList wishlist = new WishList();
        wishlist.setUser(user);
        wishlist.setProduct(product);

        // save wishlist
        WishList savedWishlist = wishlistRepository.save(wishlist);
        logger.info("successfully added productid {} to wishlist for userid {}", productId, userId);
        return savedWishlist;
    }

    // remove a product from the  wishlist
    @CacheEvict(value = "wishlistCache", key = "#userId + '-*'")
    public void removeFromWishlist(Integer userId, Integer productId) {
        logger.info(" remove product id {} from wishlist for userid {}", productId, userId);

        // get the wishlist item by user and product id
        WishList wishlist = wishlistRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> {
                    logger.warn("wishlist item not found for productid {} and userid {}", productId, userId);
                    return new WishlistNotFoundException("wishlist item not found for product id " + productId);
                });

        // delete the wishlist
        wishlistRepository.delete(wishlist);
        logger.info("successfully deleted productid {} from wishlist for userid {}", productId, userId);
    }
}
