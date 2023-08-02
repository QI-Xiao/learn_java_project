package org.example.controller;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/employee"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

//    @RequestMapping(value = "/{Id}", method = RequestMethod.POST)
//    public void create(@PathVariable(name = "Id") Long id, @RequestBody Employee employee) {
//
//        Department department = employee.getDepartment();
//
//        employeeService.save(employee);
//    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.POST)
    public void create(@PathVariable(name = "Id") Long id, @RequestBody Employee employee) {

//        Department department = employee.getDepartment();

        employeeService.save(employee);
    }
}
