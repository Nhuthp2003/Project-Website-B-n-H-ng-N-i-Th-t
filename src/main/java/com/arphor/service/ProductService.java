package com.arphor.service;

import java.util.List;

import com.arphor.entity.Product;

public interface ProductService {

	List<Product> getAllProducts();
	
    Product getProductById(Integer productId);
    
    Product createProduct(Product product);
    
    Product updateProduct(Product product);
    
	List<Product> getProductsByCategoryId(int categoryId);

	List<Product> getProductsByParentName(String parentName);

	List<Product> getRelatedProducts(Integer productId);
    
	List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);
	
	int getProductByIdfa(int productId);

	void deleteProduct(Integer productId, boolean b);

	List<Product> getProductsByProductName(String productName);
}
