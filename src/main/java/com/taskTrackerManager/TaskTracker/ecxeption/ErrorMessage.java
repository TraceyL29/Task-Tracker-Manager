package com.taskTrackerManager.TaskTracker.ecxeption;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
    private String path;
}
