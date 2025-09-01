package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username.trim());


        String singleAuthority = toAuthority(extractSingleRole(u.getRoles())); // es. "ROLE_ADMIN"

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(singleAuthority) // una sola authority
                .build();
    }
    /** Aggiunge "ROLE_" se manca. */
    private static String toAuthority(String raw) {
        String s = raw == null ? "" : raw.trim();
        if (s.isEmpty()) throw new UsernameNotFoundException("Nome ruolo vuoto");
        return s.startsWith("ROLE_") ? s : "ROLE_" + s;
    }
    /**
     * Estrae l'unico ruolo dalla collection, altrimenti lancia.
     */
    private static <T> String extractSingleRole(Collection<T> roles) {
        if (roles == null || roles.isEmpty())
            throw new UsernameNotFoundException("Ruolo mancante per l'utente");

        if (roles.size() != 1)
            throw new IllegalStateException("L'utente ha " + roles.size() + " ruoli: il sistema ne prevede 1");

        T r = roles.iterator().next();

        // Adatta qui al tuo tipo di ruolo:
        if (r instanceof String s) return s.trim();                                  // "ADMIN" o "ROLE_ADMIN"
        if (r instanceof org.springframework.security.core.GrantedAuthority ga) return ga.getAuthority();
        if (r instanceof com.example.demo.entity.Role roleEntity) return roleEntity.getName(); // es. "ADMIN"
        return r.toString().trim(); // fallback
    }
}