package com.example.demo.service;

import com.example.demo.model.Menu;
import com.example.demo.model.CartItem;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private static final String CART_SESSION_ATTRIBUTE = "cart";
    private static final String TABLE_NUMBER_SESSION_ATTRIBUTE = "tableNumber"; // เพิ่มสำหรับเก็บหมายเลขโต๊ะ

    public List<CartItem> getCartItems(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_ATTRIBUTE, cart);
        }
        return cart;
    }

    public void addToCart(Menu menu, int quantity, Integer tableNumber, HttpSession session) {
        List<CartItem> cart = getCartItems(session);
        boolean itemExists = false;

        // ตรวจสอบว่ามีรายการนี้อยู่ในตะกร้าหรือไม่
        for (CartItem item : cart) {
            if (item.getMenu().getMenuId().equals(menu.getMenuId())) {  // ตรวจสอบว่าอาหารนี้มีในตะกร้าหรือไม่
                item.setQuantity(item.getQuantity() + quantity);  // เพิ่มจำนวนในรายการเดิม
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            // ถ้าไม่มีรายการอาหารนี้ในตะกร้า ให้เพิ่มเข้าไปใหม่
            CartItem newItem = new CartItem();
            newItem.setMenu(menu);
            newItem.setQuantity(quantity);
            newItem.setTableNumber(tableNumber); // กำหนดหมายเลขโต๊ะในรายการตะกร้า
            cart.add(newItem);
        }

        // เก็บหมายเลขโต๊ะใน session
        session.setAttribute(TABLE_NUMBER_SESSION_ATTRIBUTE, tableNumber);
    }

    public void removeFromCart(Long menuId, HttpSession session) {
        List<CartItem> cart = getCartItems(session);
        cart.removeIf(item -> item.getMenu().getMenuId().equals(menuId));
    }

    public Integer getTableNumber(HttpSession session) {
        // ดึงหมายเลขโต๊ะจาก session
        return (Integer) session.getAttribute(TABLE_NUMBER_SESSION_ATTRIBUTE);
    }
}
