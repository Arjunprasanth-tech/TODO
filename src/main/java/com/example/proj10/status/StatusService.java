package com.example.proj10.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository repo;

    public List<StatusDetails> getAllStatuses() {
        return repo.findAll();
    }

    public void createStatus(StatusDetails statusDetails) {
        statusDetails.setId(UUID.randomUUID().toString());
        repo.save(statusDetails);
    }
}