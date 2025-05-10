package com.santosh.employeemanagement.service;

import com.santosh.employeemanagement.entity.Employee;
import com.santosh.employeemanagement.entity.User;
import com.santosh.employeemanagement.repository.EmployeeRepository;
import com.santosh.employeemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeRepository employeeRepository;

    // Save the user with an encoded password
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encoding password
        userRepository.save(user);

        Employee employee = Employee.builder()
                .name(user.getUsername()) // same as username
                .age(0) // default age or null if you make it Integer
                .department(null)
                .salary(0.0)
                .role(null)
                .build();

        employeeRepository.save(employee);
    }

    // Find user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
