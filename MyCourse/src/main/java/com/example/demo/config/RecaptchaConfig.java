package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RecaptchaConfig {

    @Value("${google.recaptcha.site-key}")
    private String siteKey;

    @Value("${google.recaptcha.secret-key}")
    private String secretKey;

    public String getSiteKey() {
        return siteKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
