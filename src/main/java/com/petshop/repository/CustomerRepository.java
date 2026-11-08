package com.petshop.repository;

import com.petshop.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    // 1. Retrieve all customers for POS selection
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }

    // 2. Find customer by ID
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding customer: " + e.getMessage());
        }
        return null;
    }

    // 3. Register a new customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getAddress());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
            return false;
        }
    }
}
