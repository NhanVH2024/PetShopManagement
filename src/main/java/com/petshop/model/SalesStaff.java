package com.petshop.model;

public class SalesStaff extends Employee {
    public SalesStaff(int id, String name, String phone, String email, String address, String username, String password) {
        super(id, name, phone, email, address, username, password, "SALES_STAFF");
    }

    @Override
    public boolean canDelete() { return false; }

    @Override
    public boolean canViewReports() { return false; }
}
