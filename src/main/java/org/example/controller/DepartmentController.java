package org.example.controller;

import org.example.model.Department;
import org.example.repository.DepartmentHibernateDaoImpl;
import org.example.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/department", "/dept"})
public class DepartmentController {
    private final Logger logger = LoggerFactory.getLogger(DepartmentHibernateDaoImpl .class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Department> getDepartments() {
        logger.info("This is ");

        List<Department> departments = departmentService.getDepartment();
        return departments;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    public Department getDepartmentById(@PathVariable(name = "Id") Long id) {
        logger.info("get id by {}", id);
        return departmentService.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Department updateDepartmentName(@PathVariable("id") Long id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "description", required = false) String description) {
        logger.info("patch id by {}", id, name);
        Department d = departmentService.getById(id);

        if (name != null) {
            d.setName(name);
        }
        if (description != null) {
            d.setDescription(description);
        }

        d = departmentService.update(d);
        return d;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Department department) {
        logger.info("create {}", department.getName());

        departmentService.save(department);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteById(@PathVariable("id") Long id) {
        logger.info("delete {}", id);

        return departmentService.delete(departmentService.getById(id));
    }
}
