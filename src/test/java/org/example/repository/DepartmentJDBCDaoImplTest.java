package org.example.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class DepartmentJDBCDaoImplTest {
    DepartmentJDBCDaoImpl departmentJDBCDaoImpl;

    @Before
    public void setup() {
        departmentJDBCDaoImpl = new DepartmentJDBCDaoImpl();
    }

    @After
    public void teardown() {
        departmentJDBCDaoImpl = null;
    }

    @Test
    public void getDepartmentsTest() {
        assertEquals(0, departmentJDBCDaoImpl.getDepartments().size());
    }
}
