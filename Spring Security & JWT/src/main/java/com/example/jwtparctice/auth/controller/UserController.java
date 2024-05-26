package com.example.jwtparctice.auth.controller;

import com.example.jwtparctice.auth.model.User;
import com.example.jwtparctice.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

//    Check which user is logging in

    @GetMapping("/current-user")
    public String currentUser(Principal principal){
        return principal.getName();
    }

}
