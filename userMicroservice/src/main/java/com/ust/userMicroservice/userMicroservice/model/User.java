package com.ust.userMicroservice.userMicroservice.model;



import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int userId;
    private String username;
    private String password;
    private String userEmail;
    private String phoneNumber;
    private String address;
    private String userType;

}
