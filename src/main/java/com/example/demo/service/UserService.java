package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    void registerNewUser(String username, String password, String email, String role);
    List<Role> getAllRole();
    User login(String userName);
}
