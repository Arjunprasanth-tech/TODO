package com.example.proj10.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/api/tasks", "/tasks"})
@CrossOrigin
@RequiredArgsConstructor
public class TasksController {

    private final TasksService tasksService;

    @PostMapping({"", "/create"})
    public ResponseEntity<TaskResponse> createTask(@RequestBody TasksRequest request) {
        TaskResponse created = tasksService.createTask(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TasksRequest request) {
        TaskResponse updated = tasksService.updateTask(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String statusName = body.get("status");
        TaskResponse updated = tasksService.updateStatus(id, statusName);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long id) {
        String msg = tasksService.deleteTask(id);
        return ResponseEntity.ok(Map.of("message", msg));
    }
}
