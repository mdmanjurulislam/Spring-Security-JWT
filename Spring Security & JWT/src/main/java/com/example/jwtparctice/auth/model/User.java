package com.example.jwtparctice.auth.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private String userId;

    private String userName;
//    private String password;
    private String email;
}
