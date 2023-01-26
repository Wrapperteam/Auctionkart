package com.ust.AuthenticationMicroservice.authenticationMicroservice.service;

import com.ust.AuthenticationMicroservice.authenticationMicroservice.model.User;
import com.ust.AuthenticationMicroservice.authenticationMicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return repo.findAll();
    }

    public void getStudentById() {
         repo.findAll();
    }

    public void saveEncryptedpw(String info){
        User user=repo.findByName(info).orElseGet(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         repo.save(user);
    }

}
