package com.example.demo.service;

public interface RoleService {
    void assignSingleRole(Integer userId, String roleName);
    void removeRole(Integer userId, String roleName);
    void transferAdmin(Integer newAdminUserId);
}