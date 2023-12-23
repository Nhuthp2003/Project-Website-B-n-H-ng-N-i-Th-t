package com.arphor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arphor.entity.Category;
import com.arphor.entity.Parent;
import com.arphor.service.CategoryService;
import com.arphor.service.ParentService;
import com.arphor.service.TokenService;

@Controller
public class TokenController {
	
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private ParentService parentService;

	private TokenService tokenService;

	public TokenController(TokenService tokenService){
		this.tokenService = tokenService;
	}
	
	@GetMapping("/account/change")
	public String changeForm(Model model) {
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/change";
	}

	@PostMapping("/token")
	public String validateToken(@RequestParam("tokenValue") String tokenValue, Model model) {
	    boolean isValidToken = tokenService.validateToken(tokenValue);
	    if (isValidToken) {
	        // Mã token hợp lệ, cho phép đi tiếp
	        // Gọi các logic hoặc trả về view tương ứng ở đây
	        return "redirect:/account/change";
	    } else {
	        // Mã token không hợp lệ
	        if (tokenService.isTokenExpired(tokenValue)) {
	            // Mã token đã hết thời gian, yêu cầu gửi lại xác nhận
	            model.addAttribute("message", "Token has expired. Please request a new one.");
	            return "/security/token";
	        } else {
	            // Mã token sai, yêu cầu nhập lại
	            model.addAttribute("message", "Invalid token. Please try again.");
	            return "/security/token";
	        }
	    }
	}
}
