package com.example.proj10.status.controller;

import com.example.proj10.model.StatusModel;
import com.example.proj10.status.service.StatusService;
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
public class StatusController {

    private final StatusService service;

    @GetMapping("/status/get")
    public ResponseEntity<List<StatusModel>> getStatus(){
        List<StatusModel> list=service.getOrCreateStatuses();
        return ResponseEntity.ok(list);
    }


}
