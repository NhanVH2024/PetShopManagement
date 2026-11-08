package com.petshop.model;

public abstract class Employee extends Person {
    protected String username;
    protected String password;
    protected String role;

    public Employee(int id, String name, String phone, String email, String address, String username, String password, String role) {
        super(id, name, phone, email, address);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public abstract boolean canDelete();
    public abstract boolean canViewReports();
}
