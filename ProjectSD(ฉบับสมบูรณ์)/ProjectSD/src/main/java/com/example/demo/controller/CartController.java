package com.example.demo.controller;

import com.example.demo.model.Menu;
import com.example.demo.model.CartItem;
import com.example.demo.service.CartService;
import com.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cartItems = cartService.getCartItems(session);
        Integer tableNumber = (Integer) session.getAttribute("tableNumber"); // ดึง tableNumber จาก session
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("tableNumber", tableNumber); // ส่ง tableNumber ไปยัง Model
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long menuId, @RequestParam int quantity, @RequestParam Integer tableNumber, HttpSession session) {
        Menu menu = menuService.getMenuById(menuId);
        cartService.addToCart(menu, quantity, tableNumber, session); // ส่ง tableNumber ด้วย
        return "redirect:/cart/view";
    }

    // เมธอดสำหรับลบรายการอาหารออกจากตะกร้า
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long menuId, HttpSession session) {
        cartService.removeFromCart(menuId, session);
        return "redirect:/cart/view";
    }
}
