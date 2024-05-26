package com.example.jwtparctice.auth.controller;

import com.example.jwtparctice.auth.jwtservice.JwtService;
import com.example.jwtparctice.auth.model.LoginRequest;
import com.example.jwtparctice.auth.response.CommonResponse;
import com.example.jwtparctice.auth.response.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
        this.doAuthenticate(loginRequest.getEmail(),loginRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = this.jwtService.generateToken(userDetails);

        JwtResponse jwtResponse = JwtResponse.builder().token(token).userName(userDetails.getUsername()).build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }



    private void doAuthenticate(String email, String password){

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);
        try {
            authenticationManager.authenticate(authentication);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid Username or Password !!");
        }
    }


    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
