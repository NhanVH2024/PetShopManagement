package com.petshop.model;

public class Manager extends Employee {
    public Manager(int id, String name, String phone, String email, String address, String username, String password) {
        super(id, name, phone, email, address, username, password, "MANAGER");
    }

    @Override
    public boolean canDelete() { return true; }

    @Override
    public boolean canViewReports() { return true; }
}
