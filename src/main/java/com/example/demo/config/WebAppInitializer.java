package com.example.demo.config;

import javax.servlet.Filter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ AppRootConfig.class, SecurityConfig.class };
    }
    @Override protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ WebMvcConfig.class };
    }
    @Override protected String[] getServletMappings() { return new String[]{ "/" }; }

    @Override
    protected Filter[] getServletFilters() {
        // Aggiungiamo il DelegatingFilterProxy
        return new Filter[]{
                new org.springframework.web.filter.DelegatingFilterProxy("springSecurityFilterChain")
        };
    }
}