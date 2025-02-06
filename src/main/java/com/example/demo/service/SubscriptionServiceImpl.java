package com.example.demo.service;

import com.example.demo.config.ConfigPaymentType;
import com.example.demo.entity.Course;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SubscriptionJdbcRepository;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionJdbcRepository subscriptionJdbcRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ConfigPaymentType paymentType;

    public void subscriptionVote(int subscriptionId, int vote) {
        int rowsAffected = subscriptionJdbcRepository.updateVote(subscriptionId, vote);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento fallito. Subscription con ID " + subscriptionId + " non trovato.");
        }
    }

    public String getPaymentType() {
        return paymentType.getPaymentType();
    }

    /**
     * Crea (o aggiorna) l'iscrizione dopo il pagamento avvenuto.
     *
     * @param userId        lo studente
     * @param courseId      il corso
     * @param amount        importo pagato
     * @param currency      valuta
     * @param paymentType   "PAYPAL" o "STRIPE"
     * @param transactionId l'ID transazione restituito dal gateway
     * @return l'oggetto Subscription salvato
     */
    public Subscription createSubscription(
            Integer userId,
            Integer courseId,
            BigDecimal amount,
            String currency,
            String paymentType,
            String transactionId) {
        Subscription subscription = new Subscription();
        User user = userRepository.findById(userId).orElse(null);
        subscription.setUser(user);
        Course course = courseRepository.findCourseById(courseId);
        subscription.setCourse(course);
        subscription.setPaymentDate(new Date());
        subscription.setPaidAmount(amount);
        subscription.setPaidCurrency(currency);
        subscription.setPaymentType(paymentType);
        subscription.setTransactionId(transactionId);
        subscription.setVote(1);
        int subscriptionId = subscriptionJdbcRepository.saveSubscription(subscription);
        Optional<Subscription> savedSubscription = subscriptionRepository.findById(subscriptionId);
        return savedSubscription.orElse(null);
    }
}
