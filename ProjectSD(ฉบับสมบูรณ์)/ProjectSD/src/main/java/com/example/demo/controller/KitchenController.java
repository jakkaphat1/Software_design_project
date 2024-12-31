package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.service.OrderService;



@Controller
public class KitchenController {

	@Autowired
	private OrderService orderService;
	
    // @GetMapping("/kitchen")
    // @PreAuthorize("hasAuthority('KITCHENMAN')")
    // public String viewKitchenOrders(Model model) {
    //     // ดึงข้อมูลรายการคำสั่งซื้อที่จะให้ห้องครัวแสดง (อาจจะดึงจาก service)
    //     model.addAttribute("orders", orderService.getAllOrders());
        
    //     return "kitchen-orders"; // ชื่อไฟล์ HTML ที่ใช้
    // }
    
    // Handle starting an order
    @PostMapping("/kitchen/orders/start/{orderId}")
    public String startOrder(@PathVariable("orderId") Long orderId) {
        orderService.updateOrderStatus(orderId, "Cooking");  // Update status to "Cooking"
        return "redirect:/kitchen-page";  // Redirect back to the kitchen view
    }

    // Complete an order after cooking
    @PostMapping("/kitchen/orders/complete/{orderId}")
    public String completeOrder(@PathVariable("orderId") Long orderId) {
        orderService.updateOrderStatus(orderId, "Completed");  // Update status to "Completed"
        return "redirect:/kitchen-page";  // Redirect back to the kitchen view
    }
    


    // Delete an order item (food item)
    @PostMapping("/kitchen/order/item/delete/{orderItemId}")
    public String deleteOrderItem(@PathVariable("orderItemId") Long orderItemId) {
        orderService.deleteOrderItem(orderItemId); // Add method in OrderService to delete the order item
        return "redirect:/kitchen-page";
    }
}

