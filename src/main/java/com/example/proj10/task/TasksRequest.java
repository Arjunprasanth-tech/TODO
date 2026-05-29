package com.example.proj10.task;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TasksRequest {

    private Long userId;
    private String title;
    private String description;
    private String statusId;
    private String statusName;
    private String priorityId;
    private String priorityName;
    private LocalDate dueDate;

    private String status;
    private String priority;
}
