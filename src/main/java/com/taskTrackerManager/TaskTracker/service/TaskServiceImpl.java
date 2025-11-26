package com.taskTrackerManager.TaskTracker.service;


import com.taskTrackerManager.TaskTracker.ecxeption.TaskNotFoundException;
import com.taskTrackerManager.TaskTracker.model.Task;
import com.taskTrackerManager.TaskTracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask){
        return taskRepository.findById(id).map(
                task ->{
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new TaskNotFoundException(id));

    }

    public void deleteTask(Long id){
        if(!taskRepository.existsById(id)){
            throw  new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);

    }


}

