package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserJdbcRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserJdbcRepository userJdbcRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void registerNewUser(String username, String fullName, String password, String email, String roleName) {
        Role role = roleRepository.findByName(roleName);
        int roleId = role.getId();
        userJdbcRepository.createUserWithRole(username,fullName, passwordEncoder.encode(password),email,roleId);
    }
    public List<Role> getAllRole()
    {
        return roleRepository.findAll();
    }
    public User login(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null)
            throw new IllegalArgumentException("Invalid User Name");
        return user;
    }
}
