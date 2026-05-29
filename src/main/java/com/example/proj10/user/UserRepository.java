package com.example.proj10.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<UserDetails,Long> {

    Optional<UserDetails> findByEmail(String email);
}