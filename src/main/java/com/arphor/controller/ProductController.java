package com.arphor.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arphor.entity.Category;
import com.arphor.entity.Parent;
import com.arphor.entity.Product;
import com.arphor.service.CategoryService;
import com.arphor.service.ParentService;
import com.arphor.service.ProductService;

@Controller
public class ProductController {

	@Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
	@Autowired
	private ParentService parentService;

	@GetMapping("/product/list")
	public String getAllProducts(Model model,
	        @RequestParam(value = "categoryId", required = false) Integer categoryId,
	        @RequestParam(value = "parentName", required = false) String parentName) {
	    // Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);

	    // Lấy danh sách sản phẩm dựa trên categoryId hoặc parentId
	    List<Product> products;

	    if (categoryId != null) {
	        // Nếu categoryId được cung cấp, lấy danh sách sản phẩm liên quan đến categoryId
	        products = productService.getProductsByCategoryId(categoryId);
	        model.addAttribute("title", products.get(0).getCategory().getCategoryName());
	        model.addAttribute("pageTitle", products.get(0).getCategory().getCategoryName() + " | Arphor");
	    } else if (parentName != null) {
	        // Nếu parentId được cung cấp, lấy danh sách sản phẩm liên quan đến parentId
	        products = productService.getProductsByParentName(parentName);
	        model.addAttribute("title", parentName);
	        model.addAttribute("pageTitle", parentName + " | Arphor");
	    } else {
	        // Nếu không có categoryId hoặc parentId được cung cấp, lấy toàn bộ danh sách sản phẩm
	    	
	        products = productService.getAllProducts();
	    }
	    model.addAttribute("products", products);
	    return "product/list";
	}
    
    @GetMapping("/products/{productId}")
    public String getProductDetails(@PathVariable Integer productId, Model model) {
    	//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        String pageTitle = product.getName();
        model.addAttribute("pageTitle", pageTitle + " - Arphor");
        List<Product> relatedProducts = productService.getRelatedProducts(productId);
        model.addAttribute("relatedProducts", relatedProducts);
        return "product/detail";
    }
    
    //Lọc Theo Giá
    @RequestMapping("/price/less-than-500")
    public String showProductsLessThan500(Model model,
    		@RequestParam(value = "categoryId", required = false) Integer categoryId,
	        @RequestParam(value = "parentName", required = false) String parentName) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
	    
	    //Lọc sản phẩm dưới $500
        List<Product> products = productService.getProductsByPriceRange(0.1, 99.99);
        model.addAttribute("title", "Products Less Than $100");
        model.addAttribute("products", products);
        return "product/list";
    }
    
    @RequestMapping("/price/500-1000")
    public String showProductFrom500To1000(Model model,
    		@RequestParam(value = "categoryId", required = false) Integer categoryId,
	        @RequestParam(value = "parentName", required = false) String parentName) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
    	
    	//Lọc sản phẩm từ $500 đến $1000
        List<Product> products = productService.getProductsByPriceRange(99.99, 199.99);
        model.addAttribute("title", "Product From $100 To $200");
        model.addAttribute("products", products);
    	return "product/list";
    }
    
    @RequestMapping("/price/1000-3000")
    public String showProductFrom1000To3000(Model model,
    		@RequestParam(value = "categoryId", required = false) Integer categoryId,
	        @RequestParam(value = "parentName", required = false) String parentName) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
    	
    	//Lọc sản phẩm từ $500 đến $1000
        List<Product> products = productService.getProductsByPriceRange(199.99, 299.99);
        model.addAttribute("title", "Product From $200 To $300");
        model.addAttribute("products", products);
    	return "product/list";
    }
    
    @RequestMapping("/price/3000-5000")
    public String showProductFrom3000To5000(Model model,
    		@RequestParam(value = "categoryId", required = false) Integer categoryId,
	        @RequestParam(value = "parentName", required = false) String parentName) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
    	
    	//Lọc sản phẩm từ $3000 đến $5000
        List<Product> products = productService.getProductsByPriceRange(299.99, 499.99);
        model.addAttribute("title", "Product From $300 To $500");
        model.addAttribute("products", products);
    	return "product/list";
    }
    
    @RequestMapping("/price/more-than-5000")
    public String showProductMoreThan5000(Model model,
    		@RequestParam(value = "categoryId", required = false) Integer categoryId,
	        @RequestParam(value = "parentName", required = false) String parentName) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
    	
    	//Lọc sản phẩm hơn $5000
        List<Product> products = productService.getProductsByPriceRange(499.99, 9999999999.99);
        model.addAttribute("title", "Product More Than $500");
        model.addAttribute("products", products);
    	return "product/list";
    }
    
    //Lọc Theo Loại
    @RequestMapping("/name/name-A-to-Z")
    public String showProductNameAToZ(Model model) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
	    
    	//Lọc sản phẩm hơn từ A đến Z
        List<Product> products = productService.getProductsByPriceRange(0.1, 9999999999.99);
        model.addAttribute("title", "Product From A To Z");
        Collections.sort(products, Comparator.comparing(Product::getPrice));
        model.addAttribute("products", products);
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                // Sử dụng getter để lấy tên sản phẩm
                return product1.getName().compareTo(product2.getName());
            }
        });
    	return "product/list";
    }
    
    @RequestMapping("/name/name-Z-to-A")
    public String showProductNameZToA(Model model) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
	    
    	//Lọc sản phẩm hơn từ Z đến A
        List<Product> products = productService.getProductsByPriceRange(0.1, 9999999999.99);
        model.addAttribute("title", "Product From Z To A");
        Collections.sort(products, Comparator.comparing(Product::getPrice));
        model.addAttribute("products", products);
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                // Đảo ngược sự so sánh bằng cách sử dụng compareTo ngược
                return product2.getName().compareTo(product1.getName());
            }
        });
    	return "product/list";
    }
    
    @RequestMapping("/price/low-to-high")
    public String showProductPriceLowToHigh(Model model) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
	    
    	//Lọc sản phẩm hơn từ giá thấp đến cao
        List<Product> products = productService.getProductsByPriceRange(0.1, 9999999999.99);
        model.addAttribute("title", "Product From Price Low To High");
        Collections.sort(products, Comparator.comparing(Product::getPrice));
        model.addAttribute("products", products);
    	return "product/list";
    }
    
    @RequestMapping("/price/high-to-low")
    public String showProductPriceHighToLow(Model model) {
    	// Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);
	    
    	//Lọc sản phẩm hơn từ giá cao đến thấp
        List<Product> products = productService.getProductsByPriceRange(0.1, 9999999999.99);
        model.addAttribute("title", "Product From Price High To Low");
        Collections.sort(products, Comparator.comparing(Product::getPrice, Collections.reverseOrder()));
        model.addAttribute("products", products);
    	return "product/list";
    }
    
    @GetMapping("/product/search")
	public String searchProducts(Model model, @RequestParam(name = "productName", required = false) String productName) {
	    // Lấy danh sách tất cả các danh mục
	    List<Category> categories = categoryService.getAllCategories();
	    model.addAttribute("categories", categories);

	    // Lấy danh sách tất cả các danh mục cha
	    List<Parent> parents = parentService.getAllParents();
	    model.addAttribute("parents", parents);

	    if (productName != null && !productName.isEmpty()) {
	        // Nếu searchTerm được cung cấp, lấy danh sách sản phẩm liên quan đến searchTerm
	        List<Product> products = productService.getProductsByProductName(productName);
	        model.addAttribute("products", products);

	        if (!products.isEmpty()) {
	            model.addAttribute("products", products);
	            model.addAttribute("title", "Search Results for: " + productName);
	            return "product/list";
	        } else {
	            // Nếu không tìm thấy sản phẩm, có thể xử lý theo ý của bạn, ví dụ chuyển hướng hoặc hiển thị thông báo
	        	model.addAttribute("title", "No products found for: " + productName);
	            return "product/list";
	        }
	    } else {
	        // Nếu không có searchTerm được cung cấp, chuyển hướng hoặc xử lý theo logic của bạn
	        // ở đây là chuyển hướng về trang danh sách sản phẩm hoặc trang chính
	    	 model.addAttribute("title", "Vui lòng nhập sản phẩm muốn tìm kiếm");
	        return "redirect:/product/list";
	    }
	}
}
