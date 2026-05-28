package com.example.proj10.priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/priorityDetails")
@CrossOrigin
public class PriorityController {
    @Autowired
    private PriorityService priorityService;

    @PostMapping(path = "/addPriority")
    public ResponseEntity<?>addPriority(@RequestBody PriorityModel priorityModel){
        try {
            return priorityService.addPriority(priorityModel);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
