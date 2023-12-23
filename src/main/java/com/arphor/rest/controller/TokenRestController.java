package com.arphor.rest.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.arphor.dao.UserRolesDAO;
import com.arphor.entity.Token;
import com.arphor.entity.UserRoles;
import com.arphor.service.TokenService;

@RestController
public class TokenRestController {
	
	private final UserRolesDAO userRolesDAO;
	
    public TokenRestController(UserRolesDAO userRolesDAO) {
        this.userRolesDAO = userRolesDAO;
    }

	@Autowired
    private TokenService tokenService;

	@PostMapping("/generateToken")
	public ModelAndView generateToken(Model model, @RequestParam("email") String email, HttpSession session) {
	    UserRoles userRoles = userRolesDAO.findByEmail(email);

	    if (userRoles != null) {
	    	session.setAttribute("email", email);
	        Token token = tokenService.generateToken(email);
	        ModelAndView modelAndView = new ModelAndView("redirect:/account/token");
	        modelAndView.addObject("token", token);
	        return modelAndView;
	    } else {
	        String message = "No account found with that email.";
	        model.addAttribute("message", message);
	        ModelAndView modelAndView = new ModelAndView("/security/reset");
	        return modelAndView;
	    }
	}
}
