package com.wrapperteam.bidding.controller;

import com.wrapperteam.bidding.exception.AlreadyExistException;
import com.wrapperteam.bidding.model.biddingmodel;
import com.wrapperteam.bidding.service.biddingservice;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bidder")
public class biddingcontroller {
    @Autowired
    biddingservice service;

    @GetMapping("/all")
    public List<biddingmodel> getAll(){
        return service.getAll();
    }
    @PostMapping("/save")
    public ResponseEntity saveDetails(@RequestBody biddingmodel bidder) {
        ResponseEntity responseEntity;
        try {
            service.saveBidder(bidder);
            responseEntity = new ResponseEntity<>("sucessfully created", HttpStatus.CREATED);
        } catch (AlreadyExistException e) {
            responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @PutMapping("/update")
    public String updateBidder(@RequestBody biddingmodel bidder) {
        return service.updateBidder(bidder);

    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        return service.deleteBidder(id);

    }


}
