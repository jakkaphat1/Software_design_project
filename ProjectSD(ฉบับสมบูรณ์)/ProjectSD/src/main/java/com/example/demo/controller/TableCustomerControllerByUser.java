package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.User.EmpUserDatailsService;
import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.TableCustomer;
import com.example.demo.service.CartService;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrderService;
import com.example.demo.service.TableCustomerService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/menu")
public class TableCustomerControllerByUser {
	@Autowired
	private MenuService menuService;

	@Autowired
	EmpUserDatailsService empUserDatailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private TableCustomerService tableCustomerService;

	@Autowired
	private CartService cartService;

	@Autowired
	OrderService orderService;

	// @GetMapping("/table/{tableNumber}")
	// @PreAuthorize("hasAuthority('USER')")
	// public String viewMenu(@PathVariable("tableNumber") Integer tableNumber,
	// Model model, HttpSession session) {
	// List<Menu> menus = menuService.getAllMenus();
	// List<Order> orders = orderService.getOrdersByTableNumber(tableNumber); //
	// ดึงคำสั่งซื้อเฉพาะโต๊ะ
	// model.addAttribute("orders", orders);
	// model.addAttribute("menus", menus);
	// model.addAttribute("tableNumber", tableNumber);
	// session.setAttribute("tableNumber", tableNumber);
	// return "menu"; // ส่งไปยังหน้า menu.html
	// }

	@GetMapping("/table/{tableNumber}")
	public String viewMenu(@PathVariable("tableNumber") Integer tableNumber, Model model, HttpSession session) {
		TableCustomer tableCustomer = tableCustomerService.getTableByNumber(tableNumber);
		if (tableCustomer == null) {
			return "redirect:/error";
		}

		List<Menu> menus = menuService.getAllMenus();
		List<Order> orders1 = orderService.getOrdersByTableNumberWithTotal(tableNumber); // คอมเมนท์ก่อน ค่อยเอาออก



		// คำนวณยอดรวมของทุก orders
		double totalAmount = orders1.stream().mapToDouble(Order::getTotalAmount).sum();//

		model.addAttribute("orders", orders1);//
		model.addAttribute("menus", menus);
		model.addAttribute("tableNumber", tableNumber);
		model.addAttribute("totalAmount", totalAmount); // ส่งยอดรวมไปยังหน้า view //
		session.setAttribute("tableNumber", tableNumber);

		return "menu";
	}
	


}