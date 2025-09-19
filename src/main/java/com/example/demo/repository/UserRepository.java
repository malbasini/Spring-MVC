package com.example.demo.repository;

import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByEmail(String email);
    // --- gestione per ruolo ---
    long countByRoles_Name(String roleName);                    // quanti utenti hanno quel ruolo
    Optional<User> findFirstByRoles_Name(String roleName);      // primo utente con quel ruolo
    List<User> findAllByRoles_Name(String roleName);            // tutti gli utenti con quel ruolo
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u join u.roles r where r.name = :roleName")
    List<User> lockUsersByRoleName(@Param("roleName") String roleName);
    // UserRepository
    boolean existsByUsernameAndRoles_Name(String username, String roleName);
    @Query("select u from User u left join fetch u.roles where u.username = :username")
    Optional<User> findByUsernameFetchRoles(@Param("username") String username);


}