package com.example.demo.service;

import com.example.demo.entity.Subscription;
import com.example.demo.repository.SubscriptionJdbcRepository;
import com.example.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionJdbcRepository subscriptionJdbcRepository;

    public int saveSubscription(Subscription subscription) {
        return subscriptionJdbcRepository.saveSubscription(subscription);
    }
    public void subscriptionVote(int subscriptionId, int vote) {
        int  rowsAffected = subscriptionJdbcRepository.updateVote(subscriptionId, vote);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento fallito. Subscription con ID " + subscriptionId + " non trovato.");
        }
    }
}
