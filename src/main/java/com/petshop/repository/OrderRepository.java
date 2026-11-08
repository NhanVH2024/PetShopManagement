package com.petshop.repository;

import com.petshop.model.ItemType;
import com.petshop.model.Order;
import com.petshop.model.OrderLine;
import java.sql.*;

public class OrderRepository {

    // Lưu hóa đơn hoàn chỉnh bằng Transaction
    public boolean saveOrder(Order order) {
        String insertOrderSql = "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?) RETURNING id";
        String insertLineSql = "INSERT INTO order_lines (order_id, item_type, item_id, item_name, quantity, unit_price, subtotal) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updatePetSql = "UPDATE pets SET is_available = false WHERE id = ?";
        String updateProductSql = "UPDATE products SET stock = stock - ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            int generatedOrderId = -1;

            // 1. Thêm bản ghi orders
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

            // 2. Thêm từng dòng chi tiết đơn hàng (order_lines) & Cập nhật kho
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

                    // Cập nhật trạng thái mặt hàng tương ứng
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

            conn.commit(); // Hoàn tất giao dịch an toàn
            return true;

        } catch (SQLException e) {
            System.err.println("Lỗi Transaction khi lưu đơn hàng: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Hoàn tác nếu có lỗi
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
}
