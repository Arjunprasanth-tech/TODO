package com.example.proj10.status;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDetails {

    @Id
    private String id;

    private String name;
}