package com.ust.userMicroservice.userMicroservice.service;

import com.ust.userMicroservice.userMicroservice.exception.UserNotFoundException;
import com.ust.userMicroservice.userMicroservice.model.User;
import com.ust.userMicroservice.userMicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;


    public User getUser(int userId) {
        Optional<User> userOpt =  repo.findById(userId);
        return userOpt.orElseGet(null);
    }

    public void saveUser(User user) {
        repo.save(user);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public void updateUser(User user) {
        if (repo.existsById(user.getUserId())) {
            User saveUser = repo.save(user);
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    public void deleteUser(int id) {
        repo.deleteById(id);
    }
}
