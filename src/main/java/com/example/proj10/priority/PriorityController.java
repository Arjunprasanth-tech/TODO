package com.example.proj10.priority;

import com.example.proj10.priority.PriorityModel;
import com.example.proj10.priority.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService service;

    @GetMapping("/priority/get")
    public ResponseEntity<List<PriorityModel>> getStatus(){
        List<PriorityModel> list=service.getAllStatuses();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/priority/save")
    public ResponseEntity<PriorityModel> savePriority(@RequestBody PriorityModel priority) {
        PriorityModel saved = service.saveOrUpdatePriority(priority);
        return ResponseEntity.ok(saved);
    }


}
