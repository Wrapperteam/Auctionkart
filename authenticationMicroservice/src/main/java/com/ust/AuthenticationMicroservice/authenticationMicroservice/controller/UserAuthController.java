package com.ust.AuthenticationMicroservice.authenticationMicroservice.controller;

import com.ust.AuthenticationMicroservice.authenticationMicroservice.model.User;
import com.ust.AuthenticationMicroservice.authenticationMicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserAuthController {

    @Autowired
    private UserService userService;


    @GetMapping("/sellerLogin")
    @PreAuthorize("hasAuthority('SELLER')")
    public List<User> sellerLogin(){
        return userService.getAll();
    }

    @GetMapping("/bidderLogin")
    @PreAuthorize("hasAuthority('BIDDER')")
    public String bidderLogin(){
        return "bidder logged in";
    }


}
