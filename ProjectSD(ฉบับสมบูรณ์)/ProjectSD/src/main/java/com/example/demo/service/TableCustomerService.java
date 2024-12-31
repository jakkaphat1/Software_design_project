package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.TableCustomer;
import com.example.demo.repository.TableCustomerRepository;

import java.util.List;

@Service
public class TableCustomerService {

    @Autowired
    private TableCustomerRepository tableCustomerRepository;

    public List<TableCustomer> getAllTables() {
        return tableCustomerRepository.findAll();
    }

    public TableCustomer getTableById(Long id) {
        return tableCustomerRepository.findById(id).orElseThrow(() -> new RuntimeException("Table not found"));
    }

    public TableCustomer getTableByNumber(Integer tableNumber) {
        return tableCustomerRepository.findByTableNumber(tableNumber);
    }
    
    public TableCustomer saveTable(TableCustomer tableCustomer) {
        return tableCustomerRepository.save(tableCustomer);  // คืนค่าโต๊ะที่ถูกบันทึก
    }

    public void updateTable(Long id, TableCustomer updatedTable) {
        TableCustomer table = getTableById(id);
        table.setTableNumber(updatedTable.getTableNumber());
        table.setStatus(updatedTable.getStatus());
        tableCustomerRepository.save(table);
    }

    public void deleteTable(Long id) {
        tableCustomerRepository.deleteById(id);
    }
    
    
}


