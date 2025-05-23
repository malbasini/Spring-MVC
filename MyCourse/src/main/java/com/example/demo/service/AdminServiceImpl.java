package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Course;
import com.example.demo.repository.AdminJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminJdbcRepository adminRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveRole(Admin admin) {
        int id;
        try {
            id = adminRepository.saveRole(admin);
            if (id == 0) {
                throw new RuntimeException("Inserimento fallito");
            }
        }
        catch (Exception ex){
            throw ex;
        }
        return id;
    }
    @Override
    public void updateRole(Admin admin) {
        int rowsAffected = adminRepository.updateRole(admin);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento fallito. ID " + admin.getId() + " non trovato.");
        }
    }
    @Override
    public void deleteRole(int id) {
        int rowsAffected = adminRepository.deleteRole(id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Eliminazione fallita. ID " + id + " non trovato.");
        }
    }
}
