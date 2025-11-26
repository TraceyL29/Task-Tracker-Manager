package com.taskTrackerManager.TaskTracker.controller;

import com.taskTrackerManager.TaskTracker.model.Task;
import com.taskTrackerManager.TaskTracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor

public class controller {

 private final TaskService taskService;



 @GetMapping
    public ResponseEntity <List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

 @GetMapping("/{id}")
 public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
     return taskService.getTaskById(id)
             .map(ResponseEntity::ok)          // return 200 + task if found
             .orElse(ResponseEntity.notFound().build()); // return 404 if not found
 }

    @PostMapping
    public ResponseEntity <Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity <Task> updateTask(@PathVariable Long id,@RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Task> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); //204
    }






}
