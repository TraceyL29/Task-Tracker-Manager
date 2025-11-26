package com.taskTrackerManager.TaskTracker.service;

import com.taskTrackerManager.TaskTracker.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface TaskService {

    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    Task createTask(Task task);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

}
