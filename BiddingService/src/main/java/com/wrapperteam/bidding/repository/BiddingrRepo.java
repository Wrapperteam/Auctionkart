package com.wrapperteam.bidding.repository;

import com.wrapperteam.bidding.model.BiddingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BiddingrRepo extends JpaRepository<BiddingModel,Integer>{


    BiddingModel findByProductID(int productID);
}
