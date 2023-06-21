package org.example.repository;

import org.example.model.Employee;

import java.util.List;

public interface IEmployeeDao {
    public default List<Employee> getEmployees() {
        return null;
    }
}
