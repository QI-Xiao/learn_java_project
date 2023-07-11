package org.example.repository;

import org.example.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentJDBCDaoImpl implements IDepartmentDao{
    static final String DB_URL = "jdbc:postgresql://localhost:5431/training_db";
    static final String USER = "admin";
    static final String PASS = "Training123!";

    @Override
    public void save(Department department) {

    }

    @Override
    public List<Department> getDepartments() {
        Logger logger = LoggerFactory.getLogger(getClass());

        // logger at the beginning of the function
        logger.debug("start getDepartments from postgres via JDBC");

        //Step1: Prepare the required data model
        List<Department> departments = new ArrayList<Department>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //Step2: Open a connection ->5 key points to connect db
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Step3: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM departments";
            rs = stmt.executeQuery(sql);

            // logger
            logger.info("connects to DB and execute the query");

            //Step4: Extract data from result set
            while(rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");

                Department department = new Department(id, name, description, location);
//                department.setId(id);
//                department.setName(name);
//                department.setDescription(description);
//                department.setLocation(location);
                departments.add(department);
            }

        } catch (SQLException e) {
            // logger
            logger.error("Unable to connect to db or execute query", e);
        } finally {
            //Step6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                // logger
                logger.error("Unable to close db connection", e);
            }
        }
        // logger departments
        logger.info("finish getDepartments %v", departments);
        return departments;
    }

    @Override
    public Department getById(long id) {
        return IDepartmentDao.super.getById(id);
    }

    @Override
    public boolean delete(Department department) {
        return false;
    }

    @Override
    public Department getDepartmentEagerBy(Long id) {
        return null;
    }
}