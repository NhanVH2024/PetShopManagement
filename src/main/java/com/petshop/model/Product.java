package com.petshop.model;

public class Product extends SaleItem {
    private String category;
    private int stock;

    public Product(int id, String name, long price, String category, int stock) {
        super(id, name, price);
        this.category = category;
        this.stock = stock;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public boolean canSell(int quantity) {
        return this.stock >= quantity;
    }

    @Override
    public String cannotSellMessage() {
        return "Số lượng tồn kho không đủ (chỉ còn " + stock + " sản phẩm)!";
    }

    @Override
    public void afterSale(int quantity) {
        this.stock -= quantity;
    }
}
