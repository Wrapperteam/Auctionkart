package com.ust.AuthenticationMicroservice.authenticationMicroservice.repository;

import com.ust.AuthenticationMicroservice.authenticationMicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByName(String username);
}
