package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Menu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;
    private String category;
    private String name;
    private String description;
    private double price;

    // Default constructor สำหรับ JPA
    public Menu() {
        super();
    }

    // Constructor ที่มีพารามิเตอร์ทั้งหมด
    public Menu(Long menuId, String category, String name, String description, double price) {
        this.menuId = menuId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters และ Setters
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

