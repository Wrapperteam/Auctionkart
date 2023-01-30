package com.ust.userMicroservice.userMicroservice.service;

import com.ust.userMicroservice.userMicroservice.Dto.UserDto;
import com.ust.userMicroservice.userMicroservice.exception.UserNotFoundException;
import com.ust.userMicroservice.userMicroservice.model.User;
import com.ust.userMicroservice.userMicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;


    public UserDto getUser(int userId) {
        User userOpt =  repo.findById(userId).get();

        UserDto userDto=new UserDto();
        userDto.setUserId(userOpt.getUserId());
        userDto.setName(userOpt.getName());
        userDto.setUserEmail(userOpt.getUserEmail());
        userDto.setPhoneNumber(userOpt.getPhoneNumber());
        userDto.setAddress(userOpt.getAddress());
        userDto.setRole(userOpt.getRole());
        return userDto;
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
