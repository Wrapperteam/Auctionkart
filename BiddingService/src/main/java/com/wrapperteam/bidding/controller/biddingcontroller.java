package com.wrapperteam.bidding.controller;

import com.wrapperteam.bidding.exception.AlreadyExistException;
import com.wrapperteam.bidding.model.BiddingModel;
import com.wrapperteam.bidding.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bidder")
public class BiddingController {
    @Autowired
    BiddingService service;

    @GetMapping("/all")
    public List<BiddingModel> getAll(){
        return service.getAll();
    }
    @PostMapping("/save")
    public ResponseEntity saveDetails(@RequestBody BiddingModel bidder) {
        ResponseEntity responseEntity;
        try {
            service.saveBidder(bidder);
            responseEntity = new ResponseEntity<>("sucessfully created", HttpStatus.CREATED);
        } catch (AlreadyExistException e) {
            responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        return service.deleteBidder(id);

    }

    @PutMapping("/update")
    public String bidding(@RequestBody BiddingModel bidder) {
       // return service.updateBidder(bidder);
     return service.updateBiddingAmount(bidder);
        //return "Bidding amount updated successfully";
    }


}
