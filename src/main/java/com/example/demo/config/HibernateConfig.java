package com.example.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
public class HibernateConfig {

    @Bean
    public DataSource dataSource() {
        var ds = new ComboPooledDataSource();
        try { ds.setDriverClass("com.mysql.cj.jdbc.Driver"); }
        catch (PropertyVetoException e) { throw new RuntimeException(e); }
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/MyCourseMvc?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        ds.setUser("root");
        ds.setPassword("KHJuial0a100?");
        ds.setMinPoolSize(5);
        ds.setMaxPoolSize(20);
        ds.setMaxIdleTimeExcessConnections(300);
        ds.setUnreturnedConnectionTimeout(300);
        ds.setDebugUnreturnedConnectionStackTraces(true);
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setPackagesToScan("com.example.demo.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        var props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update"); // o validate in prod
        emf.setJpaProperties(props);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}