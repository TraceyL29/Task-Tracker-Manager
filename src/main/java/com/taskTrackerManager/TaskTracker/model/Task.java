package com.taskTrackerManager.TaskTracker.model;

import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tasks")
@Data                   // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // No-args constructor
@AllArgsConstructor     // All-args constructor

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean completed;



}
