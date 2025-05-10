package com.santosh.employeemanagement.controller;

import com.santosh.employeemanagement.dto.LoginRequest;
import com.santosh.employeemanagement.entity.User;
import com.santosh.employeemanagement.service.UserService;
import com.santosh.employeemanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userService.saveUser(user);
        return "User registered successfully!";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<User> optionalUser = userService.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // Return the token to the client
        return ResponseEntity.ok("Bearer " + token);
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, authenticated user!";
    }

}
