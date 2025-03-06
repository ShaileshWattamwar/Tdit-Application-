package com.ecommerce.WishList.controller;

import com.ecommerce.WishList.entity.WishList;
import com.ecommerce.WishList.service.WishlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    @Autowired
    private WishlistService wishlistService;

    // get wishlist items userid
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishList>> getUserWishlist(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        logger.info("get wishlist for userid {}, page {}, size {}", userId, page, size);

        Page<WishList> wishlistPage = wishlistService.getWishlistItems(userId, page, size);

        logger.info("successfully get wishlist for userid {}", userId);
        List<WishList> wishlist = wishlistPage.getContent();
        return ResponseEntity.ok(wishlist);
    }

    // add product to wishlist
    @PostMapping("/add/{userId}/{productId}")
    public ResponseEntity<WishList> addToWishlist(@PathVariable Integer userId, @PathVariable Integer productId) {

        logger.info("adding productid {} to wishlist for userid {}", productId, userId);

        WishList wishlistItem = wishlistService.addToWishlist(userId, productId);

        logger.info("successfully added productid: {} to wishlist for userid {}", productId, userId);
        return ResponseEntity.ok(wishlistItem);
    }

    // delete product from Wishlist
    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Integer userId, @PathVariable Integer productId) {

        logger.info("deleting productid {} from wishlist for userid {}", productId, userId);

        wishlistService.removeFromWishlist(userId, productId);

        logger.info("successfully deleted productid {} from wishlist for userid {}", productId, userId);
        return ResponseEntity.ok("product removed from wishlist successfully");
    }
}
