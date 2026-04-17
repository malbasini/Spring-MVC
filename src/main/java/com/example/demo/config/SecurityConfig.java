package com.example.demo.config;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@EnableMethodSecurity // abilita @PreAuthorize sui controller
public class SecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("customUserDetailsService") CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/css/**")
                .requestMatchers("/js/**")
                .requestMatchers("/images/**");

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PersistentTokenRepository persistentTokenRepository) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() //
                        .requestMatchers("/login", "/register", "/doRegister", "/doLogin").permitAll() // Rotte pubbliche
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Accesso ristretto agli amministratori
                        .requestMatchers("/editor/**").hasAuthority("ROLE_EDITOR") // Accesso ristretto agli utenti TEACHER
                        .requestMatchers("/student/**").hasAuthority("ROLE_STUDENT") // Accesso ristretto agli utenti STUDENT
                        .anyRequest().authenticated() // Tutto il resto richiede autenticazione
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                // Configurazione rememberMe
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me")  // Nome del parametro checkbox
                        .tokenValiditySeconds(2 * 24 * 60 * 60) // 2 giorni in secondi (172.800)
                        .key("mykey")
                        .tokenRepository(persistentTokenRepository)
                )
                // 👇 differenzia UNAUTHENTICATED (401→login) da FORBIDDEN (403→pagina dedicata)
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")   // utente autenticato ma senza permessi
                );

        return http.build();
    }

    // Configura il DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false); // Da usare solo la prima volta
        return jdbcTokenRepository;
    }


}