package com.arphor.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.arphor.entity.Product;
import com.arphor.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }
    
    @GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts().stream()
			.filter(product -> product.getDeleted() == 0)
			.collect(Collectors.toList());
	}

    // Create new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Update existing product
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product product) {
        return productService.updateProduct(product);
    }

    // Delete product by ID
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId, true);
    }
    
    @RequestMapping
    public List<Product> getAllProducts(@RequestParam(name = "minPrice", required = false) Double minPrice, 
    									@RequestParam(name = "maxPrice", required = false) Double maxPrice) {
    	 if (minPrice != null && maxPrice != null && minPrice <= maxPrice) {
    		 System.out.println(minPrice+"  "+maxPrice);
             return productService.getProductsByPriceRange(minPrice, maxPrice);
         } else {
             return productService.getAllProducts();
         }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsByProductName(@RequestParam String productName) {
        List<Product> products = productService.getProductsByProductName(productName);
        
        if (products.isEmpty()) {
            // Trả về HTTP Status 404 Not Found nếu không tìm thấy sản phẩm
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // Trả về danh sách sản phẩm và HTTP Status 200 OK nếu tìm thấy sản phẩm
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }
}
