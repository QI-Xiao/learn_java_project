package org.example.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentJDBCDaoImplTest {
    DepartmentJDBCDaoImpl departmentJDBCDaoImpl;

    @BeforeEach
    public void setup() {
        departmentJDBCDaoImpl = new DepartmentJDBCDaoImpl();
    }

    @AfterEach
    public void teardown() {
        departmentJDBCDaoImpl = null;
    }

    @Test
    public void getDepartmentsTest() {
        assertEquals(0, departmentJDBCDaoImpl.getDepartments().size());
    }
}
