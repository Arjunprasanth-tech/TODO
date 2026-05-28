package com.example.proj10.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/status", "/status"})
@CrossOrigin
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping({"", "/get"})
    public ResponseEntity<List<StatusDetails>> getStatuses() {
        List<StatusDetails> list = statusService.getAllStatuses();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/post")
    public ResponseEntity<?> createStatus(@RequestBody StatusDetails statusDetails){
        statusService.createStatus(statusDetails);
        return ResponseEntity.ok("okkk");
    }
}