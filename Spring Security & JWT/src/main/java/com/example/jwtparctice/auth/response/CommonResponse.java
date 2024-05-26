package com.example.jwtparctice.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse <T> {

    private T message;
    private T response;
    private T code;
}
