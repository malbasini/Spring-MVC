package com.example.demo.service;

import com.example.demo.entity.Subscription;
import java.math.BigDecimal;

public interface SubscriptionService {

    Subscription createSubscription(
            Integer userId,
            Integer courseId,
            BigDecimal amount,
            String currency,
            String paymentType,
            String transactionId);
    void subscriptionVote(int subscriptionId,
                          int vote);

}
