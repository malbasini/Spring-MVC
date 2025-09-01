package com.example.demo.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserJdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public int createUserWithRole(String username, String fullname, String password, String email, int roleId) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email già esistente!");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username già esistente!");
        }
        Integer newUserId = 0;
        String insertUserSQL = "INSERT INTO register (username,fullname, password, email, enabled) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insertUserSQL, username, fullname, password, email, true);
            newUserId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            String insertRoleSQL = "INSERT INTO register_roles (user_id, role_id) VALUES (?, ?)";
            jdbcTemplate.update(insertRoleSQL, newUserId, roleId);
        } catch (Exception e) {
            throw e;
        }
        return newUserId;
    }
    @Transactional
    public void updateRole(int userId, int roleId) {

        String insertRoleSQL = "INSERT INTO register_roles (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(insertRoleSQL, userId, roleId);
    }
    @Transactional
    public void deleteRole(int userId, int roleId) {

        String sql = "DELETE FROM register_roles WHERE user_id = ? AND role_id = ?";
        jdbcTemplate.update(sql, userId, roleId);


    }
}
