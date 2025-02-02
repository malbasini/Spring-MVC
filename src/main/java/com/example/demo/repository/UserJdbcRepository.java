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
    public void createUserWithRole(String username, String password, String email, int roleId) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email già esistente!");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username già esistente!");
        }
        String insertUserSQL = "INSERT INTO register (username, password, email, enabled) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insertUserSQL, username, password, email, true);
            Integer newUserId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            String insertRoleSQL = "INSERT INTO register_roles (user_id, role_id) VALUES (?, ?)";
            jdbcTemplate.update(insertRoleSQL, newUserId, roleId);
        } catch (Exception e) {
            throw e;
        }
    }

}
