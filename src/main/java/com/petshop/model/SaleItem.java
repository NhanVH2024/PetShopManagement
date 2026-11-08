package com.petshop.model;

public abstract class SaleItem {
    protected int id;
    protected String name;
    protected long price;

    public SaleItem(int id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }

    public abstract boolean canSell(int quantity);
    public abstract String cannotSellMessage();
    public abstract void afterSale(int quantity);
}
