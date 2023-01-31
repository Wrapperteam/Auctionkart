package com.auction.productService.productDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String name;
    private String userEmail;
    private String phoneNumber;
    private String address;
    private String role;
}
