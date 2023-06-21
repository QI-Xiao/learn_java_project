package org.example.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class EmployeeJDBCDAOImplTest {
    EmployeeJDBCDaoImpl employeeJDBCDAOImpl;

    @Before
    public void setup() {
        employeeJDBCDAOImpl = new EmployeeJDBCDaoImpl();
    }

    @After
    public void teardown() {
        employeeJDBCDAOImpl = null;
    }

    @Test
    public void getEmployeesTest() {
        assertEquals(0, employeeJDBCDAOImpl.getEmployees().size());
    }
}
