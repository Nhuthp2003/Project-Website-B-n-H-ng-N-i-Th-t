package com.arphor.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arphor.entity.Favourite;
import com.arphor.service.FavouriteService;
import com.arphor.service.ProductService;

@Controller
public class FavouriteController {

	private final FavouriteService favouriteService;
	private final ProductService productService;

	public FavouriteController(FavouriteService favouriteService, ProductService productService) {
		this.favouriteService = favouriteService;
		this.productService = productService;
	}

	@PostMapping("/favourite/add")
	public String addToFavourites(@RequestParam("productId") int productId, Model model, Principal principal) {
		// Kiểm tra xem người dùng đã đăng nhập hay chưa
		if (principal == null) {
			// Nếu người dùng chưa đăng nhập, thì thêm thông báo vào Model
			model.addAttribute("message", "Bạn cần đăng nhập để thêm sản phẩm vào danh sách yêu thích.");
			// Chuyển hướng người dùng đến trang đăng nhập
			return "redirect:/account/login";
		}

		// Lấy thông tin người dùng hiện tại từ Principal
		String email = principal.getName();

		// Tìm kiếm sản phẩm dựa trên productId
		int product = productService.getProductByIdfa(productId);

		// Kiểm tra xem sản phẩm đã được yêu thích hay chưa
		boolean isAlreadyFavourite = favouriteService.isProductFavourited(email, product);

		if (isAlreadyFavourite == true) {
			// Nếu sản phẩm đã được yêu thích, thêm thông báo vào Model
			model.addAttribute("message", "Sản phẩm đã được yêu thích.");
		} else {
			// Tạo một đối tượng Favourite
			Favourite favourite = new Favourite(0, product, email);
			// Thêm sản phẩm vào danh sách yêu thích của người dùng
			favouriteService.saveFavourite(favourite);
			// Thêm thông báo thành công vào Model
			model.addAttribute("message", "Sản phẩm đã được thêm vào danh sách yêu thích.");
		}

		// Chuyển hướng người dùng đến trang danh sách sản phẩm
		return "redirect:/product/list";
	}

	@PostMapping("/favourite/delete")
	public String deleteFromFavourites(@RequestParam("productId") int productId, Principal principal) {
		// Lấy thông tin người dùng hiện tại từ Principal
		String email = principal.getName();
		int product = productService.getProductByIdfa(productId);
		// Xoá sản phẩm khỏi danh sách yêu thích
		favouriteService.deleteFromFavourites(email, product);

		// Redirect hoặc trả về view hiển thị danh sách yêu thích đã cập nhật
		return "redirect:/profile";
	}

}
