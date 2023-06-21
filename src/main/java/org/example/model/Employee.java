package org.example.model;

import java.util.Date;

public class Employee {
    public Employee() {}

    private long id;
    private String name;
    private String first_name;
    private String last_name;
    private String email;
    private String address;
    private Date hired_date;
    private long department_id;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHired_date(Date hired_date) {
        this.hired_date = hired_date;
    }

    public void setDepartment_id(long department_id) {
        this.department_id = department_id;
    }
}
