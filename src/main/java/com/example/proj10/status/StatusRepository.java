package com.example.proj10.status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository
        extends JpaRepository<StatusDetails,String> {

    Optional<StatusDetails> findByName(String name);
    Optional<StatusDetails> findByNameIgnoreCase(String name);
    Optional<StatusDetails> findByIdAndNameIgnoreCase(String id, String name);
}