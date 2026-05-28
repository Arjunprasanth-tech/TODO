package com.example.proj10.priority;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PriorityRepository extends JpaRepository<PriorityModel,String> {
    Optional<PriorityModel> findByIdAndNameIgnoreCase(String id, String name);
    Optional<PriorityModel> findByNameIgnoreCase(String name);
}
