package com.example.demo.model;

public class CartItem {
    private Menu menu;
    private int quantity;
    private Integer tableNumber; // เพิ่มฟิลด์ tableNumber

    // Getters และ Setters
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }
}
