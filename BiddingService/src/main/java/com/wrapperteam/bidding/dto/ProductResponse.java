package com.wrapperteam.bidding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductResponse {
    private int productId;
    private int sellerId;
    private String username;
    private String productName;
    private String productType;
    private String description;
    private String url;
    private double minAmount;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiryDateTime;


}

