package com.taskTrackerManager.TaskTracker.ecxeption;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not found", ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not found", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    
}
