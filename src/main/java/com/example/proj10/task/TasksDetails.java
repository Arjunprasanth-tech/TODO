package com.example.proj10.task;

import com.example.proj10.priority.PriorityModel;
import com.example.proj10.status.StatusDetails;
import com.example.proj10.user.UserDetails;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TasksDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails user;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private PriorityModel priority;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusDetails status;

    private LocalDate dueDate;
}
