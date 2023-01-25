package com.ust.AuthenticationMicroservice.authenticationMicroservice.repository;

import com.ust.AuthenticationMicroservice.authenticationMicroservice.model.User;
import com.ust.AuthenticationMicroservice.authenticationMicroservice.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AuthRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
