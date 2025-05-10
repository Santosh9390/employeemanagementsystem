package com.santosh.employeemanagement.controller;

import com.santosh.employeemanagement.entity.Employee;
import com.santosh.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Add a new employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Update an employee by ID
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);

    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Employee>> searchEmployees(@PathVariable String name) {
        List<Employee> result = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/department/{department}")
    public ResponseEntity<List<String>> searchEmployeesByDepartment(@PathVariable String department) {
        List<String> employeeNames = employeeService.searchEmployeesByDepartment(department);
        return new ResponseEntity<>(employeeNames, HttpStatus.OK);
    }
}
