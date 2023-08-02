package org.example.service;

import org.example.model.Employee;
import org.example.repository.IEmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private IEmployeeDao employeeDao;

    public void save(Employee employee) {
        employeeDao.save(employee);
    }
}
