package org.example;

import org.example.model.Department;
import org.example.repository.DepartmentJDBCDaoImpl;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DepartmentJDBCDaoImpl departmentJDBCDaoImpl = new DepartmentJDBCDaoImpl();
        List<Department> departments = departmentJDBCDaoImpl.getDepartments();
        System.out.format("List department %s", departments);
//        System.out.println( "Hello World!" );
    }
}
