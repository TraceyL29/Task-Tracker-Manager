package com.taskTrackerManager.TaskTracker.ecxeption;

public class UserNotFoundException extends RuntimeException{
    public  UserNotFoundException(String email){
        super("User not found: " + email);
    }
}
