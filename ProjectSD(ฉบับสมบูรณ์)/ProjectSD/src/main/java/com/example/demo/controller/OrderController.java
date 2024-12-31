package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.service.CartService;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrderService;


import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;

    	
    @PostMapping("/confirm")
    public String confirmOrder(HttpSession session, Model model) {
        List<CartItem> cartItems = cartService.getCartItems(session);
        Integer tableNumber = (Integer) session.getAttribute("tableNumber"); // ดึงหมายเลขโต๊ะจาก session

        if (!cartItems.isEmpty()) {
            Order order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setTableNumber(tableNumber); // กำหนดหมายเลขโต๊ะในคำสั่งซื้อ

            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setMenu(cartItem.getMenu());
                orderItem.setQuantity(cartItem.getQuantity());
                order.addOrderItem(orderItem);
            }

            // คำสั่งนี้ถูกลบออกเพราะคำนวณ totalAmount ใน getTotalAmount() แล้ว
            // order.setTotalAmount(totalAmount);

            orderService.saveOrder(order);
            session.removeAttribute("cart"); // ล้างตะกร้าหลังยืนยันคำสั่งซื้อ

            model.addAttribute("tableNumber", tableNumber); // ส่งหมายเลขโต๊ะไปยัง view ก่อนล้างจาก session
            session.removeAttribute("tableNumber"); // ล้างหมายเลขโต๊ะหลังยืนยันคำสั่งซื้อ
        }

        model.addAttribute("message", "คำสั่งซื้อของคุณได้รับการยืนยันแล้ว!");
        return "orderConfirmation";
    }
	
 
    
    @GetMapping("/management")
    public String viewOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order-management";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateOrderStatus(@PathVariable("id") Long orderId, @RequestParam("status") String status) {
        Order order = orderService.getOrderById(orderId);

        if ("Served".equals(status)) {
            order.setPickupDate(LocalDateTime.now()); // บันทึกเวลาปัจจุบันเมื่อเสิร์ฟ
        }

        order.setStatus(status); // อัปเดตสถานะ
        orderService.saveOrder(order); // บันทึกการเปลี่ยนแปลง

        return "redirect:/order/management"; // กลับไปยังหน้า management
    }
    
    @PostMapping("/delete/{id}")
    public String clearOrder(@PathVariable("id") Long orderId) {
        Order order = orderService.getOrderById(orderId); // Fetch the order
        if (order.getStatus().equals("Denied") || order.getStatus().equals("Served")) {
            orderService.deleteOrderById(orderId); // Delete if status is Denied or Served
        }
        return "redirect:/order/management";
    }
    
    @PostMapping("/clearByTableNumber/{tableNumber}")
    public String clearOrdersByTableNumber(@PathVariable("tableNumber") Integer tableNumber) {
        orderService.clearOrdersByTableNumber(tableNumber);
        return "redirect:/order/management";
    }
}



