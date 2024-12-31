package com.example.demo.controller;

import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.service.CartService;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CartService cartService;
    
    @Autowired OrderService orderService;

    @GetMapping("/all")
    public String listMenus(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        return "menu"; // หน้า menu.html
    }
    
    
    
}
