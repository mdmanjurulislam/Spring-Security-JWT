package com.example.jwtparctice.auth.service;

import com.example.jwtparctice.auth.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();

    public UserService() {
        userList.add(new User(UUID.randomUUID().toString(),"manjurul","abc@gmail.com"));
        userList.add(new User(UUID.randomUUID().toString(),"sohag","xyz@gmail.com"));
    }

    public List<User> getAllUser(){
        return userList;
    }
}
