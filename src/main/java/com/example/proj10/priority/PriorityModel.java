package com.example.proj10.priority;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "priority")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriorityModel{


    @Id
    private String id;
    private String name;
}
