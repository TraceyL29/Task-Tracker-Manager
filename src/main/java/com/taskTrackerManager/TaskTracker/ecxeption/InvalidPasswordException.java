package com.taskTrackerManager.TaskTracker.ecxeption;

public class InvalidPasswordException extends RuntimeException{
    public  InvalidPasswordException(){
        super("Wrong password!");
    }
}
