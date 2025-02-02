package com.example.demo.repository;

import com.example.demo.entity.Course;
import com.example.demo.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class SubscriptionJdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public int saveSubscription(Subscription subscription) {
        try {
            String sql = "INSERT INTO Subscription (UserId, CourseId, PaymentDate, PaidAmount,Vote) VALUES (?,?,?,?,?)";
            jdbcTemplate.update(sql, subscription.getUser().getId(), subscription.getCourse().getId(), subscription.getPaymentDate(), subscription.getPaidAmount(), subscription.getVote());
            Integer subscriptionId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            return subscriptionId;
        }catch (Exception e) {
            throw e;
        }
    }
}
