package org.example.repository;

import org.example.model.Department;

import java.util.List;

public interface IDepartmentDao {
    public default List<Department> getDepartments() {
        return null;
    }
}
