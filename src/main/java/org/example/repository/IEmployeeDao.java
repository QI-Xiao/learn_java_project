package org.example.repository;

import org.example.model.Department;
import org.example.model.Employee;

import java.util.List;

public interface IEmployeeDao {
    public List<Employee> getEmployees();

    void save(Employee employee);

    Employee getById(Long id);

    void delete(Employee employee);


}
