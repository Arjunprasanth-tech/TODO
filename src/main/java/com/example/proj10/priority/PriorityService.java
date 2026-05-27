package com.example.proj10.priority;

import com.example.proj10.priority.PriorityModel;
import com.example.proj10.priority.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
     * Checks if statuses are present in the database.
     * If empty, populates the predefined statuses with unique UUIDs.
     * Then, retrieves and returns all statuses from the database.
     */
    public List<PriorityModel> getOrCreateStatuses() {
        if (repo.count() == 0) {
            PriorityModel s1 = new PriorityModel(UUID.randomUUID().toString(), "complete");
            PriorityModel s2 = new PriorityModel(UUID.randomUUID().toString(), "pending");
            PriorityModel s3 = new PriorityModel(UUID.randomUUID().toString(), "overdue");

            repo.saveAll(Arrays.asList(s1, s2, s3));
        }
        return repo.findAll();
    }
}
