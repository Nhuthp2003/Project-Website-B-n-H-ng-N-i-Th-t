package com.arphor.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arphor.entity.Category;
import com.arphor.entity.Parent;
import com.arphor.service.CategoryService;
import com.arphor.service.ParentService;

@Controller
public class SecurityController {
	
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private ParentService parentService;
	
	@RequestMapping("/account/login")
	public String loginForm(Model model) {
		model.addAttribute("pageTitle", "Account - Arphor");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/login";
	}
	
	@RequestMapping("/login")
	public String loginProcess(Model model) {
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/login";
	}
	
	@RequestMapping("/account/login=success")
	public String loginSuccess(Model model) {
		model.addAttribute("pageTitle", "Arphor");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/layout/home";
	}
	
	@RequestMapping("/account/login=error")
	public String loginError(Model model) {
		model.addAttribute("pageTitle", "Arphor");
		model.addAttribute("message", "Incorrect email or password.");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/login";
	}
	
	@RequestMapping("/account/logout")
	public String logoutForm(Model model) {
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/login";
	}
	
	@RequestMapping("/account/logout=success")
	public String logoffSuccess(Model model) {
		model.addAttribute("pageTitle", "Arphor");
		model.addAttribute("message", "Logged out successfully");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/login";
	}
	
	@RequestMapping("/account/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Access Denied!");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/login";
	}

	@RequestMapping("/account/reset")
	public String resetForm(Model model) {
		model.addAttribute("pageTitle", "Reset Account - Arphor");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/reset";
	}
	
	@RequestMapping("/account/token")
	public String tokenForm(Model model) {
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/token";
	}
}
