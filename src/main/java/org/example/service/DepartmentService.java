package org.example.service;

import org.example.model.Department;
import org.example.repository.DepartmentHibernateDaoImpl;
import org.example.repository.IDepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private IDepartmentDao departmentDao;

    public void save(Department department) {
        departmentDao.save(department);
    }

    public List<Department> getDepartment() {
        return departmentDao.getDepartments();
    }

    public Department getById(long id) {
        return departmentDao.getById(id);
    }

    public boolean delete(Department department) {
        return departmentDao.delete(department);
    }

    public Department getDepartmentEagerBy(Long id) {
        return departmentDao.getDepartmentEagerBy(id);
    }

    public Department update(Department d) {
        return departmentDao.update(d);
    }
}
