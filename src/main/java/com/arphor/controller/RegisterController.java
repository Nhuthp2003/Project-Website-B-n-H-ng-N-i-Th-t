package com.arphor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.arphor.dao.UserRolesDAO;
import com.arphor.entity.Category;
import com.arphor.entity.Parent;
import com.arphor.entity.UserRoles;
import com.arphor.model.UserRolersDto;
import com.arphor.service.CategoryService;
import com.arphor.service.ParentService;
import com.arphor.service.RoleIdService;
import com.arphor.service.UserRolesService;

@Controller
public class RegisterController {
	
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private ParentService parentService;

	@Autowired
	UserRolesService userRolesService;
	
	@Autowired
	UserRolesDAO userRolesDAO;
	
	@Autowired
	RoleIdService roleIdService; 

	@RequestMapping("/account/register")
	public String add(Model model) {
		UserRolersDto dto = new UserRolersDto();
		dto.setIsEdit(false);
		model.addAttribute("userRoles", dto);
		model.addAttribute("pageTitle", "Create Account - Arphor");
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/security/register";
	}

	@PostMapping("/register/save")
	public ModelAndView saveOrUpdate(ModelMap model,
			@ModelAttribute("userRoles") UserRolersDto dto, BindingResult result) {
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
        
		UserRoles entityCheck = new UserRoles();
		entityCheck=userRolesService.getUserByEmail(dto.getEmail());
		
		if (entityCheck!=null) {
			model.addAttribute("message", "This email address is already associated with an account. If this account is yours, you can <a class=\"text-dark\" href=\"/account/reset\">reset your password</a>.");
			return new ModelAndView("/security/register");
		}
		UserRoles user = new UserRoles();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRole(roleIdService.getRoleByRoleName("User"));
		userRolesService.saveUser(user);
		model.addAttribute("message", "Account created successfully!");
		return new ModelAndView("forward:/account/login", model);
	}

}
