package com.petshop.repository;

import com.petshop.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // 1. Query all the product list
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getLong("price"),
                    rs.getString("category"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllProducts: " + e.getMessage());
        }
        return products;
    }

    // 2. Find product by id
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getLong("price"),
                        rs.getString("category"),
                        rs.getInt("stock")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getProductById: " + e.getMessage());
        }
        return null;
    }

    // 3. Add new product
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, price, category, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setLong(2, product.getPrice());
            pstmt.setString(3, product.getCategory());
            pstmt.setInt(4, product.getStock());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi addProduct: " + e.getMessage());
            return false;
        }
    }

    // 4.Decrease the total after sold
    public boolean deductStock(int productId, int quantitySold) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantitySold);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantitySold);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi deductStock: " + e.getMessage());
            return false;
        }
    }
        // 5. Update merchandise information or restock inventory
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, category = ?, stock = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setLong(2, product.getPrice());
            pstmt.setString(3, product.getCategory());
            pstmt.setInt(4, product.getStock());
            pstmt.setInt(5, product.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in updateProduct: " + e.getMessage());
            return false;
        }
    }

    // 6. Delete merchandise permanently (Manager role permission only)
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in deleteProduct: " + e.getMessage());
            return false;
        }
    }

}
