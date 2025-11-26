package com.taskTrackerManager.TaskTracker.controller;

import com.taskTrackerManager.TaskTracker.model.User;
import com.taskTrackerManager.TaskTracker.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class authController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user,
                                          HttpServletRequest request) {
        try {
            if (authService.isValidEmail(user.getEmail()))
                return ResponseEntity.ok(authService.registerUser(user.getEmail(), user.getPassword(), user.getName()));
        } catch (
                RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {


        if (!authService.userExists(user.getEmail()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");

        User authenticatedUser = authService.authenticate(user.getEmail(), user.getPassword());

       return ResponseEntity.ok(authenticatedUser);
    }


    @GetMapping("/csrf-token")
    public CsrfToken csrf(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/check-session")
    public String checkSession(HttpSession session) {
        return "Session ID: " + session.getId();
    }


}
