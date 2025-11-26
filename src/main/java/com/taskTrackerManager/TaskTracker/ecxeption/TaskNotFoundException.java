package com.taskTrackerManager.TaskTracker.ecxeption;

public class TaskNotFoundException extends RuntimeException{
    public  TaskNotFoundException(Long id){
        super("Task not found: " + id);
    }
}
