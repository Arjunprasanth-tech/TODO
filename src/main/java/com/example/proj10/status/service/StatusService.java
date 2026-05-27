package com.example.proj10.status.service;

import com.example.proj10.model.StatusModel;
import com.example.proj10.repo.StatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class StatusService {

   // @Autowired
    private StatusRepo repo;
    public StatusService(StatusRepo repo){
        this.repo=repo;
    }

    /**
     * Checks if statuses are present in the database.
     * If empty, populates the predefined statuses with unique UUIDs.
     * Then, retrieves and returns all statuses from the database.
     */
    public List<StatusModel> getOrCreateStatuses() {
        if (repo.count() == 0) {
            StatusModel s1 = new StatusModel(UUID.randomUUID().toString(), "complete");
            StatusModel s2 = new StatusModel(UUID.randomUUID().toString(), "pending");
            StatusModel s3 = new StatusModel(UUID.randomUUID().toString(), "overdue");

            repo.saveAll(Arrays.asList(s1, s2, s3));
        }
        return repo.findAll();
    }
}