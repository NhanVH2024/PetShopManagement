package com.petshop.repository;

import com.petshop.model.ItemType;  
import com.petshop.model.Order;
import com.petshop.model.OrderLine;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderRepository {

    // 1. Save complete order transaction including order header, lines, and stock update
    public boolean saveOrder(Order order) {
        String insertOrderSql = "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?) RETURNING id";
        String insertLineSql = "INSERT INTO order_lines (order_id, item_type, item_id, item_name, quantity, unit_price, subtotal) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updatePetSql = "UPDATE pets SET is_available = false WHERE id = ?";
        String updateProductSql = "UPDATE products SET stock = stock - ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start database transaction

            int generatedOrderId = -1;

            // Step 1: Insert into 'orders' table and retrieve auto-generated ID
            try (PreparedStatement pstmtOrder = conn.prepareStatement(insertOrderSql)) {
                pstmtOrder.setInt(1, order.getCustomer().getId());
                pstmtOrder.setLong(2, order.total());
                try (ResultSet rs = pstmtOrder.executeQuery()) {
                    if (rs.next()) {
                        generatedOrderId = rs.getInt(1);
                    }
                }
            }

            if (generatedOrderId == -1) {
                conn.rollback();
                return false;
            }

            // Step 2: Insert each line item and update inventory status
            try (PreparedStatement pstmtLine = conn.prepareStatement(insertLineSql);
                 PreparedStatement pstmtPet = conn.prepareStatement(updatePetSql);
                 PreparedStatement pstmtProd = conn.prepareStatement(updateProductSql)) {

                for (OrderLine line : order.getOrderLines()) {
                    pstmtLine.setInt(1, generatedOrderId);
                    pstmtLine.setString(2, line.getItemType().name());
                    pstmtLine.setInt(3, line.getItemId());
                    pstmtLine.setString(4, line.getItemName());
                    pstmtLine.setInt(5, line.getQuantity());
                    pstmtLine.setLong(6, line.getUnitPrice());
                    pstmtLine.setLong(7, line.subtotal());
                    pstmtLine.executeUpdate();

                    // Update corresponding item status based on item type
                    if (line.getItemType() == ItemType.PET) {
                        pstmtPet.setInt(1, line.getItemId());
                        pstmtPet.executeUpdate();
                    } else if (line.getItemType() == ItemType.PRODUCT) {
                        pstmtProd.setInt(1, line.getQuantity());
                        pstmtProd.setInt(2, line.getItemId());
                        pstmtProd.executeUpdate();
                    }
                }
            }

            conn.commit(); // Successfully commit the transaction
            return true;

        } catch (SQLException e) {
            System.err.println("Transaction failed while saving order: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback all changes if an error occurs
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 2. Query aggregated daily revenue between specific dates (Manager Report)
    public Map<String, Long> getDailyRevenue(String startDate, String endDate) {
        Map<String, Long> reportData = new LinkedHashMap<>();
        String sql = "SELECT DATE(order_date) AS report_date, SUM(total_amount) AS daily_revenue "
                   + "FROM orders "
                   + "WHERE DATE(order_date) BETWEEN ?::date AND ?::date "
                   + "GROUP BY DATE(order_date) "
                   + "ORDER BY report_date ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String date = rs.getString("report_date");
                    long revenue = rs.getLong("daily_revenue");
                    reportData.put(date, revenue);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error generating daily revenue report: " + e.getMessage());
        }
        return reportData;
    }
}
