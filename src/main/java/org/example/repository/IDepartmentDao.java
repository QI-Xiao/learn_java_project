package org.example.repository;

import org.example.model.Department;

import java.util.List;

public interface IDepartmentDao {
    void save(Department department);

    default List<Department> getDepartments() {
        return null;
    }

    default Department getById(long id) {
        return null;
    }

    boolean delete(Department department);

    Department getDepartmentEagerBy(Long id);
}
