package com.ecommerce.WishList.repository;

import com.ecommerce.WishList.entity.Product;
import com.ecommerce.WishList.entity.User;
import com.ecommerce.WishList.entity.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishList, Integer> {

    //  find wishlist items by user id
      Page<WishList> findByUserId(Integer userId, Pageable pageable);

    //  find a specific wishlist item by user and product
    Optional<WishList> findByUserIdAndProductId(Integer userId, Integer productId);

    //  check if a product already present in  wishlist
    boolean existsByUserAndProduct(User user, Product product);

}