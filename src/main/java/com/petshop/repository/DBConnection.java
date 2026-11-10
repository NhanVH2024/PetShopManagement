package com.petshop.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Đường dẫn kết nối tới database petshop_db chạy trên cổng 5432
    private static final String URL = "jdbc:postgresql://localhost:5432/petshop_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Vhnstudent=2024"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        System.out.println("Đang kiểm tra kết nối tới PostgreSQL...");
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối cơ sở dữ liệu petshop_db thành công rực rỡ!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}
