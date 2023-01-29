package com.example.cloudgateway;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/fallback")
public class FallbackMethodController {
    @GetMapping("/authenticationFallBack")
    public String authenticationFallBackMethod() {
        return "authentication taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/biddingFallBack")
    public String biddingFallBackMethod() {
        return "bidding Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/productFallBack")
    public String productFallBackMethod() {
        return "productService is taking longer than Expected." +
                " Please try again later";
    }
    @GetMapping("/userFallBack")
    public String userFallBackMethod() {
        return "user Service is taking longer than Expected." +
                " Please try again later";
    }

}

