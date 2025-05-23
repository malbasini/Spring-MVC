package com.example.demo.service;

import com.example.demo.entity.Admin;

public interface AdminService {

    int saveRole(Admin admin);

    void updateRole(Admin admin);

    void deleteRole(int id);
}
