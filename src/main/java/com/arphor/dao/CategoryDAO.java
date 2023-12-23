package com.arphor.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.arphor.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer>{
	
}
