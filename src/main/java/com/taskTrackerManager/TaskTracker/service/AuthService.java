package com.taskTrackerManager.TaskTracker.service;

import com.taskTrackerManager.TaskTracker.model.User;

public interface AuthService {

        public User authenticate (String email, String password);
        public User registerUser (String email, String password, String name);
        public boolean isValidEmail (String email);
        public boolean userExists (String email);


    }
