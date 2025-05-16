package com.example.demo.repository;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class AdminJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public int updateRole(Admin admin) {

        String sql = "UPDATE role " +
                "SET email = ?, " +
                "full_name = ?, " +
                "revoked = ?, " +
                "user_id = ?, " +
                "role = ? " +
                "WHERE id = ?";

        return jdbcTemplate.update(sql,
                admin.getEmail(),
                admin.getFullname(),
                admin.getRevoke(),
                admin.getUserId(),
                admin.getRole(),
                admin.getId());
    }
    @Transactional
    public int saveRole(Admin admin) {
        String sql = "INSERT INTO role (email, full_name,revoked,user_id,role) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, admin.getEmail(), admin.getFullname(), admin.getRevoke(), admin.getUserId(), admin.getRole());
        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return id;
    }

    @Transactional
    public int deleteRole(int id) {
        String sql = "DELETE FROM role WHERE id = ?";
        return jdbcTemplate.update(sql, id);

    }





}
