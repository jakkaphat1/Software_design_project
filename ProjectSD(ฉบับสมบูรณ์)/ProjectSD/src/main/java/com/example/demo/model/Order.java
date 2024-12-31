package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDateTime orderDate;
    private Integer tableNumber;
    private String status;

    @Column(name = "total_amount", nullable = false)  // กำหนดคอลัมน์ในฐานข้อมูล
    private double totalAmount;

    private LocalDateTime pickupDate; // เพิ่มฟิลด์ pickupDate

    @ManyToOne
    @JoinColumn(name = "table_id") // เพิ่มความสัมพันธ์กับ TableCustomer
    private TableCustomer tableCustomer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // เมธอดสำหรับเพิ่ม OrderItem และอัปเดตยอดรวม
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        // อัปเดตยอดรวม
        this.totalAmount = calculateTotalAmount();
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
        // อัปเดตยอดรวม
        this.totalAmount = calculateTotalAmount();
    }

    // คำนวณยอดรวมของ orderItems
    private double calculateTotalAmount() {
        return orderItems.stream()
            .mapToDouble(item -> item.getMenu().getPrice() * item.getQuantity())
            .sum();
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        // อัปเดตยอดรวม
        this.totalAmount = calculateTotalAmount();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TableCustomer getTableCustomer() {
        return tableCustomer;
    }

    public void setTableCustomer(TableCustomer tableCustomer) {
        this.tableCustomer = tableCustomer;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }
}
