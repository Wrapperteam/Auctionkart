package com.wrapperteam.bidding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="biddingtable")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class biddingmodel {
@Id
    private int UserID;

    private String SellerID;

    private String BidderID;

    private int amount;

}
