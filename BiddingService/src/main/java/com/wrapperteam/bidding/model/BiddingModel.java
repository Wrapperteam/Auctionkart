package com.wrapperteam.bidding.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="biddingtable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BiddingModel {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserID;
    private int bidderId;
    private int productID;
    private int amount;

}
