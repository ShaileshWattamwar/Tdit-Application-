package com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.UserProductInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProductRepository extends JpaRepository<UserProductInteraction,Long> {

    @Query("SELECT DISTINCT up.userId FROM UserProductInteraction up " +
            "WHERE up.productId IN :userProducts AND up.userId <> :userId")
    List<Long> findSimilarUsers(@Param("userProducts") List<Long> userProducts,
                                @Param("userId") Long userId);

    @Query("SELECT DISTINCT up.productId FROM UserProductInteraction up " +
            "WHERE up.userId IN :similarUsers AND up.productId NOT IN :userProducts")
    List<Long> findRecommendedProducts(@Param("similarUsers") List<Long> similarUsers,
                                       @Param("userProducts") List<Long> userProducts);

    // Find all product IDs that a user has interacted with
    @Query("SELECT DISTINCT up.productId FROM UserProductInteraction up WHERE up.userId = :userId")

    List<Long> findUserProducts(@Param("userId") Long userId);

    Optional<UserProductInteraction> findByUserIdAndProductId(Long userId, Long productId);


}
