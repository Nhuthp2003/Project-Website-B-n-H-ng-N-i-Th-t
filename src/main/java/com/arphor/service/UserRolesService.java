package com.arphor.service;


import java.util.List;

import org.springframework.ui.Model;

import com.arphor.entity.RoleId;
import com.arphor.entity.UserRoles;

public interface UserRolesService {

	List<UserRoles> getAllUsers();
	UserRoles getUserByEmail(String email);
    void saveUser(UserRoles userRoles);
    void changePassword(String email, String newPassword);
	List<UserRoles> getUsersByRole(RoleId userRole);
	UserRoles getUserById(int userId);
	void deleteUser(int userId, boolean b);
	
	UserRoles updateUser(UserRoles user);
	void saveUser(Model model);
}
