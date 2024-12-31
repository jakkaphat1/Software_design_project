package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.TableCustomer;
import com.example.demo.service.TableCustomerService;

@Controller
@RequestMapping("/admin")
public class TableCustomerControllerByAdmin {

    @Autowired
    private TableCustomerService tableCustomerService;

    @GetMapping("/tables")
    public String listTables(Model model) {
        model.addAttribute("tables", tableCustomerService.getAllTables());
        return "admin/tablecustomer"; // ตรวจสอบให้แน่ใจว่าไฟล์ HTML อยู่ในตำแหน่งที่ถูกต้อง
    }

    @GetMapping("/tables/add")
    public String showAddForm(Model model) {
        model.addAttribute("tableCustomer", new TableCustomer());
        return "admin/addTable";
    }

    @PostMapping("/tables/add")
    public String addTable(@ModelAttribute("tableCustomer") TableCustomer tableCustomer) {
        tableCustomerService.saveTable(tableCustomer);
        return "redirect:/admin/tables";
    }

    @GetMapping("/tables/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tableCustomer", tableCustomerService.getTableById(id));
        return "admin/editTable";
    }
    
   


    @PostMapping("/tables/edit/{id}")
    public String updateTable(@PathVariable("id") Long id, @ModelAttribute("tableCustomer") TableCustomer tableCustomer) {
        tableCustomerService.updateTable(id, tableCustomer);
        return "redirect:/admin/tables";
    }

    @PostMapping("/tables/delete/{id}")
    public String deleteTable(@PathVariable("id") Long id) {
        tableCustomerService.deleteTable(id);
        return "redirect:/admin/tables";
    }

}
