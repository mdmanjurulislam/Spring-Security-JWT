package com.example.jwtparctice.auth.response;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtResponse <T>{

    String token;
    String userName;
}
