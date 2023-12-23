package com.arphor.controller;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.arphor.dao.UserRolesDAO;
import com.arphor.entity.Category;
import com.arphor.entity.Favourite;
import com.arphor.entity.Order;
import com.arphor.entity.Parent;
import com.arphor.entity.Product;
import com.arphor.entity.UserRoles;
import com.arphor.service.CategoryService;
import com.arphor.service.FavouriteService;
import com.arphor.service.OrderService;
import com.arphor.service.ParentService;
import com.arphor.service.ProductService;
import com.arphor.service.UploadService;

@Controller
public class HomeController {

	@Autowired
	FavouriteService favouriteService;
	ProductService productService;
	
	@Autowired
	UploadService uploadService;

	@Autowired
	public void FavouriteController(FavouriteService favouriteService, ProductService productService) {
		this.favouriteService = favouriteService;
		this.productService = productService;
	}

	@Autowired
	private final UserRolesDAO userRolesDAO;
	private final OrderService orderService;

	public HomeController(UserRolesDAO userRolesDAO, OrderService orderService) {
		this.orderService = orderService;
		this.userRolesDAO = userRolesDAO;
	}

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ParentService parentService;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("pageTitle", "Arphor");
		// Menu Category
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		List<Parent> parents = parentService.getAllParents();
		model.addAttribute("parents", parents);
		return "/layout/home";
	}

	@RequestMapping("/profile")
	public String profile(Model model, Principal principal) {
		model.addAttribute("pageTitle", "My Account - Arphor");
		String email = principal.getName();
		UserRoles userRoles = userRolesDAO.findByEmail(email);

		if (userRoles != null) {
			// Lấy firstName và lastName từ người dùng
			String firstName = userRoles.getFirstName();
			String lastName = userRoles.getLastName();
			String phone = userRoles.getPhone();
			String address = userRoles.getAddress();
			Boolean gender = userRoles.getGender();
			String imageName = userRoles.getImages();
			String genderText = "";
			if (gender != null) {
				if (gender) {
					genderText = "Nữ";
				} else {
					genderText = "Nam";
				}
			} else {
				genderText = "Khác";
			}

			// Truyền thông tin vào model
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("phone", phone);
			model.addAttribute("gender", genderText);
			model.addAttribute("address", address);
			model.addAttribute("userRoles", userRoles);
			model.addAttribute("imageName", imageName);
			model.addAttribute("email", email);
			// Gọi phương thức showFavourites() và lưu kết quả vào biến
			List<Product> products = showFavourites(model, principal);

			// Truyền danh sách yêu thích vào model
			model.addAttribute("products", products);
			
			// Gọi phương thức orderListByEmail() và lưu kết quả vào biến
            List<Order> orders = orderListByEmail(model, principal);
            // Truyền danh sách đơn hàng vào model
            model.addAttribute("orders", orders);
		}

		// Menu Category
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		List<Parent> parents = parentService.getAllParents();
		model.addAttribute("parents", parents);
		List<Order> orders = orderService.getOrdersByCustomer(email);
		model.addAttribute("orders", orders);
		return "/layout/profile";
	}

	@PostMapping("/profile/edit")
	public String updateUser(@ModelAttribute("userRoles") UserRoles userRoles,@RequestParam("image") MultipartFile image, Model model, Principal principal, HttpServletRequest request) {
		String email = principal.getName();
		UserRoles existingUser = userRolesDAO.findByEmail(email);
		if (existingUser != null) {
			// Cập nhật thông tin người dùng
			existingUser.setFirstName(userRoles.getFirstName());
			existingUser.setLastName(userRoles.getLastName());
			existingUser.setPhone(userRoles.getPhone());
			existingUser.setAddress(userRoles.getAddress());
			existingUser.setGender(userRoles.getGender());
			existingUser.setEmail(userRoles.getEmail());
			existingUser.setImages(userRoles.getImages());
			
		       // Xử lý hình ảnh
	        File saveFile = uploadService.save(image, "home");
	        existingUser.setImages(saveFile.getName());
			
			// Lưu thông tin cập nhật vào CSDL
			userRolesDAO.save(existingUser);

		}
		// Menu Category
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		List<Parent> parents = parentService.getAllParents();
		model.addAttribute("parents", parents);
		return "redirect:/profile";
	}
	
	private List<Order> orderListByEmail(Model model, Principal principal) {
		 String email = principal.getName();
		 List<Order> orders = orderService.findByUsername(email);
		 model.addAttribute("orders", orders);
		 return orders;
	}

	private List<Product> showFavourites(Model model, Principal principal) {
		// Lấy thông tin người dùng hiện tại từ Principal
		String email = principal.getName();

		// Lấy danh sách yêu thích của người dùng từ service
		List<Favourite> favourites = favouriteService.getFavouritesByEmail(email);

		// Tạo một danh sách các sản phẩm từ danh sách yêu thích
		List<Product> products = new ArrayList<>();
		for (Favourite favourite : favourites) {
			// Lấy thông tin sản phẩm từ ProductService
			int productId = favourite.getProductId();

			Product product = productService.getProductById(productId);
			products.add(product);

		}
		// Truyền danh sách sản phẩm vào Model để hiển thị trong view
		model.addAttribute("products", products);

		// Trả về view hiển thị danh sách yêu thích
		return products;
	}
}
