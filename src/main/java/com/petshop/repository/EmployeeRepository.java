package com.petshop.repository;

import com.petshop.model.Employee;
import com.petshop.model.Manager;
import com.petshop.model.SalesStaff;
import java.sql.*;

public class EmployeeRepository {

    // 1. Authenticate user credentials and return specific Employee polymorphism instance
    public Employee authenticate(String username, String password) {
        String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String role = rs.getString("role");

                    // Instantiate child class based on the database role
                    if ("MANAGER".equalsIgnoreCase(role)) {
                        return new Manager(id, name, phone, email, address, username, password);
                    } else if ("SALES_STAFF".equalsIgnoreCase(role)) {
                        return new SalesStaff(id, name, phone, email, address, username, password);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during authentication: " + e.getMessage());
        }
        return null; // Return null if authentication fails
    }

    // 2. Add sample employee (useful for initial system setup or seeding)
    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, phone, email, address, username, password, role) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getPhone());
            pstmt.setString(3, emp.getEmail());
            pstmt.setString(4, emp.getAddress());
            pstmt.setString(5, emp.getUsername());
            pstmt.setString(6, emp.getPassword());
            pstmt.setString(7, emp.getRole());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }
}
