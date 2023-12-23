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
public class CartController {
	
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private ParentService parentService;

	@RequestMapping("/cart/view")
	public String cartForm(Model model) {
		model.addAttribute("pageTitle", "Your Shopping Cart - Arphor");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/cart/view";
	}
}