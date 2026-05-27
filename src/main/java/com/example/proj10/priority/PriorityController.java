package com.example.proj10.priority;

import com.example.proj10.priority.PriorityModel;
import com.example.proj10.priority.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService service;

    @GetMapping("/status/get")
    public ResponseEntity<List<PriorityModel>> getStatus(){
        List<PriorityModel> list=service.getOrCreateStatuses();
        return ResponseEntity.ok(list);
    }


}
