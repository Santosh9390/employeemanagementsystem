package com.santosh.employeemanagement.repository;

import com.santosh.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom query methods will be added later
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByDepartmentContainingIgnoreCase(String name);

}
