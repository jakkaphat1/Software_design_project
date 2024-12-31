package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.User.EmpUserDatailsService;
import com.example.demo.User.UserDto;
import com.example.demo.model.TableCustomer;
import com.example.demo.service.OrderService;
import com.example.demo.service.TableCustomerService;
import com.example.demo.service.UserService;

@Controller
public class RoleController {
	
	@Autowired
	EmpUserDatailsService empUserDatailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TableCustomerService tableCustomerService;

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/admin/register")
//    @PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAuthority('ADMIN')")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

	@PostMapping("/admin/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String registerUserByAdmin(@ModelAttribute("user") UserDto userDto, RedirectAttributes redirectAttributes) {
        try {
            userService.save(userDto);
            redirectAttributes.addFlashAttribute("successMessage", "User registered successfully");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Registration failed: " + e.getMessage());
            return "redirect:/admin/register";

        }
    }
	
	@GetMapping("/")
	public String getIndex(Model model) {
		return "login";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		return "login";
	}
	
//	@GetMapping("/user-page")
//	@PreAuthorize("hasAuthority('USER')")
//	public String userPage(Model model,Principal principal) {
//		UserDetails userDetails = empUserDatailsService.loadUserByUsername(principal.getName());
//		model.addAttribute("user",userDetails);
//		return "table";
//	}
	@GetMapping("/user-page")
	@PreAuthorize("hasAuthority('USER')")
	public String userPage(Model model, Principal principal) {
	    UserDetails userDetails = empUserDatailsService.loadUserByUsername(principal.getName());
	    
	    // เรียกข้อมูลโต๊ะที่ผู้ดูแลระบบได้สร้าง
	    List<TableCustomer> tables = tableCustomerService.getAllTables();
	    
	    model.addAttribute("user", userDetails);
	    model.addAttribute("tables", tables); // เพิ่มข้อมูลโต๊ะลงในโมเดล
	    return "empTable"; // ชื่อ template ที่จะแสดง
	}

	
	@GetMapping("/admin-page")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminPage(Model model,Principal principal) {
		UserDetails userDetails = empUserDatailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user",userDetails);
		return "admin/admin";
	}
	
	@GetMapping("/kitchen-page")
	@PreAuthorize("hasAuthority('KITCHENMAN')")
	public String getKitchenPage(Model model, Principal principal) {
	    UserDetails userDetails = empUserDatailsService.loadUserByUsername(principal.getName());
	    model.addAttribute("user", userDetails);
		// model.addAttribute("orders", orderService.getAllOrders());
		model.addAttribute("orders", orderService.getAllOrdersSortedByNewest());
	    return "kitchen-orders";
	}

}
