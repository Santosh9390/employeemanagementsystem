package com.santosh.employeemanagement.service;

import com.santosh.employeemanagement.entity.Employee;
import com.santosh.employeemanagement.entity.User;
import com.santosh.employeemanagement.exception.ResourceNotFoundException;
import com.santosh.employeemanagement.repository.EmployeeRepository;
import com.santosh.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;



    @Override
    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
        public List<Employee> searchEmployeesByName(String name){
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<String> searchEmployeesByDepartment(String department) {
        List<Employee> employees = repository.findByDepartmentContainingIgnoreCase(department);
        return employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        // Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        String currentUsername = currentUser.getUsername();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        System.out.println("Logged in user: " + currentUsername);
        System.out.println("Employee name: " + existing.getName());

        // If not admin, ensure user is updating only their own employee record
        if (!isAdmin && !existing.getName().equals(currentUsername)) {
            throw new AccessDeniedException("You are not authorized to update this employee");
        }


        existing.setAge(employee.getAge());
        existing.setDepartment(employee.getDepartment());
        existing.setRole(employee.getRole());
        existing.setSalary(employee.getSalary());
        return repository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
