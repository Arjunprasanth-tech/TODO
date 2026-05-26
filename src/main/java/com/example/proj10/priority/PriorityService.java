package com.example.proj10.priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PriorityService {

    @Autowired
    private PriorityRepository priorityRepository;

    public ResponseEntity<?> addPriority(PriorityModel priorityModel) {
        return new ResponseEntity<>(priorityRepository.save(priorityModel), HttpStatus.OK);
    }
}
