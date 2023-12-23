package com.arphor.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arphor.service.UserRolesService;

@Controller
public class ChangeController {
	
	@Autowired
	private UserRolesService userRolesService;
	@PostMapping("/change")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("message", "New password and confirm password do not match.");
            return "/security/change";
        }
        userRolesService.changePassword(email, newPassword);
        return "redirect:/account/login"; 
    }
}
