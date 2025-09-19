package com.example.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
@ComponentScan(
        basePackages = "com.example.demo",
        excludeFilters = @ComponentScan.Filter(org.springframework.stereotype.Controller.class)
)
@Import(HibernateConfig.class) // DataSource, EMF, JpaTransactionManager
// dentro c’è DataSource, EMF e JpaTransactionManager
public class AppRootConfig { }