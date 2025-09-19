package com.example.demo.service;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ADMIN  = "ROLE_ADMIN";
    public static final String ROLE_EDITOR = "ROLE_EDITOR";

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public RoleServiceImpl(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public void assignSingleRole(Integer userId, String roleName) {
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utente inesistente"));

        var role = Optional.ofNullable(roleRepo.findByName(roleName))
                .orElseThrow(() -> new IllegalArgumentException("Ruolo inesistente: " + roleName));

        // blocco concorrenza su ADMIN
        if (ROLE_ADMIN.equals(roleName)) {
            userRepo.lockUsersByRoleName(ROLE_ADMIN); // crea un lock sulla “vista” admin
            var already = userRepo.findFirstByRoles_Name(ROLE_ADMIN);
            if (already.isPresent() && !already.get().getId().equals(userId)) {
                throw new IllegalStateException("Esiste già un amministratore: " + already.get().getUsername());
            }
        }

        // se vuoi che ogni utente abbia UN SOLO ruolo effettivo:
        user.getRoles().clear();
        user.getRoles().add(role);

        // se invece l'utente può avere più ruoli,
        // sostituisci le due righe sopra con:
        // user.getRoles().add(role);

        // flush automatico con @Transactional
    }

    public void removeRole(Integer userId, String roleName) {
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utente inesistente"));

        if (ROLE_ADMIN.equals(roleName)) {
            // vieta di rimuovere l’unico ADMIN
            userRepo.lockUsersByRoleName(ROLE_ADMIN);
            long adminCount = userRepo.countByRoles_Name(ROLE_ADMIN);
            if (adminCount <= 1 && user.getRoles().stream().anyMatch(r -> ROLE_ADMIN.equals(r.getName()))) {
                throw new IllegalStateException("Non puoi rimuovere l'unico amministratore.");
            }
        }

        user.getRoles().removeIf(r -> r.getName().equals(roleName));
    }

    /** Operazione atomica per spostare il cappello di ADMIN */
    public void transferAdmin(Integer newAdminUserId) {
        userRepo.lockUsersByRoleName(ROLE_ADMIN);
        var newAdmin = userRepo.findById(newAdminUserId)
                .orElseThrow(() -> new IllegalArgumentException("Utente inesistente"));

        var adminRole = Optional.ofNullable(roleRepo.findByName(ROLE_ADMIN))
                .orElseThrow(() -> new IllegalArgumentException("Ruolo ROLE_ADMIN mancante"));
        var teacherRole = Optional.ofNullable(roleRepo.findByName(ROLE_EDITOR))
                .orElseThrow(() -> new IllegalArgumentException("Ruolo ROLE_TEACHER mancante"));

        // rimuovi ROLE_ADMIN da chiunque lo abbia
        var admins = userRepo.findAllByRoles_Name(ROLE_ADMIN);
        for (var u : admins) {
            u.getRoles().removeIf(r -> ROLE_ADMIN.equals(r.getName()));
            // opzionale: degradalo a TEACHER
            u.getRoles().add(teacherRole);
        }
        // assegna al nuovo
        newAdmin.getRoles().removeIf(r -> ROLE_ADMIN.equals(r.getName())); // idempotente
        newAdmin.getRoles().add(adminRole);
    }
}
