package com.userservice.repository;

import com.userservice.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer,Long> {
    Consumer findByUserName(String username);

    //pr
    boolean existsByUserName(String userName);


    boolean existsByEmail(String email);

    void deleteByUserName(String username);


}
