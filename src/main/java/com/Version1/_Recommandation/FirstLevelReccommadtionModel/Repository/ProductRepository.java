package com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
