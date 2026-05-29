package com.example.proj10.task;

import com.example.proj10.priority.*;
import com.example.proj10.status.*;
import com.example.proj10.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;

    public TaskResponse createTask(TasksRequest request) {
        checkAndAutoUpdateOverdueTasks();

        UserDetails user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId()).orElse(null);
        }

        // Verify Status
        StatusDetails status;
        if (request.getStatusId() != null || request.getStatusName() != null) {
            if (request.getStatusId() == null || request.getStatusName() == null) {
                throw new RuntimeException("Both statusId and statusName must be provided");
            }
            status = statusRepository.findByIdAndNameIgnoreCase(request.getStatusId(), request.getStatusName())
                    .orElseThrow(() -> new RuntimeException("Status not found or name mismatch for status ID " 
                            + request.getStatusId() + " with name " + request.getStatusName()));
        } else if (request.getStatus() != null) {
            status = statusRepository.findById(request.getStatus())
                    .or(() -> statusRepository.findByNameIgnoreCase(request.getStatus()))
                    .orElseThrow(() -> new RuntimeException("Status not found by ID or Name: " + request.getStatus()));
        } else {
            // Default is pending
            status = statusRepository.findByName("Pending")
                    .orElseGet(() -> statusRepository.findByName("pending")
                            .orElseGet(() -> statusRepository.findByName("PENDING")
                                    .orElseThrow(() -> new RuntimeException("Default pending status not found"))));
        }

        // Apply Date-based automatic status rules for creation
        if (request.getDueDate() != null) {
            String statusName = status != null ? status.getName() : "";
            boolean isCompleted = statusName.equalsIgnoreCase("completed") || statusName.equalsIgnoreCase("complete");
            if (request.getDueDate().isBefore(LocalDate.now())) {
                if (!isCompleted) {
                    status = statusRepository.findByNameIgnoreCase("Overdue")
                            .orElseThrow(() -> new RuntimeException("Overdue status not found"));
                }
            } else {
                if (statusName.equalsIgnoreCase("overdue")) {
                    status = statusRepository.findByNameIgnoreCase("Pending")
                            .orElseThrow(() -> new RuntimeException("Pending status not found"));
                }
            }
        }

        // Verify Priority
        PriorityModel priority;
        if (request.getPriorityId() != null || request.getPriorityName() != null) {
            if (request.getPriorityId() == null || request.getPriorityName() == null) {
                throw new RuntimeException("Both priorityId and priorityName must be provided");
            }
            priority = priorityRepository.findByIdAndNameIgnoreCase(request.getPriorityId(), request.getPriorityName())
                    .orElseThrow(() -> new RuntimeException("Priority not found or name mismatch for priority ID " 
                            + request.getPriorityId() + " with name " + request.getPriorityName()));
        } else if (request.getPriority() != null) {
            priority = priorityRepository.findById(request.getPriority())
                    .or(() -> priorityRepository.findByNameIgnoreCase(request.getPriority()))
                    .orElseThrow(() -> new RuntimeException("Priority not found by ID or Name: " + request.getPriority()));
        } else {
            throw new RuntimeException("Priority is required and must be provided");
        }

        TasksDetails task = new TasksDetails();

        task.setUser(user);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(priority);
        task.setStatus(status);
        task.setDueDate(request.getDueDate());

        TasksDetails savedTask = tasksRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    public TaskResponse updateTask(Long id, TasksRequest request) {
        checkAndAutoUpdateOverdueTasks();

        TasksDetails task = tasksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        // Verify and update Status
        if (request.getStatusId() != null || request.getStatusName() != null) {
            if (request.getStatusId() == null || request.getStatusName() == null) {
                throw new RuntimeException("Both statusId and statusName must be provided to update status");
            }
            StatusDetails status = statusRepository.findByIdAndNameIgnoreCase(request.getStatusId(), request.getStatusName())
                    .orElseThrow(() -> new RuntimeException("Status not found or name mismatch for status ID " 
                            + request.getStatusId() + " with name " + request.getStatusName()));
            task.setStatus(status);
        } else if (request.getStatus() != null) {
            StatusDetails status = statusRepository.findById(request.getStatus())
                    .or(() -> statusRepository.findByNameIgnoreCase(request.getStatus()))
                    .orElseThrow(() -> new RuntimeException("Status not found by ID or Name: " + request.getStatus()));
            task.setStatus(status);
        }

        // Verify and update Priority
        if (request.getPriorityId() != null || request.getPriorityName() != null) {
            if (request.getPriorityId() == null || request.getPriorityName() == null) {
                throw new RuntimeException("Both priorityId and priorityName must be provided to update priority");
            }
            PriorityModel priority = priorityRepository.findByIdAndNameIgnoreCase(request.getPriorityId(), request.getPriorityName())
                    .orElseThrow(() -> new RuntimeException("Priority not found or name mismatch for priority ID " 
                            + request.getPriorityId() + " with name " + request.getPriorityName()));
            task.setPriority(priority);
        } else if (request.getPriority() != null) {
            PriorityModel priority = priorityRepository.findById(request.getPriority())
                    .or(() -> priorityRepository.findByNameIgnoreCase(request.getPriority()))
                    .orElseThrow(() -> new RuntimeException("Priority not found by ID or Name: " + request.getPriority()));
            task.setPriority(priority);
        }

        // Apply Date-based automatic status rules for updates
        if (task.getDueDate() != null) {
            String statusName = task.getStatus() != null ? task.getStatus().getName() : "";
            boolean isCompleted = statusName.equalsIgnoreCase("completed") || statusName.equalsIgnoreCase("complete");
            if (task.getDueDate().isBefore(LocalDate.now())) {
                if (!isCompleted) {
                    StatusDetails overdueStatus = statusRepository.findByNameIgnoreCase("Overdue")
                            .orElseThrow(() -> new RuntimeException("Overdue status not found"));
                    task.setStatus(overdueStatus);
                }
            } else {
                if (statusName.equalsIgnoreCase("overdue")) {
                    StatusDetails pendingStatus = statusRepository.findByNameIgnoreCase("Pending")
                            .orElseThrow(() -> new RuntimeException("Pending status not found"));
                    task.setStatus(pendingStatus);
                }
            }
        }

        TasksDetails savedTask = tasksRepository.save(task);
        return mapToTaskResponse(savedTask);
    }

    public TaskResponse updateStatus(Long id, String statusName) {
        checkAndAutoUpdateOverdueTasks();

        TasksDetails task = tasksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getStatus() != null && task.getStatus().getName().equalsIgnoreCase("overdue")) {
            throw new RuntimeException("Cannot modify the status of an overdue task. Please update the due date first.");
        }

        StatusDetails status = statusRepository.findByName(statusName)
                .orElseGet(() -> statusRepository.findByName(statusName.toUpperCase())
                        .orElseGet(() -> statusRepository.findByName(statusName.toLowerCase())
                                .orElseThrow(() -> new RuntimeException("Status not found: " + statusName))));

        task.setStatus(status);
        TasksDetails savedTask = tasksRepository.save(task);
        return mapToTaskResponse(savedTask);
    }

    public String deleteTask(Long id) {
        TasksDetails task = tasksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        tasksRepository.delete(task);

        return "Task Deleted Successfully";
    }

    private void checkAndAutoUpdateOverdueTasks() {
        StatusDetails overdueStatus = statusRepository.findByName("Overdue")
                .orElseGet(() -> statusRepository.findByName("overdue")
                        .orElseGet(() -> statusRepository.findByName("OVERDUE")
                                .orElse(null)));

        if (overdueStatus != null) {
            tasksRepository.autoUpdateOverdueTasks(overdueStatus, LocalDate.now());
        }
    }

    private TaskResponse mapToTaskResponse(TasksDetails task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority() != null ? task.getPriority().getName() : null,
                task.getStatus() != null ? task.getStatus().getName() : null,
                task.getDueDate()
        );
    }
}
