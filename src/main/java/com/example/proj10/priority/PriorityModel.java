package com.example.proj10.priority;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "priorityTable")
@AllArgsConstructor
@NoArgsConstructor
public class PriorityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "priorityId")
    private String priorityId;
}
