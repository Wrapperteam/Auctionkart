package com.ust.AuthenticationMicroservice.authenticationMicroservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {

    @GetMapping("/login")
    public String login(){
        return "Login Sucessfull";
    }

}