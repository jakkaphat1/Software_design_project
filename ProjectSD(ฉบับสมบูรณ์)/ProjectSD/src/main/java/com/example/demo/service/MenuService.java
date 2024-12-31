package com.example.demo.service;

import com.example.demo.model.Menu;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository; // ต้องมี repository สำหรับ OrderItem

    public void deleteMenu(Long menuId) {
        // ลบ OrderItem ที่เชื่อมโยงกับ Menu ก่อน
        List<OrderItem> orderItems = orderItemRepository.findByMenu_MenuId(menuId);
        orderItemRepository.deleteAll(orderItems);

        // ลบ Menu หลังจากลบ OrderItem ที่เกี่ยวข้องแล้ว
        menuRepository.deleteById(menuId);
    }


    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    public Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    // เพิ่มฟังก์ชันค้นหาตามหมวดหมู่ถ้าจำเป็น
    public List<Menu> getMenusByCategory(String category) {
        return menuRepository.findByCategory(category);
    }
}
