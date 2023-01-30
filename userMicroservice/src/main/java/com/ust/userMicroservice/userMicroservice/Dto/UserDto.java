package com.ust.userMicroservice.userMicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private int userId;
    private String name;
    private String userEmail;
    private String phoneNumber;
    private String address;
    private String role;

}
