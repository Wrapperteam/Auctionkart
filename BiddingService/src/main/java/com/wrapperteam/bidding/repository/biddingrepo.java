package com.wrapperteam.bidding.repository;

import com.wrapperteam.bidding.model.biddingmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface biddingrepo extends JpaRepository<biddingmodel,Integer>{


    biddingmodel findByProductID(int productID);
}
