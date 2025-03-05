package com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
