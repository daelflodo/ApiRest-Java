package com.dael.ApiRest.persistence.repository;

import com.dael.ApiRest.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // ...
    Optional<UserEntity> findUserEntityByUsername(String username);

//    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
//    Optional<UserEntity> findUser(@Param("username") String username);

}

