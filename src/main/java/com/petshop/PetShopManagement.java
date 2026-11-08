package com.petshop;

import com.petshop.repository.DBConnection;

public class PetShopManagement {
    public static void main(String[] args) {
        // Chạy kiểm tra kết nối tới cơ sở dữ liệu PostgreSQL
        DBConnection.main(args);
    }
}
