//package org.example.repository;
//
//import org.example.model.Employee;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class EmployeeJDBCDaoImpl implements IEmployeeDao{
//    static final String DB_URL = "jdbc:postgresql://localhost:5431/training_db";
//    static final String USER = "admin";
//    static final String PASS = "Training123!";
//
//    @Override
//    public List<Employee> getEmployees() {
//        List<Employee> employeeList = new ArrayList<>();
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            stmt = conn.createStatement();
//            String sql = "SELECT * FROM employees";
//            rs = stmt.executeQuery(sql);
//
//            while (rs.next()) {
//                Long id = rs.getLong("id");
//                String name = rs.getString("name");
//                String first_name = rs.getString("first_name");
//                String last_name = rs.getString("last_name");
//                String email = rs.getString("email");
//                String address = rs.getString("address");
//                Date hired_date = rs.getDate("hired_date");
//                Long department_id = rs.getLong("department_id");
//
//                Employee employee = new Employee();
//                employee.setId(id);
//                employee.setName(name);
//                employee.setFirst_name(first_name);
//                employee.setLast_name(last_name);
//                employee.setEmail(email);
//                employee.setAddress(address);
//                employee.setHired_date(hired_date);
//                employee.setDepartment_id(department_id);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if(rs != null) rs.close();
//                if(stmt != null) stmt.close();
//                if(conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return employeeList;
//    }
//
//    @Override
//    public void save(Employee employee) {
//
//    }
//
//    @Override
//    public Employee getById(Long id) {
//        return null;
//    }
//
//    @Override
//    public void delete(Employee employee) {
//
//    }
//}
