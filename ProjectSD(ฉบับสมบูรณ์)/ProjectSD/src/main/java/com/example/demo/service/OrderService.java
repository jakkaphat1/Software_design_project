package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;

    public void saveOrder(Order order) {
        // คำนวณยอดรวมก่อนการบันทึก
        double totalAmount = order.getTotalAmount();
        order.setTotalAmount(totalAmount);

        // บันทึกคำสั่งซื้อ
        orderRepository.save(order);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public void clearOrdersByTableNumber(Integer tableNumber) {
        List<Order> orders = orderRepository.findByTableNumber(tableNumber);
        orderRepository.deleteAll(orders);
    }

    
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
    
    public List<Order> getOrdersByTableNumber(Integer tableId) {
        return orderRepository.findByTableNumber(tableId);
    }

    public void deleteOrderById(Long id) {
    	orderRepository.deleteById(id);
    }

    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow();
	}

    public List<Order> getAllOrdersSortedByNewest() {
        return orderRepository.findAllByOrderByOrderIdDesc();
    }
    
    public List<Order> getOrdersByTableNumberWithTotal(Integer tableId) {
        List<Order> orders = orderRepository.findByTableNumber(tableId);

        // เรียกใช้ calculateTotalAmount() เพื่อคำนวณยอดรวม
        for (Order order : orders) {
            order.getTotalAmount(); // คำนวณยอดรวม
        }

        return orders;
    }


}
