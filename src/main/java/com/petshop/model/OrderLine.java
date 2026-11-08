package com.petshop.model;

public class OrderLine {
    private int id;
    private int orderId;
    private ItemType itemType;
    private int itemId;
    private String itemName;
    private int quantity;
    private long unitPrice;

    public OrderLine(int id, int orderId, ItemType itemType, int itemId, String itemName, int quantity, long unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() { return id; }
    public int getOrderId() { return orderId; }
    public ItemType getItemType() { return itemType; }
    public int getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public long getUnitPrice() { return unitPrice; }

    public long subtotal() {
        return this.quantity * this.unitPrice;
    }
}
