package com.ust.AuthenticationMicroservice.authenticationMicroservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserAuth {
    private String userName;
    private String password;
}
