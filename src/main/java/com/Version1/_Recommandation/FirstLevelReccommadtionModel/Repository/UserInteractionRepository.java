package com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction,Long> {
    List<UserInteraction> findByUser_Id(long userId);
//    List<UserInteraction> findByUser_Id(Long userId);

}
