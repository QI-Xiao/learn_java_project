package org.example.repository;

import org.example.model.Department;
import org.example.model.Employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartmentHibernateDaoImplTest {
    @Autowired
    private IDepartmentDao departmentHibernateDao;

    @Autowired
    private IEmployeeDao employeeHibernateDao;

    private Department d1;

    private Employee e1;
    private Employee e2;

    @BeforeEach
    public void setUp() {
//        departmentHibernateDao = new DepartmentHibernateDaoImpl();
        d1 = new Department();
        d1.setName("hr333");
        d1.setDescription("random description");
        d1.setLocation("US");
        departmentHibernateDao.save(d1);

//        employeeHibernateDao = new EmployeeHibernateDaoImpl();

        e1 = new Employee();
        e1.setName("zhang12333");
        e1.setAddress("US");
        e1.setDepartment(d1);
        employeeHibernateDao.save(e1);

        e2 = new Employee();
        e2.setName("zhang2233");
        e2.setAddress("US");
        e2.setDepartment(d1);
        employeeHibernateDao.save(e2);
    }

    @AfterEach
    public void tearDown() {
        employeeHibernateDao.delete(e1);
        employeeHibernateDao.delete(e2);
        departmentHibernateDao.delete(d1);
    }

    @Test
    public void getDepartmentsTest() {
        assertEquals(3, departmentHibernateDao.getDepartments().size());
    }

    @Test
    public void getDepartmentEagerByTest() {
        Department department = departmentHibernateDao.getDepartmentEagerBy(d1.getId());
        assertNotNull(department);
        assertEquals(department.getName(), d1.getName());
        assertTrue(department.getEmployees().size() == 6);
    }
}