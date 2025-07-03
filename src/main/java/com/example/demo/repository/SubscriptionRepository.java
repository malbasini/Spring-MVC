package com.example.demo.repository;

import com.example.demo.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findByCourse_Id(Integer id);
    Subscription findByUser_Id(Integer id);

    Subscription findByCourse_IdAndUser_Id(int courseId, Integer userId);
}