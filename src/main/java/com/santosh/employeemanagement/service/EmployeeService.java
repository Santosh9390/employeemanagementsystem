package com.santosh.employeemanagement.service;

import com.santosh.employeemanagement.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    List<Employee> searchEmployeesByName(String name);
    List<String> searchEmployeesByDepartment(String name);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
}
