package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	// คุณอาจเพิ่มเมธอดสำหรับค้นหาตามหมวดหมู่ได้ เช่น:
    List<Menu> findByCategory(String category);
}
