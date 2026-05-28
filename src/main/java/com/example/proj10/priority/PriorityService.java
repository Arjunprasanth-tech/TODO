package com.example.proj10.priority;

import com.example.proj10.priority.PriorityModel;
import com.example.proj10.priority.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PriorityService {

    // @Autowired
    private PriorityRepository repo;
    public PriorityService(PriorityRepository repo){
        this.repo=repo;
    }

    /**
     * Retrieves all statuses from the database.
     */
    public List<PriorityModel> getAllStatuses() {
        return repo.findAll();
    }

    /**
     * Saves or updates a priority.
     * If the ID is null or empty, the backend generates a random UUID.
     */
    public PriorityModel saveOrUpdatePriority(PriorityModel priority) {
        if (priority.getId() == null || priority.getId().trim().isEmpty()) {
            priority.setId(UUID.randomUUID().toString());
        }
        return repo.save(priority);
    }
}
