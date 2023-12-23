package com.arphor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arphor.entity.Category;
import com.arphor.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	
	List<Product> findByCategoryCategoryID(int categoryId);
	
	List<Product> findByCategoryParentParentName(String parentName);

	List<Product> findByCategory(Category category);
	
	List<Product> findByPriceBetween(double minPrice, double maxPrice);
	
	List<Product> findByProductNameContaining(String productName);
}
