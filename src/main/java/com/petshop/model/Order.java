package com.petshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime orderDateTime;
    private Customer customer;
    private List<OrderLine> orderLines;

    public Order(int id, LocalDateTime orderDateTime, Customer customer) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.customer = customer;
        this.orderLines = new ArrayList<>();
    }

    public int getId() { return id; }
    public LocalDateTime getOrderDateTime() { return orderDateTime; }
    public Customer getCustomer() { return customer; }
    public List<OrderLine> getOrderLines() { return orderLines; }

    public void addOrderLine(OrderLine line) {
        this.orderLines.add(line);
    }

    public long total() {
        long sum = 0;
        for (OrderLine line : orderLines) {
            sum += line.subtotal();
        }
        return sum;
    }
}
