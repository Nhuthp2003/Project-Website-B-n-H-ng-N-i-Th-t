package com.arphor.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arphor.dao.UserRolesDAO;
import com.arphor.entity.Category;
import com.arphor.entity.Parent;
import com.arphor.entity.UserRoles;
import com.arphor.service.CategoryService;
import com.arphor.service.OrderDetailService;
import com.arphor.service.OrderService;
import com.arphor.service.ParentService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService  orderService;
	@Autowired
	private OrderDetailService orderDEService;
	private final UserRolesDAO userRolesDAO;
	
	public OrderController(UserRolesDAO userRolesDAO) {
		this.userRolesDAO = userRolesDAO;
	}
	
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private ParentService parentService;
	
	@RequestMapping("/order/invoice")
	public String orderDetail(Model model) {
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/order/detail";
	}

	@RequestMapping("/order/checkout")
	public String orderCheckout(Model model, Principal principal) {
		model.addAttribute("pageTitle", "Shipping - Arphor");
		String email = principal.getName();
		UserRoles userRoles = userRolesDAO.findByEmail(email);
		if (userRoles != null) {
            // Lấy firstName và lastName từ người dùng
            String firstName = userRoles.getFirstName();
            String lastName = userRoles.getLastName();
            String phone = userRoles.getPhone();
            String address = userRoles.getAddress();
            Boolean gender = userRoles.getGender();
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
        }
		//Menu Category
    	List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/order/checkout";
	}
	
	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") int id, Model model) {
		model.addAttribute("pageTitle", "Invoice - Arphor");
		model.addAttribute("order", orderService.findById(id));
		model.addAttribute("orderde", orderDEService.findByOrderID(id));
		//Menu Category
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
        List<Parent> parents = parentService.getAllParents();
        model.addAttribute("parents", parents);
		return "/order/detail";
	}
}
