package com.arphor.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.arphor.dao.UserRolesDAO;
import com.arphor.entity.Order;
import com.arphor.entity.RoleId;
import com.arphor.entity.UserRoles;
import com.arphor.service.OrderDetailService;
import com.arphor.service.OrderService;
import com.arphor.service.RoleIdService;
import com.arphor.service.UserRolesService;

@Controller
public class DashboardController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserRolesService users;
	
	@Autowired
	private final UserRolesDAO userRolesDAO;
	OrderDetailService orderDEService;
	private final RoleIdService roleIdService;
	private final UserRolesService userRolesService;
	
	public DashboardController(UserRolesDAO userRolesDAO, UserRolesService userRolesService,
			RoleIdService roleIdService, OrderDetailService orderDetailService) {
		this.userRolesDAO = userRolesDAO;
		this.roleIdService = roleIdService;
		this.userRolesService = userRolesService;
	}

	@GetMapping("/dashboard")
	public String index(Model model, Principal principal) {
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
            model.addAttribute("imageName", imageName);
        }
		return "/admin/index";
	}
	
	@GetMapping("/dashboard/home")
	public String dashboard() {
		return "admin/dashboard";
	}
	
	@GetMapping("/dashboard/analytic")
	public String analytics() {
		return "admin/analytics";
	}
	
	@GetMapping("/dashboard/customer")
	public String customers(Model model) {
	    // Lấy vai trò của người dùng
	    RoleId userRole = roleIdService.getRoleById(2); // Giả sử roleId của người dùng là 2
	    
	    // Lấy danh sách người dùng dựa trên vai trò
	    List<UserRoles> users = (List<UserRoles>) userRolesService.getUsersByRole(userRole);
	    
	    // Lọc danh sách người dùng dựa trên điều kiện user.getDeleted() == 0
	    List<UserRoles> filteredUsers = users.stream()
	                                          .filter(user -> user.getDeleted() == 0)
	                                          .collect(Collectors.toList());
	    
	    model.addAttribute("users", filteredUsers);
	    
	    return "admin/customers";
	}

	
	@PostMapping("/dashboard/customers/delete/{userId}")
	public String deleteUser(@PathVariable("userId") int userId) {
	    userRolesService.deleteUser(userId, true);
	    return "redirect:/dashboard#!/customer"; 
	}
	
	@GetMapping("/dashboard/customer/{userId}")
	public String viewUserDetails(@PathVariable("userId") int userId, Model model) {
	    // Lấy thông tin chi tiết người dùng từ userId
	    UserRoles user = userRolesService.getUserById(userId);
	    
	    // Truyền thông tin người dùng vào model
	    model.addAttribute("user", user);
	   
	    // Trả về view hiển thị chi tiết người dùng
	    return "admin/infouser"; 
	}
	
	@GetMapping("/dashboard/order")
	public String order(Model model) {
		List<Order> orders = orderService.getAllOrders();
		model.addAttribute("orders", orders);
		return "admin/order";
	}
	
	@GetMapping("/dashboard/product")
	public String product() {
		return "admin/product";
	}
	
	@GetMapping("/dashboard/category")
	public String category() {
		return "admin/category";
	}
}
