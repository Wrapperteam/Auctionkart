package com.wrapperteam.bidding.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.events.Event;

import java.util.Date;

@Entity
@Table(name="biddingtable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class biddingmodel {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserID;
    private String productID;
    private int amount;

}
