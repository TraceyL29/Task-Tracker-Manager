package com.taskTrackerManager.TaskTracker.service;

import com.taskTrackerManager.TaskTracker.ecxeption.InvalidPasswordException;
import com.taskTrackerManager.TaskTracker.ecxeption.UserNotFoundException;
import com.taskTrackerManager.TaskTracker.model.User;
import com.taskTrackerManager.TaskTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(String email, String password) {
        if(email == null || email.isEmpty()){
            throw new UserNotFoundException(email);
        }

        if(password == null || password.isEmpty()){
            throw new UserNotFoundException(password);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->  new UserNotFoundException(email));

        if(passwordEncoder.matches(password, user.getPassword()))
            return user;
        else
            throw new InvalidPasswordException();


    }


    @Override
    public User registerUser(String email, String password, String name) {
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("Email is required");
        }

        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("password is required");
        }

        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is required");
        }

        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("Email already registered");

        }

        String encryptPassword= passwordEncoder.encode(password);
        return userRepository.save(new User(email, encryptPassword, name));


    }

    @Override
    public boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailPattern);
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
