package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TableCustomer;
@Repository
public interface TableCustomerRepository extends JpaRepository<TableCustomer, Long> {
    TableCustomer findByTableNumber(Integer tableNumber);
}