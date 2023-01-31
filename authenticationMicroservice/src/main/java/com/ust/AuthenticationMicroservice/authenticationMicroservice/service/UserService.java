package com.ust.AuthenticationMicroservice.authenticationMicroservice.service;

import com.ust.AuthenticationMicroservice.authenticationMicroservice.dto.ProductResponse;
import com.ust.AuthenticationMicroservice.authenticationMicroservice.model.User;
import com.ust.AuthenticationMicroservice.authenticationMicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    RestTemplate restTemplate = new RestTemplate();

    String userName=null;
    int userId=0;


    public List<ProductResponse> geForSeller() {
        //RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:8083/product/seller/"+userId, List.class);
        List<ProductResponse> response= responseEntity.getBody();
        return response;
    }

    public List<ProductResponse> getAllProducts() {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:8083/product/products", List.class);
        List<ProductResponse> response= responseEntity.getBody();
        System.out.println(response);
        return response;
    }


    public Optional<User> saveEncryptedpw(String info){
        userName=info;
        Optional<User> user=repo.findByName(info);
        user.get().setPassword(passwordEncoder.encode(user.get().getPassword()));
        userId =user.get().getUserId();
        return user;
    }

}
