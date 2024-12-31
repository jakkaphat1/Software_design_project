package com.example.demo.controller;

import com.example.demo.model.Menu;
import com.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MenuService menuService;

    // หน้าแรกของ Admin
    @GetMapping
    public String home(Model model) {
        boolean newOrder = false; // ตัวอย่างการกำหนดค่าเพื่อแสดงว่ามีคำสั่งซื้อใหม่
        model.addAttribute("newOrder", newOrder);
        return "admin/home"; // ระบุชื่อไฟล์ HTML ที่จะแสดงเป็นหน้าแรกของ Admin
    }

    @GetMapping("/menu")
    public String showMenuList(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        return "admin/adminMenu"; // ชี้ไปที่ admin/adminMenu.html
    }

    @GetMapping("/menu/add")
    public String showAddMenuForm() {
        return "admin/addmenu"; // ชี้ไปที่ admin/addmenu.html
    }

    @PostMapping("/menu/add")
    public String addMenu(@RequestParam String name, @RequestParam String description, 
                          @RequestParam double price, @RequestParam String category) {
        Menu newMenu = new Menu();
        newMenu.setName(name);
        newMenu.setDescription(description);
        newMenu.setPrice(price);
        newMenu.setCategory(category);  // เพิ่มการตั้งค่าหมวดหมู่
        menuService.saveMenu(newMenu);
        return "redirect:/admin/menu";
    }

    @GetMapping("/menu/edit/{id}")
    public String showEditMenuForm(@PathVariable("id") Long id, Model model) {
        Menu menu = menuService.getMenuById(id);
        model.addAttribute("menu", menu);
        return "admin/editmenu";
    }

    @PostMapping("/menu/edit")
    public String editMenu(@RequestParam Long menuId, @RequestParam String name, 
                           @RequestParam String description, @RequestParam double price,
                           @RequestParam String category) {
        Menu menu = menuService.getMenuById(menuId);
        menu.setName(name);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setCategory(category); // เพิ่มการตั้งค่าหมวดหมู่ในการแก้ไข
        menuService.saveMenu(menu);
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/delete/{id}")
    public String deleteMenu(@PathVariable("id") Long menuId) {
        menuService.deleteMenu(menuId);
        return "redirect:/admin/menu";
    }
}
