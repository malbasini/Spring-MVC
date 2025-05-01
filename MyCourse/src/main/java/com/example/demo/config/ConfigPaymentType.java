package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigPaymentType {
    // Recuperate da application.properties o da variabili d'ambiente
    @Value("${provider.payment.type}")
    private String paymentType;

    public String getPaymentType() {
        return paymentType;
    }
}
