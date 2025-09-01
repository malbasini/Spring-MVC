package com.example.demo.config;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


@Configuration
@ComponentScan(basePackages = "com.example.demo")
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, HibernateConfig.class}; // Contesto principale (Spring Security)
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class}; // Contesto specifico MVC
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        // Aggiungiamo il DelegatingFilterProxy
        return new Filter[]{
                new org.springframework.web.filter.DelegatingFilterProxy("springSecurityFilterChain")
        };
    }

}

