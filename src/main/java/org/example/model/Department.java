package org.example.model;

public class Department {
    public Department() {}

    private long id;
    private String name;
    private String description;
    private String location;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
