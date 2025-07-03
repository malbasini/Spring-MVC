package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    int registerNewUser(String username,String fullname, String password, String email, String role);
    List<Role> getAllRole();
    User login(String userName, String rawPassword);
    void updateRole(int userId, String roleName);
    void deleteRole(int userId, String roleName);
}
