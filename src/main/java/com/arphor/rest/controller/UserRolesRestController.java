package com.arphor.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arphor.entity.Authority;
import com.arphor.entity.RoleId;
import com.arphor.entity.UserRoles;
import com.arphor.service.AuthorityService;
import com.arphor.service.RoleIdService;
import com.arphor.service.UserRolesService;

@RestController
@RequestMapping("/api/users")
public class UserRolesRestController {

	private final UserRolesService userRolesService;
	private final RoleIdService roleIdService;
	private final AuthorityService authorityService;

	public UserRolesRestController(UserRolesService userRolesService, RoleIdService roleIdService,
			AuthorityService authorityService) {
		this.userRolesService = userRolesService;
		this.roleIdService = roleIdService;
		this.authorityService = authorityService;
	}

	@GetMapping("/all")
	public List<UserRoles> getAllUsers() {
		return userRolesService.getAllUsers();
	}

	@GetMapping("/{email}")
	public ResponseEntity<UserRoles> getUserByEmail(@PathVariable String email) {
		UserRoles user = userRolesService.getUserByEmail(email);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRoles userRoles) {
		// Kiểm tra xem user đã tồn tại trong hệ thống chưa
		UserRoles existingUser = userRolesService.getUserByEmail(userRoles.getEmail());
		if (existingUser != null) {
			return ResponseEntity.badRequest().build();
		}

		// Lưu user mới
		userRolesService.saveUser(userRoles);

		// Tạo authority cho user
		Authority authority = new Authority();
		authority.setEmail(userRoles.getEmail());
		authority.setRole(userRoles.getRole());
		authorityService.saveAuthority(authority);

		return ResponseEntity.ok().build();

	}

	@GetMapping("/roles/{roleId}")
	public ResponseEntity<RoleId> getRoleById(@PathVariable int roleId) {
		RoleId role = roleIdService.getRoleById(roleId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PutMapping("{email}")
	public ResponseEntity<UserRoles> updateUser(@PathVariable("email") String email, @RequestBody UserRoles user) {
		UserRoles updateURoles = userRolesService.updateUser(user);
		
		return new ResponseEntity<>(updateURoles, HttpStatus.OK);
	}
	
}