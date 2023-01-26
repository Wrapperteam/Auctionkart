package com.wrapperteam.bidding.service;

import com.wrapperteam.bidding.model.biddingmodel;
import com.wrapperteam.bidding.repository.biddingrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class biddingservice {
    @Autowired
    biddingrepo repo;
    public List<biddingmodel> getAll() {
        return repo.findAll();

    }

    public biddingmodel saveBidder(biddingmodel bidder) {
        return repo.save(bidder);
    }

    public String updateBidder(biddingmodel bidder) {

            Optional<biddingmodel> biddupdate = repo.findById(bidder.getUserID());

            if(biddupdate.isPresent()) {
                biddupdate.get().setAmount(bidder.getAmount());

            }else {
                return "Not found";
            }

            repo.save(biddupdate.get());
            return "Successfully saved";
        }
    public String deleteBidder(Integer bidder) {
        // TODO Auto-generated method stub
        repo.deleteById(bidder);

        return "Employee Details Deleted";
    }

}

