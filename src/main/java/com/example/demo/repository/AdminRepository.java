package com.example.demo.repository;
import com.example.demo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);

    Admin findByEmailAndRole(String email, String role);

    Admin findByRole(String roleAdmin);
}
